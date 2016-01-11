package shoppinglist;

import shoppinglist.data.ProductItem;
import shoppinglist.data.ShoppingList;
import shoppinglist.data.Status;
import shoppinglist.exceptions.DublicateProductException;
import shoppinglist.exceptions.RemoveShoppingListException;
import shoppinglist.exceptions.ShoppingListNotFoundException;
import shoppinglist.persistence.shoppinglist.ShoppingListJsonPersistance;
import shoppinglist.service.ShoppingListService;
import shoppinglist.service.WriteService;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by Ramasinka on 2015.08.17.
 */
public class ProductItemServiceTest {
    ShoppingListService shoppingListService;
    File file = new File("C:/");
    ShoppingListJsonPersistance shoppingListJsonPersistance = new ShoppingListJsonPersistance(file, "ShoppingList");
    ShoppingList shoppingList;
    WriteService writeService;
    private List<ProductItem> productsList = new ArrayList<ProductItem>();

    @Before
    public void setUp() throws ShoppingListNotFoundException, DublicateProductException {
        shoppingListService = new ShoppingListService(shoppingListJsonPersistance);
        shoppingList = shoppingListService.createShoppingList("Gerimai");
    }

    @Test
    public void addProductItem() {
        ProductItem productItem = new ProductItem(1, "Pienas", 1, 10, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem);
        productsList = shoppingList.getProductItemList();
        ProductItem productItem1 = new ProductItem(2, "Alus", 1, 20, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem1);
        productsList = shoppingList.getProductItemList();
        assertEquals(2, productsList.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cantAddProductItemWithIllegalAmount() throws RemoveShoppingListException {
        ProductItem productItem = new ProductItem(1, "Pienas", -100, 30, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cantAddProductItemWithoutName() throws RemoveShoppingListException {
        ProductItem productItem = new ProductItem(1, "", 1, 40, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem);
    }

    @Test
    public void removeProductItem() throws RemoveShoppingListException {
        ProductItem productItem = new ProductItem(1, "Pienas", 3, 1, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem);
        productsList = shoppingList.getProductItemList();
        assertEquals(1, productsList.size());
        ProductItem productItem1 = new ProductItem(2, "Alus", 2, 2, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem1);
        productsList = shoppingList.getProductItemList();
        assertEquals(2, productsList.size());
        shoppingList.removeProduct(productItem);
        assertEquals(1, productsList.size());
        shoppingList.removeProduct(productItem1);
        assertEquals(0, productsList.size());
    }

    @Test(expected = RemoveShoppingListException.class)
    public void cantRemoveProductItemThatIsNotListed() throws RemoveShoppingListException {
        ProductItem productItem = new ProductItem(1, "Pienas", 3, 10, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem);
        ProductItem productItem2 = new ProductItem(3, "Gira", 1, 100, Status.UNCHECKED, shoppingList);
        shoppingList.removeProduct(productItem2);
        productsList = shoppingList.getProductItemList();
        assertEquals(1, productsList.size());
    }

    @Test(expected = RemoveShoppingListException.class)
    public void cantRemoveProductItemFromEmptyList() throws RemoveShoppingListException {
        ProductItem productItem = new ProductItem(1, "Pienas", 3, 15, Status.UNCHECKED, shoppingList);
        shoppingList.removeProduct(productItem);
        productsList = shoppingList.getProductItemList();
        assertEquals(0, productsList.size());
    }

    @Test
    public void sortByName() throws DublicateProductException, RemoveShoppingListException {
        ProductItem productItem = new ProductItem(1, "Pienas", 1, 15, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem);
        productsList = shoppingList.getProductItemList();
        assertEquals(1, productsList.size());
        ProductItem productItem1 = new ProductItem(2, "Alus", 1, 15, Status.UNCHECKED, shoppingList);
        shoppingList.addProductItem(productItem1);
        productsList = shoppingList.getProductItemList();
        assertEquals(2, productsList.size());
        shoppingList.sortProductByName();
        assertEquals(productItem1, productsList.get(0));
    }

   /* @Test
    public void getTotalPrice() throws DublicateProductException {
        ProductItem productItem = new ProductItem(2, "Pienas", 2, 15, "");
        shoppingList.addProductItem(productItem);
        productsList = shoppingList.getProductItemList();
        assertEquals(30, shoppingList.getTotalProductCount());
    }

    @Test
    public void getAllItemsCost() throws DublicateProductException {
        ProductItem productItem = new ProductItem(1, "Pienas", 3, 15, "");
        shoppingList.addProductItem(productItem);
        productsList = shoppingList.getProductItemList();
        assertEquals(1, productsList.size());
        ProductItem productItem1 = new ProductItem(2, "Alus", 2, 15, "");
        shoppingList.addProductItem(productItem1);
        productsList = shoppingList.getProductItemList();
        assertEquals(75, shoppingList.getAllItemsCount());
    }*/
}