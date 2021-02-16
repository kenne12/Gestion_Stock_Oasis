package utils;

import entities.Menu;
import entities.Mouchard;
import entities.Parametrage;
import entities.Privilege;
import entities.Utilisateur;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import sessions.UtilisateurFacadeLocal;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean extends AbstractLoginBean
        implements Serializable {

    @EJB
    private UtilisateurFacadeLocal utilisateurFacadeLocal;
    /*  42 */    private Utilisateur utilisateur = new Utilisateur();
    private Utilisateur utilisateurConnected;
    /*  45 */    String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

    @PostConstruct
    public void init() {
        /*  49 */ this.utilisateur = new Utilisateur();
        /*  50 */ this.utilisateur.setTheme("bootstrap");
    }

    public void login() {
        try {
            this.utilisateur = this.utilisateurFacadeLocal.login(this.utilisateur.getLogin(), new ShaHash().hash(this.utilisateur.getPassword()));
            if (this.utilisateur != null) {
                if (this.utilisateur.getActif().booleanValue()) {
                    HttpSession session = SessionMBean.getSession();
                    session.setAttribute("compte", this.utilisateur);
                    session.setAttribute("session", Boolean.valueOf(false));

                    this.param = ((Parametrage) this.parametrageFacadeLocal.findAll().get(0));

                    session.setAttribute("parametre", this.param);

                    List<Privilege> privilegesTemp = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue());
                    List accesses = new ArrayList();
                    List access = new ArrayList();

                    for (Privilege p : privilegesTemp) {
                        accesses.add(Long.valueOf(p.getIdmenu().getIdmenu().longValue()));
                        String[] menus = p.getIdmenu().getRessource().split(";");
                        for (String temp : menus) {
                            if (!access.contains(temp)) {
                                access.add(temp);
                            }
                        }
                    }

                    /*  86 */ session.setAttribute("accesses", accesses);
                    /*  87 */ session.setAttribute("access", access);

                    /*  89 */ this.showSessionPanel = false;
                    /*  90 */ initSession();
                    /*  91 */ FacesContext.getCurrentInstance().getExternalContext().redirect(this.sc + "/index.html");
                } else {
                    JsfUtil.addWarningMessage("Compte bloqué ! contactez l'administrateur");
                }
            } else {
                this.utilisateur = new Utilisateur();
                JsfUtil.addErrorMessage("Login ou mot de passe incorrect");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            this.utilisateur = new Utilisateur();
            JsfUtil.addErrorMessage("Echec système");
        }
    }

    public void initSession() {
        try {
            /* 110 */ HttpSession session = SessionMBean.getSession();

            /* 112 */ List allAccess = new ArrayList();

            /* 114 */ for (Menu m : this.menuFacadeLocal.findAll()) {
                /* 115 */ String[] menus = m.getRessource().split(";");
                /* 116 */ for (String temp : menus) {
                    /* 117 */ if (!allAccess.contains(temp)) {
                        /* 118 */ allAccess.add(temp);
                    }
                }
            }

            session.setAttribute("allAccess", allAccess);

            Utilitaires.saveOperation(this.mouchardFacadeLocal, "Connexion", this.utilisateur);
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Echec");
        }
    }

    public void closeSession() {
        try {
            this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void updateFermetture() {
        try {
            /* 155 */ if (Utilitaires.isAccess(Long.valueOf(40L))) {
                /* 156 */ RequestContext.getCurrentInstance().execute("PF('FermettureCreerDialog').show()");
            } else {
                /* 158 */ this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("acces_refuse"));
                /* 159 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                /* 160 */ return;
            }
        } catch (Exception e) {
            /* 164 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            /* 165 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void deconnexion() {
        /* 171 */ this.traceur = new Mouchard();
        /* 172 */ Utilisateur user = SessionMBean.getUserAccount();
        /* 173 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Déconnexion", user);
        try {
            /* 175 */ FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            /* 176 */ FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            /* 177 */ String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

            /* 179 */ UtilitaireSession.getInstance().setuser(null);
            /* 180 */ FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/login.html");
        } catch (IOException ex) {
            /* 183 */ Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void change_session() {
        try {
            /* 190 */ this.showSessionPanel = true;
            /* 191 */ String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            /* 192 */ FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/index.html?faces-redirect=true");
        } catch (IOException ex) {
            /* 194 */ Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update_theme() {
        try {
            /* 200 */ this.utilisateurFacadeLocal.edit(this.utilisateur);
        } catch (Exception e) {
            /* 202 */ e.printStackTrace();
            /* 203 */ this.utilisateur.setTheme("bootstrap");
            /* 204 */ this.utilisateurFacadeLocal.edit(this.utilisateur);
        }
    }

    public void hibbernate() {
        try {
            /* 211 */ this.showHibernatePanel = true;
            /* 212 */ this.hibernatePassword = "";
        } catch (Exception e) {
            /* 214 */ e.getMessage();
        }
    }

    public void unHibbernate() {
        try {
            if (new ShaHash().hash(this.hibernatePassword).equals(SessionMBean.getUserAccount().getPassword())) {
                this.showHibernatePanel = false;
            } else {
                this.showHibernatePanel = true;
                JsfUtil.addErrorMessage(this.routine.localizeMessage("mot_passe_incorrect"));
            }
        } catch (Exception e) {
            e.getMessage();
            JsfUtil.addErrorMessage(this.routine.localizeMessage("erreur"));
        }
    }

    public Utilisateur getUserconnected() {
        this.utilisateurConnected = UtilitaireSession.getInstance().getuser();
        String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
        if (this.utilisateurConnected == null) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/login.html");
            } catch (IOException ex) {
                Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Uitlisateur déconnecté +++++++++++++++++++ ");
        }
        return this.utilisateurConnected;
    }

    public void setPriv() {
        watcher();
    }

    public static void watcher() {
        try {
            if (!FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("compte")) {
                String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
                FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/login.html");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateCompte() {
        try {
            if ((!this.utilisateur.getPassword().equals("")) || (!this.utilisateur.getPassword().equals(null))) {
                if (this.utilisateur.getPassword().equals(this.confirmPassword)) {
                    this.utilisateur.setPassword(new ShaHash().hash(this.confirmPassword));
                    this.utilisateurFacadeLocal.edit(this.utilisateur);
                    this.confirmPassword = "";
                    RequestContext.getCurrentInstance().execute("PF('Modifier_compteDialog').hide()");
                    this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
                    RequestContext.getCurrentInstance().execute("PF('NotifyDialog').show()");
                } else {
                    this.utilisateur = this.utilisateurFacadeLocal.find(this.utilisateur.getIdutilisateur());
                    this.confirmPassword = "";
                    this.routine.feedBack("avertissement", "/resources/tool_images/error.png", this.routine.localizeMessage("mot_de_passe_non_identic"));
                    RequestContext.getCurrentInstance().execute("PF('NotifyDialog').show()");
                }
            } else {
                this.utilisateur = this.utilisateurFacadeLocal.find(this.utilisateur.getIdutilisateur());
                this.confirmPassword = "";
                this.routine.feedBack("avertissement", "/resources/tool_images/error.png", this.routine.localizeMessage("saisir_mot_de_passe"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog').show()");
            }
        } catch (Exception e) {
            this.utilisateur = this.utilisateurFacadeLocal.find(this.utilisateur.getIdutilisateur());
            this.confirmPassword = "";
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog').show()");
        }
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Object getUser() {
        return this.utilisateur;
    }

    public void setUser(Object user) {
        this.utilisateur = ((Utilisateur) user);
    }
}

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     utils.LoginBean
 * JD-Core Version:    0.6.2
 */
