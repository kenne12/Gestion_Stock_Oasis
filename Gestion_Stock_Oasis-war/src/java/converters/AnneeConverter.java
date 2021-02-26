package converters;

import entities.Annee;
import entities.Magasin;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import sessions.AnneeFacadeLocal;
import utils.JsfUtil;

@FacesConverter("anneeConverter")
public class AnneeConverter implements Converter {

    @EJB
    private AnneeFacadeLocal ejbFacade;

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
        if ((object instanceof Annee)) {
            Annee a = (Annee) object;
            return getStringKey(a.getIdannee());
        }
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Annee.class.getName()});
        return null;
    }
}
