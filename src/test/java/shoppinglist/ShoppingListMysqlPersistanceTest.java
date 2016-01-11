package shoppinglist;

import shoppinglist.data.ShoppingList;
import shoppinglist.data.Status;
import shoppinglist.exceptions.ShoppingListNotFoundException;
import shoppinglist.persistence.shoppinglist.ShoppingListMysqlPersistance;
import shoppinglist.service.ConnectionToDatabaseService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Romcikas on 12/8/2015.
 */
public class ShoppingListMysqlPersistanceTest {
    ConnectionToDatabaseService connectionToDatabaseService;
    ShoppingListMysqlPersistance shoppingListMysqlPersistance = new ShoppingListMysqlPersistance(connectionToDatabaseService);
    List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();

    public void removeRecordsFromDatabase() {
        Connection connectToMysql = null;
        try {
            connectToMysql = connectionToDatabaseService.connectToMysql();
            Statement stmt = connectToMysql.createStatement();
            String sql = "DELETE FROM shoppinglist";
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Before
    public void setUp() throws ShoppingListNotFoundException {
        removeRecordsFromDatabase();
    }

    @Test
    public void emptyShoppingList() {
        assertTrue(shoppingLists.isEmpty());
    }

    @Test
    public void saveShoppingList() throws ShoppingListNotFoundException {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName("Test");
        ShoppingList shoppingList1 = new ShoppingList();
        shoppingList1.setName("Test1");
        shoppingListMysqlPersistance.saveShoppingList(shoppingList);
        shoppingListMysqlPersistance.saveShoppingList(shoppingList1);
        assertEquals(shoppingListMysqlPersistance.getAllShoppingLists().size(), 2);
    }

    @Test
    public void getShoppingListById() {
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName("Test");
        ShoppingList shoppingList1 = new ShoppingList();
        shoppingList1.setName("Test1");
        shoppingList1.setStatus(Status.UNCHECKED);
        shoppingListMysqlPersistance.saveShoppingList(shoppingList);
        shoppingListMysqlPersistance.saveShoppingList(shoppingList1);
        shoppingListMysqlPersistance.getShoppingListById(0);
        assertEquals(shoppingListMysqlPersistance.getShoppingListById(175), shoppingList1);
    }

    @Test
    public void deleteShoppingList() {
        ShoppingList shoppingList = new ShoppingList(0);
        shoppingList.setName("Test");
        ShoppingList shoppingList1 = new ShoppingList(1);
        shoppingList1.setName("Test1");
        shoppingListMysqlPersistance.saveShoppingList(shoppingList);
        shoppingListMysqlPersistance.saveShoppingList(shoppingList1);
        shoppingListMysqlPersistance.deleteShoppingList(shoppingList);
        assertEquals(shoppingListMysqlPersistance.getAllShoppingLists().size(), 1);
        shoppingListMysqlPersistance.deleteShoppingList(shoppingList1);
        assertEquals(shoppingListMysqlPersistance.getAllShoppingLists().size(), 0);
    }

    @Test
    public void updateShoppingList() throws ShoppingListNotFoundException {
        ShoppingList shoppingList = new ShoppingList(0);
        shoppingList.setName("Test");
        shoppingListMysqlPersistance.saveShoppingList(shoppingList);
        shoppingList.setName("Test1");
        shoppingListMysqlPersistance.updateShoppingList(shoppingList);
        assertEquals(shoppingListMysqlPersistance.updateShoppingList(shoppingList), shoppingList);
        assertEquals(shoppingListMysqlPersistance.updateShoppingList(shoppingList).getListName(), "Test1");
    }

    @Test
    public void getShoppingListByName() throws ShoppingListNotFoundException {
        ShoppingList shoppingList = new ShoppingList(0);
        shoppingList.setName("test");
        shoppingListMysqlPersistance.saveShoppingList(shoppingList);
        assertEquals(shoppingListMysqlPersistance.getShoppingListByName("test"), "test");
    }

    @After
    public void removeAllFilesFromDirectory() {
        removeRecordsFromDatabase();
    }
}

