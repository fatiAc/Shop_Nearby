/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.User;
import controller.util.HashageUtil;
import controller.util.JsfUtil;
import controller.util.SessionUtil;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author FatimaAc
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "Job_NearbyPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }

    public int createAccount(User user, String email, String passwrd) {
        User loadedUser = find(email);
        if (loadedUser == null) {
            user.setPasswrd(HashageUtil.sha256(passwrd));
            user.setEmail(email);
            create(user);
            return 1;
        } else {
            JsfUtil.addErrorMessage("email already exists ");
            return 0;
        }
    }

    public int singIn(String email,String passwrd) {
        User existedUser = find(email);
        if (existedUser == null) {
            JsfUtil.addErrorMessage("Email not found ");
            return -1;
        } else if (!existedUser.getPasswrd().equals(HashageUtil.sha256(passwrd))) {
            JsfUtil.addErrorMessage("Password is invalid ");
            return -2;
        } else {
            SessionUtil.registerUser(existedUser);
            return 1;
        }
    }
    
    public void deconnection(){
        if(SessionUtil.getConnectedUser() != null){
            SessionUtil.unSetUser(SessionUtil.getConnectedUser());
        }
    }

}
