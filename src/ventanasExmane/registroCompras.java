/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanasExmane;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Kendall
 */
public class registroCompras extends javax.swing.JFrame {

    /**
     * Creates new form registroCompras
     */
    private Connection connection = null;
    private ResultSet rs = null; 
    private Statement s = null;
    ArrayList productos = new ArrayList();
    ArrayList copiaproductos = new ArrayList();
    String nombre = "", producto = "", sexo = "";
    int edad = 0;
    Date fecha;
    int idproducto = 0;
    public static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    
    
    public registroCompras() {
        initComponents();
        this.setLocationRelativeTo(null);
    }
    public void conexion(){
        if (connection != null) {
            return;
        }

        String url = "jdbc:postgresql://localhost:5432/examenIIICuatriKendall";
        String password = "postgresql";
        try {
            Class.forName("org.postgresql.Driver");//libreria
            connection = DriverManager.getConnection(url, "postgres", password);
            if (connection != null) {
                System.out.println("Connecting to database...");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null , e);
        }
    }
    public void cargarProductos(){
        conexion();
        
        try{
            s = connection.createStatement();
            rs = s.executeQuery("SELECT id_producto, nombre_producto, tipo FROM productos");
            
            while(rs.next()){
                productos.add(rs.getString("tipo"));
                productos.add(rs.getString("id_producto"));
                productos.add(rs.getString("nombre_producto"));
                
                copiaproductos.add(rs.getString("nombre_producto"));
                copiaproductos.add(rs.getString("id_producto"));
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error de conexión");
        }
    }
    public void verifiacarEdadCompra() {
        try {
            edad = Integer.parseInt(txtEdad.getText());
            cargarProductos();
            if (edad > 0 && edad <= 12) {
                jCB2Productos.removeAllItems();
                for(int x = 0; x < productos.size(); x++){
                    if(productos.get(x).equals("niño")){
                        jCB2Productos.addItem(productos.get(x + 2).toString());
                    }
                }
            }
            else if(edad >= 13 && edad <= 17){
                jCB2Productos.removeAllItems();
                for(int x = 0; x < productos.size(); x++){
                    if(productos.get(x).equals("joven")){
                        jCB2Productos.addItem(productos.get(x + 2).toString());
                    }
                }
            }
            else if(edad >= 18){
                jCB2Productos.removeAllItems();
                for(int x = 0; x < productos.size(); x++){
                    if(productos.get(x).equals("adulto")){
                        jCB2Productos.addItem(productos.get(x + 2).toString());
                    }
                }
            }
            productos.clear();
            btnCompras.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "La edad debe ser de tipo numerico.");
        }
    }
    public void guardarCompras(){
        try{
            conexion();
            fecha = jDateFecha.getDate();
            String fechaDig = dateFormat.format(fecha);
            System.out.println(fechaDig);

            nombre = txtNombre.getText();
            producto = jCB2Productos.getSelectedItem().toString();
            sexo = jCB1Sexo.getSelectedItem().toString();
            
            if (nombre.equals("") || producto.equals("") || sexo.equals("")) {
                JOptionPane.showMessageDialog(null, "Los espacios de nombre, edad y sexo no pueden quedar vacio.");
            } else {
                for (int x = 0; x < copiaproductos.size(); x++) {
                    if (copiaproductos.get(x).equals(producto)) {
                        String codigo = (String) copiaproductos.get(x + 1);
                        idproducto = Integer.parseInt(codigo);
                        break;
                    }
                }

                s = connection.createStatement();
                int z = s.executeUpdate("INSERT INTO compras (nombre_usuario, edad, sexo, id_producto, fecha_compra) VALUES ('" + nombre + "', '" + edad + "', '" + sexo + "','" + idproducto + "' ,'" + fecha + "')");
                if (z == 1) {
                    JOptionPane.showMessageDialog(null, "Se agregó el registro de manera exitosa");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al insertar el registro de la compra");
                }
                btnCompras.setEnabled(false);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "No esta seleccionando la fecha");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jCB1Sexo = new javax.swing.JComboBox<>();
        txtNombre = new javax.swing.JTextField();
        btnCompras = new javax.swing.JButton();
        jCB2Productos = new javax.swing.JComboBox<>();
        txtEdad = new javax.swing.JTextField();
        jDateFecha = new com.toedter.calendar.JDateChooser();
        btnVerificarEdad = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Realizar Compras");

        jLabel2.setText("Nombre Comprador");

        jLabel3.setText("Edad Comprador");

        jLabel4.setText("Sexo");

        jLabel5.setText("Fecha Compra");

        jLabel6.setText("Productos");

        jCB1Sexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccionar", "Masculino", "Femenino" }));

        btnCompras.setText("Comprar Producto");
        btnCompras.setEnabled(false);
        btnCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprasActionPerformed(evt);
            }
        });

        btnVerificarEdad.setText("Verificar Edad");
        btnVerificarEdad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerificarEdadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombre)
                            .addComponent(txtEdad, javax.swing.GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 149, Short.MAX_VALUE)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jCB2Productos, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jDateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jCB1Sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnVerificarEdad)
                        .addGap(18, 18, 18)
                        .addComponent(btnCompras)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(jCB2Productos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jDateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jCB1Sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnVerificarEdad)
                    .addComponent(btnCompras))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprasActionPerformed
        guardarCompras();
    }//GEN-LAST:event_btnComprasActionPerformed

    private void btnVerificarEdadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificarEdadActionPerformed
        verifiacarEdadCompra();
    }//GEN-LAST:event_btnVerificarEdadActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(registroCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(registroCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(registroCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(registroCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registroCompras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCompras;
    private javax.swing.JButton btnVerificarEdad;
    private javax.swing.JComboBox<String> jCB1Sexo;
    private javax.swing.JComboBox<String> jCB2Productos;
    private com.toedter.calendar.JDateChooser jDateFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField txtEdad;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
