
import javax.swing.JOptionPane;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import com.toedter.calendar.JDateChooser;
import javax.swing.DefaultCellEditor;
import javax.swing.table.TableCellEditor;
import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author hezekiahmagalona
 */
public class affiliate extends javax.swing.JPanel {
   
    /**
     * Creates new form customer
     */    
    private Connection conn;
    private String[] originalIds;
    private Object[][] originalData;
    public affiliate() {
        
        initComponents(); 
        DB_connect();
        loadAffiliateTable();
        customizeAffiliateTable(); 

        javax.swing.table.DefaultTableCellRenderer centerRenderer =
                new javax.swing.table.DefaultTableCellRenderer();

        centerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);

        for (int i = 0; i < affiliatetable.getColumnCount(); i++) {
            affiliatetable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private void DB_connect() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/IFC_Database",
                "postgres",
                "123"
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void loadAffiliateTable() {
        try {
            String sql = "SELECT * FROM Affiliates";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return true;
                }
            };

            model.setColumnIdentifiers(new String[]{
                "ID", "Name", "Start Date", "End Date", "Status", "Social Media"
            });

            java.util.List<String> idList = new java.util.ArrayList<>();
            java.util.List<Object[]> dataList = new java.util.ArrayList<>();

            while (rs.next()) {

                String id = rs.getString("affiliate_id");

                Object[] row = new Object[]{
                    id,
                    rs.getString("aff_name"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("aff_status"),
                    rs.getString("soc_med_acc")
                };

                model.addRow(row);
                idList.add(id);
                dataList.add(row); 
            }

            originalIds = idList.toArray(new String[0]);
            originalData = dataList.toArray(new Object[0][]);

            affiliatetable.setModel(model);
            customizeAffiliateTable();
            
            // START DATE EDIT
            affiliatetable.getColumnModel().getColumn(2)
                .setCellEditor(new DateCellEditor());

            // END DATE EDIT
            affiliatetable.getColumnModel().getColumn(3)
                .setCellEditor(new DateCellEditor());

            // COMBO BOX EDIT
            String[] statuses = {"Active", "Inactive", "Expired"};
            JComboBox<String> comboBox = new JComboBox<>(statuses);

            affiliatetable.getColumnModel().getColumn(4)
                    .setCellEditor(new DefaultCellEditor(comboBox));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void loadAffiliateRecord(String affiliateId) {

        String sql = "SELECT * FROM Affiliates WHERE affiliate_id = ?";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, affiliateId);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel();

            model.addColumn("ID");
            model.addColumn("Name");
            model.addColumn("Start Date");
            model.addColumn("End Date");
            model.addColumn("Status");
            model.addColumn("Social Media");

            boolean found = false;

            while (rs.next()) {
                found = true;

                model.addRow(new Object[]{
                    rs.getString("affiliate_id"),
                    rs.getString("aff_name"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("aff_status"),
                    rs.getString("soc_med_acc")
                });
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Affiliate ID not found.");
                return;
            }

            affiliatetable.setModel(model); 

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
    public class DateCellEditor extends AbstractCellEditor implements TableCellEditor { // DATE EDIT 

        private JDateChooser dateChooser;

        public DateCellEditor() {
            dateChooser = new JDateChooser();
        }

        @Override
        public Object getCellEditorValue() {
            return dateChooser.getDate();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            if (value instanceof java.util.Date) {
                dateChooser.setDate((java.util.Date) value);
            }
            return dateChooser;
        }
    }

    private void customizeAffiliateTable() {

    affiliatetable.setBackground(java.awt.Color.WHITE);
    affiliatetable.setForeground(new java.awt.Color(25, 25, 25));
    affiliatetable.setFont(new java.awt.Font("Baskerville", java.awt.Font.PLAIN, 18));
    affiliatetable.setRowHeight(38);

    affiliatetable.setGridColor(new java.awt.Color(220, 220, 220));
    affiliatetable.setShowGrid(true);
    affiliatetable.setShowVerticalLines(false);

    affiliatetable.setSelectionBackground(new java.awt.Color(35, 35, 35));
    affiliatetable.setSelectionForeground(java.awt.Color.WHITE);

    affiliatetable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
    affiliatetable.setFillsViewportHeight(true);

    affiliatetable.getColumnModel().getColumn(0).setMinWidth(80);
    affiliatetable.getColumnModel().getColumn(0).setPreferredWidth(100);
    affiliatetable.getColumnModel().getColumn(0).setMaxWidth(120);

    javax.swing.table.JTableHeader header = affiliatetable.getTableHeader();
    header.setPreferredSize(new java.awt.Dimension(header.getWidth(), 42));
    header.setReorderingAllowed(false);
    header.setResizingAllowed(true);

    javax.swing.table.DefaultTableCellRenderer headerRenderer =
            new javax.swing.table.DefaultTableCellRenderer();

    headerRenderer.setHorizontalAlignment(javax.swing.JLabel.CENTER);
    headerRenderer.setBackground(new java.awt.Color(28, 28, 28));
    headerRenderer.setForeground(java.awt.Color.WHITE);
    headerRenderer.setFont(new java.awt.Font("Big Caslon", java.awt.Font.BOLD, 18));
    headerRenderer.setBorder(javax.swing.BorderFactory.createMatteBorder(
            0, 0, 1, 1, new java.awt.Color(65, 65, 65)
    ));

    for (int i = 0; i < affiliatetable.getColumnModel().getColumnCount(); i++) {
        affiliatetable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        back = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        edittable = new javax.swing.JButton();
        searchaffiliateID = new javax.swing.JButton();
        savebtn = new javax.swing.JButton();
        refreshbtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        affiliatetable = new javax.swing.JTable();
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

        edittable.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        edittable.setForeground(new java.awt.Color(0, 0, 0));
        edittable.setText("Edit Table");
        edittable.addActionListener(this::edittableActionPerformed);
        add(edittable, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 100, 120, 40));

        searchaffiliateID.setFont(new java.awt.Font("Kokonor", 0, 18)); // NOI18N
        searchaffiliateID.setForeground(new java.awt.Color(0, 0, 0));
        searchaffiliateID.setText("Search Affiliate ID");
        searchaffiliateID.setToolTipText("");
        searchaffiliateID.addActionListener(this::searchaffiliateIDActionPerformed);
        add(searchaffiliateID, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 410, 40));

        savebtn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        savebtn.setText("Save");
        savebtn.addActionListener(this::savebtnActionPerformed);
        add(savebtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 100, 120, 40));

        refreshbtn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        refreshbtn.setText("Refresh");
        refreshbtn.addActionListener(this::refreshbtnActionPerformed);
        add(refreshbtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 100, 120, 40));

        jScrollPane1.setBackground(new java.awt.Color(204, 204, 204));

        affiliatetable.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        affiliatetable.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(affiliatetable);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, 1070, 580));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ifccrafts/assets/mainbg.png"))); // NOI18N
        bg.setText("jLabel5");
        add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1300, 850));
    }// </editor-fold>                        

    private void backActionPerformed(java.awt.event.ActionEvent evt) {                                     
        javax.swing.JFrame frame =
        (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        frame.setContentPane(new homepage());
        frame.revalidate();
        frame.repaint();
    }                                    

    private void edittableActionPerformed(java.awt.event.ActionEvent evt) {                                          
        javax.swing.JFrame frame =
        (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        frame.setContentPane(new editaffiliate());
        frame.revalidate();
        frame.repaint(); 
    }                                         

    private void searchaffiliateIDActionPerformed(java.awt.event.ActionEvent evt) {                                                  
        String id = JOptionPane.showInputDialog(this, "Enter Affiliate ID:");

        if (id == null || id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an ID.");
            return;
        }

        try {
            String sql = "SELECT * FROM Affiliates WHERE affiliate_id ILIKE ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, "%" + id.trim() + "%");

            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new String[]{
                "ID", "Name", "Start Date", "End Date", "Status", "Social Media"
            });

            boolean found = false;

            while (rs.next()) {
                found = true;

                model.addRow(new Object[]{
                    rs.getString("affiliate_id"),
                    rs.getString("aff_name"),
                    rs.getDate("start_date"),
                    rs.getDate("end_date"),
                    rs.getString("aff_status"),
                    rs.getString("soc_med_acc")
                });
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "No matching record found.");
                return;
            }

            affiliatetable.setModel(model); 
            customizeAffiliateTable();

            String[] statuses = {"Active", "Inactive", "Expired"};
            JComboBox<String> comboBox = new JComboBox<>(statuses);

            affiliatetable.getColumnModel().getColumn(4)
                    .setCellEditor(new DefaultCellEditor(comboBox));

            pst.close();
            rs.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    
    }                                                 

    private void refreshbtnActionPerformed(java.awt.event.ActionEvent evt) {                                           
        loadAffiliateTable();
        javax.swing.JOptionPane.showMessageDialog(this, "All records are now displayed.");

    }                                          

    private void savebtnActionPerformed(java.awt.event.ActionEvent evt) {                                        

        DefaultTableModel model = (DefaultTableModel) affiliatetable.getModel();
        StringBuilder preview = new StringBuilder();
        boolean hasChanges = false;

        try {

            for (int i = 0; i < model.getRowCount(); i++) {

                boolean rowChanged = false;

                for (int j = 0; j < model.getColumnCount(); j++) {

                    Object newVal = model.getValueAt(i, j);
                    Object oldVal = originalData[i][j];

                    if (newVal == null && oldVal != null ||
                        newVal != null && !newVal.toString().equals(oldVal.toString())) {

                        rowChanged = true;
                        break;
                    }
                }

                if (rowChanged) {

                    hasChanges = true;

                    preview.append("ID: ").append(model.getValueAt(i, 0)).append("\n")
                           .append("Name: ").append(model.getValueAt(i, 1)).append("\n")
                           .append("Start Date: ").append(model.getValueAt(i, 2)).append("\n")
                           .append("End Date: ").append(model.getValueAt(i, 3)).append("\n")
                           .append("Status: ").append(model.getValueAt(i, 4)).append("\n")
                           .append("Social: ").append(model.getValueAt(i, 5)).append("\n\n");
                }
            }

            if (!hasChanges) {
                JOptionPane.showMessageDialog(this, "No changes detected.");
                return;
            }

            // PREVIEW NG EDITED ROWS ONLYYYYY
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "SAVE THESE CHANGES ONLY:\n\n" + preview.toString(),
                    "Confirm Save",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            for (int i = 0; i < model.getRowCount(); i++) {

                boolean rowChanged = false;

                for (int j = 0; j < model.getColumnCount(); j++) {

                    Object newVal = model.getValueAt(i, j);
                    Object oldVal = originalData[i][j];

                    if (newVal == null && oldVal != null ||
                        newVal != null && !newVal.toString().equals(oldVal.toString())) {

                        rowChanged = true;
                        break;
                    }
                }

                if (!rowChanged) continue;

                String OG_Id = originalIds[i];
                String new_Id = model.getValueAt(i, 0).toString();
                String name = model.getValueAt(i, 1).toString();

                java.sql.Date startDate = null;
                java.sql.Date endDate = null;

                if (model.getValueAt(i, 2) instanceof java.util.Date) {
                    startDate = new java.sql.Date(((java.util.Date) model.getValueAt(i, 2)).getTime());
                }

                if (model.getValueAt(i, 3) instanceof java.util.Date) {
                    endDate = new java.sql.Date(((java.util.Date) model.getValueAt(i, 3)).getTime());
                }

                String status = model.getValueAt(i, 4).toString();
                // AUTO EXPIRE 
                java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

                if (endDate != null && endDate.before(today)) {
                    status = "Expired";
                    model.setValueAt("Expired", i, 4); // JTable UPDATE
                }

                String social = model.getValueAt(i, 5) != null
                        ? model.getValueAt(i, 5).toString()
                        : "";

                String sql = "UPDATE Affiliates SET "
                        + "affiliate_id=?, aff_name=?, start_date=?, end_date=?, aff_status=?, soc_med_acc=? "
                        + "WHERE affiliate_id=?";

                PreparedStatement pst = conn.prepareStatement(sql);

                pst.setString(1, new_Id);
                pst.setString(2, name);
                pst.setDate(3, startDate);
                pst.setDate(4, endDate);
                pst.setString(5, status);
                pst.setString(6, social);
                pst.setString(7, OG_Id);

                pst.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Changes saved successfully!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }

        loadAffiliateTable();
    
    }                                       


    // Variables declaration - do not modify                     
    private javax.swing.JTable affiliatetable;
    private javax.swing.JButton back;
    private javax.swing.JLabel bg;
    private javax.swing.JButton edittable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refreshbtn;
    private javax.swing.JButton savebtn;
    private javax.swing.JButton searchaffiliateID;
    // End of variables declaration                   

}
