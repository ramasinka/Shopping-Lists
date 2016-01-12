package shoppinglist.gui;

import shoppinglist.data.ProductItem;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Romcikas on 1/10/2016.
 */
public class GUI extends JFrame implements ActionListener {

    private JButton createShoppingListButton, deleteShoppingListButton, itemsButton, createItemButton, deleteItemButton;
    private JTextField shoppingListName, productitemName;
    private JTable table;
    private JTextArea textArea;
    public Object[] columns;
    public DefaultTableModel defaultTableModel;
    private ShoppingListController shoppingListController;
    private java.util.List<ProductItem> productsList = new ArrayList<ProductItem>();

    public GUI(final ShoppingListController db) {

        super();
        this.shoppingListController = db;

        columns = new Object[]{"ShoppingList ID", "ShoppingList Name"};
        defaultTableModel = new DefaultTableModel(shoppingListController.getAllShoppingLists(), columns) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(defaultTableModel);

        table.setRowHeight(table.getRowHeight() + 8);
        table.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        createShoppingListButton = new JButton("Create");
        deleteShoppingListButton = new JButton("Delete");
        createItemButton = new JButton("CreateItem");
        deleteItemButton = new JButton("DeleteItem");
        itemsButton = new JButton("Show List items");

        createShoppingListButton.addActionListener(this);
        deleteShoppingListButton.addActionListener(this);
        createItemButton.addActionListener(this);
        deleteItemButton.addActionListener(this);
        itemsButton.addActionListener(this);

        shoppingListName = new JTextField("ShoppingList name", 15);
        productitemName = new JTextField("ProductItem name", 15);
        JPanel inputPanel = new JPanel();
        inputPanel.add(textArea);
        inputPanel.add(productitemName);
        inputPanel.add(createItemButton);
        inputPanel.add(deleteItemButton);
        inputPanel.add(shoppingListName);
        inputPanel.add(createShoppingListButton);
        inputPanel.add(deleteShoppingListButton);
        inputPanel.add(itemsButton);
        // set the input panel to the bottom and the error panel to the top
        this.add(inputPanel, BorderLayout.SOUTH);

        // Center the ID column in the table
        DefaultTableCellRenderer centerColumns = new DefaultTableCellRenderer();
        centerColumns.setHorizontalAlignment(JLabel.CENTER);
        //TableColumn tc = table.getColumn("Room number");
        // tc.setCellRenderer(centerColumns);

        setColumnWidths(columns, 10, 50, 50, 50);
        setSize(1110, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Shopping list");
        setVisible(true);
        table.getModel().addTableModelListener(new TableModelListener() {

            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getColumn() > 0) {
                    int row = e.getFirstRow();

                }
            }
        });
    }

    public void refreshRoomTable() {
        Object[][] foundEntries = shoppingListController.getAllShoppingLists();
        int rowCount = defaultTableModel.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            defaultTableModel.removeRow(i);
        }
        for (Object[] entry : foundEntries) {
            defaultTableModel.addRow(entry);
        }
    }
    public void refreshShoppingListItems(){

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createShoppingListButton) {
            if (shoppingListController.createShoppingList(shoppingListName.getText()) == null) {
                JOptionPane.showMessageDialog(this, "Check In failed. All Hotel rooms are occupied");
            } else {
                refreshRoomTable();
            }
        } else if (e.getSource() == deleteShoppingListButton) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please, select a row to checkOut guest.", "CheckOut",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                shoppingListController.deleteShoppingList((int) table.getModel().getValueAt(selectedRow, 0));
                refreshRoomTable();
            }
        } else if (e.getSource() == createItemButton) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow < 0) {
                JOptionPane.showMessageDialog(this, "Please, select a row to checkOut guest.", "CheckOut",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                ProductItem productItem = new ProductItem();
                productItem.setProductName(productitemName.getText());
                productItem.setShoppingList(shoppingListController.getShoppingListById((int) table.getModel().getValueAt(selectedRow, 0)));
                shoppingListController.createProductItem(productItem);
                productsList.add(productItem);
            }
        } else if (e.getSource() == itemsButton) {
            int selectedRow = table.getSelectedRow();
            productsList = shoppingListController.getAllProductItems((int) table.getModel().getValueAt(selectedRow, 0));
            textArea.setText("");
            for (ProductItem item : productsList) {
                textArea.append(item.toString());
                textArea.append("\n");
                textArea.revalidate();
                refreshRoomTable();
            }
        }
    }

    public void setColumnWidths(Object[] columns, int... widths) {
        TableColumn column;
        for (int i = 0; i < columns.length; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(widths[i]);
        }
    }
}