package shoppinglist.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import shoppinglist.App;
import shoppinglist.data.ProductItem;
import shoppinglist.data.ShoppingList;
import shoppinglist.persistence.shoppinglist.ShoppingListPersistance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramasinka on 2015.08.17.
 */
public class ShoppingListService {
    private static final Logger logger =
            LoggerFactory.getLogger("SLF4J");

    @Autowired
    private ShoppingListPersistance shoppingListPersistance;
    private List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();

    @Autowired
    public ShoppingListService(ShoppingListPersistance shoppingListPersistance) {
        this.shoppingListPersistance = shoppingListPersistance;
    }

    public ShoppingList createShoppingList(String listName) {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName(listName);
        shoppingList = shoppingListPersistance.saveShoppingList(shoppingList);
        logger.info("Created Shopping list:" + listName);
        return shoppingList;
    }

    public void deleteShoppingList(ShoppingList shoppingList) {
        shoppingListPersistance.deleteShoppingList(shoppingList);
    }

    public List<ShoppingList> getAllShoppingLists() {
        shoppingLists = shoppingListPersistance.getAllShoppingLists();
        return shoppingLists;
    }

    public ShoppingList getShoppingListById(int id) {
        return shoppingListPersistance.getShoppingListById(id);
    }

    public ShoppingList updateShoppingList(ShoppingList shoppingList) {
        return shoppingListPersistance.updateShoppingList(shoppingList);
    }
}

