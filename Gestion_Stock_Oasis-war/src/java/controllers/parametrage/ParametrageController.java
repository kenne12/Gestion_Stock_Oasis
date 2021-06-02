package controllers.parametrage;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class ParametrageController extends AbstractParametrageController implements Serializable {

    @PostConstruct
    private void init() {
        this.parametrage = this.parametrageFacadeLocal.find(SessionMBean.getParametrage().getId());
        this.filename = this.parametrage.getLogo();
        this.password.add("momo1234");
        this.password.add("kenne1234");
    }

    public void save() {
        try {
            if (!Utilitaires.isAccess((41L))) {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("acces_refuse"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }

            this.parametrageFacadeLocal.edit(this.parametrage);
            this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareUpload() {
        try {
            if (!Utilitaires.isAccess((41L))) {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("acces_refuse"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }
            RequestContext.getCurrentInstance().execute("PF('LogoDialog').show()");
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            if ((event.getFile() == null) || (event.getFile().getFileName() == null) || (event.getFile().getFileName().equals(""))) {
                this.routine.feedBack("avertissement", "/resources/tool_images/error.png", this.routine.localizeMessage("nom_image_incorrect"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }

            if (!Utilitaires.estExtensionImage(Utilitaires.getExtension(event.getFile().getFileName()))) {
                this.routine.feedBack("avertissement", "/resources/tool_images/error.png", this.routine.localizeMessage("fichier_non_pris_en_charge"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }

            String nom = "logo_atlantis." + Utilitaires.getExtension(event.getFile().getFileName());

            FileOutputStream out = new FileOutputStream(Utilitaires.path + "" + this.imageDir + "/" + nom, true);

            byte[] bytes = event.getFile().getContents();
            out.write(bytes);

            this.filename = nom;
            this.parametrage.setLogo(nom);
            this.parametrageFacadeLocal.edit(this.parametrage);

            this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
            RequestContext.getCurrentInstance().execute("PF('LogoDialog').hide()");

            File f1 = new File(Utilitaires.path + "" + this.imageDir + "/" + nom);
            File f2 = new File(SessionMBean.getParametrage().getRepertoireLogo() + "" + nom);
            Files.copy(f1, f2);
        } catch (IOException ex) {
            this.routine.catchException(ex, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }
}
