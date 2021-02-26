package converters;

import entities.Magasinarticle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import sessions.MagasinarticleFacadeLocal;
import utils.JsfUtil;

@FacesConverter("magasinarticleConverter")
public class MagasinarticleConverter implements Converter {

    @EJB
    private MagasinarticleFacadeLocal ejbFacade;

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
        if ((object instanceof Magasinarticle)) {
            Magasinarticle m = (Magasinarticle) object;
            return getStringKey(m.getIdmagasinarticle());
        }
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Magasinarticle.class.getName()});
        return null;
    }
}
