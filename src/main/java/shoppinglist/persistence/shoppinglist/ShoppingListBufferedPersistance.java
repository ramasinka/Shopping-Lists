package shoppinglist.persistence.shoppinglist;



import shoppinglist.data.ShoppingList;

import java.util.List;

/**
 * Created by Ramasinka on 2015.10.07.
 */
public class ShoppingListBufferedPersistance implements ShoppingListPersistance {

    @Override
    public ShoppingList saveShoppingList(ShoppingList shoppingList) {
        return null;
    }

    @Override
    public ShoppingList getShoppingListById(int id) {
        return null;
    }

    @Override
    public List<ShoppingList> getAllShoppingLists() {
        return null;
    }

    @Override
    public void deleteShoppingList(ShoppingList shoppingList) {

    }

    @Override
    public ShoppingList updateShoppingList(ShoppingList shoppingList) {
        return null;
    }




        /*List<ProductItem> productItems = shoppingList.getProductItemList();
        File file = new File("shopping_list.txt");
        Writer fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            fileWriter = new FileWriter(file);
            bufferedWriter = new BufferedWriter(fileWriter);
            for (ProductItem productItem : productItems) {
                bufferedWriter.persistance("Product ID: " + productItem.getProductId() + " Product name: " + productItem.getProductName()
                        + " Product status: " + productItem.getStatus());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null && fileWriter != null) {
                try {
                    bufferedWriter.close();
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/

}
