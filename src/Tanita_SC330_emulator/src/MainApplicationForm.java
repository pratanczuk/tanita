
import java.awt.Component;
import java.io.Console;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import jssc.SerialPort;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author para
 */
public class MainApplicationForm extends javax.swing.JFrame {

    /**
     * Creates new form NewApplication
     */
    public MainApplicationForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Port" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select Test", "Adult", "Athlete", "Child" }));

        jButton1.setText("Send");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Get test from data.csv");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("or");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jLabel1))
                .addGap(26, 26, 26)
                .addComponent(jButton1)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    String datagram = "";
    
    if( COMPort.Port.isEmpty()) 
    {
            JOptionPane.showMessageDialog(null, "Wybierz port COM");
            return;
    }

        if( this.jComboBox2.getSelectedItem().toString().equalsIgnoreCase("Adult")) datagram = data.getSampleAdultData();
        else if( this.jComboBox2.getSelectedItem().toString().equalsIgnoreCase("Child"))  datagram = data.getSampleChildData();
        else if( this.jComboBox2.getSelectedItem().toString().equalsIgnoreCase("Athlete")) datagram = data.getSampleAthleteData();
        else if(!data.TestData.isEmpty()) datagram = data.TestData;
        else
        {
            JOptionPane.showMessageDialog(null, "Wybierz jeden z testów\nlub wczytaj z pliku");
            return;
        }
        System.out.println( datagram);

        writeToSeriaPort(datagram);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        String[] portNames = SerialPortList.getPortNames();
        for(int i = 0; i < portNames.length; i++)
        {
            System.out.println(portNames[i]);
            this.jComboBox1.addItem(portNames[i].toString());
        }  
        // TODO add your handling code here:
    }//GEN-LAST:event_formComponentShown

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        final JFileChooser fc = new JFileChooser();
        int returnVal;
        String selectedFile  = null;
        Component aComponent = null;
        returnVal = fc.showOpenDialog(this);
        if( 0 == returnVal)
        {
            selectedFile  = fc.getSelectedFile().getPath();
            System.out.println(selectedFile);
            //load csv
            //parse csv
            try
            {
                data.TestData =  data.ArrayToString( data.importFromFile(selectedFile));
                System.out.println( data.TestData );
            }
            catch( Exception ex)
            {
                JOptionPane.showMessageDialog(null, "Nieprawidłowy plik testowy");
                return;
            }
            JOptionPane.showMessageDialog(null, "Test załadowany poprawnie\n");

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        if( jComboBox1.getSelectedIndex() > 0)
        {
            COMPort.Port = jComboBox1.getSelectedItem().toString();
            System.out.println(COMPort.Port);
        }
        else COMPort.Port = "";
            
    }//GEN-LAST:event_jComboBox1ActionPerformed

    static SerialPortConfig COMPort = new SerialPortConfig();
    static TanitaData data = new TanitaData(); 

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
            java.util.logging.Logger.getLogger(MainApplicationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainApplicationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainApplicationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainApplicationForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainApplicationForm().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

      private static void writeToSeriaPort( String datagram)
    {
        
        SerialPort serialPort = new SerialPort(COMPort.Port);
        boolean writeresult = false; 
        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(	COMPort.baudRate, 
            				COMPort.dataBits,
            				COMPort.stopBits,
            				COMPort.parity);
            writeresult = serialPort.writeBytes(datagram.getBytes());
            writeresult &= serialPort.writeByte((byte)0xd);
            writeresult &= serialPort.writeByte((byte)0xa);
            if( !writeresult) JOptionPane.showMessageDialog(null, "Błąd zapisu do portu com"); 
            
            serialPort.purgePort(serialPort.PURGE_RXCLEAR);
            
            System.out.println("COMPort.Port");
            System.out.println(datagram);

            serialPort.closePort();//Close serial port
        }
        catch (SerialPortException ex) {
            JOptionPane.showMessageDialog(null, "Błąd zapisu do portu com:\n" + ex.getExceptionType()); 
            System.out.println(ex);
            return;
        }
        JOptionPane.showMessageDialog(null, "Dane zostały wysłane do portu " + COMPort.Port); 
    }    	

}
