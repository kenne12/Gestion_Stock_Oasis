package utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "themes")
@SessionScoped
public class themes implements Serializable {

    List<String> themes;

    @PostConstruct
    public void init() {
        this.themes = new ArrayList();
        this.themes.add("afterdark");
        this.themes.add("afternoon");
        this.themes.add("afterwork");
        this.themes.add("aristo");
        this.themes.add("black-tie");
        this.themes.add("blitzer");
        this.themes.add("bluesky");
        this.themes.add("bootstrap");
        this.themes.add("casablanca");
        this.themes.add("cupertino");
        this.themes.add("cruze");
        this.themes.add("dark-hive");
        this.themes.add("delta");
        this.themes.add("dot-luv");
        this.themes.add("eggplant");
        this.themes.add("excite-bike");
        this.themes.add("flick");
        this.themes.add("glass-x");
        this.themes.add("home");
        this.themes.add("hot-sneaks");
        this.themes.add("humanity");
        this.themes.add("le-frog");
        this.themes.add("midnight");
        this.themes.add("mint-choc");
        this.themes.add("overcast");
        this.themes.add("pepper-grinder");
        this.themes.add("rocket");
        this.themes.add("sam");
        this.themes.add("smoothness");
        this.themes.add("south-street");
        this.themes.add("start");
        this.themes.add("sunny");
        this.themes.add("swanky-purse");
        this.themes.add("trontastic");
        this.themes.add("ui-darkness");
        this.themes.add("ui-lightness");
        this.themes.add("vader");
    }

    public List<String> getThemes() {
        return this.themes;
    }

    public void setThemes(List<String> themes) {
        this.themes = themes;
    }
}
