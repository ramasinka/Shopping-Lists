package shoppinglist.service;

import shoppinglist.data.ShoppingList;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Romcikas on 11/22/2015.
 */
public class WriteService {
    private File parent;
    private String child;

    public WriteService(File parent, String child) {
        this.parent = parent;
        this.child = child;
    }

    /*    public void writeToAndroid(ShoppingList shoppingList) {
            try {
                Gson gson = new Gson();
                String jsonProductItems = gson.toJson(shoppingList);
                String fname = shoppingList.getShoppingList() + ".txt";
                String root = Environment.getExternalStorageDirectory().toString();
                File myDir = new File(root + "/ShoppingList");
                myDir.mkdirs();
                File file = new File(myDir, fname);
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fileWriter);
                JSONObject obj = new JSONObject();
                obj.put("Id", shoppingList.getShoppingList());
                obj.put("Name", shoppingList.getName());
                obj.put("Status", shoppingList.getListStatus());

                obj.put("ProductList:",shoppingList.getProductItemList());
                bw.persistance(obj.toString());
                bw.flush();
                bw.close();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    public void writeShoppingList(ShoppingList shoppingList) {
        try {
            Gson gson = new Gson();
            String shoppingListData = gson.toJson(shoppingList);
            String fname = shoppingList.getListId() + ".txt";
            File myDir = new File(parent + child);
            myDir.mkdirs();
            File file = new File(myDir, fname);
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(shoppingListData);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
