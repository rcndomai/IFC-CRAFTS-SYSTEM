// nasa bandang pinakataas ito
public editorderitem() {
        initComponents();
        loadFonts();

        oID.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
        opid.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
        qua.setFont(customFont.deriveFont(java.awt.Font.BOLD, 28f));
    }

    public editorderitem(String orderID) {
        this(); // calls the first constructor

        orderD.setText(orderID);
    }

// sa SaveActionPerformed()
int rowsInserted = pst.executeUpdate();
        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this,"Order Item saved successfully!");
        
            // Clear fields AFTER save
            productID.setText("");
            quantity.setText("");

            // Keep Order ID so user can add more items
            productID.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this,"Save failed.");
        }

// bandang baba na ito
private void backtotableActionPerformed(java.awt.event.ActionEvent evt) {                                            
        javax.swing.JFrame frame =
        (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);

        orderitem orderItemPage = new orderitem();
        frame.setContentPane(orderItemPage);

        orderItemPage.revalidate();
        orderItemPage.repaint();           
    }  
