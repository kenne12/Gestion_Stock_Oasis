package controllers.mouchard;

import entities.Mouchard;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class MouchardController extends AbstractMouchardController {

    @PostConstruct
    private void init() {
        this.mouchard = new Mouchard();
    }
}
