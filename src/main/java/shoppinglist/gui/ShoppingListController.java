package shoppinglist.gui;

import shoppinglist.data.ProductItem;
import shoppinglist.data.ShoppingList;
import shoppinglist.data.Status;
import shoppinglist.persistence.shoppinglist.ShoppingListPersistance;
import shoppinglist.service.ProductItemService;
import shoppinglist.service.ShoppingListService;

import java.util.List;

/**
 * Created by Romcikas on 1/10/2016.
 */
public class ShoppingListController {
    private ShoppingListService shoppingListService;
    private ProductItemService productItemService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    public void setProductItemService(ProductItemService productItemService){
        this.productItemService = productItemService;
    }
    public Object[][] getAllShoppingLists() {
        List<ShoppingList> shoppingLists = shoppingListService.getAllShoppingLists();
        return entriesToArray(shoppingLists);
    }

    public ShoppingList createShoppingList(String name) {
        return shoppingListService.createShoppingList(name);
    }

    public void deleteShoppingList(int id) {
        ShoppingList shoppingList = shoppingListService.getShoppingListById(id);
        shoppingListService.deleteShoppingList(shoppingList);
    }

    public void updateShoppingList(int id) {
        ShoppingList shoppingList = shoppingListService.getShoppingListById(id);
        shoppingListService.updateShoppingList(shoppingList);
    }

    public ProductItem createProductItem(ProductItem productItem) {
        return productItemService.createProductItem(productItem);
    }
/*    public List<ProductItem> getAllProductItems(){
        return productItemService.getProductItems();
    }*/

    private Object[][] entriesToArray(List<ShoppingList> shoppingLists) {
        Object[][] entriesArray = new Object[shoppingLists.size()][];
        int row = 0;
        for (ShoppingList r : shoppingLists) {
            entriesArray[row] = new Object[4];
            entriesArray[row][0] = r.getListId();
            if (r.getListStatus() == Status.CHECKED) {
                entriesArray[row][1] = "";
                entriesArray[row][2] = "";
            } else {
                entriesArray[row][1] = r.getListName();
            }
            row++;
        }
        return entriesArray;
    }
}
