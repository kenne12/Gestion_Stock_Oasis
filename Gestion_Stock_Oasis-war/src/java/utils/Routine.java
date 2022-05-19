package utils;

import java.util.ResourceBundle;
import javax.faces.context.FacesContext;

public class Routine {

    private ResourceBundle rs = ResourceBundle.getBundle("langues.langue", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    private String titleNotify = "";
    private String message = "";
    private String iconMessage = "";
    private String processus;
    private String showProcessus;
    private String progress;
    private String progressLabel;
    private String showProgress;
    private String converse = "false";

    public String localizeMessage(String info) {
        String msg = info;
        try {
            msg = rs.getString(info);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return msg;
        }
    }

    public void catchException(Exception e, String contexte) {
        e.printStackTrace();
        this.message = "";
        this.message = (this.message + localizeMessage("contexte") + " : " + contexte + ", \n");
        this.message = (this.message + localizeMessage("message") + "  : " + e.getMessage() + ", \n");
        this.message = (this.message + localizeMessage("cause") + "     : " + e.getCause() + ", \n");
        this.message = (this.message + localizeMessage("class") + "   : " + e.getClass() + ", \n");
        this.iconMessage = "/resources/tool_images/error.png";
        this.titleNotify = localizeMessage("erreur");
    }

    public void feedBack(String type, String icon, String msg) {
        this.titleNotify = localizeMessage(type);
        this.iconMessage = icon;
        this.message = msg;
    }

    public String convert(String value) {
        if (value.equals("0")) {
            return "false";
        }
        return "true";
    }

    public void progressBarHandler(String operation, String state) {
        if (operation.equals("open")) {
            this.processus = localizeMessage(state);
            this.showProcessus = "true";
            this.showProgress = "true";
            this.progress = "0";
            this.progressLabel = "0%";
        }
        if (operation.equals("progress")) {
            this.progress = state;
            this.progressLabel = (state + "%");
        }
        if (operation.equals("close")) {
            this.showProcessus = "false";
            this.showProgress = "false";
        }
    }

    public void stopConverse() {
        this.converse = "false";
    }

    public String getTitleNotify() {
        return this.titleNotify;
    }

    public void setTitleNotify(String titleNotify) {
        this.titleNotify = titleNotify;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIconMessage() {
        return this.iconMessage;
    }

    public void setIconMessage(String iconMessage) {
        this.iconMessage = iconMessage;
    }

    public String getProcessus() {
        return this.processus;
    }

    public void setProcessus(String processus) {
        this.processus = processus;
    }

    public String getShowProcessus() {
        return this.showProcessus;
    }

    public void setShowProcessus(String showProcessus) {
        this.showProcessus = showProcessus;
    }

    public String getProgress() {
        return this.progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getProgressLabel() {
        return this.progressLabel;
    }

    public void setProgressLabel(String progressLabel) {
        this.progressLabel = progressLabel;
    }

    public String getShowProgress() {
        return this.showProgress;
    }

    public void setShowProgress(String showProgress) {
        this.showProgress = showProgress;
    }

    public String getConverse() {
        return this.converse;
    }

    public void setConverse(String converse) {
        this.converse = converse;
    }
}
