import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;

public class order extends javax.swing.JPanel {
    
    private Integer[] originalIds;
    private Object[][] originalData;
    private String currentView = "Orders";
    
    public order() {
        initComponents();
        customizeOrderTable();
        loadOrderTable();
        
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        for (int i = 0; i < ordertable.getColumnCount(); i++) {
            ordertable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
     private void centerAlignOrderTable() {
        javax.swing.table.DefaultTableCellRenderer centerRenderer =
                new javax.swing.table.DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        for (int i = 0; i < ordertable.getColumnCount(); i++) {
            ordertable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
    
    private void loadOrderTable() {
        currentView = "Orders";

        try {

            String sql = "SELECT * FROM Orders ORDER BY order_id";

            Connection conn = IFCDatabase.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel() {
                
            @Override
            public boolean isCellEditable(int row, int column) {
            // Order ID, Customer ID, & Total Amount column NOT editable
                if (column == 0 || column == 4 || column == 6) {
                    return false;
                }
                    return true;
                }
            
            };

        model.setColumnIdentifiers(new String[]{
            "Order ID",
            "Order Date",
            "Due Date",
            "Order Status",
            "Customer ID",
            "Affiliate ID",
            "Total Amount"
        });

        java.util.List<Integer> idList = new java.util.ArrayList<>();
        java.util.List<Object[]> dataList = new java.util.ArrayList<>();

        while (rs.next()) {
            Object[] row = {

                rs.getInt("order_id"),
                rs.getDate("order_date"),
                rs.getDate("due_date"),
                rs.getString("order_status"),
                rs.getInt("customer_id"),
                rs.getString("affiliate_id"),
                rs.getDouble("total_amount")
            };

            model.addRow(row);

            idList.add(rs.getInt("order_id"));
            dataList.add(row);
        }

        originalIds = idList.toArray(new Integer[0]);
        originalData = dataList.toArray(new Object[0][]);

        ordertable.setModel(model);

        customizeOrderTable();

        // DATE EDITORS
        ordertable.getColumnModel().getColumn(1)
                .setCellEditor(new DateCellEditor());

        ordertable.getColumnModel().getColumn(2)
                .setCellEditor(new DateCellEditor());

        // STATUS COMBOBOX
        String[] statuses = {
            "Not Started",
            "In Progress",
            "Done"
        };

        JComboBox<String> comboBox = new JComboBox<>(statuses);

        ordertable.getColumnModel().getColumn(3)
                .setCellEditor(new DefaultCellEditor(comboBox));

    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, e.getMessage());
    }
}

    private void loadOrderRecord(int orderId) {
        currentView = "Orders";

        try {

            String sql = "SELECT * FROM Orders WHERE order_id = ?";

            Connection conn = IFCDatabase.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, orderId);
            ResultSet rs = pst.executeQuery();
            
            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                // Order ID and Total Amount not editable
                    if (column == 0 || column == 4 || column == 6) {
                        return false;
                    }
                    return true;
                }
            };

            model.setColumnIdentifiers(new String[]{
                "Order ID",
                "Order Date",
                "Due Date",
                "Order Status",
                "Customer ID",
                "Affiliate ID",
                "Total Amount"
            });

            java.util.List<Integer> idList = new java.util.ArrayList<>();
            java.util.List<Object[]> dataList = new java.util.ArrayList<>();

            boolean found = false;
            while (rs.next()) {
                found = true;

                Object[] row = {
                    rs.getInt("order_id"),
                    rs.getDate("order_date"),
                    rs.getDate("due_date"),
                    rs.getString("order_status"),
                    rs.getInt("customer_id"),
                    rs.getString("affiliate_id"),
                    rs.getDouble("total_amount")
                };
                model.addRow(row);

                idList.add(rs.getInt("order_id"));
                dataList.add(row);
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Order ID not found.");
                return;
            }

            originalIds = idList.toArray(new Integer[0]);
            originalData = dataList.toArray(new Object[0][]);
            ordertable.setModel(model);
            customizeOrderTable();
            // DATE EDITORS
            ordertable.getColumnModel().getColumn(1)
                    .setCellEditor(new DateCellEditor());

            ordertable.getColumnModel().getColumn(2)
                    .setCellEditor(new DateCellEditor());

            // STATUS COMBOBOX
            String[] statuses = {
                "Not Started",
                "In Progress",
                "Done"
            };

            JComboBox<String> comboBox = new JComboBox<>(statuses);

            ordertable.getColumnModel().getColumn(3)
                    .setCellEditor(new DefaultCellEditor(comboBox));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void loadOrderItemRecord(int orderId) {
        currentView = "Order_Items";
        try {
            String sql = "SELECT * FROM Order_Items WHERE order_id = ?";

            Connection conn = IFCDatabase.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, orderId);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = new DefaultTableModel() {

                @Override
                public boolean isCellEditable(int row, int column) {

                    // Order ID, Product ID, and Price at Order NOT editable
                    if (column == 0 || column == 1 || column == 3) {
                        return false;
                    }

                    return true;
                }
            };
            
            model.setColumnIdentifiers(new String[]{
                "Order ID",
                "Product ID",
                "Quantity",
                "Price at Order"
            });

            java.util.List<Integer> idList = new java.util.ArrayList<>();
            java.util.List<Object[]> dataList = new java.util.ArrayList<>();

            boolean found = false;   
            while (rs.next()) {
                found = true;
                Object[] row = {
                    rs.getInt("order_id"),
                    rs.getInt("product_id"),
                    rs.getInt("quantity"),
                    rs.getDouble("price_at_order")
            };

                model.addRow(row);
                idList.add(rs.getInt("order_id"));
                dataList.add(row);
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Order ID not found.");
                return;
            }

            originalIds = idList.toArray(new Integer[0]);
            originalData = dataList.toArray(new Object[0][]);
            ordertable.setModel(model);
            customizeOrderTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
  // PARA MAEDIT 'YUNG DATE SA MISMONG TABLE 
    public class DateCellEditor extends AbstractCellEditor implements TableCellEditor { 
        private JDateChooser dateChooser;

        public DateCellEditor() {
            dateChooser = new JDateChooser();
        }

        @Override
        public Object getCellEditorValue() {
            return dateChooser.getDate();
        }

        @Override
        public Component getTableCellEditorComponent(
                JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column) {

            if (value instanceof java.util.Date) {
                dateChooser.setDate((java.util.Date) value);
            } else {
                dateChooser.setDate(null);
            }

            return dateChooser;
        }
    }

    private void customizeOrderTable() {
        ordertable.setBackground(java.awt.Color.WHITE);
        ordertable.setForeground(new java.awt.Color(25, 25, 25));
        ordertable.setFont(new java.awt.Font("Baskerville", java.awt.Font.PLAIN, 18));
        ordertable.setRowHeight(38);

        ordertable.setGridColor(new java.awt.Color(220, 220, 220));
        ordertable.setShowGrid(true);
        ordertable.setShowVerticalLines(false);

        ordertable.setSelectionBackground(new java.awt.Color(35, 35, 35));
        ordertable.setSelectionForeground(java.awt.Color.WHITE);

        ordertable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        ordertable.setFillsViewportHeight(true);
        
        ordertable.getColumnModel().getColumn(0).setMinWidth(45);
        ordertable.getColumnModel().getColumn(0).setPreferredWidth(55);
        ordertable.getColumnModel().getColumn(0).setMaxWidth(65);

        javax.swing.table.JTableHeader header = ordertable.getTableHeader();
        header.setPreferredSize(new java.awt.Dimension(header.getWidth(), 42));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(true);

        javax.swing.table.DefaultTableCellRenderer headerRenderer = new javax.swing.table.DefaultTableCellRenderer();

        headerRenderer.setHorizontalAlignment(JLabel.CENTER);
        headerRenderer.setBackground(new java.awt.Color(28, 28, 28));
        headerRenderer.setForeground(java.awt.Color.WHITE);
        headerRenderer.setFont(new java.awt.Font("Big Caslon", java.awt.Font.BOLD, 18));
        headerRenderer.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 1, new java.awt.Color(65, 65, 65)
        ));

        for (int i = 0; i < ordertable.getColumnModel().getColumnCount(); i++) {
            ordertable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
        centerAlignOrderTable();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        back = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        add_del = new javax.swing.JButton();
        searchorderID = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ordertable = new javax.swing.JTable();
        savebtn = new javax.swing.JButton();
        refreshbtn = new javax.swing.JButton();
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

        add_del.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        add_del.setText("Add/Delete");
        add_del.setActionCommand("Add/Delete");
        add_del.addActionListener(this::add_delActionPerformed);
        add(add_del, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 100, 120, 40));

        searchorderID.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        searchorderID.setText("Search Order ID");
        searchorderID.addActionListener(this::searchorderIDActionPerformed);
        add(searchorderID, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 410, 40));

        jScrollPane1.setBackground(new java.awt.Color(204, 204, 204));

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

        savebtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        savebtn.setText("Save");
        savebtn.addActionListener(this::savebtnActionPerformed);
        add(savebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 100, 120, 40));

        refreshbtn.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        refreshbtn.setText("Refresh");
        refreshbtn.addActionListener(this::refreshbtnActionPerformed);
        add(refreshbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 100, 120, 40));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/mainbg.png"))); // NOI18N
        bg.setText("jLabel5");
        add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 850));
    }// </editor-fold>                        

    private void backActionPerformed(java.awt.event.ActionEvent evt) {                                     
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        frame.setContentPane(new orderpages());
        frame.revalidate();
        frame.repaint();
    }                                    

    private void add_delActionPerformed(java.awt.event.ActionEvent evt) {                                        
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        frame.setContentPane(new editorder());
        frame.revalidate();
        frame.repaint(); 
    }                                       
 
    private void searchorderIDActionPerformed(java.awt.event.ActionEvent evt) {                                              
        String input = JOptionPane.showInputDialog(this, "Enter Order ID:");

        if (input == null || input.trim().isEmpty()) {
            return;
        }

        try {
            int orderId = Integer.parseInt(input);

            String[] options = {
                "View Orders",
                "View Order Items"
            };

            int choice = JOptionPane.showOptionDialog(
                this,
                "Choose what to view:",
                "Search",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
            );

            if (choice == 0) {
                loadOrderRecord(orderId);
            } else if (choice == 1) {
                loadOrderItemRecord(orderId);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Order ID.");
        }
    }                                             

    private void savebtnActionPerformed(java.awt.event.ActionEvent evt) {                                        
        if (ordertable.isEditing()) {
            ordertable.getCellEditor().stopCellEditing();
        }
        DefaultTableModel model = (DefaultTableModel) ordertable.getModel();

        try {
            Connection conn = IFCDatabase.getConnection();
            
            for (int i = 0; i < model.getRowCount(); i++) {
                boolean changed = false;
                
                for (int j = 0; j < model.getColumnCount(); j++) {
                    Object oldVal = originalData[i][j];
                    Object newVal = model.getValueAt(i, j);

                    if (oldVal == null && newVal != null ||
                        oldVal != null && !oldVal.toString().equals(newVal.toString())) {
                        changed = true;
                        break;
                    }
                }

                if (!changed) {
                    continue;
                }

                StringBuilder preview = new StringBuilder();
                
                preview.append("Current Information:\n\n");
                for (int j = 0; j < model.getColumnCount(); j++) {
                    preview.append(model.getColumnName(j))
                        .append(": ")
                        .append(originalData[i][j])
                        .append("\n");
                }

                preview.append("\nNew Information:\n\n");
                for (int j = 0; j < model.getColumnCount(); j++) {
                    preview.append(model.getColumnName(j))
                        .append(": ")
                        .append(model.getValueAt(i, j))
                        .append("\n");
                }

                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    preview.toString(),
                    "Confirm Save",
                    JOptionPane.YES_NO_OPTION
                );

                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                if (currentView.equals("Orders")) {
                    String sql =
                        "UPDATE Orders SET "
                        + "order_date=?, "
                        + "due_date=?, "
                        + "order_status=?, "
                        + "customer_id=?, "
                        + "affiliate_id=?, "
                        + "total_amount=? "
                        + "WHERE order_id=?";

                        PreparedStatement pst = conn.prepareStatement(sql);

                        // Get values safely
                        Object orderDateObj = model.getValueAt(i, 1);
                        Object dueDateObj = model.getValueAt(i, 2);
                        Object statusObj = model.getValueAt(i, 3);
                        Object customerObj = model.getValueAt(i, 4);
                        Object affiliateObj = model.getValueAt(i, 5);
                        Object totalObj = model.getValueAt(i, 6);

                        // Validation
                        if (orderDateObj == null ||
                            dueDateObj == null ||
                            statusObj == null ||
                            customerObj == null ||
                            totalObj == null) {

                            JOptionPane.showMessageDialog(this,"Required fields cannot be empty.");
                            return;
                            }

                            // Dates
                            pst.setDate(1,
                                new java.sql.Date(
                                    ((java.util.Date) orderDateObj).getTime()));

                            pst.setDate(2,
                                new java.sql.Date(
                                    ((java.util.Date) dueDateObj).getTime()));

                            // Strings
                            pst.setString(3, statusObj.toString());

                            // Customer ID
                            pst.setInt(4,
                                Integer.parseInt(customerObj.toString()));

                            // Affiliate ID (nullable)
                            if (affiliateObj == null ||
                                affiliateObj.toString().trim().isEmpty()) {

                                pst.setNull(5, java.sql.Types.VARCHAR);

                            } else {
                                pst.setString(5, affiliateObj.toString());
                            }

                            // Total amount
                            pst.setDouble(6,
                                Double.parseDouble(totalObj.toString()));

                            // Order ID
                            pst.setInt(7,
                                Integer.parseInt(model.getValueAt(i, 0).toString()));

                            pst.executeUpdate();
                } else if (currentView.equals("Order_Items")) {
                    String sql =
                        "UPDATE Order_Items SET "
                        + "quantity=?, "
                        + "price_at_order=? "
                        + "WHERE order_id=? "
                        + "AND product_id=?";
                    
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setInt(1,
                        Integer.parseInt(model.getValueAt(i, 2).toString()));
                    pst.setDouble(2,
                        Double.parseDouble(model.getValueAt(i, 3).toString()));
                    pst.setInt(3,
                        Integer.parseInt(model.getValueAt(i, 0).toString()));
                    pst.setInt(4,
                        Integer.parseInt(model.getValueAt(i, 1).toString()));
                    pst.executeUpdate();
                }
            }
            
            JOptionPane.showMessageDialog(this,"Changes saved successfully!");
            loadOrderTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,e.getMessage());
        }
    }                                       

    private void refreshbtnActionPerformed(java.awt.event.ActionEvent evt) {                                           
        loadOrderTable();
        JOptionPane.showMessageDialog(this, "All records are now displayed.");
    }                                          


    // Variables declaration - do not modify                     
    private javax.swing.JButton add_del;
    private javax.swing.JButton back;
    private javax.swing.JLabel bg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable ordertable;
    private javax.swing.JButton refreshbtn;
    private javax.swing.JButton savebtn;
    private javax.swing.JButton searchorderID;
    // End of variables declaration                   
}
