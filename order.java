import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class order extends javax.swing.JPanel {
    public order() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        back = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        edittable = new javax.swing.JButton();
        searchorderID = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ordertable = new javax.swing.JTable();
        bg = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/back.png"))); // NOI18N
        back.setBorderPainted(false);
        back.setContentAreaFilled(false);
        back.setFocusPainted(false);
        back.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        back.addActionListener(this::backActionPerformed);
        add(back, new org.netbeans.lib.awtextra.AbsoluteConstraints(98, 75, -1, 77));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 280, -1, 70));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 380, -1, 70));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 480, -1, 70));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 180, -1, 70));

        edittable.setBackground(new java.awt.Color(255, 255, 255));
        edittable.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        edittable.setForeground(new java.awt.Color(0, 0, 0));
        edittable.setText("Edit Table");
        edittable.addActionListener(this::edittableActionPerformed);
        add(edittable, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 100, 120, 40));

        searchorderID.setBackground(new java.awt.Color(255, 255, 255));
        searchorderID.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        searchorderID.setForeground(new java.awt.Color(0, 0, 0));
        searchorderID.setText("Search Order ID");
        searchorderID.addActionListener(this::searchorderIDActionPerformed);
        add(searchorderID, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 100, 410, 40));

        jScrollPane1.setBackground(new java.awt.Color(204, 204, 204));

        ordertable.setBackground(new java.awt.Color(255, 255, 255));
        ordertable.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        ordertable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(ordertable);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 1070, 580));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/mainbg.png"))); // NOI18N
        bg.setText("jLabel5");
        add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 850));
    }// </editor-fold>                        

    private void backActionPerformed(java.awt.event.ActionEvent evt) {                                     
        javax.swing.JFrame frame =
        (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        frame.setContentPane(new orderpages());
        frame.revalidate();
        frame.repaint();
    }                                    

    private void edittableActionPerformed(java.awt.event.ActionEvent evt) {                                          
        javax.swing.JFrame frame =
        (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        frame.setContentPane(new editorder());
        frame.revalidate();
        frame.repaint(); 
    }                                         

    private void showResultTable(String title, DefaultTableModel model) {

    javax.swing.JDialog dialog = new javax.swing.JDialog(
            (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this),
            title,
            true
    );

    javax.swing.JTable table = new javax.swing.JTable(model);
    javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(table);

    dialog.add(scroll);

    dialog.setSize(800, 400);
    dialog.setLocationRelativeTo(this);
    dialog.setVisible(true);
}
    
    private void loadOrderRecord(int orderId) {

    String sql = "SELECT * FROM Orders WHERE order_id = ?";

    try (Connection conn = IFCDatabase.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        if (!rs.next()) {
            JOptionPane.showMessageDialog(this, "Order ID not found. Try again.");
            searchorderIDActionPerformed(null);
            return;
        }

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Order ID");
        model.addColumn("Order Date");
        model.addColumn("Due Date");
        model.addColumn("Status");
        model.addColumn("Customer ID");
        model.addColumn("Affiliate ID");
        model.addColumn("Total Amount");

        model.addRow(new Object[]{
                rs.getInt("order_id"),
                rs.getDate("order_date"),
                rs.getDate("due_date"),
                rs.getString("order_status"),
                rs.getInt("customer_id"),
                rs.getString("affiliate_id"),
                rs.getBigDecimal("total_amount")
        });

        showResultTable("Order Record", model);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading order record.");
    }
}
    
    private void loadOrderItems(int orderId) {

    String sql = "SELECT * FROM Order_Items WHERE order_id = ?";

    try (Connection conn = IFCDatabase.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, orderId);
        ResultSet rs = ps.executeQuery();

        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Order ID");
        model.addColumn("Product ID");
        model.addColumn("Quantity");
        model.addColumn("Price at Order");

        boolean found = false;

        while (rs.next()) {
            found = true;

            model.addRow(new Object[]{
                    rs.getInt("order_id"),
                    rs.getInt("product_id"),
                    rs.getInt("quantity"),
                    rs.getBigDecimal("price_at_order")
            });
        }

        if (!found) {
            JOptionPane.showMessageDialog(this, "Order ID not found. Try again.");
            searchorderIDActionPerformed(null);
            return;
        }

        showResultTable("Order Items", model);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading order items.");
    }
}
    
    private void searchorderIDActionPerformed(java.awt.event.ActionEvent evt) {                                              
    javax.swing.JTextField orderIdField = new javax.swing.JTextField();

    javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(2, 1, 5, 5));
    panel.add(new javax.swing.JLabel("Input Order ID:"));
    panel.add(orderIdField);

    Object[] options = {"View Order Record", "View Order Items"};

    int choice = javax.swing.JOptionPane.showOptionDialog(
            this,
            panel,
            "Search Order",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
    );

    if (choice == javax.swing.JOptionPane.CLOSED_OPTION) {
        return;
    }

    String inputOrderID = orderIdField.getText().trim();

    if (inputOrderID.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this,
                "Please input an Order ID.");
        searchorderIDActionPerformed(evt);
        return;
    }

    int orderID;
    try {
        orderID = Integer.parseInt(inputOrderID);

    // Check if below valid Order ID range
    if (orderID < 3000) {

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Invalid Order ID. Order IDs must be 3000 or higher."
        );

        // reopen dialog
        searchorderIDActionPerformed(evt);
        return;
    }

        java.sql.Connection conn = IFCDatabase.getConnection();

        java.sql.PreparedStatement pst;

        if (choice == 0) {

            String sql = "SELECT * FROM Orders WHERE order_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderID);

        }
        
        else {

            String sql = "SELECT * FROM Order_Items WHERE order_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setInt(1, orderID);
        }

        java.sql.ResultSet rs = pst.executeQuery();

        java.sql.ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        javax.swing.table.DefaultTableModel model =
                new javax.swing.table.DefaultTableModel();

        for (int i = 1; i <= columnCount; i++) {
            model.addColumn(metaData.getColumnName(i));
        }

        boolean found = false;

        while (rs.next()) {
            found = true;

            Object[] row = new Object[columnCount];

            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = rs.getObject(i);
            }

            model.addRow(row);
        }

        if (!found) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Order ID not found. Try again.");

            searchorderIDActionPerformed(evt);
            return;
        }

        javax.swing.JTable resultTable = new javax.swing.JTable(model);

        javax.swing.JScrollPane scrollPane =
                new javax.swing.JScrollPane(resultTable);

        scrollPane.setPreferredSize(new java.awt.Dimension(700, 300));

        javax.swing.JDialog dialog = new javax.swing.JDialog();

        dialog.setTitle("Search Results");
        dialog.add(scrollPane);

        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setModal(true);
        dialog.setVisible(true);

        rs.close();
        pst.close();
        conn.close();

    } catch (NumberFormatException e) {

        javax.swing.JOptionPane.showMessageDialog(this,
                "Order ID must be a valid number.");
        
        searchorderIDActionPerformed(evt);
        return;

    } catch (Exception e) {

        javax.swing.JOptionPane.showMessageDialog(this,
                "Error: " + e.getMessage());

        e.printStackTrace();
    }
    }                                             

    // Variables declaration - do not modify                     
    private javax.swing.JButton back;
    private javax.swing.JLabel bg;
    private javax.swing.JButton edittable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable ordertable;
    private javax.swing.JButton searchorderID;
    // End of variables declaration                   

}
