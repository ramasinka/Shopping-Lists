package shoppinglist;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.LoggerFactory;
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

        ShoppingListController shoppingListController = (ShoppingListController) context.getBean("shoppingListController");

        new GUI(shoppingListController);
    }
}
