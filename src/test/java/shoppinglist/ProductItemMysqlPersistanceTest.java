package shoppinglist;

import shoppinglist.data.ProductItem;
import shoppinglist.data.ShoppingList;
import shoppinglist.data.Status;
import shoppinglist.exceptions.ShoppingListNotFoundException;
import shoppinglist.persistence.productitem.ProductItemMysqlPersistance;
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
 * Created by Romcikas on 12/10/2015.
 */
public class ProductItemMysqlPersistanceTest {

    ConnectionToDatabaseService connectionToDatabaseService;
    ProductItemMysqlPersistance productItemMysqlPersistance = new ProductItemMysqlPersistance(connectionToDatabaseService);
    List<ProductItem> productItems = new ArrayList<ProductItem>();

    public void removeRecordsFromDatabase() {
        Connection connectToMysql = null;
        try {
            connectToMysql = connectionToDatabaseService.connectToMysql();
            Statement stmt = connectToMysql.createStatement();
            String sql = "DELETE FROM productitem";
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
    public void emptyProductItemsList() {
        assertTrue(productItems.isEmpty());
    }

    @Test
    public void saveProductItem(){
        ShoppingList shoppingList = new ShoppingList(0);
        ProductItem productItem = new ProductItem(0, "alus", 3, 4, Status.UNCHECKED, shoppingList);
        ProductItem productItem1 = new ProductItem(1, "pienas", 3, 4, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem);
        shoppingList.addProductItem(productItem1);
        assertEquals(productItemMysqlPersistance.getProductItemById(0).getProductName(), "alus");
    }

    @Test
    public void getProductItemById(){
        ShoppingList shoppingList = new ShoppingList(1);
        ProductItem productItem = new ProductItem(0, "alus", 3, 4, Status.UNCHECKED, shoppingList);
        ProductItem productItem1 = new ProductItem(1, "pienas", 3, 4, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem);
        shoppingList.addProductItem(productItem1);
        assertEquals(productItemMysqlPersistance.getProductItemById(0), productItem);
        assertEquals(productItemMysqlPersistance.getProductItemById(1), productItem1);
        assertEquals(productItemMysqlPersistance.getProductItemById(0).getProductName(), "alus");
        assertEquals(productItemMysqlPersistance.getProductItemById(1).getProductName(), "pienas");
    }

    @Test
    public void deleteProductItem(){
        ShoppingList shoppingList = new ShoppingList(0);
        ProductItem productItem = new ProductItem(0, "alus", 3, 4, Status.UNCHECKED, shoppingList);
        ProductItem productItem1 = new ProductItem(1, "pienas", 3, 4, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem);
        shoppingList.addProductItem(productItem1);
        productItemMysqlPersistance.deleteProductItem(productItem);
        assertEquals(shoppingList.getProductItemList().size(),1);
        productItemMysqlPersistance.deleteProductItem(productItem1);
        assertEquals(shoppingList.getProductItemList().size(),0);
    }

    @Test
    public void updateProductItem(){
        ShoppingList shoppingList = new ShoppingList(1);
        ProductItem productItem = new ProductItem(0, "alus", 3, 4, Status.UNCHECKED, shoppingList);
        ProductItem productItem1 = new ProductItem(1, "pienas", 3, 4, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem);
        shoppingList.addProductItem(productItem1);
        productItem1.setProductName("test");
        productItemMysqlPersistance.updateProductItem(productItem1);
        assertEquals(productItemMysqlPersistance.updateProductItem(productItem1), productItem1);
        assertEquals(productItemMysqlPersistance.updateProductItem(productItem1).getProductName(), "test");
    }

    @After
    public void removeAllFilesFromDirectory() {
        removeRecordsFromDatabase();
    }
}


