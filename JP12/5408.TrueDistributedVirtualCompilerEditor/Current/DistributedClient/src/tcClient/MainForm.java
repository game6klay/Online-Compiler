
package tcClient;
import java.awt.*;
import java.io.*;
import java.net.*;
import JavaLib.*;
import java.util.*;
import javax.swing.DefaultListModel;
import tc_Pack.*;

public class MainForm extends javax.swing.JFrame {
    //ClientDB clientDB;
    String externalEditor;
    CodeOb co;
    SingleUser currUser;
    String centralServer;
    String compileServer;
    
    DefaultListModel lmCodeList;
    
    public MainForm(SingleUser currClient, String centralServer) {
        this.centralServer = centralServer;
        
        initComponents();
        Dimension sd  = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(sd.width / 2 - this.getWidth()/ 2, sd.height / 2 - this.getHeight()/ 2);
        
        compileServer = "";
        
        this.currUser = currClient;
        jLabelUser.setText("WELCOME " + currClient.userName);

        lmCodeList = new DefaultListModel();
        updateCodeList();
        
    }
    
    public void updateCodeList() {
        lmCodeList.clear();
        for(CodeOb cob: currUser.codes) {
            lmCodeList.addElement(cob.mainClass + " " + cob.getLanguage());
        }
        jListCodeList.setModel(lmCodeList);
    }
    
    
    public boolean updateProfile() {
        try{
            String urlstr = "http://" + centralServer + ":8084/CentralServlets/UpdateUser";
            URL url = new URL(urlstr);
            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);

            // don't use a cached version of URL connection
            connection.setUseCaches(false);
            connection.setDefaultUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            // write object to servlet
            ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
            out.writeObject(currUser);
            out.close();
            
            // read object from servlet just to flush read stream
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
            in.readObject();
            in.close();
            
            return true;
        }catch(Exception e) {
            new MessageBox(this,"Error Transacting With Server!").setVisible(true);
            System.out.println("Exception: " + e);
            return false;
        }
        
    }
    
    public String getServer() {
        try{
            String urlstr = "http://" + centralServer + ":8084/CentralServlets/GetServerAddress";
            URL url = new URL(urlstr);
            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);

            // don't use a cached version of URL connection
            connection.setUseCaches(false);
            connection.setDefaultUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            // write object to servlet
            ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
            out.writeObject(currUser);
            out.close();
            
            // read object from servlet just to flush read stream
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
            SingleServer ss = (SingleServer)in.readObject();
            in.close();

            compileServer = ss.serverAddress;
            jLabelCompileServer.setText(compileServer);
            
            return ss.serverAddress;
        }catch(Exception e) {
            new MessageBox(this,"Error Transacting With Server!").setVisible(true);
            System.out.println("Exception: " + e);
            return "";
        }
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListCodeList = new javax.swing.JList();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextMainClass = new javax.swing.JTextField();
        jRadioJava = new javax.swing.JRadioButton();
        jRadioC = new javax.swing.JRadioButton();
        jLabelUser = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabelCompileServer = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextCode = new javax.swing.JTextArea();
        jLabelStatus = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton5.setText("Update");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton6.setText("Compile");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton7.setText("Execute");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton13.setText("Load From");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton8.setText("Save As");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("MY TASK");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton14.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton14.setText("MY CODES");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jListCodeList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListCodeList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jListCodeListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jListCodeList);

        jButton3.setText("Blank Compile Server Fetch Test");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jButton5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton13, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jButton6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jButton7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jButton14, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                    .add(jButton2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jScrollPane2)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jButton3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jButton5)
                .add(18, 18, 18)
                .add(jButton6)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton7)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton2)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton14)
                .add(18, 18, 18)
                .add(jButton3)
                .add(18, 18, 18)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton8)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton13)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Main Class");
        jLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTextMainClass.setText("MainClass");

        jRadioJava.setBackground(new java.awt.Color(255, 153, 0));
        buttonGroup1.add(jRadioJava);
        jRadioJava.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jRadioJava.setSelected(true);
        jRadioJava.setText("Java");
        jRadioJava.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioJava.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jRadioC.setBackground(new java.awt.Color(255, 153, 0));
        buttonGroup1.add(jRadioC);
        jRadioC.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jRadioC.setText("C/C++");
        jRadioC.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jRadioC.setMargin(new java.awt.Insets(0, 0, 0, 0));

        jLabelUser.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelUser.setText("USER");
        jLabelUser.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("LAST COMPILE SERVER ");
        jLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelCompileServer.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabelCompileServer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelCompileServer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 90, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jTextMainClass, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jRadioJava, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jRadioC))
                    .add(jPanel2Layout.createSequentialGroup()
                        .add(jLabelUser, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 382, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 175, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLabelCompileServer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelCompileServer, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 20, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabelUser)
                        .add(jLabel3)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jRadioC)
                    .add(jLabel2)
                    .add(jTextMainClass, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jRadioJava))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(new java.awt.Component[] {jLabel2, jLabel3, jLabelCompileServer, jLabelUser, jRadioC, jRadioJava, jTextMainClass}, org.jdesktop.layout.GroupLayout.VERTICAL);

        jPanel5.setBackground(new java.awt.Color(153, 153, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jTextCode.setColumns(20);
        jTextCode.setRows(5);
        jScrollPane1.setViewportView(jTextCode);

        jLabelStatus.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabelStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabelStatus.setText("STATUS");
        jLabelStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("CLOSE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(0, 102, 102));
        jButton9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton9.setText("ADD TO SUBMISSION");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(jLabelStatus, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 113, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jButton9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jButton9)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(jLabelStatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 28, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jPanel5Layout.createSequentialGroup()
                        .add(jButton1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                        .add(1, 1, 1)))
                .addContainerGap())
        );

        jPanel5Layout.linkSize(new java.awt.Component[] {jButton1, jLabelStatus}, org.jdesktop.layout.GroupLayout.VERTICAL);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(6, 6, 6))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jPanel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        new LoadForm();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
// TODO add your handling code here:
        getServer();
        if(compileServer.equals("")) {
            new MessageBox(this, "NO COMPILE SERVER AVAILABLE!").setVisible(true);
            return;
        }
        
        prepareCodeOb();
        if(jRadioJava.isSelected()) {
            co.toDo = 4; // execute java
            setStatus("Java Execution Session Called");
            if(!transact()) { // if server down
                return;
            }
            if(co.toDo == 401) { // ok
                new TaskBox(this,"PROGRAM OUTPUT",co.returnString).setVisible(true);
            }else {
                new TaskBox(this,"ERROR EXECUTING CODE",co.returnString).setVisible(true);
            }
        }
        else {
            co.toDo = 14; // execute c
            setStatus("C Execution Session Called");
            if(!transact()) { // if server down
                return;
            }
            if(co.toDo == 1401) { // ok
                new TaskBox(this,"PROGRAM OUTPUT",co.returnString).setVisible(true);
            }else {
                new TaskBox(this,"ERROR EXECUTING CODE",co.returnString).setVisible(true);
            }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
// TODO add your handling code here:
        getServer();
        if(compileServer.equals("")) {
            new MessageBox(this, "NO COMPILE SERVER AVAILABLE!").setVisible(true);
            return;
        }
        
        prepareCodeOb();
        if(jRadioJava.isSelected()) {
            co.toDo = 3; // compile java
            setStatus("Java Compilation Session Called");
            if(!transact()) { // if server down
                return;
            }
            if(co.toDo == 301) { // ok
                new TaskBox(this,"COMPILER OUTPUT",co.returnString).setVisible(true);
            }else {
                new TaskBox(this,"ERROR COMPILING CODE",co.returnString).setVisible(true);
            }
        }
        else {
            co.toDo = 13; // compile c
            setStatus("C Compilation Session Called");
            if(!transact()) { // if server down
                return;
            }
            if(co.toDo == 1301) { // ok
                new TaskBox(this,"COMPILER OUTPUT",co.returnString).setVisible(true);
            }else {
                new TaskBox(this,"ERROR COMPILING CODE",co.returnString).setVisible(true);
            }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    public void setStatus(String str) {
        Calendar c = Calendar.getInstance();
        String refreshTime = c.get(c.HOUR) + ":" + c.get(c.MINUTE) + ":" + c.get(c.SECOND) + " >> ";
        jLabelStatus.setText(refreshTime + str);
    }
    

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
// TODO add your handling code here:
        if(updateProfile()) {
            new MessageBox(this,"Profile Updated Successfully!").setVisible(true);
        }else {
            new MessageBox(this,"Error Updating Profile!").setVisible(true);
        }

    }//GEN-LAST:event_jButton5ActionPerformed

    public void prepareCodeOb() {
        co = new CodeOb();
        co.userID = currUser.userID;
        co.code = jTextCode.getText();
        co.mainClass = jTextMainClass.getText();
        co.languageID = jRadioJava.isSelected() ? 0 : 1;
    }
    
    public boolean transact() {
        
        try{
            String urlstr = "http://" + compileServer + ":8084/CompileServlets/TCMain";
            URL url = new URL(urlstr);
            URLConnection connection = url.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);

            // don't use a cached version of URL connection
            connection.setUseCaches(false);
            connection.setDefaultUseCaches(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            
            // write object to servlet
            ObjectOutputStream out = new ObjectOutputStream(connection.getOutputStream());
            out.writeObject(co);
            out.close();
            
            // read object from servlet
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
            co = (CodeOb)in.readObject();
            in.close();
            
            return true;
        }catch(Exception e) {
            new MessageBox(this,"Error Transacting With Current Compile Server. Try Again!").setVisible(true);
            System.out.println("Exception: " + e);
            return false;
        }        
    }
    
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
// TODO add your handling code here:
        String code, fname;
        FileDialog fd = new FileDialog(this,"SELECT FILE TO SAVE TO",FileDialog.SAVE);
        fd.setVisible(true);
        if(fd.getFile()==null) {
            return;
        }
        
        code = jTextCode.getText();
        fname = fd.getDirectory() + fd.getFile();
        
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fname));
            out.write(code);
            out.close();
        }catch(Exception e) {
            System.out.println("Error Writing File. " + e);
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
// TODO add your handling code here:
        String code, fname;
        FileDialog fd = new FileDialog(this,"SELECT FILE TO COPY FROM",FileDialog.LOAD);
        fd.setVisible(true);
        if(fd.getFile()==null) {
            return;
        }
        
        fname = fd.getDirectory() + fd.getFile();
        code = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(fname));
            String temp;
            while((temp = in.readLine())!=null) {
                code += temp + "\n";
            }
            in.close();
            jTextCode.setText(code);
        }catch(Exception e) {
            System.out.println("Error Reading File. " + e);
        }        
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
        TaskBox tb = new TaskBox(this," TASK DESCRIPTION",currUser.task);
        tb.setVisible(true);
        
    }//GEN-LAST:event_jButton2ActionPerformed
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
        if(updateProfile()) {
            System.out.println("Profile Updated Successfully!");
        }else {
            System.out.println("Error Updating Profile!");
        }

        setVisible(false);
        new Login(centralServer).setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        new ManageCodes(this).setVisible(true);
        updateCodeList();
        
        
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        CodeOb coNew = new CodeOb();
        coNew.mainClass = jTextMainClass.getText();
        coNew.code = jTextCode.getText();
        coNew.languageID = jRadioJava.isSelected() ? 0 : 1;
        
        boolean found = false;
        int index = 0;
        for(CodeOb co: currUser.codes) {
            if(co.mainClass.equals(coNew.mainClass) && co.languageID==coNew.languageID) {
                found = true;
                break;
            }
            index++;
        }
        
        if(found) {
            MessageBox mb = new MessageBox(this, "Mainclass " + coNew.mainClass + " Already Exists. Overwrite?");
            mb.setVisible(true);
            if(mb.ok) {
                currUser.codes.remove(index);
                currUser.codes.add(index, coNew);
                updateCodeList();
            }
        }else {
            currUser.codes.add(coNew);
            updateCodeList();
        }
        
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jListCodeListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jListCodeListMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()!=2) {
            return;
        }
        
        int index = jListCodeList.getSelectedIndex();
        if(index==-1) {
            return;
        }

        CodeOb cob = currUser.codes.get(index);
        jTextCode.setText(cob.code);
        jTextMainClass.setText(cob.mainClass);
        if(cob.languageID==0) {
            jRadioJava.setSelected(true);
        }else {
            jRadioC.setSelected(true);
            
        }
        
    }//GEN-LAST:event_jListCodeListMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        getServer();
        
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelCompileServer;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelUser;
    private javax.swing.JList jListCodeList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioC;
    private javax.swing.JRadioButton jRadioJava;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextCode;
    private javax.swing.JTextField jTextMainClass;
    // End of variables declaration//GEN-END:variables
    
}
