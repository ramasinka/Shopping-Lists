package shoppinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import shoppinglist.service.ShoppingListService;

/**
 * Created by Romcikas on 1/9/2016.
 */
@ContextConfiguration
public class ShoppingListServiceTest {
    @Autowired
    private ShoppingListService shoppingListService;

    public void test() {

    }
}
