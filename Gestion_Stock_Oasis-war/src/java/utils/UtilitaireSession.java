package utils;

import entities.Utilisateur;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean
@SessionScoped
public class UtilitaireSession {

    private static final UtilitaireSession instance = new UtilitaireSession();

    public static UtilitaireSession getInstance() {
        return instance;
    }

    public void destroy() {
        FacesContext fc = FacesContext.getCurrentInstance();
        getSession(fc).invalidate();
    }

    private boolean isContextOk(FacesContext fc) {
        boolean res = (fc != null) && (fc.getExternalContext() != null) && (fc.getExternalContext().getSession(false) != null);
        return res;
    }

    private HttpSession getSession(FacesContext fc) {
        return (HttpSession) fc.getExternalContext().getSession(false);
    }

    public Object get(String cle) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Object res = null;
        if (isContextOk(fc)) {
            res = getSession(fc).getAttribute(cle);
        }
        return res;
    }

    public void set(String cle, Object valeur) {
        FacesContext fc = FacesContext.getCurrentInstance();
        if ((fc != null) && (fc.getExternalContext() != null)) {
            getSession(fc).setAttribute(cle, valeur);
        }
    }

    public void setuser(Utilisateur utilisateur) {
        set("user", utilisateur);
    }

    public Utilisateur getuser() {
        return (Utilisateur) get("user");
    }
}
