package shoppinglist;

import shoppinglist.data.ShoppingList;
import shoppinglist.data.Status;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Created by Romcikas on 12/17/2015.
 */
public class ShoppingListHibernatePersistenceTest {
    private static final String PERSISTENCE_UNIT_NAME = "Eclipselink_JPA";
    private EntityManagerFactory emfactory;
    private static EntityManager entitymanager;

    @Before
    public void setUp() throws Exception {
        emfactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        entitymanager = emfactory.createEntityManager();
        entitymanager.getTransaction().begin();

    }

    @Test
    public void checkFamily() {
        EntityManager entitymanager = emfactory.createEntityManager();
        // Go through each of the entities and print out each of their
        // messages, as well as the date on which it was created
        Query q = entitymanager.createQuery("select m from ShoppingList m");

        System.out.print(q);
        entitymanager.close();
    }
    @Test
    public void createList(){
        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setName("Test");
        shoppingList.setStatus(Status.UNCHECKED);
        entitymanager.persist(shoppingList);
        entitymanager.getTransaction().commit();
        entitymanager.close();

    }
}