/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.structure;

import entities.Magasin;
import entities.Menu;
import entities.Parametrage;
import entities.Personnel;
import entities.Privilege;
import entities.Qualite;
import entities.Utilisateur;
import entities.Utilisateurmagasin;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
//import javax.transaction.Transactional;
import org.primefaces.context.RequestContext;
import utils.SessionMBean;
import utils.ShaHash;
import utils.Utilitaires;

/**
 *
 * @author USER
 */
@ManagedBean
@ViewScoped
public class StructureController extends AbstractStructureController implements Serializable {

    /**
     * Creates a new instance of StructureController
     */
    public StructureController() {
    }
    
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
            if (!Utilitaires.isAccess(1L)) {
                signalError("acces_refuse");
                return;
            }
            
            this.mode = "Create";
            
            this.setWindowHeight(95);
            this.setColumn(3);
            
            parametrage = new Parametrage();
            parametrage.setNomStructure("-");
            parametrage.setContact1("-");
            parametrage.setContact2("-");
            parametrage.setContact3("-");
            parametrage.setNumeroCompte("-");
            parametrage.setDescriptif("-");
            parametrage.setBoitePostale("BP : ");
            parametrage.setLocalisation("-");
            
            magasin = new Magasin();
            magasin.setCentral(true);
            magasin.setNom("SIEGE SOCIAL");
            magasin.setCode("SC");
            
            personnel = new Personnel();
            personnel.setNiveauscolaire("-");
            personnel.setNom("PDG");
            personnel.setPrenom("-");
            personnel.setMatricule("P1");
            personnel.setDateembauche(Date.from(Instant.now()));
            personnel.setContact("-");
            personnel.setAddresse("-");
            personnel.setIdqualite(new Qualite());
            
            utilisateur = new Utilisateur();
            utilisateur.setActif(true);
            utilisateur.setPhoto("user_avatar.png");
            utilisateur.setTemplate((String) this.templates.get(0));
            utilisateur.setTheme("hot-sneaks");
            utilisateur.setLogin("login_s" + parametrageFacadeLocal.nextVal());
            
            RequestContext.getCurrentInstance().execute("PF('StructureCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void prepareEdit() {
        try {
            if (!Utilitaires.isAccess(1L)) {
                signalError("acces_refuse");
                return;
            }
            
            if (this.parametrage == null) {
                signalError("not_row_selected");
                return;
            }
            
            this.setWindowHeight(25);
            this.setColumn(12);
            this.mode = "Edit";
            RequestContext.getCurrentInstance().execute("PF('StructureCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    private void savePrivile(Menu menu) {
        Privilege p = new Privilege();
        p.setIdprivilege(this.privilegeFacadeLocal.nextVal());
        p.setIdutilisateur(this.utilisateur);
        p.setIdmenu(menu);
        this.privilegeFacadeLocal.create(p);
    }
    
    //@Transactional
    public void create() {
        try {
            if (this.mode.equals("Create")) {
                
                Utilisateur user = utilisateurFacadeLocal.login(utilisateur.getLogin());
                if (user != null) {
                    signalError(routine.localizeMessage("login_existant"));
                    return;
                }
                
                parametrage.setId(parametrageFacadeLocal.nextVal());
                parametrage.setBanque("-");
                parametrage.setFax("-");
                parametrage.setCheminTemplate("/SystemTemplate/template.xhtml");
                parametrage.setRcm("-");
                parametrage.setNoContrib("-");
                parametrage.setRepertoireLogo("-");
                parametrage.setRepertoireSauvegardre("-");
                parametrage.setLogo("-");
                parametrageFacadeLocal.create(parametrage);
                
                magasin.setIdmagasin(magasinFacadeLocal.nextVal());
                magasin.setParametrage(parametrage);
                magasin.setCentral(true);
                magasinFacadeLocal.create(magasin);
                
                personnel.setIdpersonnel(personnelFacadeLocal.nextVal());
                personnel.setIdmagasin(magasin);
                personnel.setIdservice(magasin.getIdmagasin());
                personnelFacadeLocal.create(personnel);
                
                utilisateur.setIdutilisateur(utilisateurFacadeLocal.nextVal());
                utilisateur.setPassword(new ShaHash().hash(this.utilisateur.getPassword()));
                utilisateur.setActif(true);
                utilisateur.setIdpersonnel(this.personnel);
                utilisateur.setDatecreation(Date.from(Instant.now()));
                utilisateur.setTemplate("/SystemTemplate/template.xhtml");
                utilisateurFacadeLocal.create(this.utilisateur);
                
                Utilisateurmagasin u = new Utilisateurmagasin();
                u.setIdutilisateurmagasin(utilisateurmagasinFacadeLocal.nextVal());
                u.setIdmagasin(magasin);
                u.setIdutilisateur(utilisateur);
                utilisateurmagasinFacadeLocal.create(u);
                
                savePrivile(new Menu(1));
                savePrivile(new Menu(30));
                savePrivile(new Menu(31));
                savePrivile(new Menu(32));
                savePrivile(new Menu(33));
                
                this.utilisateur = new Utilisateur();
                this.modifier = this.supprimer = true;
                RequestContext.getCurrentInstance().execute("PF('StructureCreerDialog').hide()");
                signalSuccess();
            } else if (this.parametrage != null) {
                this.parametrageFacadeLocal.edit(this.parametrage);
                this.modifier = this.supprimer = true;
                RequestContext.getCurrentInstance().execute("PF('StructureCreerDialog').hide()");
                signalSuccess();
            } else {
                signalError("not_row_selected");
            }
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
                //mouchardFacadeLocal.deleteByIdutilisateur(utilisateur.getIdutilisateur());
                utilisateurmagasinFacadeLocal.deleteByIdutilisateur(utilisateur.getIdutilisateur());
                privilegeFacadeLocal.deleteByIdUtilisateur(utilisateur.getIdutilisateur());
                this.utilisateurFacadeLocal.remove(this.utilisateur);
                signalSuccess();
                
            } else {
                signalError("not_row_selected");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }
    
    public void signalError(String chaine) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.feedBack("information", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(chaine));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
    
    public void signalSuccess() {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
    
    public void signalException(Exception e) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
    
}
