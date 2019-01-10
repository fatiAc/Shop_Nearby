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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author FatimaAc
 */
@Stateless
public class RelationFacade extends AbstractFacade<Relation> {

    @PersistenceContext(unitName = "Job_NearbyPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RelationFacade() {
        super(Relation.class);
    }

    public void likeShop(Shop shop) {
        User connectedUser = SessionUtil.getConnectedUser();
        if (shop != null && connectedUser != null) {
            Relation relation = new Relation(1, shop, connectedUser);
            create(relation);
        }
    }

    public void dislikeShop(Shop shop) {
        User connectedUser = SessionUtil.getConnectedUser();
        if (shop != null && connectedUser != null) {
            Relation relation = new Relation(2, shop, connectedUser);
            create(relation);
        }
    }

    public void removeDislikeReation(Shop shop) {
        for (Relation relation : findAll()) {
            if (relation.getType() == 2 && relation.getShop().getId().equals(shop.getId()) && relation.getUser().getEmail().equals(SessionUtil.getConnectedUser().getEmail())) {
                remove(relation);
            }
        }
    }

    public void removeLikeReation(Shop shop) {
        for (Relation relation : findAll()) {
            if (relation.getType() == 1 && relation.getShop().getId().equals(shop.getId()) && relation.getUser().getEmail().equals(SessionUtil.getConnectedUser().getEmail())) {
                remove(relation);
            }
        }
    }

}
