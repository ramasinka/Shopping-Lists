package shoppinglist.data;


import shoppinglist.exceptions.RemoveShoppingListException;
import shoppinglist.persistence.productitem.ProductItemMysqlPersistance;
import shoppinglist.service.ConnectionToDatabaseService;
import shoppinglist.service.WriteService;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ramasinka on 2015.08.17.
 */
@XmlRootElement
@Entity
public class ShoppingList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    @OneToMany(mappedBy = "shoppingList")
    private List<ProductItem> productsList = new ArrayList<ProductItem>();
    private Status status;

    public ShoppingList(int id) {
        this.id = id;
    }

    public ShoppingList() {
    }

    public String getListName() {
        return name;
    }

    public Status getListStatus() {
        return status;
    }

    public int getListId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String sname) {
        this.name = sname;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void addProductItem(ProductItem productItem){
        ConnectionToDatabaseService connectionToDatabaseService = new ConnectionToDatabaseService();
        ProductItemMysqlPersistance productItemMysqlPersistance = new ProductItemMysqlPersistance(connectionToDatabaseService);
        File file = new File("C:/");
        WriteService writeService = new WriteService(file, "ShoppingListServiceTest");
        productsList.add(productItem);
        productItemMysqlPersistance.saveProductItem(productItem);
        // writeService.writeShoppingList(ShoppingList.this);
    }

    public List<ProductItem> getProductItemList() {
        return productsList;
    }

    public void setProductsList(List<ProductItem> productsList) {
        this.productsList = productsList;
    }

    public void removeProduct(ProductItem productItem) throws RemoveShoppingListException {
        if (productsList.size() == 0) {
            throw new RemoveShoppingListException("You can't remove item from list. List is empty");
        } else {
            if (productsList.remove(productItem) == false) {
                throw new RemoveShoppingListException("You can't remove item from list. Item not found");
            }
        }
    }


/*    public int getTotalProductCount() {
        for (ProductItem productItem : productsList) {
            totalPrice = productItem.getPrice() * productItem.getAmount();
        }
        return totalPrice;
    }

    public int getAllItemsCount() {
        for (ProductItem productItem : productsList) {
            allItemsPrice += productItem.getPrice() * productItem.getAmount();
        }
        return allItemsPrice;
    }*/

    public void sortProductByName() {
        Collections.sort(productsList, ProductItem.COMPARE_BY_NAME);
    }

    public void sortProductByStatus() {
        Collections.sort(productsList, ProductItem.COMPARE_BY_STATUS);

    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ShoppingList && ((ShoppingList) o).getListId() == id;
    }

    @Override
    public String toString() {
        return "ShoppingList [ID=" + id + ", Name=" + name + ", Status=" + status + ", list="
                + productsList + "]";
    }
}


