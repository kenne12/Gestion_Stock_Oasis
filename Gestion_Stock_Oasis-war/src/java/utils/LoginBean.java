package utils;

import entities.Annee;
import entities.AnneeMois;
import entities.Journee;
import entities.Livraisonclient;
import entities.Magasin;
import entities.Menu;
import entities.Privilege;
import entities.Utilisateur;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import sessions.UtilisateurFacadeLocal;

@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean extends AbstractLoginBean implements Serializable {

    @EJB
    private UtilisateurFacadeLocal utilisateurFacadeLocal;
    private Utilisateur utilisateur = new Utilisateur();

    String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

    @PostConstruct
    public void init() {
        utilisateur.setTheme("bootstrap");
        lineModel = new LineChartModel();
    }

    public void login() {
        try {
            this.utilisateur = this.utilisateurFacadeLocal.login(this.utilisateur.getLogin(), new ShaHash().hash(this.utilisateur.getPassword()));
            if (this.utilisateur != null) {
                if (this.utilisateur.getActif()) {
                    this.showSessionPanel = true;
                    HttpSession session = SessionMBean.getSession();
                    session.setAttribute("compte", this.utilisateur);
                    session.setAttribute("session", false);

                    this.param = this.parametrageFacadeLocal.find(utilisateur.getIdpersonnel().getIdmagasin().getParametrage().getId());

                    session.setAttribute("parametre", this.param);

                    this.magasins = Utilitaires.returMagasinByUser(magasinFacadeLocal, utilisateurmagasinFacadeLocal, utilisateur.getIdpersonnel());
                    if (Objects.equals(this.magasins.size(), 1)) {
                        this.magasin = this.magasins.get(0);
                        session.setAttribute("magasin", magasin);
                        List<Magasin> list = new ArrayList<>();
                        list.add(magasin);
                        session.setAttribute("magasins", list);
                    }

                    Annee an = anneeFacadeLocal.findOneDefault();
                    if (an != null) {
                        this.annee = an;
                        anneeMoises = anneeMoisFacadeLocal.findByAnneeEtat(an.getIdannee(), true);
                    }

                    filterDate(date);
                    this.annees = anneeFacadeLocal.findByEtat(true);
                    session.setAttribute("annees", annees);
                    redirect("/index.html");
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

    private void intDefaultMonth(Annee annee, HttpSession session) {
        AnneeMois defaultMonth = anneeMoisFacadeLocal.findDefaultMonthByIdannee(annee.getIdannee());
        if (defaultMonth != null) {
            session.setAttribute("default_mois", defaultMonth);
        }
    }

    public void initPrivilegeMap() {
        HttpSession session = SessionMBean.getSession();
        List allAccess = new ArrayList();
        for (Menu m : this.menuFacadeLocal.findAll()) {
            String[] menus = m.getRessource().split(";");
            for (String temp : menus) {
                if (!allAccess.contains(temp)) {
                    allAccess.add(temp);
                }
            }
        }
        session.setAttribute("allAccess", allAccess);
    }

    public void initUserPrivilege(int idUtilisateur, HttpSession session) {
        List<Privilege> privilegesTemp = this.privilegeFacadeLocal.findByUser(idUtilisateur);
        List accesses = new ArrayList();
        List access = new ArrayList();

        for (Privilege p : privilegesTemp) {
            accesses.add(Long.valueOf(p.getIdmenu().getIdmenu()));
            String[] menus = p.getIdmenu().getRessource().split(";");
            for (String temp : menus) {
                if (!access.contains(temp)) {
                    access.add(temp);
                }
            }
        }
        session.setAttribute("accesses", accesses);
        session.setAttribute("access", access);
    }

    private void filterDate(Date date) {
        for (AnneeMois a : this.anneeMoises) {
            try {
                if ((a.getDateDebut().equals(date) || a.getDateDebut().before(date)) && (a.getDateFin().equals(date) || a.getDateFin().after(date))) {
                    this.anneeMois = a;
                    break;
                }
            } catch (Exception exception) {
            }
        }
    }

    public void finalizeSession() {
        if (magasin.getIdmagasin().equals(0)) {
            JsfUtil.addWarningMessage("Sélectionner le Dépot ou Magasin");
            return;
        }

        if (annee.getIdannee().equals(0)) {
            JsfUtil.addWarningMessage("Sélectionner l'exercice");
            return;
        }

        if (anneeMois.getIdAnneeMois().equals(0)) {
            JsfUtil.addWarningMessage("Sélectionner le mois");
            return;
        }

        anneeMois = anneeMoisFacadeLocal.find(anneeMois.getIdAnneeMois());
        if (this.date.equals(this.anneeMois.getDateDebut()) || (this.date.after(this.anneeMois.getDateDebut()) && this.date.equals(this.anneeMois.getDateFin())) || this.date.before(this.anneeMois.getDateFin())) {

            HttpSession session = SessionMBean.getSession();
            annee = anneeFacadeLocal.find(annee.getIdannee());

            session.setAttribute("annee", annee);
            session.setAttribute("mois", anneeMois);
            session.setAttribute("date", date);

            if (magasins.size() > 1) {
                session.setAttribute("magasins", magasins);
                magasin = magasinFacadeLocal.find(magasin.getIdmagasin());
                session.setAttribute("magasin", magasin);
            }
            journee = initJour(magasin);
            initPrivilegeMap();
            this.intDefaultMonth(annee, session);
            this.initUserPrivilege(utilisateur.getIdutilisateur(), session);
            this.showSessionPanel = false;
            Utilitaires.saveOperation(this.mouchardFacadeLocal, "Connexion", this.utilisateur);

            redirect("/index.html");
        }
    }

    private void redirect(String link) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(this.sc + link);
        } catch (Exception e) {
        }
    }

    private Journee initJour(Magasin magasin) {
        Journee journee = journeeFacadeLocal.findByIdMagasinDate(magasin.getIdmagasin(), new Date());
        if (journee == null) {
            journee = new Journee();
            journee.setIdjournee(journeeFacadeLocal.nextVal());
            journee.setMagasin(magasin);
            journee.setHeureOuverture(Date.from(Instant.now()));
            journee.setDateJour(Date.from(Instant.now()));
            journee.setUtilisateurOuverture(utilisateur);
            journee.setOuverte(true);
            journee.setFermee(false);
            journeeFacadeLocal.create(journee);
        }
        return journee;
    }

    public void updateCalendar() {
        try {
            if (this.anneeMois.getIdAnneeMois() != 0) {
                this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
                return;
            }
            JsfUtil.addErrorMessage("Veuillez sélectionner une année");
        } catch (Exception e) {
            e.printStackTrace();
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
            if (Utilitaires.isAccess(40L)) {
                livraisonclients = livraisonclientFacadeLocal.findByIdmagasinAndDate(SessionMBean.getMagasin().getIdmagasin(), date);
                RequestContext.getCurrentInstance().execute("PF('FermettureCreerDialog').show()");
                return;
            }
            this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("acces_refuse"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void deconnexion() {
        Utilisateur user = SessionMBean.getUserAccount();
        Utilitaires.saveOperation(this.mouchardFacadeLocal, "Déconnexion", user);
        try {
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();

            UtilitaireSession.getInstance().setuser(null);
            FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/login.html");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void change_session() {
        try {
            this.showSessionPanel = true;
            String sc = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(sc + "/index.html?faces-redirect=true");
        } catch (IOException ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void update_theme() {
        try {
            this.utilisateurFacadeLocal.edit(this.utilisateur);
        } catch (Exception e) {
            e.printStackTrace();
            this.utilisateur.setTheme("bootstrap");
            this.utilisateurFacadeLocal.edit(this.utilisateur);
        }
    }

    public void hibbernate() {
        try {
            this.showHibernatePanel = true;
            this.hibernatePassword = "";
        } catch (Exception e) {
            e.getMessage();
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

    private void createLineModels() {
        this.lineModel = initLinearModel();
        this.lineModel.setTitle("Courbe évolutive des ventes et approvisionnement");
        this.lineModel.setLegendPosition("e");
        this.lineModel.getAxes().put(AxisType.X, new CategoryAxis("Mois"));
        this.lineModel.getAxes().put(AxisType.Y, new CategoryAxis("Montant"));
        Axis yAxis = this.lineModel.getAxis(AxisType.Y);
        yAxis.setMin(0);
    }

    private LineChartModel initLinearModel() {
        try {
            LineChartModel model = new LineChartModel();
            DateFormat sdf = new SimpleDateFormat("MM-yyyy");
            for (Magasin m : magasins) {
                LineChartSeries series1 = new LineChartSeries();
                series1.setLabel(m.getNom());

                for (Journee j : journeeFacadeLocal.findByTwoDates(SessionMBean.getMois().getDateDebut(), SessionMBean.getMois().getDateFin())) {
                    Integer somme = 0;
                    List<Livraisonclient> livraisonclients = livraisonclientFacadeLocal.findByIdmagasinAndDate(m.getIdmagasin(), j.getDateJour());
                    for (Livraisonclient l : livraisonclients) {
                        somme += (int) l.getMontantTtc();
                    }
                    series1.set(sdf.format(j.getDateJour()), somme);
                }
                model.addSeries((ChartSeries) series1);
            }
            return model;
        } catch (Exception e) {
            e.printStackTrace();
            return new LineChartModel();
        }
    }

    public String findPersonnelFermetture() {
        String result = "";
        try {
            if (this.journee.getUtilisateurFermetture() != null) {
                Utilisateur u = this.utilisateurFacadeLocal.find(this.journee.getUtilisateurFermetture().getIdutilisateur());
                if (u != null && u.getIdpersonnel() != null) {
                    result = "" + u.getIdpersonnel().getNom() + " " + u.getIdpersonnel().getPrenom();
                }
            }
        } catch (Exception e) {
            result = "";
        }
        return result;
    }

    public void calculTotal() {

    }

    public void calculMontantRegle() {

    }

    public void calculMontantReste() {

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
