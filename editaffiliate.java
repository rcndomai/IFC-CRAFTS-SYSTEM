import java.awt.CardLayout;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import java.awt.FontFormatException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JPanel;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import com.toedter.calendar.JDateChooser;

public class editaffiliate extends javax.swing.JPanel {
    
    private java.awt.Font customFont;
    private Connection conn;
    private JPanel container; 
    private CardLayout layout;
    
    public editaffiliate() {
    initComponents();
    initConnection();

    affiliatestatus.setUI(new javax.swing.plaf.basic.BasicComboBoxUI());
    affiliatestatus.setBackground(java.awt.Color.WHITE);
    affiliatestatus.setForeground(java.awt.Color.BLACK);

    loadFonts();

    aID.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
    affname.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
    startd.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
    endd.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
    affstat.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
    sma.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
}
    
    private void initConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/IFC_Database",
                "postgres",  
                "123"       
            );

            if (conn != null && !conn.isClosed()) {
                JOptionPane.showMessageDialog(this, "PostgreSQL Connected Successfully!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Database Connection Failed!\n" + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace();
        }
    }

/** GENERATED CODE NG private void initComponents() {

                ...........

                                   */

  //BUTTONS
    private void afiliatenameActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }    
  
  //DELETE BUTTON WITH PREVIEWWWWW
    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {                                       

        String affiliateId = affiliateID.getText().trim();

        if (affiliateId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Affiliate ID to delete.");
            return;
        }

        try {
            // DETAILS RECORD
            String sql = "SELECT * FROM Affiliates WHERE affiliate_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, affiliateId);

            ResultSet rs = pst.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Affiliate ID not found.");
                return;
            }

            String name = rs.getString("aff_name");
            Date start = rs.getDate("start_date");
            Date end = rs.getDate("end_date");
            String status = rs.getString("aff_status");
            String social = rs.getString("soc_med_acc");

            // PREVIEW DIALOUGE 
            String message = "Are you sure to delete this record?:\n\n"
                    + "ID: " + affiliateId + "\n"
                    + "Name: " + name + "\n"
                    + "Start Date: " + start + "\n"
                    + "End Date: " + end + "\n"
                    + "Status: " + status + "\n"
                    + "Social Media: " + social + "\n\n"
                    + "Are you sure you want to delete this?";

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    message,
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String deleteSql = "DELETE FROM Affiliates WHERE affiliate_id = ?";
            PreparedStatement deletePst = conn.prepareStatement(deleteSql);
            deletePst.setString(1, affiliateId);

            int rows = deletePst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Affiliate deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed.");
            }

            deletePst.close();

            affiliateID.setText("");
            afiliatename.setText("");
            startdate.setDate(null);
            enddate.setDate(null);
            affiliatestatus.setSelectedIndex(-1);
            socmedacc.setText("");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
    }                                      
    // CLEAR BUTTON
    private void clearActionPerformed(java.awt.event.ActionEvent evt) {                                      
        int response = javax.swing.JOptionPane.showConfirmDialog(this,
            "This will clear all your inputs. Continue?",
            "Confirm Clear",
            javax.swing.JOptionPane.YES_NO_OPTION);

        if (response == javax.swing.JOptionPane.YES_OPTION) {
            affiliateID.setText("");
            afiliatename.setText("");
            startdate.setDate(null);
            enddate.setDate(null);
            affiliatestatus.setSelectedIndex(-1);
            socmedacc.setText("");
            
            javax.swing.JOptionPane.showMessageDialog(this, "Fields cleared successfully.");}
    }     
  
    // UPDATE BUTTON
    private void updateActionPerformed(java.awt.event.ActionEvent evt) {                                       

        String affiliateId = affiliateID.getText().trim();
        String affiliateName = afiliatename.getText().trim();
        String status = affiliatestatus.getSelectedItem() != null
                ? affiliatestatus.getSelectedItem().toString()
                : "";
        String socialMedia = socmedacc.getText().trim();

        java.util.Date startUtilDate = startdate.getDate();
        java.util.Date endUtilDate = enddate.getDate();

        if (affiliateId.isEmpty() || affiliateName.isEmpty()
                || startUtilDate == null || endUtilDate == null
                || status.isEmpty() || socialMedia.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        java.sql.Date startDate = new java.sql.Date(startUtilDate.getTime());
        java.sql.Date endDate = new java.sql.Date(endUtilDate.getTime());

        try {

            String checkSql = "SELECT * FROM Affiliates WHERE affiliate_id = ?";
            PreparedStatement checkPst = conn.prepareStatement(checkSql);
            checkPst.setString(1, affiliateId);
            ResultSet rs = checkPst.executeQuery();

            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Affiliate ID not found.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Update this affiliate record?",
                    "Confirm Update",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String sql = "UPDATE Affiliates SET "
                    + "aff_name = ?, "
                    + "start_date = ?, "
                    + "end_date = ?, "
                    + "aff_status = ?, "
                    + "soc_med_acc = ? "
                    + "WHERE affiliate_id = ?";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, affiliateName);
            pst.setDate(2, startDate);
            pst.setDate(3, endDate);
            pst.setString(4, status);
            pst.setString(5, socialMedia);
            pst.setString(6, affiliateId);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Affiliate updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Update failed.");
            }

            pst.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }

        affiliateID.setText("");
        afiliatename.setText("");
        startdate.setDate(null);
        enddate.setDate(null);
        affiliatestatus.setSelectedIndex(-1);
        socmedacc.setText("");

    }      
  
  // SAVE BUTTON
    private void saveActionPerformed(java.awt.event.ActionEvent evt) {                                     

        String affiliateId = affiliateID.getText().trim();
        String affiliateName = afiliatename.getText().trim();
        String status = affiliatestatus.getSelectedItem() != null 
                        ? affiliatestatus.getSelectedItem().toString() 
                        : "";
        String socialMedia = socmedacc.getText().trim();

        java.util.Date startUtilDate = startdate.getDate();
        java.util.Date endUtilDate = enddate.getDate();

        if (affiliateId.isEmpty() || affiliateName.isEmpty() || 
            startUtilDate == null || endUtilDate == null || 
            status.isEmpty() || socialMedia.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please fill in all fields.");
            return;
        }

        java.sql.Date startDate = new java.sql.Date(startUtilDate.getTime());
        java.sql.Date endDate = new java.sql.Date(endUtilDate.getTime());

        String message = "Save this affiliate?\n\n"
                + "ID: " + affiliateId + "\n"
                + "Name: " + affiliateName + "\n"
                + "Start Date: " + startDate + "\n"
                + "End Date: " + endDate + "\n"
                + "Status: " + status + "\n"
                + "Social Media: " + socialMedia;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                message,
                "Confirm Save",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            String sql = "INSERT INTO Affiliates "
                    + "(affiliate_id, aff_name, start_date, end_date, aff_status, soc_med_acc) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, affiliateId);
            pst.setString(2, affiliateName);
            pst.setDate(3, startDate);
            pst.setDate(4, endDate);
            pst.setString(5, status);
            pst.setString(6, socialMedia);

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Affiliate saved successfully!");

            pst.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }
        
        affiliateID.setText("");
        afiliatename.setText("");
        startdate.setDate(null);
        enddate.setDate(null);
        affiliatestatus.setSelectedIndex(-1);
        socmedacc.setText("");

    
    }       
  
  // BACK TO TABLE BUTTON
    private void backtotableActionPerformed(java.awt.event.ActionEvent evt) {                                            
        javax.swing.JFrame frame =
        (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        frame.setContentPane(new affiliate()); 
        frame.revalidate();
        frame.repaint();
    }                                           

    private void affiliatestatusActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void affiliateIDActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    } 

/** GENERATED CODE  NG // Variables declaration - do not modify   

          ....................... 
          
                      */ 

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
