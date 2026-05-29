import java.awt.FontFormatException;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.sql.*;

public class editorder extends javax.swing.JPanel {
    private java.awt.Font customFont;

    public editorder() {
        initComponents();
        loadFonts();
        
         loadCustomerIDs(); 
         loadAffiliateIDs();
        
        ((javax.swing.JTextField) orderdateDateChooser.getDateEditor().getUiComponent()).setEditable(false);
        ((javax.swing.JTextField) duedateDateChooser.getDateEditor().getUiComponent()).setEditable(false);

        oID.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
        odate.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
        oduedate.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
        ostat.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
        ocID.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
        oaID.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
        
        generateNextOrderID();
    }

    private void loadCustomerIDs() {
    try {
        java.sql.Connection conn = IFCDatabase.getConnection();

        String sql = "SELECT customer_id FROM Customers ORDER BY customer_id";
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();

        customerid.removeAllItems();

        while (rs.next()) {
            customerid.addItem(String.valueOf(rs.getInt("customer_id")));
        }

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Failed to load customers: " + e.getMessage());
    }
}
    private void loadAffiliateIDs() {
    try {
        java.sql.Connection conn = IFCDatabase.getConnection();

        String sql = "SELECT affiliate_id FROM Affiliates ORDER BY affiliate_id";
        java.sql.PreparedStatement pst = conn.prepareStatement(sql);
        java.sql.ResultSet rs = pst.executeQuery();

        affiliateid.removeAllItems();

        // Optional: allow empty selection
        affiliateid.addItem(""); 

        while (rs.next()) {
            affiliateid.addItem(rs.getString("affiliate_id"));
        }

        rs.close();
        pst.close();
        conn.close();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Failed to load affiliates: " + e.getMessage());
    }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        progress = new javax.swing.ButtonGroup();
        orderID = new javax.swing.JTextField();
        clear = new javax.swing.JButton();
        duedateDateChooser = new com.toedter.calendar.JDateChooser();
        save = new javax.swing.JButton();
        backtotable = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        odate = new javax.swing.JLabel();
        oduedate = new javax.swing.JLabel();
        ocID = new javax.swing.JLabel();
        oID = new javax.swing.JLabel();
        ostat = new javax.swing.JLabel();
        oaID = new javax.swing.JLabel();
        done = new javax.swing.JRadioButton();
        notstarted = new javax.swing.JRadioButton();
        inprogress = new javax.swing.JRadioButton();
        orderdateDateChooser = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        customerid = new javax.swing.JComboBox<>();
        producttype = new javax.swing.JLabel();
        affiliateid = new javax.swing.JComboBox<>();
        producttype1 = new javax.swing.JLabel();
        mainbg = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        orderID.setFont(new java.awt.Font("Baskerville", 0, 24)); // NOI18N
        orderID.setBorder(null);
        add(orderID, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, 370, 50));

        clear.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        clear.setText("Clear");
        clear.addActionListener(this::clearActionPerformed);
        add(clear, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 660, 120, 40));
        add(duedateDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 320, 350, 50));

        save.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        save.setText("Save");
        save.addActionListener(this::saveActionPerformed);
        add(save, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 660, 120, 40));

        backtotable.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        backtotable.setText("Back to Table");
        backtotable.setToolTipText("");
        backtotable.addActionListener(this::backtotableActionPerformed);
        add(backtotable, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 660, 150, 40));

        delete.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        delete.setText("Delete");
        delete.addActionListener(this::deleteActionPerformed);
        add(delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 660, 120, 40));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/tfield.png"))); // NOI18N
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 310, -1, 70));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/tfield.png"))); // NOI18N
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 150, -1, 70));

        odate.setFont(new java.awt.Font("Big Caslon", 0, 24)); // NOI18N
        odate.setForeground(new java.awt.Color(255, 255, 255));
        odate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        odate.setText("Order Date:");
        add(odate, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 180, 50));

        oduedate.setFont(new java.awt.Font("Big Caslon", 0, 24)); // NOI18N
        oduedate.setForeground(new java.awt.Color(255, 255, 255));
        oduedate.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        oduedate.setText("Due Date:");
        add(oduedate, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 320, 180, 50));

        ocID.setFont(new java.awt.Font("Big Caslon", 0, 24)); // NOI18N
        ocID.setForeground(new java.awt.Color(255, 255, 255));
        ocID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ocID.setText("Customer ID:");
        add(ocID, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 480, 240, 50));

        oID.setFont(new java.awt.Font("Big Caslon", 0, 24)); // NOI18N
        oID.setForeground(new java.awt.Color(255, 255, 255));
        oID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        oID.setText("Order ID:");
        add(oID, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, 170, 50));

        ostat.setFont(new java.awt.Font("Big Caslon", 0, 24)); // NOI18N
        ostat.setForeground(new java.awt.Color(255, 255, 255));
        ostat.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ostat.setText("Order Status:");
        add(ostat, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 400, 240, 50));

        oaID.setFont(new java.awt.Font("Big Caslon", 0, 24)); // NOI18N
        oaID.setForeground(new java.awt.Color(255, 255, 255));
        oaID.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        oaID.setText("Affiliate ID:");
        add(oaID, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 560, 240, 50));

        progress.add(done);
        done.setFont(new java.awt.Font("Kokonor", 0, 24)); // NOI18N
        done.setForeground(new java.awt.Color(255, 255, 255));
        done.setText("Done");
        done.setToolTipText("");
        done.addActionListener(this::doneActionPerformed);
        add(done, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 396, -1, 60));

        progress.add(notstarted);
        notstarted.setFont(new java.awt.Font("Kokonor", 0, 24)); // NOI18N
        notstarted.setForeground(new java.awt.Color(255, 255, 255));
        notstarted.setText("Not Started");
        add(notstarted, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 396, -1, 60));

        progress.add(inprogress);
        inprogress.setFont(new java.awt.Font("Kokonor", 0, 24)); // NOI18N
        inprogress.setForeground(new java.awt.Color(255, 255, 255));
        inprogress.setText("In Progress");
        inprogress.addActionListener(this::inprogressActionPerformed);
        add(inprogress, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 396, -1, 60));
        add(orderdateDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 240, 350, 50));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/tfield.png"))); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 230, -1, 70));

        customerid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(customerid, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 480, 370, 50));

        producttype.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        producttype.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/tfield.png"))); // NOI18N
        add(producttype, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 470, -1, 70));

        affiliateid.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        add(affiliateid, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 560, 370, 50));

        producttype1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        producttype1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/tfield.png"))); // NOI18N
        add(producttype1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 550, -1, 70));

        mainbg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/mainbg.png"))); // NOI18N
        add(mainbg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, -1));
    }// </editor-fold>                        

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {                                       
        try {
            String orderIDText = orderID.getText().trim();
            if (orderIDText.isEmpty()) {
                JOptionPane.showMessageDialog(this,"Please enter an Order ID.");
                return;
            }
            
            if (orderdateDateChooser.getDate() != null
            || duedateDateChooser.getDate() != null
            || progress.getSelection() != null
            || customerid.getSelectedItem() != null
            || affiliateid.getSelectedItem() != null) {

                JOptionPane.showMessageDialog(this,"Only Order ID should have a value when deleting.");
                return;
            }

            int orderIDValue;
            try {
                orderIDValue = Integer.parseInt(orderIDText);

                if (orderIDValue < 3000) {
                    JOptionPane.showMessageDialog(this,"Order ID must be 3000 or above.");
                    return;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,"Order ID must be a valid number.");
                return;
            }

            java.sql.Connection conn = IFCDatabase.getConnection();

            String checkSQL = "SELECT * FROM Orders WHERE order_id = ?";
            java.sql.PreparedStatement checkStmt = conn.prepareStatement(checkSQL);
            checkStmt.setInt(1, orderIDValue);
            java.sql.ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this,"Order ID does not exist.");
                conn.close();
                return;
            }

            String currentOrderDate = rs.getDate("order_date").toString();
            String currentDueDate = rs.getDate("due_date").toString();
            String currentStatus = rs.getString("order_status");
            int currentCustomerID = rs.getInt("customer_id");
            String currentAffiliateID = rs.getString("affiliate_id");

            String message = "Are you sure you want to delete this order record?\n\n"
            + "Order ID: " + orderIDValue + "\n"
            + "Order Date: " + currentOrderDate + "\n"
            + "Due Date: " + currentDueDate + "\n"
            + "Progress: " + currentStatus + "\n"
            + "Customer ID: " + currentCustomerID + "\n"
            + "Affiliate ID: "
            + (currentAffiliateID == null ? "" : currentAffiliateID);

            int confirm = JOptionPane.showConfirmDialog(this,message,"Confirm Delete",JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) {
                conn.close();
                return;
            }

            String deleteSQL = "DELETE FROM Orders WHERE order_id = ?";

            java.sql.PreparedStatement deleteStmt =conn.prepareStatement(deleteSQL);
            deleteStmt.setInt(1, orderIDValue);
            int rowsDeleted = deleteStmt.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this,"Order deleted successfully!");
                orderID.setText("");
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
         int response = JOptionPane.showConfirmDialog(this,"This will clear all your inputs. Continue?","Confirm Clear",JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            orderID.setText("");
            orderdateDateChooser.setDate(null);
            duedateDateChooser.setDate(null);
            progress.clearSelection();
            if (customerid.getItemCount() > 0) {
                customerid.setSelectedIndex(0); // resets to first item
            } else {
                customerid.setSelectedIndex(-1);
            }
            if (affiliateid.getItemCount() > 0) {
                affiliateid.setSelectedIndex(0); // empty item
            } else {
                affiliateid.setSelectedIndex(-1);
            }

            JOptionPane.showMessageDialog(this, "Fields cleared successfully.");
        }
    }                                     

    private void saveActionPerformed(java.awt.event.ActionEvent evt) {                                     
        try {
        java.sql.Connection conn = IFCDatabase.getConnection();

        String orderIDText = orderID.getText().trim();
        String customerIDText = (String) customerid.getSelectedItem();
        
        String affiliateIDText = (String) affiliateid.getSelectedItem();

            if (affiliateIDText != null && affiliateIDText.isEmpty()) {
                affiliateIDText = null;
            }
            
        java.util.Date orderDateValue = orderdateDateChooser.getDate();
        java.util.Date dueDateValue = duedateDateChooser.getDate();
        String progressStatus = "";

        if (notstarted.isSelected()) {
            progressStatus = "Not Started";
        } else if (inprogress.isSelected()) {
            progressStatus = "In Progress";
        } else if (done.isSelected()) {
            progressStatus = "Done";
        }

        if (orderDateValue == null
                || dueDateValue == null
                || customerIDText.isEmpty()
                || progressStatus.isEmpty()) {
            JOptionPane.showMessageDialog(this,"Please fill in all required fields.");
            return;
        }

        java.sql.Date sqlOrderDate = new java.sql.Date(orderDateValue.getTime());

        java.sql.Date sqlDueDate = new java.sql.Date(dueDateValue.getTime());

        if (sqlDueDate.before(sqlOrderDate)) {
            JOptionPane.showMessageDialog(this,"Due date should be after the order date.");
            return;
        }

        int customerIDValue;
        try {
            customerIDValue = Integer.parseInt(customerIDText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Customer ID must be a number.");
            return;
        }

        java.sql.PreparedStatement checkCustomer =
                conn.prepareStatement(
                        "SELECT * FROM Customers WHERE customer_id = ?"
                );
        checkCustomer.setInt(1, customerIDValue);
        java.sql.ResultSet customerRS = checkCustomer.executeQuery();
        if (!customerRS.next()) {
            JOptionPane.showMessageDialog(this,"Customer ID does not exist.");
            return;
        }

        if (affiliateIDText != null && !affiliateIDText.isEmpty()) {
            java.sql.PreparedStatement checkAffiliate =
                    conn.prepareStatement(
                            "SELECT * FROM Affiliates WHERE affiliate_id = ?"
                    );

            checkAffiliate.setString(1, affiliateIDText);
            java.sql.ResultSet affiliateRS = checkAffiliate.executeQuery();
            if (!affiliateRS.next()) {
                JOptionPane.showMessageDialog(this,"Affiliate ID does not exist.");
                return;
            }
        }

        Integer orderIDValue = null;
        if (!orderIDText.isEmpty()) {
            try {
                orderIDValue = Integer.parseInt(orderIDText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,"Order ID must be a number.");
                return;
            }

            java.sql.PreparedStatement checkOrder =
                    conn.prepareStatement(
                            "SELECT * FROM Orders WHERE order_id = ?"
                    );

            checkOrder.setInt(1, orderIDValue);
            java.sql.ResultSet orderRS = checkOrder.executeQuery();

            if (orderRS.next()) {
                JOptionPane.showMessageDialog(this,"Order ID already exists.");
                return;
            }
        }

        String summary =
                "Are you sure you want to save this?\n\n"
                + "Order ID: " + orderIDText + "\n"
                + "Order Date: " + sqlOrderDate + "\n"
                + "Due Date: " + sqlDueDate + "\n"
                + "Progress: " + progressStatus + "\n"
                + "Customer ID: " + customerIDText + "\n"
                + "Affiliate ID: " + affiliateIDText;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                summary,
                "Confirm Save",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql;
        java.sql.PreparedStatement pst;

        if (orderIDValue != null) {
            sql = """
                  INSERT INTO Orders
                  (order_id, order_date, due_date, order_status, customer_id, affiliate_id)
                  VALUES (?, ?, ?, ?, ?, ?)
                  """;
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderIDValue);
            pst.setDate(2, sqlOrderDate);
            pst.setDate(3, sqlDueDate);
            pst.setString(4, progressStatus);
            pst.setInt(5, customerIDValue);

            if (affiliateIDText.isEmpty()) {
                pst.setNull(6, java.sql.Types.VARCHAR);
            } else {
                pst.setString(6, affiliateIDText);
            }
        } else {
            sql = """
                  INSERT INTO Orders
                  (order_date, due_date, order_status, customer_id, affiliate_id)
                  VALUES (?, ?, ?, ?, ?)
                  """;
            pst = conn.prepareStatement(sql);
            pst.setDate(1, sqlOrderDate);
            pst.setDate(2, sqlDueDate);
            pst.setString(3, progressStatus);
            pst.setInt(4, customerIDValue);
            if (affiliateIDText.isEmpty()) {
                pst.setNull(5, java.sql.Types.VARCHAR);
            } else {
                pst.setString(5, affiliateIDText);
            }
        }
        
        //MAY BAGO
        int rowsInserted = pst.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this, "Order saved successfully!");
            String finalOrderID = orderIDText;
            // If PostgreSQL auto-generated the ID
            if (finalOrderID.isEmpty()) {

            String getIDSQL = "SELECT MAX(order_id) AS latest_id FROM Orders";
            PreparedStatement getIDStmt = conn.prepareStatement(getIDSQL);
            ResultSet idRS = getIDStmt.executeQuery();

            if (idRS.next()) {
                finalOrderID = String.valueOf(idRS.getInt("latest_id"));
            }
        }
            
            // Clear text fields
            orderID.setText("");
            if (customerid.getItemCount() > 0) {
                customerid.setSelectedIndex(0); // resets to first item
            } else {
                customerid.setSelectedIndex(-1);
            }
            if (affiliateid.getItemCount() > 0) {
                affiliateid.setSelectedIndex(0); // empty item
            } else {
                affiliateid.setSelectedIndex(-1);
            }

            // Clear date choosers
            orderdateDateChooser.setDate(null);
            duedateDateChooser.setDate(null);

            // Clear radio buttons
            progress.clearSelection();

        javax.swing.JFrame frame =(javax.swing.JFrame)
        javax.swing.SwingUtilities.getWindowAncestor(this);

        frame.setContentPane(
            new editorderitem(finalOrderID)
        );

        frame.revalidate();
        frame.repaint();
        // HANGGANG DITO
    } else {
            JOptionPane.showMessageDialog(this,"Save failed.");
        }
        pst.close();
        conn.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,"Save failed: " + e.getMessage()
        );
        e.printStackTrace();
    }
    }                                    

    private void backtotableActionPerformed(java.awt.event.ActionEvent evt) {                                            
        javax.swing.JFrame frame =
        (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        frame.setContentPane(new order());
        frame.revalidate();
        frame.repaint();            
    }                                           

    private void doneActionPerformed(java.awt.event.ActionEvent evt) {                                     
        // TODO add your handling code here:
    }                                    

    private void inprogressActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          


    // Variables declaration - do not modify                     
    private javax.swing.JComboBox<String> affiliateid;
    private javax.swing.JButton backtotable;
    private javax.swing.JButton clear;
    private javax.swing.JComboBox<String> customerid;
    private javax.swing.JButton delete;
    private javax.swing.JRadioButton done;
    private com.toedter.calendar.JDateChooser duedateDateChooser;
    private javax.swing.JRadioButton inprogress;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel mainbg;
    private javax.swing.JRadioButton notstarted;
    private javax.swing.JLabel oID;
    private javax.swing.JLabel oaID;
    private javax.swing.JLabel ocID;
    private javax.swing.JLabel odate;
    private javax.swing.JLabel oduedate;
    private javax.swing.JTextField orderID;
    private com.toedter.calendar.JDateChooser orderdateDateChooser;
    private javax.swing.JLabel ostat;
    private javax.swing.JLabel producttype;
    private javax.swing.JLabel producttype1;
    private javax.swing.ButtonGroup progress;
    private javax.swing.JButton save;
    // End of variables declaration                   
    
    private void generateNextOrderID() {
        try {
            java.sql.Connection conn = IFCDatabase.getConnection();

            String sql = "SELECT MAX(order_id) AS max_id FROM Orders";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            java.sql.ResultSet rs = pst.executeQuery();

            int nextID = 3000;

            if (rs.next()) {
                int maxID = rs.getInt("max_id");

                if (!rs.wasNull()) {
                    nextID = maxID + 1;
                }
            }

            orderID.setText(String.valueOf(nextID));

            rs.close();
            pst.close();
            conn.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Failed to generate Order ID: " + e.getMessage());
        }
    }
    
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
