package shoppinglist.persistence.shoppinglist;

import shoppinglist.data.ShoppingList;
import shoppinglist.exceptions.ShoppingListNotFoundException;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ramasinka on 2015.10.07.
 */
public class ShoppingListJsonPersistance implements ShoppingListPersistance {
    private File parent;
    private File dir;
    private String child;

    public ShoppingListJsonPersistance(File parent, String child) {
        this.parent = parent;
        this.child = child;
        this.dir = new File(parent, child);
    }

    class NameFilter implements FilenameFilter {
        @Override
        public boolean accept(File parent, String child) {
            return child.endsWith(".txt");
        }

        ;
    }

    public int calculateId() {
        int sid = 1;
        int largest = -1;
        int txtText = 4;
        String name;
        String nameWithoutExtention;
        File dir = new File(parent, child);
        for (File aFile : dir.listFiles(new NameFilter())) {
            name = aFile.getName();
            nameWithoutExtention = name.substring(0, name.length() - txtText);
            sid = Integer.parseInt(nameWithoutExtention);
            if (sid > largest) {
                largest = sid;
            }
        }
        return largest + 1;
    }

    @Override
    public ShoppingList saveShoppingList(ShoppingList shoppingList)   {
        try {
            shoppingList.setId(calculateId());
            Gson gson = new Gson();
            String shoppingListData = gson.toJson(shoppingList);
            String fname = calculateId() + ".txt";
            File myDir = new File(parent, child);
            myDir.mkdirs();
            File file = new File(myDir, fname);
            FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fileWriter);
            bw.write(shoppingListData);
            bw.flush();
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return shoppingList;
    }

    @Override
    public ShoppingList getShoppingListById(int id) {
        String name;
        String nameWithoutExtention;
        ShoppingList shoppingList = null;
        int txtText = 4;

        Gson gson = new Gson();
        try {
            for (File aFile : dir.listFiles(new NameFilter())) {
                name = aFile.getName();
                nameWithoutExtention = name.substring(0, name.length() - txtText);
                BufferedReader buffered = new BufferedReader(new FileReader(aFile));
                if (id == Integer.parseInt(nameWithoutExtention)) {
                    shoppingList = gson.fromJson(buffered, ShoppingList.class);
                    buffered.close();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException();
        }
        return shoppingList;
    }

    @Override
    public List<ShoppingList> getAllShoppingLists() {
        List<ShoppingList> shoppingLists = new ArrayList<ShoppingList>();
        Gson gson = new Gson();
        try {
            for (File aFile : dir.listFiles(new NameFilter())) {
                FileReader reader = new FileReader(aFile);
                ShoppingList shoppingList = gson.fromJson(reader, ShoppingList.class);
                shoppingLists.add(shoppingList);
                reader.close();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return shoppingLists;
    }

    @Override
    public void deleteShoppingList(ShoppingList shoppingList)   {
        Gson gson = new Gson();
        int sid = shoppingList.getListId();
        int id = 0;
        int txtText = 4;
        String name;
        String nameWithoutExtention;
        try {
            for (File aFile : dir.listFiles(new NameFilter())) {
                name = aFile.getName();
                nameWithoutExtention = name.substring(0, name.length() - txtText);
                if (sid == Integer.parseInt(nameWithoutExtention)) {
                    FileReader reader = new FileReader(aFile);
                    shoppingList = gson.fromJson(reader, ShoppingList.class);
                    id = shoppingList.getListId();
                    reader.close();
                    if (id == sid) {
                        aFile.delete();
                    }
                }
            }
        } catch (IOException e) {
        }
    }

    @Override
    public ShoppingList updateShoppingList(ShoppingList shoppingList) {
        Gson gson = new Gson();
        String name;
        String nameWithoutExtention;
        int txtText = 4;
        int sid = shoppingList.getListId();
        try {
            String shoppingListData = gson.toJson(shoppingList);
            for (File aFile : dir.listFiles(new NameFilter())) {
                name = aFile.getName();
                nameWithoutExtention = name.substring(0, name.length() - txtText);
                FileWriter fileWriter = new FileWriter(aFile, true);
                BufferedWriter bw = new BufferedWriter(fileWriter);
                if (sid == Integer.parseInt(nameWithoutExtention)) {
                    bw.write(shoppingListData);
                    bw.flush();
                    bw.close();
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return shoppingList;
    }
}