package shoppinglist.persistence.productitem;


import shoppinglist.data.ProductItem;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Romcikas on 12/19/2015.
 */
public class ProductItemHibernatePersistence implements ProductItemPersistance {
    EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
    EntityManager entitymanager = emfactory.createEntityManager();

    @Override
    public ProductItem saveProductItem(ProductItem productItem) {
        entitymanager.getTransaction().begin();
        entitymanager.persist(productItem);
        entitymanager.getTransaction().commit();
        return productItem;
    }

    @Override
    public ProductItem getProductItemById(int id) {
        ProductItem productItem = entitymanager.find(ProductItem.class, id);
        entitymanager.getTransaction().commit();
        return productItem;
    }

    @Override
    public void deleteProductItem(ProductItem productItem) {
        productItem = entitymanager.find(ProductItem.class, productItem.getProductId());
        entitymanager.getTransaction().begin();
        entitymanager.remove(productItem);
        entitymanager.getTransaction().commit();
    }

    @Override
    public ProductItem updateProductItem(ProductItem productItem) {
        entitymanager.getTransaction().begin();
        productItem = entitymanager.find(ProductItem.class, productItem.getProductId());
        entitymanager.getTransaction().commit();
        return productItem;
    }
}
