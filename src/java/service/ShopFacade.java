/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Relation;
import bean.Shop;
import bean.User;
import controller.util.SessionUtil;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author FatimaAc
 */
@Stateless
public class ShopFacade extends AbstractFacade<Shop> {

    @EJB
    private RelationFacade relationFacade;

    @PersistenceContext(unitName = "Job_NearbyPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ShopFacade() {
        super(Shop.class);
    }

    public boolean isDislikedShop(Shop shop) {
        boolean isDisliked = false;
        String email = SessionUtil.getConnectedUser().getEmail();
        List<Relation> relations = relationFacade.findAll();
        for (Relation relation : relations) {
            if (relation.getType() == 2 && relation.getShop().getId().equals(shop.getId()) && relation.getUser().getEmail().equals(email)) {
                isDisliked = true;
            }
        }
        return isDisliked;
    }

    public boolean isLikedShop(Shop shop) {
        boolean isLiked = false;
        String email = SessionUtil.getConnectedUser().getEmail();
        List<Relation> relations = relationFacade.findAll();
        for (Relation relation : relations) {
            if (relation.getType() == 1 && relation.getShop().getId().equals(shop.getId()) && relation.getUser().getEmail().equals(email)) {
                isLiked = true;
            }
        }
        return isLiked;
    }

    public List<Shop> likedShopByUser() {
        List<Shop> likedShops = new ArrayList<>();
        for (Relation relation : relationFacade.findAll()) {
            if (relation.getType() == 1 && relation.getUser().getEmail().equals(SessionUtil.getConnectedUser().getEmail())) {
                likedShops.add(relation.getShop());
            }
        }
        return likedShops;
    }

}
