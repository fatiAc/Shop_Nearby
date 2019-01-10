package controller;

import bean.Shop;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import service.ShopFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.ELState;

@Named("shopController")
@SessionScoped
public class ShopController implements Serializable {

    @EJB
    private service.ShopFacade ejbFacade;
    @EJB
    private service.RelationFacade relationFacade;
    @EJB
    private service.UserFacade userFacade;
    private List<Shop> items = null;
    private List<Shop> likedShops = null;
    private Shop selected;
    private int dislikeState = 1;
    private int likeState = 1;

    public void likedShope(Shop shop) {
        if (!ejbFacade.isLikedShop(shop) && !ejbFacade.isDislikedShop(shop)) {
            relationFacade.likeShop(shop);
        } else if (ejbFacade.isLikedShop(shop)) {
            relationFacade.removeLikeReation(shop);
            likeState = 2;
        }
    }

    public void dislikeShop(Shop shop) {
        if (!ejbFacade.isDislikedShop(shop) && !ejbFacade.isLikedShop(shop)) {
            relationFacade.dislikeShop(shop);
        } else if (ejbFacade.isDislikedShop(shop)) {
            relationFacade.removeDislikeReation(shop);
            dislikeState = 2;
        }
    }

    public String dislikeColor(Shop shop) {
        if (ejbFacade.isDislikedShop(shop) && !ejbFacade.isLikedShop(shop)) {
            return "red";
        } else if (dislikeState == 2) {
            return "white";
        } else {
            return "white";
        }

    }

    public String likeColor(Shop shop) {
        if (ejbFacade.isLikedShop(shop) && !ejbFacade.isDislikedShop(shop)) {
            return "lightgreen";
        } else if (likeState == 2) {
            return "white";
        } else {
            return "white";
        }
    }

    public void removeShop(Shop shop) {
        relationFacade.removeLikeReation(shop);
        likedShops.remove(shop);
    }

    public boolean isConnectedUser() {
        if (SessionUtil.getConnectedUser() == null) {
            return false;
        } else {
            return true;
        }
    }
    
     public String deconnection(){
       userFacade.deconnection();
       return "/user/SingIn?faces-redirect=true";
   }

    public List<Shop> getLikedShops() {
        if (likedShops == null) {
            likedShops = ejbFacade.likedShopByUser();
        }
        return likedShops;
    }

    public void setLikedShops(List<Shop> likedShops) {
        this.likedShops = likedShops;
    }

    public ShopController() {
    }

    public Shop getSelected() {
        return selected;
    }

    public void setSelected(Shop selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ShopFacade getFacade() {
        return ejbFacade;
    }

    public Shop prepareCreate() {
        selected = new Shop();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ShopCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ShopUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ShopDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Shop> getItems() {
        if (items == null) {
            items = getFacade().findAll();
            System.out.println("+++++++++++++++++++++++++++++++ " + items.size());
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Shop getShop(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Shop> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Shop> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Shop.class)
    public static class ShopControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ShopController controller = (ShopController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "shopController");
            return controller.getShop(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Shop) {
                Shop o = (Shop) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Shop.class.getName()});
                return null;
            }
        }

    }

}
