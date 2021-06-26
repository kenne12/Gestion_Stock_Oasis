package controllers.privilege;

import entities.Menu;
import entities.Privilege;
import entities.Utilisateur;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class PrivilegeController extends AbstractPrivilegeController implements  Serializable {

    @PostConstruct
    private void initAcces() {
        this.utilisateur = new Utilisateur();
    }

    public void prepareCreate() {
        try {
            if (Utilitaires.isAccess(5L)) {
                this.showPrivilegeCreateDialog = true;
                this.dualMenu.getSource().clear();
                this.dualMenu.getTarget().clear();
                this.utilisateur = new Utilisateur();
                return;
            }
            this.showPrivilegeCreateDialog = false;
            JsfUtil.addErrorMessage("Vous n'avez pas le droit d'attribuer les privilèges aux utilisateurs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAccess(Utilisateur utilisateur) {
        try {
            this.utilisateur = utilisateur;
            this.privileges = this.privilegeFacadeLocal.findByUser(utilisateur.getIdutilisateur());
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Echec");
        }
    }

    public void handleUserChange() {
        this.dualMenu.getSource().clear();
        this.dualMenu.getTarget().clear();
        try {
            if (this.utilisateur.getIdutilisateur() != null) {
                this.utilisateur = this.utilisateurFacadeLocal.find(this.utilisateur.getIdutilisateur());

                List<Privilege> privilegeTemp = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur());
                if (privilegeTemp.isEmpty()) {
                    this.dualMenu.setSource(this.menuFacadeLocal.findAllRangeEtatIsTrue());
                } else {
                    List menusTarget = new ArrayList();

                    for (Privilege p : privilegeTemp) {
                        menusTarget.add(p.getIdmenu());
                    }

                    this.dualMenu.getTarget().addAll(menusTarget);
                    this.dualMenu.getSource().addAll(this.menuFacadeLocal.findAllRangeEtatIsTrue());
                    this.dualMenu.getSource().removeAll(menusTarget);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            if (this.utilisateur.getIdutilisateur() != null) {
                this.utilisateur = this.utilisateurFacadeLocal.find(this.utilisateur.getIdutilisateur());

                for (Menu m : dualMenu.getSource()) {
                    Privilege p = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue(), m.getIdmenu().intValue());
                    if (p != null) {
                        this.privilegeFacadeLocal.remove(p);
                        Utilitaires.saveOperation(this.mouchardFacadeLocal, "RETRAIT DU PRIVILEGE -> " + m.getNom() + " à l'utilisateur -> " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                    }
                }

                Boolean flag = (false);
                for (Menu m : this.dualMenu.getTarget()) {
                    if (!flag) {
                        if (m.getIdmenu() == 1) {
                            flag = (true);
                            Privilege p = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue(), 1);
                            if (p == null) {
                                p = new Privilege();
                                p.setIdprivilege(this.privilegeFacadeLocal.nextVal());
                                p.setIdmenu(m);
                                p.setIdutilisateur(this.utilisateur);
                                this.privilegeFacadeLocal.create(p);
                                Utilitaires.saveOperation(this.mouchardFacadeLocal, "ATTRIBUTION DU PRIVILEGE : SUPER ADMINISTRATEUR à l'utilisateur -> " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                                break;
                            }
                            JsfUtil.addSuccessMessage("Vous disposez dejà du privilège SUPER ADMINISTRATEUR");
                            break;
                        }

                        Privilege p = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue(), m.getIdmenu().intValue());
                        if (p == null) {
                            p = new Privilege();
                            p.setIdprivilege(this.privilegeFacadeLocal.nextVal());
                            p.setIdmenu(m);
                            p.setIdutilisateur(this.utilisateur);
                            this.privilegeFacadeLocal.create(p);
                            Utilitaires.saveOperation(this.mouchardFacadeLocal, "ATTRIBUTION DU PRIVILEGE -> " + m.getNom() + " à l'utilisateur -> " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                        }
                    }

                }

                JsfUtil.addSuccessMessage("Opération réussie");
            } else {
                JsfUtil.addErrorMessage("Aucun utilisateur selectionné");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Echec");
        }
    }
}
