package shoppinglist.persistence.shoppinglist;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shoppinglist.data.ShoppingList;
import shoppinglist.exceptions.ShoppingListNotFoundException;

import java.util.List;

/**
 * Created by Ramasinka on 2015.10.04.
 */
public interface ShoppingListPersistance {

    public ShoppingList saveShoppingList(ShoppingList shoppingList);

    public ShoppingList getShoppingListById(int id);

    public List<ShoppingList> getAllShoppingLists();

    public void deleteShoppingList(ShoppingList shoppingList) ;

    public ShoppingList updateShoppingList(ShoppingList shoppingList) ;

}
