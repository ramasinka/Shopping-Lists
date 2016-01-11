package shoppinglist.service;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static EntityManagerFactory emfactory;
    private static EntityManager entitymanager;

    public void hibernateManager() {
        emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        entitymanager = emfactory.createEntityManager();
    }
}



