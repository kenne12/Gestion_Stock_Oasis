package converters;

import entities.Lot;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import sessions.LotFacadeLocal;
import utils.JsfUtil;

@FacesConverter("lotConverter")
public class LotConverter implements Converter {

    @EJB
    private LotFacadeLocal ejbFacade;

    @Override
    public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
        if ((value == null) || (value.length() == 0) || (JsfUtil.isDummySelectItem(component, value))) {
            return null;
        }
        return this.ejbFacade.find(getKey(value));
    }

    Long getKey(String value) {
        Long key = Long.valueOf(value);
        return key;
    }

    String getStringKey(Long value) {
        return "" + value;
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if ((object == null) || (((object instanceof String)) && (((String) object).length() == 0))) {
            return null;
        }
        if ((object instanceof Lot)) {
            Lot l = (Lot) object;
            return getStringKey(l.getIdlot());
        }
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Lot.class.getName()});
        return null;
    }
}
