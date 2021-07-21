package utils;

import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UISelectItem;
import javax.faces.context.FacesContext;

public class JsfUtil {

    public static void addErrorMessage(Exception ex, String defaultMsg) {
        String msg = ex.getLocalizedMessage();
        if ((msg != null) && (msg.length() > 0)) {
            addErrorMessage(msg);
        } else {
            addErrorMessage(defaultMsg);
        }
    }

    public static void addErrorMessages(List<String> messages) {
        for (String message : messages) {
            addErrorMessage(message);
        }
    }

    public static void addErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
        FacesContext.getCurrentInstance().validationFailed();
    }

    public static void addSuccessMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addWarningMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static void addFatalErrorMessage(String msg) {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    public static Throwable getRootCause(Throwable cause) {
        if (cause != null) {
            Throwable source = cause.getCause();
            if (source != null) {
                return getRootCause(source);
            }
            return cause;
        }

        return null;
    }

    public static boolean isValidationFailed() {
        return FacesContext.getCurrentInstance().isValidationFailed();
    }

    public static boolean isDummySelectItem(UIComponent component, String value) {
        for (UIComponent children : component.getChildren()) {
            if ((children instanceof UISelectItem)) {
                UISelectItem item = (UISelectItem) children;
                if ((item.getItemValue() != null) || (!item.getItemLabel().equals(value))) {
                    break;
                }
                return true;
            }
        }
        return false;
    }

    public static String getComponentMessages(String clientComponent, String defaultMessage) {
        FacesContext fc = FacesContext.getCurrentInstance();
        UIComponent component = UIComponent.getCurrentComponent(fc).findComponent(clientComponent);
        if ((component instanceof UIInput)) {
            UIInput inputComponent = (UIInput) component;
            if (inputComponent.isValid()) {
                return defaultMessage;
            }
            Iterator iter = fc.getMessages(inputComponent.getClientId());
            if (iter.hasNext()) {
                return ((FacesMessage) iter.next()).getDetail();
            }
        }

        return "";
    }

    public static String formaterStringMoney(Long valeur) {
        String chaine = Long.toString(valeur.longValue());
        if (chaine == null) {
            return null;
        }
        int taille = chaine.length();
        int j = taille;
        String result = "";
        int i = 0;
        while (i < taille) {
            result = result + chaine.charAt(i);
            i++;
            j--;
            if ((j > 0) && (j % 3 == 0)) {
                result = result + ' ';
            }
        }

        return result;
    }

    public static String formaterStringMoney(Integer valeur) {
        String chaine = Integer.toString(valeur.intValue());
        if (chaine == null) {
            return null;
        }
        int taille = chaine.length();
        int j = taille;
        String result = "";
        int i = 0;
        while (i < taille) {
            result = result + chaine.charAt(i);
            i++;
            j--;
            if ((j > 0) && (j % 3 == 0)) {
                result = result + ' ';
            }
        }

        return result;
    }

    public static String formaterStringMoney(String valeur) {
        String chaine = valeur;
        if (chaine == null) {
            return null;
        }
        int taille = chaine.length();
        int j = taille;
        String result = "";
        int i = 0;
        while (i < taille) {
            result = result + chaine.charAt(i);
            i++;
            j--;
            if ((j > 0) && (j % 3 == 0)) {
                result = result + ' ';
            }
        }

        return result;
    }

    public static String formaterStringMoney(Double val) {
        String pEntiere = partieEntiere(val);
        String pDec = partieDecimale(val);
        String chaine = pEntiere;
        int taille = chaine.length();
        int j = taille;
        String result = "";
        int i = 0;
        while (i < taille) {
            result = result + chaine.charAt(i);
            i++;
            j--;
            if ((j > 0) && (j % 3 == 0)) {
                result = result + ' ';
            }
        }
        if (pDec != null) {
            result = result + "." + pDec;
        }
        return result;
    }

    private static String partieDecimale(Double nombre) {
        return partieDecimale(nombre.toString());
    }

    private static String partieDecimale(String nombre) {
        String result = "";
        int taille = nombre.length();
        boolean copie = false;
        for (int i = 0; i < taille; i++) {
            if (copie) {
                result = result + nombre.charAt(i);
            } else if (nombre.charAt(i) == '.') {
                copie = true;
            }
        }
        if (result.equals("0")) {
            return null;
        }
        return result;
    }

    private static String partieEntiere(Double nombre) {
        Integer tmp = nombre.intValue();
        return tmp.toString();
    }
}
