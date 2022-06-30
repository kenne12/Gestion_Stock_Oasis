package controllers.parametrage;

import entities.Parametrage;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.MouchardFacadeLocal;
import sessions.ParametrageFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractParametrageController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected ParametrageFacadeLocal parametrageFacadeLocal;
    protected Parametrage parametrage = new Parametrage();

    protected String imageDir = "/photos/";
    protected String chemin_replicated_images = SessionMBean.getParametrage().getRepertoireLogo();

    protected Routine routine = new Routine();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;
    protected List<String> password = new ArrayList();

    protected String sessionPassword = "";

    protected Boolean session = true;

    protected String filename = "";

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String fileName) {
        this.filename = fileName;
    }

    public Boolean getSession() {
        try {
            this.session = !SessionMBean.getSession1();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.session;
    }

    public void setSession(Boolean session) {
        this.session = session;
    }

    public String getSessionPassword() {
        return this.sessionPassword;
    }

    public void setSessionPassword(String sessionPassword) {
        this.sessionPassword = sessionPassword;
    }

    public Parametrage getParametrage() {
        return this.parametrage;
    }

    public void setParametrage(Parametrage parametrage) {
        this.parametrage = parametrage;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public String getChemin_replicated_images() {
        return this.chemin_replicated_images;
    }

    public void setChemin_replicated_images(String chemin_replicated_images) {
        this.chemin_replicated_images = chemin_replicated_images;
    }
}
