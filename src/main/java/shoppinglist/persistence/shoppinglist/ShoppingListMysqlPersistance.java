package shoppinglist.persistence.shoppinglist;



import shoppinglist.data.ShoppingList;
import shoppinglist.data.Status;
import shoppinglist.exceptions.DublicateProductException;
import shoppinglist.exceptions.RemoveShoppingListException;
import shoppinglist.service.ConnectionToDatabaseService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romcikas on 12/3/2015.
 */
public class ShoppingListMysqlPersistance implements ShoppingListPersistance {
    ConnectionToDatabaseService connectionToDatabaseService;
    Connection connectToMysql = null;
    PreparedStatement preparedStatement = null;
    Statement stmt = null;


    public ShoppingListMysqlPersistance(ConnectionToDatabaseService connectionToDatabaseService) {
        this.connectionToDatabaseService = connectionToDatabaseService;
    }

    public String getShoppingListByName(String name) {
        String selectTableSQL = "SELECT ID,name FROM Shoppinglist ";
        try {
            Connection connectToMysql = connectionToDatabaseService.connectToMysql();
            stmt = connectToMysql.createStatement();
            ResultSet rs = stmt.executeQuery(selectTableSQL);
            while (rs.next()) {
                name = rs.getString("Name");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return name;
    }

    public int calculateListId() throws RemoveShoppingListException {
        int sid = 0;
        String selectTableSQL = "SELECT ID FROM Shoppinglist ";
        try {
            Connection connectToMysql = connectionToDatabaseService.connectToMysql();
            stmt = connectToMysql.createStatement();
            ResultSet rs = stmt.executeQuery(selectTableSQL);
            while (rs.next()) {
                sid = rs.getInt("ID");
                sid = sid + 1;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            throw new RemoveShoppingListException("Failed to make connection to database");
        }
        return sid;
    }

    @Override
    public ShoppingList saveShoppingList(ShoppingList shoppingList) {
        String name;
        String insertTableSQL = "INSERT INTO SHOPPINGLIST"
                + "(ID, NAME, STATUS) " + "VALUES"
                + "(?,?,?)";
        String selectTableSQL = "SELECT ID,name FROM Shoppinglist ";
        try {
            Connection connectToMysql = connectionToDatabaseService.connectToMysql();
            preparedStatement = connectToMysql.prepareStatement(insertTableSQL);
            preparedStatement.setInt(1, calculateListId());
            ResultSet rs = preparedStatement.executeQuery(selectTableSQL);
            while (rs.next()) {
                name = rs.getString("Name");
                if (name.equals(shoppingList.getName())) {
                    throw new DublicateProductException("You can't add shopping list. Shopping list exist");
                }
            }
            preparedStatement.setString(2, shoppingList.getListName());
            preparedStatement.setString(3, String.valueOf(shoppingList.getListStatus()));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (RemoveShoppingListException e) {
            e.printStackTrace();
        } catch (DublicateProductException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoppingList;
    }

    @Override
    public ShoppingList getShoppingListById(int id) {
        ShoppingList shoppingList = null;
        try {
            String selectTableSQL = "SELECT ID, NAME, STATUS FROM shoppinglist WHERE ID ='" + id + "'";
            Connection connectToMysql = connectionToDatabaseService.connectToMysql();
            preparedStatement = connectToMysql.prepareStatement(selectTableSQL);
            ResultSet rs = preparedStatement.executeQuery(selectTableSQL);
            while (rs.next()) {
                shoppingList = new ShoppingList(id);
                shoppingList.setId(rs.getInt("ID"));
                shoppingList.setName(rs.getString("Name"));
                shoppingList.setStatus((Status.valueOf(rs.getString("Status"))));
                //return shoppingList;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        return shoppingList;
    }

    @Override
    public List<ShoppingList> getAllShoppingLists() {
        List<ShoppingList> shoppingLists = new ArrayList<>();

        String selectTableSQL = "SELECT ID,Name FROM Shoppinglist ";
        try {
            Connection connectToMysql = connectionToDatabaseService.connectToMysql();
            stmt = connectToMysql.createStatement();
            ResultSet rs = stmt.executeQuery(selectTableSQL);
            while (rs.next()) {
                ShoppingList shoppingList = new ShoppingList(rs.getInt("ID"));
                shoppingList.setName(rs.getString("Name"));
                shoppingLists.add(shoppingList);
            }
            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoppingLists;
    }

    @Override
    public void deleteShoppingList(ShoppingList shoppingList) {
        int sid = shoppingList.getListId();
        String deleteTableSql = "DELETE FROM Shoppinglist WHERE ID  = '" + sid + "'";
        try {
            Connection connectToMysql = connectionToDatabaseService.connectToMysql();
            preparedStatement = connectToMysql.prepareStatement(deleteTableSql);
            preparedStatement.executeUpdate(deleteTableSql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ShoppingList updateShoppingList(ShoppingList shoppingList) {
        String updateTableSQL = "UPDATE shoppinglist"
                + " SET Name = '" + shoppingList.getListName() + "'"
                + " WHERE ID = '" + shoppingList.getListId() + "'";
        try {
            Connection connectToMysql = connectionToDatabaseService.connectToMysql();
            stmt = connectToMysql.createStatement();
            stmt.execute(updateTableSQL);
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return shoppingList;
    }
}
