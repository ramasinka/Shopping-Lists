package shoppinglist.data;

import javax.persistence.*;
import java.util.Comparator;

/**
 * Created by Ramasinka on 2015.08.17.
 */
@Entity
public class ProductItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    ShoppingList shoppingList;
    private String pname;
    private int pamount;
    private int pprice;
    private Status pstatus;
    public ProductItem(){}

    public ProductItem(int id, String productName, int amount, int price, Status productStatus, ShoppingList shoppingList) {

        this.id = id;
        this.pname = productName;
        this.pamount = amount;
        this.pprice = price;
        this.pstatus = productStatus;
        this.shoppingList = shoppingList;
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid product amount " + amount);
        }
        if (pname == "") {
            throw new IllegalArgumentException("Insert product item name");
        }
    }

    public void setShoppingList(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    public ShoppingList getShoppingList() {
        return shoppingList;
    }

    public int getProductId() {
        return id;
    }

    public String getProductName() {
        return pname;
    }

    public void setProductName(String name) {
        this.pname = name;
    }

    public int getAmount() {
        return pamount;
    }

    public void setAmount(int amount) {
        this.pamount = amount;
    }

    public int getPrice() {
        return pprice;
    }

    public void setPrice(int price) {
        this.pprice = price;
    }

    public Status getStatus() {
        return pstatus;
    }

    public void setStatus(Status status) {
        this.pstatus = status;
    }

    public String toString() {
        return "id: " + id + " name: " + pname + " status: " + pstatus;
    }

    public static Comparator<ProductItem> COMPARE_BY_NAME = new Comparator<ProductItem>() {
        @Override
        public int compare(ProductItem one, ProductItem other) {
            return one.pname.compareTo(other.pname);
        }
    };
    public static Comparator<ProductItem> COMPARE_BY_STATUS = new Comparator<ProductItem>() {
        @Override
        public int compare(ProductItem one, ProductItem other) {
            return one.pstatus.compareTo(other.pstatus);
        }
    };

    @Override
    public boolean equals(Object o) {
        return o instanceof ProductItem && ((ProductItem) o).getProductId() == id;
    }
}
