import java.awt.FontFormatException;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.sql.*;

public class editorderitem extends javax.swing.JPanel {
    private java.awt.Font customFont;
    
    // MAY BAGO
    public editorderitem() {
        initComponents();
        loadFonts();
        loadProductIDs();

        if (customFont != null) {
            oID.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
            opid.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
            qua.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
            priceao.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
        }
        
         priceatorder.setEditable(true);
        productid.addActionListener(e -> fetchProductPrice());
    }

    public editorderitem(String orderID) {
        this(); 

        orderD.setText(orderID);
    }
    // HANGGANG DITO
    
     private void fetchProductPrice() {
        Object selected = productid.getSelectedItem();

        if (selected == null) {
            priceatorder.setText("");
            return;
        }

        String productIDText = selected.toString().trim();

        if (productIDText.isEmpty()) {
            priceatorder.setText("");
            return;
        }

        try {
            int productIDValue = Integer.parseInt(productIDText);
            
            if (productIDValue < 2000 || productIDValue > 2999) {
                priceatorder.setText("");
                return;
            }
            String query = "SELECT unit_price FROM Products WHERE product_id = ?";
            try (Connection conn = IFCDatabase.getConnection();
                 PreparedStatement pst = conn.prepareStatement(query)) {
                
                pst.setInt(1, productIDValue);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        double price = rs.getDouble("unit_price");
                        priceatorder.setText(String.format("%.2f", price)); 
                    } else {
                        priceatorder.setText("Not Found");
                    }
                }
            }
        } catch (NumberFormatException e) {
            priceatorder.setText(""); 
        } catch (Exception e) {
            priceatorder.setText("Error");
        }
    }
     
     private void loadProductIDs() {
    try {
        Connection conn = IFCDatabase.getConnection();

        String sql = "SELECT product_id FROM Products ORDER BY product_id";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        productid.removeAllItems();

        // optional blank value (recommended since you use it interactively)
        productid.addItem("");

        while (rs.next()) {
            productid.addItem(String.valueOf(rs.getInt("product_id")));
        }

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Failed to load product IDs: " + e.getMessage()
        );
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        quantity = new javax.swing.JTextField();
        orderD = new javax.swing.JTextField();
        clear = new javax.swing.JButton();
        save = new javax.swing.JButton();
        backtotable = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        opid = new javax.swing.JLabel();
        qua = new javax.swing.JLabel();
        oID = new javax.swing.JLabel();
        priceao = new javax.swing.JLabel();
        priceatorder = new javax.swing.JTextField();
        productid = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        mainbg = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        quantity.setFont(new java.awt.Font("Baskerville", 0, 24)); // NOI18N
        quantity.setBorder(null);
        add(quantity, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 400, 370, 50));

        orderD.setFont(new java.awt.Font("Baskerville", 0, 24)); // NOI18N
        orderD.setBorder(null);
        add(orderD, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 200, 370, 50));

        clear.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        clear.setText("Clear");
        clear.addActionListener(this::clearActionPerformed);
        add(clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 640, 120, 40));

        save.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        save.setText("Save");
        save.addActionListener(this::saveActionPerformed);
        add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 640, 120, 40));

        backtotable.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        backtotable.setText("Back to Table");
        backtotable.setToolTipText("");
        backtotable.addActionListener(this::backtotableActionPerformed);
        add(backtotable, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 640, 150, 40));

        delete.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        delete.setText("Delete");
        delete.addActionListener(this::deleteActionPerformed);
        add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 640, 120, 40));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/tfield.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 390, -1, 70));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/tfield.png"))); // NOI18N
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 190, -1, 70));

        opid.setFont(new java.awt.Font("Big Caslon", 0, 24)); // NOI18N
        opid.setForeground(new java.awt.Color(255, 255, 255));
        opid.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        opid.setText("Product ID:");
        add(opid, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 300, 180, 50));

        qua.setFont(new java.awt.Font("Big Caslon", 0, 24)); // NOI18N
        qua.setForeground(new java.awt.Color(255, 255, 255));
        qua.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        qua.setText("Quantity:");
        add(qua, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 400, 180, 50));

        oID.setFont(new java.awt.Font("Big Caslon", 0, 24)); // NOI18N
        oID.setForeground(new java.awt.Color(255, 255, 255));
        oID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        oID.setText("Order ID:");
        add(oID, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 170, 50));

        priceao.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        priceao.setForeground(new java.awt.Color(255, 255, 255));
        priceao.setText("Price at Order:");
        add(priceao, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 500, -1, -1));
        add(priceatorder, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 490, 390, 50));

        productid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(productid, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 300, 370, 50));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/tfield.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 290, -1, 70));

        mainbg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/mainbg.png"))); // NOI18N
        add(mainbg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, -1));
    }// </editor-fold>                        

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {                                       
        try {
        String orderIDText = orderD.getText().trim();
        Object selected = productid.getSelectedItem();
        if (selected == null || selected.toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Product ID is required.");
            return;
        }

String productIDText = selected.toString().trim();
        
        if (orderIDText.isEmpty() || productIDText.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Order ID and Product ID are required.");
            return;
        }
        
        if (!quantity.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,"Only Order ID and Product ID should have a value when deleting.");
            return;
        }

        int orderIDValue;
        try {
            orderIDValue = Integer.parseInt(orderIDText);
            if (orderIDValue < 3000) {
                JOptionPane.showMessageDialog(this,"Order ID must be 3000 or higher.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,"Order ID must be a valid number.");
            return;
        }

        int productIDValue;
        try {
            productIDValue = Integer.parseInt(productIDText);
            if (productIDValue < 2000 || productIDValue > 2999) {
                JOptionPane.showMessageDialog(this,"Product ID must be from 2000 to 2999.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,"Product ID must be a valid number.");
            return;
        }

        java.sql.Connection conn = IFCDatabase.getConnection();

        String checkSQL =
                "SELECT * FROM Order_Items "
                + "WHERE order_id = ? "
                + "AND product_id = ?";

        java.sql.PreparedStatement checkStmt = conn.prepareStatement(checkSQL);

        checkStmt.setInt(1, orderIDValue);
        checkStmt.setInt(2, productIDValue);

        java.sql.ResultSet rs = checkStmt.executeQuery();

        if (!rs.next()) {
            JOptionPane.showMessageDialog(this,"Order Item record does not exist.");
            conn.close();
            return;
        }

        int quantity = rs.getInt("quantity");
        double priceAtOrder = rs.getDouble("price_at_order");

        String message =
                "Are you sure you want to delete this information?\n\n"

                + "Order ID: " + orderIDValue + "\n"
                + "Product ID: " + productIDValue + "\n"
                + "Quantity: " + quantity + "\n"
                + "Price at Order: " + priceAtOrder;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                message,
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            conn.close();
            return;
        }

        String deleteSQL = "DELETE FROM Order_Items " + "WHERE order_id = ? " + "AND product_id = ?";

        java.sql.PreparedStatement deleteStmt = conn.prepareStatement(deleteSQL);

        deleteStmt.setInt(1, orderIDValue);
        deleteStmt.setInt(2, productIDValue);

        int rowsDeleted = deleteStmt.executeUpdate();
        if (rowsDeleted > 0) {
            JOptionPane.showMessageDialog(this,"Order Item deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this,"Delete failed.");
        }

        conn.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,"Error: " + e.getMessage());
        e.printStackTrace();
    }
    }                                      

    private void clearActionPerformed(java.awt.event.ActionEvent evt) {                                      
        int response = javax.swing.JOptionPane.showConfirmDialog(this,
            "This will clear all your inputs. Continue?",
            "Confirm Clear",
            javax.swing.JOptionPane.YES_NO_OPTION);

        if (response == javax.swing.JOptionPane.YES_OPTION) {
            orderD.setText("");
            if (productid.getItemCount() > 0) {
                productid.setSelectedIndex(0); // blank option if you added ""
            } else {
                productid.setSelectedIndex(-1);
            }
            quantity.setText("");
            
            javax.swing.JOptionPane.showMessageDialog(this, "Fields cleared successfully.");}
    }                                     

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {                                     
                                     
    try {
        java.sql.Connection conn = IFCDatabase.getConnection();

        String orderIDText = orderD.getText().trim();
        String quantityText = quantity.getText().trim();

        Object selected = productid.getSelectedItem();

        if (orderIDText.isEmpty()
                || selected == null
                || selected.toString().trim().isEmpty()
                || quantityText.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        String productIDText = selected.toString().trim();

        int orderIDValue;
        try {
            orderIDValue = Integer.parseInt(orderIDText);
            if (orderIDValue < 3000) {
                JOptionPane.showMessageDialog(this, "Order ID must be 3000 or above.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Order ID must be a valid number.");
            return;
        }

        int productIDValue;
        try {
            productIDValue = Integer.parseInt(productIDText);
            if (productIDValue < 2000 || productIDValue > 2999) {
                JOptionPane.showMessageDialog(this, "Product ID must be between 2000 and 2999.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Product ID must be a valid number.");
            return;
        }

        int quantityValue;
        try {
            quantityValue = Integer.parseInt(quantityText);
            if (quantityValue <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Quantity must be a valid number.");
            return;
        }

        String checkOrderSQL = "SELECT * FROM Orders WHERE order_id = ?";
        PreparedStatement checkOrder = conn.prepareStatement(checkOrderSQL);
        checkOrder.setInt(1, orderIDValue);
        ResultSet orderRS = checkOrder.executeQuery();

        if (!orderRS.next()) {
            JOptionPane.showMessageDialog(this, "Order ID does not exist.");
            return;
        }

        String checkProductSQL = "SELECT * FROM Products WHERE product_id = ?";
        PreparedStatement checkProduct = conn.prepareStatement(checkProductSQL);
        checkProduct.setInt(1, productIDValue);
        ResultSet productRS = checkProduct.executeQuery();

        if (!productRS.next()) {
            JOptionPane.showMessageDialog(this, "Product ID does not exist.");
            return;
        }

        String duplicateSQL =
                "SELECT * FROM Order_Items WHERE order_id = ? AND product_id = ?";

        PreparedStatement duplicateStmt = conn.prepareStatement(duplicateSQL);
        duplicateStmt.setInt(1, orderIDValue);
        duplicateStmt.setInt(2, productIDValue);

        ResultSet duplicateRS = duplicateStmt.executeQuery();

        if (duplicateRS.next()) {
            JOptionPane.showMessageDialog(this, "This Order Item record already exists.");
            return;
        }

        String summary =
                "Are you sure you want to save this?\n\n"
                + "Order ID: " + orderIDValue + "\n"
                + "Product ID: " + productIDValue + "\n"
                + "Quantity: " + quantityValue;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                summary,
                "Confirm Save",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String insertSQL =
                "INSERT INTO Order_Items (order_id, product_id, quantity, price_at_order) "
                + "VALUES (?, ?, ?, 0)";

        PreparedStatement pst = conn.prepareStatement(insertSQL);
        pst.setInt(1, orderIDValue);
        pst.setInt(2, productIDValue);
        pst.setInt(3, quantityValue);

        int rowsInserted = pst.executeUpdate();

        if (rowsInserted > 0) {

            JOptionPane.showMessageDialog(this, "Order Item saved successfully!");

            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Will you save another record with Order ID " + orderIDValue + "?",
                    "Add Another Item",
                    JOptionPane.YES_NO_OPTION
            );

            if (choice == JOptionPane.YES_OPTION) {
                productid.setSelectedIndex(0);
                quantity.setText("");
                priceatorder.setText("");
                productid.requestFocus();
            } else {
                javax.swing.JFrame frame =
                        (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

                frame.setContentPane(new edittransaction(String.valueOf(orderIDValue)));
                frame.revalidate();
                frame.repaint();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Save failed.");
        }

        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Save failed: " + e.getMessage());
        e.printStackTrace();
    }

    }                                    
    
    // MAY BAGO 
    private void backtotableActionPerformed(java.awt.event.ActionEvent evt) {                                            
        javax.swing.JFrame frame =
        (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        orderitem orderItemPage = new orderitem();
        frame.setContentPane(orderItemPage);

        orderItemPage.revalidate();
        orderItemPage.repaint();           
    }                                           
    // HANGGANG DITO

    // Variables declaration - do not modify                     
    private javax.swing.JButton backtotable;
    private javax.swing.JButton clear;
    private javax.swing.JButton delete;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel mainbg;
    private javax.swing.JLabel oID;
    private javax.swing.JLabel opid;
    private javax.swing.JTextField orderD;
    private javax.swing.JLabel priceao;
    private javax.swing.JTextField priceatorder;
    private javax.swing.JComboBox<String> productid;
    private javax.swing.JLabel qua;
    private javax.swing.JTextField quantity;
    private javax.swing.JButton save;
    // End of variables declaration                   
    
    private void loadFonts() {
        try {
            customFont = java.awt.Font.createFont(
                    java.awt.Font.TRUETYPE_FONT,
                    getClass().getResourceAsStream("/ifccrafts/assets/MyFont.ttf")
            );
        } catch (FontFormatException | IOException e) {
            customFont = new java.awt.Font("Serif", java.awt.Font.PLAIN, 18);
        }
    }
}
