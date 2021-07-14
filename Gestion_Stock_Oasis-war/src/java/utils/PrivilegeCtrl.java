package utils;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class PrivilegeCtrl extends AbstractPrivilege {

    @PostConstruct
    private void init() {
    }

    public void setPrivilege() {
    }
}
