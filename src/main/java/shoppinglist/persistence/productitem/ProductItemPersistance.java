package shoppinglist.persistence.productitem;


import shoppinglist.data.ProductItem;

/**
 * Created by Romcikas on 12/9/2015.
 */
public interface ProductItemPersistance {
    public ProductItem saveProductItem(ProductItem productItem) ;

    public ProductItem getProductItemById(int id);

    public void deleteProductItem(ProductItem productItem);

    public ProductItem updateProductItem(ProductItem productItem);
}
