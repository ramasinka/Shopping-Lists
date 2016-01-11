package shoppinglist;


import shoppinglist.data.ProductItem;
import shoppinglist.data.ShoppingList;
import shoppinglist.data.Status;
import shoppinglist.exceptions.ShoppingListNotFoundException;
import shoppinglist.persistence.shoppinglist.ShoppingListJsonPersistance;
import shoppinglist.service.ShoppingListService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ramasinka on 2015.08.17.
 */

public class ShoppingListBufferedPersistence {
    File file = new File("C:/");
    ShoppingListService shoppingListService;
    ShoppingList shoppingList;
    ShoppingListJsonPersistance shoppingListJsonPersistance = new ShoppingListJsonPersistance(file, "ShoppingListBufferedPersistence");
    List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();

    public void removeFilesFromDirectory() {
        File dir = new File(file, "ShoppingListBufferedPersistence");
        for (File aFile : dir.listFiles()) {
            aFile.delete();
        }
    }

    @Before
    public void setUp() throws ShoppingListNotFoundException {
        removeFilesFromDirectory();
        shoppingListService = new ShoppingListService(shoppingListJsonPersistance);
        shoppingLists = shoppingListJsonPersistance.getAllShoppingLists();
    }

    @Test
    public void emptyShoppingList() {
        assertTrue(shoppingLists.isEmpty());
    }

    @Test
    public void createShoppingList() {
        shoppingList = shoppingListService.createShoppingList("Maistas");
        shoppingList = shoppingListService.createShoppingList("Gerimai");
        assertEquals(shoppingListJsonPersistance.getAllShoppingLists().size(), 2);
    }

    @Test
    public void getAllShoppingLists() {
        shoppingList = shoppingListService.createShoppingList("Maistas");
        assertEquals(shoppingListJsonPersistance.getAllShoppingLists().size(), 1);
        assertEquals(shoppingList.getListName(), "Maistas");
        assertEquals(shoppingList.getListId(), 0);
        shoppingList = shoppingListService.createShoppingList("Gerimai");
        assertEquals(shoppingList.getListId(), 1);
    }

    @Test
    public void getShoppingListById() {
        shoppingList = shoppingListService.createShoppingList("Maistas");
        shoppingList = shoppingListService.createShoppingList("alus");
        ProductItem productItem1 = new ProductItem(2, "Alus", 2, 2, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem1);
        assertEquals(shoppingList, shoppingListJsonPersistance.getShoppingListById(1));
    }

    @Test
    public void deleteShoppingList() throws ShoppingListNotFoundException {
        shoppingList = shoppingListService.createShoppingList("Maistas");
        shoppingList = shoppingListService.createShoppingList("alus");
        ProductItem productItem1 = new ProductItem(2, "Alus", 2, 2, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem1);
        shoppingListJsonPersistance.deleteShoppingList(shoppingList);
        shoppingList = shoppingListService.createShoppingList("pienas");
    }

    @Test
    public void updateShoppingList() {
        shoppingList = shoppingListService.createShoppingList("Maistas");
        shoppingList.setName("Alus");
        assertEquals(shoppingListJsonPersistance.updateShoppingList(shoppingList).getListName(), "Alus");
        shoppingLists = shoppingListJsonPersistance.getAllShoppingLists();
        assertEquals(shoppingList.getListName(), "Alus");
    }

    @After
    public void removeAllFilesFromDirectory() {
        removeFilesFromDirectory();
    }
}
