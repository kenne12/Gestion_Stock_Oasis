package controllers.utilisateur;

import entities.Demande;
import entities.Magasin;
import entities.Menu;
import entities.Personnel;
import entities.Privilege;
import entities.Utilisateur;
import entities.Utilisateurmagasin;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.ShaHash;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class UtilisateurController extends AbstractUtilisateurController
        implements Serializable {

    @PostConstruct
    private void init() {
        this.utilisateur = new Utilisateur();
        this.templates.clear();
        for (String temp : SessionMBean.getParametrage().getCheminTemplate().split(";")) {
            this.templates.add(temp);
        }
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(Long.valueOf(2L))) {
                signalError("acces_refuse");
                return;
            }

            this.mode = "Create";

            this.utilisateur = new Utilisateur();
            this.utilisateur.setActif(true);
            this.utilisateur.setPhoto("user_avatar.png");

            this.utilisateur.setTemplate((String) this.templates.get(1));
            this.utilisateur.setTheme("hot-sneaks");
            this.personnel = new Personnel();

            this.personnels = this.personnelFacadeLocal.findAllRange();
            List<Utilisateur> utilisateurs = this.utilisateurFacadeLocal.findAll();
            List listPersonnel = new ArrayList();
            if (!utilisateurs.isEmpty()) {
                for (Utilisateur u : utilisateurs) {
                    listPersonnel.add(u.getIdpersonnel());
                }
            }
            this.personnels.removeAll(listPersonnel);

            this.magasins = this.magasinFacadeLocal.findAllRangeMcIsFalse();
            this.selectedMagasins.clear();
            RequestContext.getCurrentInstance().execute("PF('UtilisateurCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void prepareEdit() {
        try {
            if (!Utilitaires.isAccess(3L)) {
                signalError("acces_refuse");
                return;
            }

            if (this.utilisateur == null) {
                signalError("not_row_selected");
                return;
            }

            this.mode = "Edit";
            this.personnel = this.utilisateur.getIdpersonnel();
            this.personnels.add(this.personnel);

            List<Utilisateurmagasin> listUm = this.utilisateurmagasinFacadeLocal.findByIdutilisateur(this.utilisateur.getIdutilisateur());
            if (!listUm.isEmpty()) {
                for (Utilisateurmagasin um : listUm) {
                    this.selectedMagasins.add(um.getIdmagasin());
                }
            }

            this.magasins = this.magasinFacadeLocal.findAllRangeMcIsFalse();
            this.magasins.remove(this.personnel.getIdmagasin());

            RequestContext.getCurrentInstance().execute("PF('UtilisateurCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void updateMagasin() {
        try {
            if (this.personnel != null) {
                this.magasins.remove(this.personnel.getIdmagasin());
                List localList = this.magasins;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void savePrivile(Menu menu) {
        Privilege p = new Privilege();
        p.setIdprivilege(this.privilegeFacadeLocal.nextVal());
        p.setIdutilisateur(this.utilisateur);
        p.setIdmenu(menu);
        this.privilegeFacadeLocal.create(p);
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {
                this.utilisateur.setIdutilisateur(this.utilisateurFacadeLocal.nextVal());
                this.utilisateur.setPassword(new ShaHash().hash(this.utilisateur.getPassword()));
                this.utilisateur.setActif(true);
                this.utilisateur.setIdpersonnel(this.personnel);
                this.utilisateurFacadeLocal.create(this.utilisateur);

                for (Magasin m : this.selectedMagasins) {
                    Utilisateurmagasin um = new Utilisateurmagasin();
                    um.setIdutilisateurmagasin(this.utilisateurmagasinFacadeLocal.nextVal());
                    um.setIdmagasin(m);
                    um.setIdutilisateur(this.utilisateur);
                    this.utilisateurmagasinFacadeLocal.create(um);
                }

                savePrivile(new Menu(30));
                savePrivile(new Menu(31));
                savePrivile(new Menu(32));
                savePrivile(new Menu(33));

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de l'utilisateur : " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                this.utilisateur = new Utilisateur();
                this.magasins.clear();
                this.selectedMagasins.clear();
                this.modifier = this.detail = this.supprimer = true;
                RequestContext.getCurrentInstance().execute("PF('UtilisateurCreerDialog').hide()");
                signalSuccess();
            } else if (this.utilisateur != null) {

                this.utilisateurFacadeLocal.edit(this.utilisateur);
                for (Magasin m : this.selectedMagasins) {
                    Utilisateurmagasin um = this.utilisateurmagasinFacadeLocal.findByIdutilisateurIdmagasin(this.utilisateur.getIdutilisateur().intValue(), m.getIdmagasin().intValue());
                    if (um == null) {
                        um = new Utilisateurmagasin();
                        um.setIdutilisateurmagasin(this.utilisateurmagasinFacadeLocal.nextVal());
                        um.setIdutilisateur(this.utilisateur);
                        um.setIdmagasin(m);
                        this.utilisateurmagasinFacadeLocal.create(um);
                    }
                }
                this.modifier = (this.detail = this.supprimer = Boolean.valueOf(true));
                RequestContext.getCurrentInstance().execute("PF('UtilisateurCreerDialog').hide()");
                signalSuccess();
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void reinitialiseAccount(Utilisateur utilisateur) {
        try {
            if (!Utilitaires.isAccess(47L)) {
                signalError("acces_refuse");
                return;
            }
            utilisateur.setPassword(new ShaHash().hash("123456"));
            this.utilisateurFacadeLocal.edit(utilisateur);
            Utilitaires.saveOperation(this.mouchardFacadeLocal, "Réinitilisation du compte utilisateur de -> " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
            signalSuccess();
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void delete() {
        try {
            if (this.utilisateur != null) {
                if (!Utilitaires.isAccess(4L)) {
                    signalError("acces_refuse");
                    return;

                }
                List<Demande> listDemande = demandeFacadeLocal.findAllRange(utilisateur.getIdpersonnel().getIdpersonnel());
                if (listDemande.isEmpty()) {
                    mouchardFacadeLocal.deleteByIdutilisateur(utilisateur.getIdutilisateur());
                    utilisateurmagasinFacadeLocal.deleteByIdutilisateur(utilisateur.getIdutilisateur());
                    privilegeFacadeLocal.deleteByIdUtilisateur(utilisateur.getIdutilisateur());
                    this.utilisateurFacadeLocal.remove(this.utilisateur);
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de l'utilisateur : " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                    signalSuccess();
                } else {
                    signalError("cet_utilisateur_associe_a_demandes");
                }
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void changeStatus(Utilisateur utilisateur, String mode) {
        try {
            /* 228 */ if (mode.equals("activer")) {
                /* 230 */ if (!Utilitaires.isAccess(Long.valueOf(6L))) {
                    /* 231 */ signalError("acces_refuse");
                    /* 232 */ return;
                }

                /* 235 */ utilisateur.setActif(Boolean.valueOf(true));
                /* 236 */ this.utilisateurFacadeLocal.edit(utilisateur);
                /* 237 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Activation du compte de l'utilisateur : " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
                /* 238 */ JsfUtil.addSuccessMessage("Operation réussie");
            } else {
                /* 241 */ if (!Utilitaires.isAccess(Long.valueOf(7L))) {
                    /* 242 */ signalError("acces_refuse");
                    /* 243 */ return;
                }

                /* 246 */ utilisateur.setActif(Boolean.valueOf(false));
                /* 247 */ this.utilisateurFacadeLocal.edit(utilisateur);
                /* 248 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Désativation du compte de l'utilisateur : " + utilisateur.getNom() + " " + utilisateur.getPrenom(), SessionMBean.getUserAccount());
                /* 249 */ JsfUtil.addSuccessMessage("Operation réussie");
            }
        } catch (Exception e) {
            /* 252 */ e.printStackTrace();
        }
    }

    public void signalError(String chaine) {
        /* 257 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 258 */ this.routine.feedBack("information", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(chaine));
        /* 259 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalSuccess() {
        /* 263 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 264 */ this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        /* 265 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalException(Exception e) {
        /* 269 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 270 */ this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
        /* 271 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
