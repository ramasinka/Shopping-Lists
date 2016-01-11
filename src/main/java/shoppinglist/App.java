package shoppinglist;

import shoppinglist.data.ShoppingList;
import shoppinglist.gui.GUI;
import shoppinglist.gui.ShoppingListController;
import shoppinglist.persistence.shoppinglist.ShoppingListPersistance;
import shoppinglist.service.ShoppingListService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        ApplicationContext context =
                new ClassPathXmlApplicationContext("shoppinglist.xml");

        ShoppingListService shoppingListService = (ShoppingListService) context.getBean("shoppinglist-service");

        new GUI(new ShoppingListController(shoppingListService));
    }
}
