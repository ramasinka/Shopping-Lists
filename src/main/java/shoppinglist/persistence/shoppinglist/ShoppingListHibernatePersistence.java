package shoppinglist.persistence.shoppinglist;


import org.springframework.beans.factory.annotation.Autowired;
import shoppinglist.data.ShoppingList;
import shoppinglist.data.Status;
import shoppinglist.exceptions.ShoppingListNotFoundException;
import shoppinglist.service.ShoppingListService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Romcikas on 12/15/2015.
 */
public class ShoppingListHibernatePersistence implements ShoppingListPersistance {
    EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
    EntityManager entitymanager = emfactory.createEntityManager();
    @Autowired
    ShoppingListService shoppingListService;

    @Override
    public ShoppingList saveShoppingList(ShoppingList shoppingList) {
        entitymanager.getTransaction().begin();
        shoppingList.setStatus(Status.UNCHECKED);
        entitymanager.persist(shoppingList);
        entitymanager.getTransaction().commit();
        return shoppingList;
    }

    @Override
    public ShoppingList getShoppingListById(int id) {
        entitymanager.getTransaction().begin();
        ShoppingList shoppingList = entitymanager.find(ShoppingList.class, id);
        entitymanager.getTransaction().commit();
        return shoppingList;
    }

    @Override
    public List<ShoppingList> getAllShoppingLists() {
        Query q = entitymanager.createQuery("SELECT u FROM ShoppingList u");
        List shoppingLists = q.getResultList();
        return shoppingLists;
    }

    @Override
    public void deleteShoppingList(ShoppingList shoppingList)   {
        shoppingList = entitymanager.find(ShoppingList.class, shoppingList.getListId());
        entitymanager.getTransaction().begin();
        entitymanager.remove(shoppingList);
        entitymanager.getTransaction().commit();
    }

    @Override
    public ShoppingList updateShoppingList(ShoppingList shoppingList) {
        entitymanager.getTransaction().begin();
        shoppingList = entitymanager.find(ShoppingList.class, shoppingList.getListId());
        entitymanager.getTransaction().commit();
        return shoppingList;
    }
}
