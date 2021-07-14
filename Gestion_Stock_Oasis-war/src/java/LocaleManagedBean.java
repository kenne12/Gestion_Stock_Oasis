
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

@ManagedBean(name = "localeManagedBean")
@SessionScoped
public class LocaleManagedBean implements Serializable {

    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    public String getLanguage() {
        return this.locale.getLanguage();
    }

    public void setLanguage(String language) {
        this.locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
    }

    public Locale getLocale() {
        return this.locale;
    }

    public SelectItem[] getLocales() {
        ArrayList items = new ArrayList();

        Application application = FacesContext.getCurrentInstance()
                .getApplication();
        Iterator supportedLocales = application.getSupportedLocales();

        while (supportedLocales.hasNext()) {
            Locale loc = (Locale) supportedLocales.next();
            items.add(new SelectItem(loc.getLanguage(), loc.getDisplayName(this.locale)));
        }

        SelectItem[] locales = new SelectItem[items.size()];
        items.toArray(locales);
        return locales;
    }

    public void activerFR() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.FRENCH);
    }

    public void activerEN() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(Locale.ENGLISH);
    }
}
