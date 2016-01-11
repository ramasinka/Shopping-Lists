package shoppinglist.persistence.productitem;



import shoppinglist.data.ProductItem;
import shoppinglist.data.ShoppingList;
import shoppinglist.data.Status;
import shoppinglist.exceptions.RemoveShoppingListException;
import shoppinglist.service.ConnectionToDatabaseService;

import java.sql.*;

/**
 * Created by Romcikas on 12/9/2015.
 */
public class ProductItemMysqlPersistance implements ProductItemPersistance {
    ConnectionToDatabaseService connectionToDatabaseService;
    Connection connectToMysql = null;
    PreparedStatement preparedStatement = null;
    Statement stmt = null;
    ShoppingList shoppingList;

    public ProductItemMysqlPersistance(ConnectionToDatabaseService connectionToDatabaseService) {
        this.connectionToDatabaseService = connectionToDatabaseService;
    }

    public int calculateItemId() throws RemoveShoppingListException {
        int sid = 0;
        String selectTableSQL = "SELECT ID FROM Productitem ";
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

    public void createPreparedStatment(String sqlTable) {
        Connection connectToMysql = null;
        try {
            connectToMysql = connectionToDatabaseService.connectToMysql();
            preparedStatement = connectToMysql.prepareStatement(sqlTable);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void performUpdate(String sqlTable, ProductItem productItem) {
        Connection connectToMysql = null;
        try {
            connectToMysql = connectionToDatabaseService.connectToMysql();
            preparedStatement = connectToMysql.prepareStatement(sqlTable);
            preparedStatement.setObject(1, calculateItemId());
            preparedStatement.setObject(2, productItem.getShoppingList().getListId());
            preparedStatement.setObject(3, productItem.getProductName());
            preparedStatement.setObject(4, productItem.getAmount());
            preparedStatement.setObject(5, productItem.getPrice());
            preparedStatement.setObject(6, String.valueOf(productItem.getStatus()));
            preparedStatement.executeUpdate();
        } catch (RemoveShoppingListException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductItem saveProductItem(ProductItem productItem)  {
        String insertTableSQL = "INSERT INTO productitem"
                + "(ID,SHOPPINGLIST_ID, NAME, AMOUNT, PRICE, STATUS) " + "VALUES"
                + "(?,?,?,?,?,?)";
        performUpdate(insertTableSQL, productItem);
        return productItem;
    }

    @Override
    public ProductItem getProductItemById(int id) {
        String selectTableSql = "SELECT ID,SHOPPINGLIST_ID, NAME, AMOUNT, PRICE, STATUS FROM productitem WHERE ID ='" + id + "'";
        ProductItem productItem = null;
        try {
            createPreparedStatment(selectTableSql);
            ResultSet rs = preparedStatement.executeQuery(selectTableSql);
            while (rs.next()) {
                productItem = new ProductItem(rs.getInt("ID"), rs.getString("NAME"), rs.getInt("AMOUNT"), rs.getInt("PRICE"), Status.valueOf(rs.getString("STATUS")), new ShoppingList(rs.getInt("SHOPPINGLIST_ID")));
                return productItem;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productItem;
    }

    @Override
    public void deleteProductItem(ProductItem productItem) {
        String deleteTableSql = "DELETE FROM productitem WHERE ID = ?";
        try {
            createPreparedStatment(deleteTableSql);
            preparedStatement.setInt(1, productItem.getProductId());
            preparedStatement.executeUpdate();
            productItem.getShoppingList().removeProduct(productItem);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RemoveShoppingListException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProductItem updateProductItem(ProductItem productItem) {
        String updateTableSQL = "UPDATE productitem SET Name = ? ,  Amount = ?,  Price = ?,  Status = ?"
                + " WHERE ID = ?";
        try {
            createPreparedStatment(updateTableSQL);
            preparedStatement.setString(1, productItem.getProductName());
            preparedStatement.setInt(2, productItem.getAmount());
            preparedStatement.setInt(3, productItem.getPrice());
            preparedStatement.setString(4, String.valueOf(productItem.getStatus()));
            preparedStatement.setInt(5, productItem.getProductId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productItem;
    }
}
