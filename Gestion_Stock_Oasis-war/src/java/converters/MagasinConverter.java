package converters;

import entities.Magasin;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import sessions.MagasinFacadeLocal;
import utils.JsfUtil;

@FacesConverter("magasinConverter")
public class MagasinConverter implements Converter {

    @EJB
    private MagasinFacadeLocal ejbFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if ((value == null) || (value.length() == 0) || (JsfUtil.isDummySelectItem(component, value))) {
            return null;
        }
        return this.ejbFacade.find(getKey(value));
    }

    Integer getKey(String value) {
        Integer key = Integer.valueOf(value);
        return key;
    }

    String getStringKey(Integer value) {
        return "" + value;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if ((object == null) || (((object instanceof String)) && (((String) object).length() == 0))) {
            return null;
        }
        if ((object instanceof Magasin)) {
            Magasin o = (Magasin) object;
            return getStringKey(o.getIdmagasin());
        }
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Magasin.class.getName()});
        return null;
    }
}
