// sa SaveActionPerformed()

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

        javax.swing.JFrame frame =(javax.swing.JFrame)
        javax.swing.SwingUtilities.getWindowAncestor(this);

        frame.setContentPane(
            new editorderitem(finalOrderID)
        );

        frame.revalidate();
        frame.repaint();
    }
