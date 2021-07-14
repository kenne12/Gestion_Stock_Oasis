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
        /*  54 */ this.titleNotify = localizeMessage(type);
        /*  55 */ this.iconMessage = icon;
        /*  56 */ this.message = msg;
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
            /*  76 */ this.progress = state;
            /*  77 */ this.progressLabel = (state + "%");
        }
        if (operation.equals("close")) {
            /*  80 */ this.showProcessus = "false";
            /*  81 */ this.showProgress = "false";
        }
    }

    public void stopConverse() {
        /*  86 */ this.converse = "false";
    }

    public String getTitleNotify() {
        /*  90 */ return this.titleNotify;
    }

    public void setTitleNotify(String titleNotify) {
        /*  94 */ this.titleNotify = titleNotify;
    }

    public String getMessage() {
        /*  98 */ return this.message;
    }

    public void setMessage(String message) {
        /* 102 */ this.message = message;
    }

    public String getIconMessage() {
        /* 106 */ return this.iconMessage;
    }

    public void setIconMessage(String iconMessage) {
        /* 110 */ this.iconMessage = iconMessage;
    }

    public String getProcessus() {
        /* 114 */ return this.processus;
    }

    public void setProcessus(String processus) {
        /* 118 */ this.processus = processus;
    }

    public String getShowProcessus() {
        /* 122 */ return this.showProcessus;
    }

    public void setShowProcessus(String showProcessus) {
        /* 126 */ this.showProcessus = showProcessus;
    }

    public String getProgress() {
        /* 130 */ return this.progress;
    }

    public void setProgress(String progress) {
        /* 134 */ this.progress = progress;
    }

    public String getProgressLabel() {
        /* 138 */ return this.progressLabel;
    }

    public void setProgressLabel(String progressLabel) {
        /* 142 */ this.progressLabel = progressLabel;
    }

    public String getShowProgress() {
        /* 146 */ return this.showProgress;
    }

    public void setShowProgress(String showProgress) {
        /* 150 */ this.showProgress = showProgress;
    }

    public String getConverse() {
        /* 154 */ return this.converse;
    }

    public void setConverse(String converse) {
        /* 158 */ this.converse = converse;
    }
}
