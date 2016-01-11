package shoppinglist;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import shoppinglist.service.ShoppingListService;

/**
 * Created by Romcikas on 1/9/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class test {
    @Autowired
    private ShoppingListService shoppingListService;
    public void saveShoppingList(){

    }
}
