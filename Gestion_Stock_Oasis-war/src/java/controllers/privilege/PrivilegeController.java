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
public class PrivilegeController extends AbstractPrivilegeController
        implements PrivilegeInterfaceController, Serializable {

    @PostConstruct
    private void initAcces() {
        this.utilisateur = new Utilisateur();
    }

    public void prepareCreate() {
        try {
            if (Utilitaires.isAccess(Long.valueOf(5L))) {
                this.showPrivilegeCreateDialog = Boolean.valueOf(true);
                this.dualMenu.getSource().clear();
                this.dualMenu.getTarget().clear();
                this.utilisateur = new Utilisateur();
                return;
            }
            this.showPrivilegeCreateDialog = Boolean.valueOf(false);
            JsfUtil.addErrorMessage("Vous n'avez pas le droit d'attribuer les privilèges aux utilisateurs");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewAccess(Utilisateur utilisateur) {
        try {
            this.utilisateur = utilisateur;
            this.privileges = this.privilegeFacadeLocal.findByUser(utilisateur.getIdutilisateur().intValue());
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

                List<Privilege> privilegeTemp = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue());
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

                Boolean flag = Boolean.valueOf(false);
                for (Menu m : this.dualMenu.getTarget()) {
                    if (!flag.booleanValue()) {
                        if (m.getIdmenu().intValue() == 1) {
                            flag = Boolean.valueOf(true);
                            Privilege p = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue(), 1);
                            if (p == null) {
                                /* 113 */ p = new Privilege();
                                /* 114 */ p.setIdprivilege(this.privilegeFacadeLocal.nextVal());
                                /* 115 */ p.setIdmenu(m);
                                /* 116 */ p.setIdutilisateur(this.utilisateur);
                                /* 117 */ this.privilegeFacadeLocal.create(p);
                                /* 118 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "ATTRIBUTION DU PRIVILEGE : SUPER ADMINISTRATEUR à l'utilisateur -> " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
                                /* 119 */ break;
                            }
                            /* 121 */ JsfUtil.addSuccessMessage("Vous disposez dejà du privilège SUPER ADMINISTRATEUR");
                            /* 122 */ break;
                        }

                        /* 125 */ Privilege p = this.privilegeFacadeLocal.findByUser(this.utilisateur.getIdutilisateur().intValue(), m.getIdmenu().intValue());
                        /* 126 */ if (p == null) {
                            /* 127 */ p = new Privilege();
                            /* 128 */ p.setIdprivilege(this.privilegeFacadeLocal.nextVal());
                            /* 129 */ p.setIdmenu(m);
                            /* 130 */ p.setIdutilisateur(this.utilisateur);
                            /* 131 */ this.privilegeFacadeLocal.create(p);
                            /* 132 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "ATTRIBUTION DU PRIVILEGE -> " + m.getNom() + " à l'utilisateur -> " + this.utilisateur.getNom() + " " + this.utilisateur.getPrenom(), SessionMBean.getUserAccount());
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
