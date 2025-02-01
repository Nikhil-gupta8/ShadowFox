import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Class representing an inventory item
class InventoryItem {
    String name;
    int quantity;

    public InventoryItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}

// Main Inventory Management System class
public class InventoryManagementSystem extends JFrame {
    private JTextField nameField;
    private JTextField quantityField;
    private JTextField searchField;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;
    private ArrayList<InventoryItem> inventoryList;

    public InventoryManagementSystem() {
        inventoryList = new ArrayList<>();
        setTitle("Inventory Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        nameField = new JTextField(15);
        quantityField = new JTextField(5);
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        inputPanel.add(new JLabel("Item Name:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);

        add(inputPanel, BorderLayout.NORTH);

        // Create table
        tableModel = new DefaultTableModel(new String[]{"Item Name", "Quantity"}, 0);
        inventoryTable = new JTable(tableModel);
        add(new JScrollPane(inventoryTable), BorderLayout.CENTER);

        // Create search panel
        JPanel searchPanel = new JPanel();
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.SOUTH);

        // Add action listeners
        addButton.addActionListener(e -> addItem());
        updateButton.addActionListener(e -> updateItem());
        deleteButton.addActionListener(e -> deleteItem());
        searchButton.addActionListener(e -> searchItem());
    }

    private void addItem() {
        String name = nameField.getText();
        int quantity;
        try {
            quantity = Integer.parseInt(quantityField.getText());
            InventoryItem item = new InventoryItem(name, quantity);
            inventoryList.add(item);
            tableModel.addRow(new Object[]{name, quantity});
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
        }
    }

    private void updateItem() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            String name = nameField.getText();
            int quantity;
            try {
                quantity = Integer.parseInt(quantityField.getText());
                inventoryList.get(selectedRow).name = name;
                inventoryList.get(selectedRow).quantity = quantity;
                tableModel.setValueAt(name, selectedRow, 0);
                tableModel.setValueAt(quantity, selectedRow, 1);
                clearFields();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid quantity.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to update.");
        }
    }

    private void deleteItem() {
        int selectedRow = inventoryTable.getSelectedRow();
        if (selectedRow >= 0) {
            inventoryList.remove(selectedRow);
            tableModel.removeRow(selectedRow);
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Please select an item to delete.");
        }
    }

    private void searchItem() {
        String searchText = searchField.getText().toLowerCase();
        tableModel.setRowCount(0); // Clear the table

        for (InventoryItem item : inventoryList) {
            if (item.name.toLowerCase().contains(searchText)) {
                tableModel.addRow(new Object[]{item.name, item.quantity});
            }
        }
    }

    private void clearFields() {
        nameField.setText("");
        quantityField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InventoryManagementSystem ims = new InventoryManagementSystem();
            ims.setVisible(true);
        });
    }
}