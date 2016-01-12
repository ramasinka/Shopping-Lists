package shoppinglist.service;

import org.springframework.beans.factory.annotation.Autowired;
import shoppinglist.data.ProductItem;
import shoppinglist.data.ShoppingList;
import shoppinglist.persistence.productitem.ProductItemPersistance;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramasinka on 2015.08.17.
 */
public class ProductItemService {
    @Autowired
    private ProductItemPersistance productItemPersistance;
    private List<ProductItem> productItems = new ArrayList<ProductItem>();
    @Autowired
    public ProductItemService(ProductItemPersistance productItemPersistance) {
        this.productItemPersistance = productItemPersistance;
    }

    public ProductItem createProductItem(ProductItem productItem) {
        return productItemPersistance.saveProductItem(productItem);
    }

    public void deleteProductItem(ProductItem productItem) {

        productItemPersistance.deleteProductItem(productItem);
    }

    public ProductItem updateProductItem(ProductItem productItem) {
        return productItemPersistance.updateProductItem(productItem);
    }
}


