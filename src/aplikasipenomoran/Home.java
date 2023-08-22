/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aplikasipenomoran;

import com.formdev.flatlaf.FlatDarkLaf;
import com.itextpdf.text.DocumentException;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.UnsupportedLookAndFeelException;
import module.Cetak;

/**
 *
 * @author User
 */
public class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    public Home() {
        callAllMethod();
    }
    
    public void callAllMethod(){
        initComponents();
        time();
        getNoAntrian();
        getAllTable();
        DefaultTab();
    }
    
    public void getAllTable(){
        getTableDays();
        getTableUser();
        getTableShowRoom();
        getTableService();
        getDataAdvisor();
    }
    
    public void realTimeMethod(){
        
        // Validasi Status Page Service
        if(sv_status.getSelectedItem().equals("SELESAI")){
            sv_btn_edit.setEnabled(false);
            sv_btn_add.setEnabled(false);
            sv_btn_add.setText("TERIMAKASIH TELAH SERVICE");
        }else if(sv_status.getSelectedItem().equals("PROSES")){
            sv_btn_edit.setEnabled(true);
            sv_btn_add.setEnabled(true);
            sv_btn_add.setText("CHECKOUT");
        }else{
            sv_btn_edit.setEnabled(true);
            sv_btn_add.setEnabled(true);
            sv_btn_add.setText("PILIH DATA UNTUK SERVICE");
        }
        
        // BUTTON
        if((sh_check_out.getText().equals("-")) || (sh_status.getSelectedItem().equals("TIDAK SERVICE"))){
            sh_btn_add.setEnabled(true);
            sh_btn_edit.setEnabled(false);
        }else if(sh_check_out.getText().equals("DALAM PROSES SERVICE")){
            sh_btn_add.setEnabled(false);
            sh_btn_edit.setEnabled(true);
        }else{
            sh_btn_add.setEnabled(false);
            sh_btn_edit.setEnabled(false);
        }
    }
    
    public void DefaultTabel(JTable newTable, int size){
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        TableColumnModel columnModel = newTable.getColumnModel();
        for (int i = 0; i < size; i++) {
            newTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            columnModel.getColumn(i).setPreferredWidth(200);
            columnModel.getColumn(i).setMinWidth(200);
        }
    }
    
    // Default JTabbed
    public void DefaultTab(){
        Jtabbed_utama.setEnabledAt(1, false);
        Jtabbed_utama.setEnabledAt(2, false);
        Jtabbed_utama.setEnabledAt(3, false);
    }
    
    // FUNGSI TIME
    public void time(){
        new Thread(){
            @Override
            public void run() {
                while (true) {  
                    realTimeMethod();
                    Date da = new Date();
                    SimpleDateFormat ft = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
                    itsTime.setText(String.valueOf(ft.format(da)));
                }
            }
            
        }.start();
    }

    // Fungsi Ambil Data
    public void getTableDays(){
        // Create New Table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("NOMOR POLISI");
        model.addColumn("STATUS SERVICE");
        
        //menampilkan data database kedalam tabel
        try {
            String sql = "SELECT no_pol, status_service FROM tb_showroom WHERE check_in=CURDATE();";
            java.sql.Connection conn = Connect.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{res.getString(1),res.getString(2)});
            }
            tbl_days.setModel(model);
            total_transaksi_days.setText(String.valueOf(tbl_days.getRowCount()));
            DefaultTabel(tbl_days, 2);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "TABEL DAYS ERROR : "+e);
        }
    }
    
    public void getTableUser(){
        // Create New Table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID USER");
        model.addColumn("USERNAME");
        model.addColumn("PASSWORD");
        model.addColumn("TYPE USER");
        
        //menampilkan data database kedalam tabel
        try {
            String sql = "SELECT id_user, username, password, type FROM `tb_user`";
            java.sql.Connection conn= Connect.configDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{res.getString(1),res.getString(2),res.getString(3),res.getString(4)});
            }
            usr_tbl_user.setModel(model);
            DefaultTabel(usr_tbl_user, 4);
            usr_total_user.setText(String.valueOf(usr_tbl_user.getRowCount()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "TABEL USER ERROR : "+e);
        }
    }
    
    public void getTableShowRoom(){
        // Create New Table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID SHOWROOM");
        model.addColumn("NO ANTRI");
        model.addColumn("NO POLISI");
        model.addColumn("CHECK IN");
        model.addColumn("CHECK OUT");
        model.addColumn("GATEPASS");
        model.addColumn("SERVICE ADVISOR");
        model.addColumn("TYPE MOBIL");
        model.addColumn("KETERANGAN");
        model.addColumn("STATUS SERVICE");
        
        //menampilkan data database kedalam tabel
        try {
            String sql = "SELECT * FROM tb_showroom";
            java.sql.Connection conn= Connect.configDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{ 
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6),
                    res.getString(7),
                    res.getString(8),
                    res.getString(9),
                    res.getString(10),
                });
            }
            sh_tbl_showroom.setModel(model);
            DefaultTabel(sh_tbl_showroom, 10);
            usr_total_showroom.setText(String.valueOf(sh_tbl_showroom.getRowCount()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "TABEL SHOWROOM ERROR : "+e);
        }
    }
    
    public void getTableService(){
        // Create New Table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID SHOWROOM");
        model.addColumn("ID SERVICE");
        model.addColumn("WARNA MOBIL");
        model.addColumn("NOMOR RANGKA");
        model.addColumn("NOMOR MESIN");
        model.addColumn("STATUS SERVICE");
        
        //menampilkan data database kedalam tabel
        try {
            String sql = "SELECT id_showroom, id_service, warna, no_rangka, no_mesin, status_service FROM `tb_service`";
            java.sql.Connection conn= Connect.configDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{ 
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6),
                });
            }
            sv_tbl_service.setModel(model);
            DefaultTabel(sv_tbl_service, 6);
            sv_total_service.setText(String.valueOf(sv_tbl_service.getRowCount()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "TABEL SERVICE ERROR : "+e);
        }
    }
    
    public void getTableNginap(){
        
        JTable nginap = new JTable();
        // Create New Table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID SERVICE");
        model.addColumn("NOMOR POLISI");
        model.addColumn("TYPE MOBIL");
        model.addColumn("WARNA");
        model.addColumn("CHECK IN");
        model.addColumn("CHECK OUT");
        model.addColumn("CHECK KETERANGAN");
        
        //menampilkan data database kedalam tabel
        try {
            String sql = "SELECT SE.id_service, SH.no_pol, SH.type_mobil, SE.warna, SH.check_in, SH.check_out, SH.keterangan FROM `tb_service` AS SE JOIN `tb_showroom` AS SH ON SE.id_showroom=SH.id_showroom WHERE SE.status_service='PROSES';";
            java.sql.Connection conn= Connect.configDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(sql);
            while(res.next()){
                model.addRow(new Object[]{ 
                    res.getString(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5),
                    res.getString(6),
                    res.getString(7),
                });
            }
            nginap.setModel(model);
            DefaultTabel(nginap, 7);
            
            try {
                new Cetak().generatePDF(nginap, "DATA MENGINAP");
            } catch (IOException | DocumentException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "TABEL SERVICE ERROR : "+e);
        }
    }
    
    public void getDataShowRoom(){
        String id_showroom = sv_id_showroom.getText();
        try {
            String sql = "SELECT check_in, no_pol, type_mobil, keterangan FROM `tb_showroom` WHERE `id_showroom` = '"+id_showroom+"'";
            java.sql.Connection conn= Connect.configDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(sql);
            while (res.next()) {                
                sv_tanggal.setText(res.getString(1));
                sv_no_polisi.setText(res.getString(2));
                sv_type_mobil.setText(res.getString(3));
                sv_keterangan.setText(res.getString(4));
            }
            DefaultTabel(usr_tbl_user, 4);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "TABEL DATA SHOWROOM ERROR : "+e);
        }
    }
    
    public void getDataAdvisor(){
        sh_service_advisor.removeAllItems();
        try {
            String sql = "SELECT username FROM `tb_user` WHERE type = 'SERVICE ADVISOR'";
            java.sql.Connection conn= Connect.configDB();
            java.sql.Statement stm=conn.createStatement();
            java.sql.ResultSet res=stm.executeQuery(sql);
            while(res.next()){
                sh_service_advisor.addItem(res.getString(1));
            }
            DefaultTabel(usr_tbl_user, 4);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "TABEL SHOWROOM ERROR : "+e);
        }
    }
    
    public void getNoAntrian(){
        try {
            String SQLQuery = "SELECT no_antrian FROM `tb_antrian` WHERE `tanggal` = CURRENT_DATE";
            java.sql.Connection conn = Connect.configDB();
            java.sql.PreparedStatement ps = conn.prepareStatement(SQLQuery);
            java.sql.ResultSet rs = ps.executeQuery();
            while(rs.next()){
                countAntrian.setText(String.valueOf(rs.getInt(1)));
            }
        } catch (SQLException ex ){
            JOptionPane.showMessageDialog(rootPane, "Nomor Antrian Tidak Tersimpan.");
        }
    }
    
    public void setNoAntrian(){
        int validate = 0;
        getNoAntrian();
        Integer count_db = Integer.valueOf(countAntrian.getText());
        
        try {
            String SQLQuery = "SELECT no_antrian FROM `tb_antrian` WHERE `tanggal` = CURRENT_DATE";
            java.sql.Connection conn = Connect.configDB();
            java.sql.PreparedStatement ps = conn.prepareStatement(SQLQuery);
            java.sql.ResultSet rs = ps.executeQuery();
            while(rs.next()){
                validate++;
                countAntrian.setText(String.valueOf(count_db));
            }
        } catch (SQLException ex ){
            JOptionPane.showMessageDialog(rootPane, "Nomor Antrian Tidak Tersimpan."+validate);
        }
        
        if(validate > 0 ){
            try {
                    String sql = "UPDATE `tb_antrian` SET `no_antrian` = ? WHERE `tanggal` = CURRENT_DATE";
                    java.sql.Connection conn = Connect.configDB();
                    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setInt(1, count_db+1);
                    pst.executeUpdate();
                    countAntrian.setText(String.valueOf(count_db+1));
                    JOptionPane.showMessageDialog(null, "Nomor Antrian Tersimpan.");
            } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }else{
            try {
                    String sql = "INSERT INTO `tb_antrian` (`no_antrian`, `tanggal`) VALUES (?, CURRENT_DATE)";
                    java.sql.Connection conn = Connect.configDB();
                    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setInt(1, count_db);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Nomor Antrian Tersimpan.");
                    countAntrian.setText(String.valueOf(count_db));
            } catch (HeadlessException | SQLException e) {
                    JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }
    
    public void getToLogin(){
        String USERNAME = text_username.getText();
        String PASSWORD = String.valueOf(text_password.getPassword());
        if(USERNAME.equals("")){
            JOptionPane.showMessageDialog(rootPane, "HARAP MASUKAN USERNAME DENGAN BENAR !!!");
            text_username.requestFocus();
        }else if(PASSWORD.equals("")){
            JOptionPane.showMessageDialog(rootPane, "HARAP MASUKAN PASSWORD DENGAN BENAR !!!");
            text_password.requestFocus();
        }else{
            
            PreparedStatement PS;
                ResultSet RS;
                String Query = "SELECT * FROM tb_user WHERE username=? AND password=?";
                try{
                PS = Connect.configDB().prepareStatement(Query);
                PS.setString(1, USERNAME);
                PS.setString(2, PASSWORD);
                RS = PS.executeQuery();
                
                if(RS.next()){
                    if(RS.getString(4).equals("ADMIN")){
                        JOptionPane.showMessageDialog(rootPane, "ANDA ADMIN");
                        Jtabbed_utama.setEnabledAt(1, true);
                        Jtabbed_utama.setEnabledAt(2, true);
                        Jtabbed_utama.setEnabledAt(3, true);
                        text_username.setText("");
                        text_password.setText("");

                    }else{
                        JOptionPane.showMessageDialog(rootPane, "ANDA SURVISOR");
                        Jtabbed_utama.setEnabledAt(1, false);
                        Jtabbed_utama.setEnabledAt(2, true);
                        Jtabbed_utama.setEnabledAt(3, true);
                        text_username.setText("");
                        text_password.setText("");
                    }
                    getAllTable();
                }else{
                    JOptionPane.showMessageDialog(rootPane, "DATA ANDA TIDAK TERSIMPAN DISYSTEM KAMI !!!");
                }
                }catch (HeadlessException | SQLException ex){
                    JOptionPane.showMessageDialog(rootPane, ex);
                }    
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        toUp = new javax.swing.JLabel();
        txt_antrian = new javax.swing.JLabel();
        itsTime = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        countAntrian = new javax.swing.JLabel();
        Jtabbed_utama = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        text_username = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        text_password = new javax.swing.JPasswordField();
        btn_login = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_days = new javax.swing.JTable();
        jLabel16 = new javax.swing.JLabel();
        total_transaksi_days = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        usr_id_user = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        usr_password = new javax.swing.JPasswordField();
        jScrollPane3 = new javax.swing.JScrollPane();
        usr_tbl_user = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        usr_total_user = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        usr_username = new javax.swing.JTextField();
        usr_type = new javax.swing.JComboBox<>();
        usr_btn_logout = new javax.swing.JButton();
        usr_btn_add = new javax.swing.JButton();
        usr_btn_delete = new javax.swing.JButton();
        usr_btn_edit = new javax.swing.JButton();
        usr_btn_refresh = new javax.swing.JButton();
        usr_btn_add2 = new javax.swing.JButton();
        usr_laporan = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        sh_tbl_showroom = new javax.swing.JTable();
        sh_btn_logout = new javax.swing.JButton();
        sh_btn_add = new javax.swing.JButton();
        sh_btn_delete = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        sh_id_showroom = new javax.swing.JTextField();
        sh_no_polisi = new javax.swing.JTextField();
        sh_check_in = new javax.swing.JButton();
        sh_check_out = new javax.swing.JButton();
        sh_gatepass = new javax.swing.JComboBox<>();
        sh_service_advisor = new javax.swing.JComboBox<>();
        sh_type_mobil = new javax.swing.JTextField();
        sh_no_antri = new javax.swing.JSpinner();
        jScrollPane5 = new javax.swing.JScrollPane();
        sh_keterangan = new javax.swing.JTextArea();
        jLabel44 = new javax.swing.JLabel();
        sh_status = new javax.swing.JComboBox<>();
        sh_btn_edit = new javax.swing.JButton();
        sh_btn_refresh = new javax.swing.JButton();
        sh_btn_cetak = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        usr_total_showroom = new javax.swing.JLabel();
        cariDate = new javax.swing.JButton();
        jLabel27 = new javax.swing.JLabel();
        search_polisi = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        sv_tbl_service = new javax.swing.JTable();
        sv_btn_logout = new javax.swing.JButton();
        sv_btn_add = new javax.swing.JButton();
        jScrollPane10 = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        sv_id_service = new javax.swing.JTextField();
        sv_no_polisi = new javax.swing.JTextField();
        sv_tanggal = new javax.swing.JButton();
        sv_type_mobil = new javax.swing.JTextField();
        jScrollPane11 = new javax.swing.JScrollPane();
        sv_keterangan = new javax.swing.JTextArea();
        jLabel55 = new javax.swing.JLabel();
        sv_status = new javax.swing.JComboBox<>();
        jLabel56 = new javax.swing.JLabel();
        sv_warna = new javax.swing.JTextField();
        jLabel57 = new javax.swing.JLabel();
        sv_no_rangka = new javax.swing.JTextField();
        jLabel58 = new javax.swing.JLabel();
        sv_no_mesin = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        sv_id_showroom = new javax.swing.JTextField();
        sv_btn_edit = new javax.swing.JButton();
        sv_btn_refresh = new javax.swing.JButton();
        sv_btn_cetak = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        sv_total_service = new javax.swing.JLabel();
        cariDate1 = new javax.swing.JButton();
        jLabel60 = new javax.swing.JLabel();
        search_showroom = new javax.swing.JTextField();
        sv_btn_nginap = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setText("Jl. Mampang Prapatan XI No. 83-85, RT.7/RW.1, Tegal Parang, Kec. Mampang Prpt.,");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setText("Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12790");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setText("Nomor HP : (021) 7987480");

        toUp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                toUpMouseClicked(evt);
            }
        });

        txt_antrian.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        txt_antrian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_antrian.setText("NOMOR ANTRIAN");
        txt_antrian.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_antrianMouseClicked(evt);
            }
        });

        itsTime.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        itsTime.setForeground(new java.awt.Color(255, 255, 255));
        itsTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itsTime.setText("it's time");

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/2.png"))); // NOI18N
        jLabel8.setOpaque(true);

        countAntrian.setBackground(new java.awt.Color(0, 0, 0));
        countAntrian.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        countAntrian.setForeground(new java.awt.Color(255, 255, 0));
        countAntrian.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        countAntrian.setText("1");
        countAntrian.setToolTipText("");
        countAntrian.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.magenta, java.awt.Color.white, java.awt.Color.pink, java.awt.Color.orange));
        countAntrian.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        countAntrian.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(toUp)
                        .addGap(24, 24, 24))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(785, 785, 785)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(countAntrian, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(itsTime, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_antrian, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txt_antrian, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(toUp)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(countAntrian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(itsTime)))
                .addContainerGap())
        );

        Jtabbed_utama.setBackground(new java.awt.Color(0, 0, 0));
        Jtabbed_utama.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Jtabbed_utama.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Welcome to Tunas Toyota Mampang");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setText("USERNAME");

        text_username.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        text_username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_usernameKeyPressed(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setText("PASSWORD");

        text_password.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        text_password.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                text_passwordKeyPressed(evt);
            }
        });

        btn_login.setBackground(new java.awt.Color(0, 0, 51));
        btn_login.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_login.setText("MASUK");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("DAFTAR TRANSAKSI UNTUK HARI INI");

        tbl_days.setAutoCreateRowSorter(true);
        tbl_days.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tbl_days.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NOMOR POLISI", "STATUS SERVICE"
            }
        ));
        tbl_days.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbl_days.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tbl_days);
        if (tbl_days.getColumnModel().getColumnCount() > 0) {
            tbl_days.getColumnModel().getColumn(0).setPreferredWidth(200);
            tbl_days.getColumnModel().getColumn(1).setPreferredWidth(200);
        }

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setText("TOTAL TRANSAKSI");

        total_transaksi_days.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        total_transaksi_days.setText("0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(130, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(text_username, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(text_password, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(total_transaksi_days, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(139, 139, 139))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel12)
                        .addGap(45, 45, 45)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(text_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(text_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btn_login)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(total_transaksi_days))
                .addGap(35, 35, 35))
        );

        Jtabbed_utama.addTab("LOGIN", jPanel3);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("ID USER");

        usr_id_user.setEditable(false);
        usr_id_user.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        usr_id_user.setEnabled(false);

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setText("PASSWORD");

        usr_password.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        usr_tbl_user.setAutoCreateRowSorter(true);
        usr_tbl_user.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usr_tbl_user.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID USER", "USERNAME", "PASSWORD", "TYPE USER"
            }
        ));
        usr_tbl_user.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        usr_tbl_user.getTableHeader().setReorderingAllowed(false);
        usr_tbl_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usr_tbl_userMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(usr_tbl_user);
        if (usr_tbl_user.getColumnModel().getColumnCount() > 0) {
            usr_tbl_user.getColumnModel().getColumn(0).setPreferredWidth(200);
            usr_tbl_user.getColumnModel().getColumn(1).setPreferredWidth(200);
            usr_tbl_user.getColumnModel().getColumn(2).setPreferredWidth(200);
            usr_tbl_user.getColumnModel().getColumn(3).setPreferredWidth(200);
        }

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel21.setText("TOTAL USER");

        usr_total_user.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usr_total_user.setText("0");

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel23.setText("USERNAME");

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel24.setText("TYPE USER");

        usr_username.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        usr_username.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                usr_usernameKeyReleased(evt);
            }
        });

        usr_type.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usr_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH", "ADMIN", "SERVICE ADVISOR" }));

        usr_btn_logout.setBackground(new java.awt.Color(153, 0, 0));
        usr_btn_logout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usr_btn_logout.setText("KELUAR");
        usr_btn_logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usr_btn_logoutMouseClicked(evt);
            }
        });
        usr_btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usr_btn_logoutActionPerformed(evt);
            }
        });

        usr_btn_add.setBackground(new java.awt.Color(0, 0, 51));
        usr_btn_add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usr_btn_add.setText("TAMBAH");
        usr_btn_add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usr_btn_addMouseClicked(evt);
            }
        });

        usr_btn_delete.setBackground(new java.awt.Color(0, 0, 51));
        usr_btn_delete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usr_btn_delete.setText("HAPUS");
        usr_btn_delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usr_btn_deleteMouseClicked(evt);
            }
        });

        usr_btn_edit.setBackground(new java.awt.Color(0, 0, 51));
        usr_btn_edit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usr_btn_edit.setText("UBAH");
        usr_btn_edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usr_btn_editMouseClicked(evt);
            }
        });
        usr_btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usr_btn_editActionPerformed(evt);
            }
        });

        usr_btn_refresh.setBackground(new java.awt.Color(0, 0, 51));
        usr_btn_refresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usr_btn_refresh.setText("PERBARUI");
        usr_btn_refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usr_btn_refreshMouseClicked(evt);
            }
        });
        usr_btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usr_btn_refreshActionPerformed(evt);
            }
        });

        usr_btn_add2.setBackground(new java.awt.Color(0, 0, 51));
        usr_btn_add2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usr_btn_add2.setText("VIEW");
        usr_btn_add2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usr_btn_add2MouseClicked(evt);
            }
        });
        usr_btn_add2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usr_btn_add2ActionPerformed(evt);
            }
        });

        usr_laporan.setBackground(new java.awt.Color(0, 0, 51));
        usr_laporan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usr_laporan.setText("CETAK LAPORAN");
        usr_laporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                usr_laporanMouseClicked(evt);
            }
        });
        usr_laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usr_laporanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(usr_btn_logout, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(usr_btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(usr_btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usr_btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usr_btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usr_type, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(usr_password, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usr_btn_add2, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                            .addComponent(usr_username)
                            .addComponent(usr_id_user))))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usr_total_user, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(usr_laporan, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 706, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usr_id_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(jLabel21)
                    .addComponent(usr_total_user)
                    .addComponent(usr_laporan))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(usr_username, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(usr_password, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(usr_btn_add2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(usr_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(usr_btn_edit)
                            .addComponent(usr_btn_add))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(usr_btn_delete)
                            .addComponent(usr_btn_refresh))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(usr_btn_logout))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        Jtabbed_utama.addTab("USER", jPanel4);

        sh_tbl_showroom.setAutoCreateRowSorter(true);
        sh_tbl_showroom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_tbl_showroom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID SHOWROOM", "NO ANTRI", "NO POLISI", "CHECK IN", "CHECK OUT", "GATEPASS", "SERVICE ADVISOR", "TYPE MOBIL", "KETERANGAN", "STATUS SERVICE"
            }
        ));
        sh_tbl_showroom.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        sh_tbl_showroom.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        sh_tbl_showroom.getTableHeader().setReorderingAllowed(false);
        sh_tbl_showroom.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sh_tbl_showroomMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(sh_tbl_showroom);
        if (sh_tbl_showroom.getColumnModel().getColumnCount() > 0) {
            sh_tbl_showroom.getColumnModel().getColumn(0).setPreferredWidth(200);
            sh_tbl_showroom.getColumnModel().getColumn(1).setPreferredWidth(200);
            sh_tbl_showroom.getColumnModel().getColumn(2).setPreferredWidth(200);
            sh_tbl_showroom.getColumnModel().getColumn(3).setPreferredWidth(200);
            sh_tbl_showroom.getColumnModel().getColumn(4).setPreferredWidth(200);
            sh_tbl_showroom.getColumnModel().getColumn(5).setPreferredWidth(200);
            sh_tbl_showroom.getColumnModel().getColumn(6).setPreferredWidth(200);
            sh_tbl_showroom.getColumnModel().getColumn(7).setPreferredWidth(200);
            sh_tbl_showroom.getColumnModel().getColumn(8).setPreferredWidth(200);
            sh_tbl_showroom.getColumnModel().getColumn(9).setPreferredWidth(200);
        }

        sh_btn_logout.setBackground(new java.awt.Color(153, 0, 0));
        sh_btn_logout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_btn_logout.setText("KELUAR");
        sh_btn_logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sh_btn_logoutMouseClicked(evt);
            }
        });

        sh_btn_add.setBackground(new java.awt.Color(0, 0, 51));
        sh_btn_add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_btn_add.setText("TAMBAH");
        sh_btn_add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sh_btn_addMouseClicked(evt);
            }
        });

        sh_btn_delete.setBackground(new java.awt.Color(0, 0, 51));
        sh_btn_delete.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_btn_delete.setText("HAPUS");
        sh_btn_delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sh_btn_deleteMouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setText("ID SHOWROOM");

        jLabel25.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel25.setText("NO ANTRI");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel26.setText("NO POLISI");

        jLabel28.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel28.setText("CHECK IN");

        jLabel29.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel29.setText("CHECK OUT");

        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel30.setText("GATEPASS");

        jLabel31.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel31.setText("NAMA ADVISOR");

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel32.setText("TYPE MOBIL");

        jLabel33.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel33.setText("KETERANGAN");

        sh_id_showroom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_id_showroom.setEnabled(false);

        sh_no_polisi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        sh_check_in.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_check_in.setText("-");
        sh_check_in.setEnabled(false);

        sh_check_out.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_check_out.setText("-");
        sh_check_out.setEnabled(false);

        sh_gatepass.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_gatepass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH", "SYSTEM", "MANUAL" }));
        sh_gatepass.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        sh_service_advisor.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_service_advisor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sh_service_advisorMouseClicked(evt);
            }
        });

        sh_type_mobil.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        sh_no_antri.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_no_antri.setEnabled(false);

        sh_keterangan.setColumns(20);
        sh_keterangan.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_keterangan.setRows(5);
        jScrollPane5.setViewportView(sh_keterangan);

        jLabel44.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel44.setText("STATUS");

        sh_status.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH", "SERVICE", "TIDAK SERVICE" }));
        sh_status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sh_statusActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel28, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sh_check_in, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sh_id_showroom, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(sh_no_antri, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sh_no_polisi)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(11, 11, 11))
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 18, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sh_gatepass, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(sh_type_mobil, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sh_service_advisor, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sh_check_out, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                            .addComponent(sh_status, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sh_id_showroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sh_no_antri, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sh_no_polisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sh_gatepass)
                    .addComponent(jLabel30))
                .addGap(13, 13, 13)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sh_check_in)
                    .addComponent(jLabel28))
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sh_check_out)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sh_status)
                    .addComponent(jLabel44))
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sh_service_advisor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sh_type_mobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel6);

        sh_btn_edit.setBackground(new java.awt.Color(0, 0, 51));
        sh_btn_edit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_btn_edit.setText("UBAH");
        sh_btn_edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sh_btn_editMouseClicked(evt);
            }
        });

        sh_btn_refresh.setBackground(new java.awt.Color(0, 0, 51));
        sh_btn_refresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_btn_refresh.setText("PERBARUI");
        sh_btn_refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sh_btn_refreshMouseClicked(evt);
            }
        });

        sh_btn_cetak.setBackground(new java.awt.Color(0, 0, 51));
        sh_btn_cetak.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sh_btn_cetak.setText("CETAK LAPORAN");
        sh_btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sh_btn_cetakActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel22.setText("TOTAL SHOWROOM");

        usr_total_showroom.setBackground(new java.awt.Color(0, 204, 204));
        usr_total_showroom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        usr_total_showroom.setForeground(new java.awt.Color(0, 0, 0));
        usr_total_showroom.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        usr_total_showroom.setText("0");
        usr_total_showroom.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        usr_total_showroom.setOpaque(true);

        cariDate.setBackground(new java.awt.Color(51, 153, 0));
        cariDate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cariDate.setForeground(new java.awt.Color(0, 0, 0));
        cariDate.setText("CARI");
        cariDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cariDateMouseClicked(evt);
            }
        });

        jLabel27.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel27.setText("NO POLISI");

        search_polisi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(sh_btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sh_btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(sh_btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sh_btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(sh_btn_logout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(search_polisi, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cariDate, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(usr_total_showroom, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sh_btn_cetak, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sh_btn_add)
                            .addComponent(sh_btn_delete))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sh_btn_edit)
                            .addComponent(sh_btn_refresh)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel27)
                            .addComponent(search_polisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cariDate))
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(usr_total_showroom)
                    .addComponent(sh_btn_cetak)
                    .addComponent(sh_btn_logout))
                .addGap(10, 10, 10))
        );

        Jtabbed_utama.addTab("SHOWROOM", jPanel5);

        sv_tbl_service.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID SHOWROOM", "ID SERVICE", "WARNA", "NO RANGKA", "NO MESIN", "STATUS"
            }
        ));
        sv_tbl_service.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        sv_tbl_service.getTableHeader().setReorderingAllowed(false);
        sv_tbl_service.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sv_tbl_serviceMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(sv_tbl_service);
        if (sv_tbl_service.getColumnModel().getColumnCount() > 0) {
            sv_tbl_service.getColumnModel().getColumn(0).setMinWidth(200);
            sv_tbl_service.getColumnModel().getColumn(0).setPreferredWidth(200);
            sv_tbl_service.getColumnModel().getColumn(1).setPreferredWidth(200);
            sv_tbl_service.getColumnModel().getColumn(2).setPreferredWidth(200);
            sv_tbl_service.getColumnModel().getColumn(3).setPreferredWidth(200);
            sv_tbl_service.getColumnModel().getColumn(4).setPreferredWidth(200);
            sv_tbl_service.getColumnModel().getColumn(5).setPreferredWidth(200);
        }

        sv_btn_logout.setBackground(new java.awt.Color(153, 0, 0));
        sv_btn_logout.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_btn_logout.setText("KELUAR");
        sv_btn_logout.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sv_btn_logoutKeyPressed(evt);
            }
        });

        sv_btn_add.setBackground(new java.awt.Color(0, 0, 51));
        sv_btn_add.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_btn_add.setText("PILIH DATA UNTUK SERVICE");
        sv_btn_add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sv_btn_addMouseClicked(evt);
            }
        });

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel45.setText("ID SERVICE");

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel47.setText("NO POLISI");

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel48.setText("TANGGAL");

        jLabel53.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel53.setText("TYPE MOBIL");

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel54.setText("KETERANGAN");

        sv_id_service.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_id_service.setEnabled(false);

        sv_no_polisi.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_no_polisi.setEnabled(false);

        sv_tanggal.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_tanggal.setText("-");
        sv_tanggal.setEnabled(false);

        sv_type_mobil.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_type_mobil.setEnabled(false);

        sv_keterangan.setColumns(20);
        sv_keterangan.setRows(5);
        sv_keterangan.setEnabled(false);
        jScrollPane11.setViewportView(sv_keterangan);

        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel55.setText("STATUS");

        sv_status.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_status.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH", "PROSES", "SELESAI" }));
        sv_status.setEnabled(false);
        sv_status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sv_statusActionPerformed(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel56.setText("WARNA");

        sv_warna.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel57.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel57.setText("NO RANGKA");

        sv_no_rangka.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        jLabel58.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel58.setText("NO MESIN");

        sv_no_mesin.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_no_mesin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sv_no_mesinActionPerformed(evt);
            }
        });

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel59.setText("ID SHOWROOM");

        sv_id_showroom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_id_showroom.setEnabled(false);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel54, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel55, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                            .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel58, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                            .addComponent(sv_no_mesin, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sv_no_rangka, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sv_warna, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sv_status, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sv_type_mobil)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel59, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sv_tanggal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sv_no_polisi)
                            .addComponent(sv_id_service, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(sv_id_showroom))))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel45)
                    .addComponent(sv_id_service, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel59)
                    .addComponent(sv_id_showroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel48)
                    .addComponent(sv_tanggal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel47)
                    .addComponent(sv_no_polisi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel53)
                    .addComponent(sv_type_mobil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel56)
                    .addComponent(sv_warna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel57)
                    .addComponent(sv_no_rangka, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel58)
                    .addComponent(sv_no_mesin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel54)
                    .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel55)
                    .addComponent(sv_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane10.setViewportView(jPanel10);

        sv_btn_edit.setBackground(new java.awt.Color(0, 0, 51));
        sv_btn_edit.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_btn_edit.setText("UBAH");
        sv_btn_edit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sv_btn_editKeyPressed(evt);
            }
        });

        sv_btn_refresh.setBackground(new java.awt.Color(0, 0, 51));
        sv_btn_refresh.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_btn_refresh.setText("PERBARUI");
        sv_btn_refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sv_btn_refreshMouseClicked(evt);
            }
        });

        sv_btn_cetak.setBackground(new java.awt.Color(0, 0, 51));
        sv_btn_cetak.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_btn_cetak.setText("CETAK LAPORAN");
        sv_btn_cetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sv_btn_cetakActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel35.setText("TOTAL SERVICE");

        sv_total_service.setBackground(new java.awt.Color(0, 204, 204));
        sv_total_service.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_total_service.setForeground(new java.awt.Color(0, 0, 0));
        sv_total_service.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        sv_total_service.setText("0");
        sv_total_service.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        sv_total_service.setOpaque(true);

        cariDate1.setBackground(new java.awt.Color(51, 153, 0));
        cariDate1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        cariDate1.setForeground(new java.awt.Color(0, 0, 0));
        cariDate1.setText("CARI");
        cariDate1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cariDate1MouseClicked(evt);
            }
        });

        jLabel60.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel60.setText("ID SHOWROOM");

        search_showroom.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        sv_btn_nginap.setBackground(new java.awt.Color(0, 0, 51));
        sv_btn_nginap.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        sv_btn_nginap.setText("CETAK MENGINAP");
        sv_btn_nginap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sv_btn_nginapActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(sv_btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sv_btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 409, Short.MAX_VALUE)
                    .addComponent(sv_btn_add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sv_btn_logout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sv_total_service, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sv_btn_nginap)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sv_btn_cetak))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel60, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(search_showroom, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cariDate1, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sv_btn_add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sv_btn_edit)
                            .addComponent(sv_btn_refresh)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cariDate1)
                            .addComponent(search_showroom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel60))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel35)
                    .addComponent(sv_btn_logout)
                    .addComponent(sv_total_service)
                    .addComponent(sv_btn_cetak)
                    .addComponent(sv_btn_nginap))
                .addGap(16, 16, 16))
        );

        Jtabbed_utama.addTab("SERVICE", jPanel9);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Jtabbed_utama)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Jtabbed_utama))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void sh_statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sh_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sh_statusActionPerformed

    private void toUpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_toUpMouseClicked
        // TODO add your handling code here:
        try {
            Integer value = 0;
            String no = "";
            String SQLQuery = "SELECT no_antrian FROM `tb_antrian` WHERE `tanggal` = CURRENT_DATE";
            java.sql.Connection conn = Connect.configDB();
            java.sql.PreparedStatement ps = conn.prepareStatement(SQLQuery);
            java.sql.ResultSet rs = ps.executeQuery();
            while (rs.next()) {     
                value++;
                no = String.valueOf(rs.getInt(1));
            }
            if(value > 0){
                String SQLQueryUpdate = "UPDATE `tb_antrian` SET `no_antrian` = ? WHERE `tb_antrian`.`tanggal` = CURRENT_DATE";
                java.sql.Connection connUpdate = Connect.configDB();
                java.sql.PreparedStatement psUpdate = connUpdate.prepareStatement(SQLQueryUpdate);
                psUpdate.setInt(1, (Integer.parseInt(countAntrian.getText())+1));
                psUpdate.executeUpdate();
                countAntrian.setText(String.valueOf(Integer.parseInt(countAntrian.getText())+1));
            }else {
                String SQLQueryUpdate = "INSERT INTO `tb_antrian` (`no_antrian`, `tanggal`) VALUES ('1', CURRENT_DATE)";
                java.sql.Connection connUpdate = Connect.configDB();
                java.sql.PreparedStatement psUpdate = connUpdate.prepareStatement(SQLQueryUpdate);
                psUpdate.execute();
                countAntrian.setText(String.valueOf(Integer.parseInt(countAntrian.getText())+1));
            }
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(rootPane, "NO ANTRIAN FAILED");
        }
//        Integer nilai_awal = Integer.parseInt(countAntrian.getText());
//        Integer hasil = nilai_awal + 1;
//        countAntrian.setText(String.valueOf(hasil));
//        sh_no_antri.setValue(Integer.valueOf(countAntrian.getText()));
    }//GEN-LAST:event_toUpMouseClicked

    private void txt_antrianMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_antrianMouseClicked
        // TODO add your handling code here:
        countAntrian.setText("0");
    }//GEN-LAST:event_txt_antrianMouseClicked

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        // TODO add your handling code here:
        getToLogin();
    }//GEN-LAST:event_btn_loginActionPerformed

    private void usr_btn_logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usr_btn_logoutMouseClicked
        // TODO add your handling code here:
        DefaultTab();
        Jtabbed_utama.setSelectedIndex(0);
        text_username.setText("");
        text_password.setText("");
        getAllTable();
    }//GEN-LAST:event_usr_btn_logoutMouseClicked

    private void usr_tbl_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usr_tbl_userMouseClicked
        // TODO add your handling code here:
        int selected_rows = usr_tbl_user.getSelectedRow();
        usr_id_user.setText(String.valueOf(usr_tbl_user.getValueAt(selected_rows, 0)));
        usr_username.setText(String.valueOf(usr_tbl_user.getValueAt(selected_rows, 1)));
        usr_password.setText(String.valueOf(usr_tbl_user.getValueAt(selected_rows, 2)));
        usr_type.setSelectedItem(String.valueOf(usr_tbl_user.getValueAt(selected_rows, 3)));
    }//GEN-LAST:event_usr_tbl_userMouseClicked

    private void usr_usernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_usr_usernameKeyReleased
        // TODO add your handling code here:
        usr_password.setText(usr_username.getText());
    }//GEN-LAST:event_usr_usernameKeyReleased

    private void usr_btn_refreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usr_btn_refreshMouseClicked
        // TODO add your handling code here:
        usr_id_user.setText("");
        usr_password.setText("");
        usr_username.setText("");
        usr_type.setSelectedIndex(0);
        getAllTable();
    }//GEN-LAST:event_usr_btn_refreshMouseClicked

    private void usr_btn_addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usr_btn_addMouseClicked
        // TODO add your handling code here:
        if(usr_username.getText().equals("")){
            usr_username.requestFocus();
        }else if(String.valueOf(usr_password.getPassword()).equals("")){
            usr_password.requestFocus();
        }else if(usr_type.getSelectedItem().equals("PILIH")){
            usr_type.requestFocus();
        }else{
            Date da = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("YYYYMMddhhmmss");
            String idUser = "USR-"+ft.format(da);

            try {
                String sql = "INSERT INTO tb_user VALUES ('"+idUser+"','"+usr_username.getText()+"','"+ String.valueOf(usr_password.getPassword())+ "', '" +usr_type.getSelectedItem()+"')";
                java.sql.Connection conn = Connect.configDB();
                java.sql.PreparedStatement pst=conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Penyimpanan Data Berhasil");
                getAllTable();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_usr_btn_addMouseClicked

    private void usr_btn_deleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usr_btn_deleteMouseClicked
        // TODO add your handling code here:
        int selected_rows = usr_tbl_user.getSelectedRow();
        if(selected_rows == -1){
            JOptionPane.showMessageDialog(null, "PILIH SALAH SATU DATA");
        }else {
            String id_user = String.valueOf(usr_tbl_user.getValueAt(selected_rows, 0));
            try {
                String sql = "DELETE FROM tb_user WHERE id_user='"+id_user+"'";
                java.sql.Connection conn = Connect.configDB();
                java.sql.PreparedStatement pst=conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Penghapusan Data Berhasil");
                getAllTable();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_usr_btn_deleteMouseClicked

    private void usr_btn_editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usr_btn_editMouseClicked
        // TODO add your handling code here:
        int selected_rows = usr_tbl_user.getSelectedRow();
        if(selected_rows == -1 ){
            JOptionPane.showMessageDialog(rootPane, "HARAP PILIH DATA TERLEBIH DAHULU");
        }else {
            try {
                String id_user = String.valueOf(usr_tbl_user.getValueAt(selected_rows, 0));
                String sql = "UPDATE `tb_user` SET `username` = ?, `password` = ?, `type` = ?, id_user = ? WHERE id_user = ?";
                java.sql.Connection conn = Connect.configDB();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, usr_username.getText());
                pst.setString(2, String.valueOf(usr_password.getPassword()));
                pst.setString(3, String.valueOf(usr_type.getSelectedItem()));
                pst.setString(4, usr_id_user.getText());
                pst.setString(5, id_user);
                pst.executeUpdate();
                DefaultTabel(usr_tbl_user, 4);
                JOptionPane.showMessageDialog(null, "Pengubahan Data Berhasil");
                getAllTable();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_usr_btn_editMouseClicked

    private void sh_service_advisorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sh_service_advisorMouseClicked
        // TODO add your handling code here:
        getDataAdvisor();
    }//GEN-LAST:event_sh_service_advisorMouseClicked

    private void usr_btn_add2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usr_btn_add2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_usr_btn_add2MouseClicked

    private void usr_btn_add2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usr_btn_add2ActionPerformed
        // TODO add your handling code here:
        String viewPass = String.valueOf(usr_password.getPassword());
        JOptionPane.showMessageDialog(rootPane, "Password Anda : " + viewPass);
    }//GEN-LAST:event_usr_btn_add2ActionPerformed

    private void sh_tbl_showroomMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sh_tbl_showroomMouseClicked
        // TODO add your handling code here:
        int selected_rows = sh_tbl_showroom.getSelectedRow();
        if(sh_tbl_showroom.getValueAt(selected_rows, 4) == null){
            sh_check_out.setText("DALAM PROSES SERVICE");
        }else{
            sh_check_out.setText(String.valueOf(sh_tbl_showroom.getValueAt(selected_rows, 4)));
        }
        sh_id_showroom.setText(String.valueOf(sh_tbl_showroom.getValueAt(selected_rows, 0)));
        sh_no_antri.setValue(Integer.valueOf(String.valueOf(sh_tbl_showroom.getValueAt(selected_rows, 1))));
        sh_no_polisi.setText(String.valueOf(sh_tbl_showroom.getValueAt(selected_rows, 2)));
        sh_check_in.setText(String.valueOf(sh_tbl_showroom.getValueAt(selected_rows, 3)));
        sh_gatepass.setSelectedItem(String.valueOf(sh_tbl_showroom.getValueAt(selected_rows, 5)).toUpperCase());
        sh_service_advisor.setSelectedItem(String.valueOf(sh_tbl_showroom.getValueAt(selected_rows, 6)));
        sh_type_mobil.setText(String.valueOf(sh_tbl_showroom.getValueAt(selected_rows, 7)));
        sh_keterangan.setText(String.valueOf(sh_tbl_showroom.getValueAt(selected_rows, 8)));
        sh_status.setSelectedItem(String.valueOf(sh_tbl_showroom.getValueAt(selected_rows, 9)));
    }//GEN-LAST:event_sh_tbl_showroomMouseClicked

    private void sh_btn_refreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sh_btn_refreshMouseClicked
        // TODO add your handling code here:
        sh_id_showroom.setText("");
        sh_no_antri.setValue(0);
        sh_no_polisi.setText("");
        sh_check_in.setText("-");
        sh_check_out.setText("-");
        sh_gatepass.setSelectedIndex(0);
        sh_service_advisor.setSelectedIndex(0);
        sh_type_mobil.setText("");
        sh_keterangan.setText("");
        sh_status.setSelectedIndex(0);
        getAllTable();
    }//GEN-LAST:event_sh_btn_refreshMouseClicked

    private void cariDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariDateMouseClicked
        // TODO add your handling code here:
        if(search_polisi.getText().equals("")){
            JOptionPane.showMessageDialog(null, "HARAP MASUKAN NO POLISI UNTUK PENCARIAN");
        }else{
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID SHOWROOM");
            model.addColumn("NO ANTRI");
            model.addColumn("NO POLISI");
            model.addColumn("CHECK IN");
            model.addColumn("CHECK OUT");
            model.addColumn("GATEPASS");
            model.addColumn("SERVICE ADVISOR");
            model.addColumn("TYPE MOBIL");
            model.addColumn("KETERANGAN");
            model.addColumn("STATUS SERVICE");

            //menampilkan data database kedalam tabel
            try {
                String SQLQuery = "SELECT * FROM `tb_showroom` WHERE `no_pol`=?";
                java.sql.Connection conn = Connect.configDB();
                java.sql.PreparedStatement pst = conn.prepareStatement(SQLQuery);
                pst.setString(1, search_polisi.getText());
                java.sql.ResultSet res=pst.executeQuery();
                while(res.next()){
                    model.addRow(new Object[]{ 
                        res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6),
                        res.getString(7),
                        res.getString(8),
                        res.getString(9),
                        res.getString(10),
                    });
                }
                sh_tbl_showroom.setModel(model);
                DefaultTabel(sh_tbl_showroom, 10);
                usr_total_showroom.setText(String.valueOf(sh_tbl_showroom.getRowCount()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(rootPane, "TABEL SHOWROOM ERROR : "+e);
            }
        }
    }//GEN-LAST:event_cariDateMouseClicked

    private void sh_btn_addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sh_btn_addMouseClicked
        // TODO add your handling code here:
        if(sh_no_polisi.getText().equals("")){
            sh_no_polisi.requestFocus();
        }else if(String.valueOf(sh_gatepass.getSelectedItem()).equals("PILIH")){
            sh_gatepass.requestFocus();
        }else if(String.valueOf(sh_status.getSelectedItem()).equals("PILIH")){
            sh_status.requestFocus();
        }else if(String.valueOf(sh_service_advisor.getSelectedItem()).equals("PILIH")){
            sh_service_advisor.requestFocus();
        }else if(sh_type_mobil.getText().equals("")){
            sh_type_mobil.requestFocus();
        }else if(sh_keterangan.getText().equals("")){
            sh_keterangan.requestFocus();
        }else{
            setNoAntrian();
            Date da = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("YYYYMMddhhmmss");
            String idSho = "SHO-"+ft.format(da);
            try {
                String SQLQuery = "default";
                if(sh_status.getSelectedItem().equals("SERVICE")){
                    SQLQuery = "INSERT INTO `tb_showroom` "
                            + "(`id_showroom`, `no_antri`, `no_pol`, `gatepass`, `service_advisor`, `type_mobil`, `keterangan`, `status_service`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                }else{
                    SQLQuery = "INSERT INTO `tb_showroom` "
                            + "(`id_showroom`, `no_antri`, `no_pol`, `gatepass`, `service_advisor`, `type_mobil`, `keterangan`, `status_service`, `check_out`) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, current_timestamp)";
                }
                java.sql.Connection conn = Connect.configDB();
                java.sql.PreparedStatement ps = conn.prepareStatement(SQLQuery);
                ps.setString(1, idSho);
                ps.setString(2, countAntrian.getText());
                ps.setString(3, sh_no_polisi.getText());
                ps.setString(4, String.valueOf(sh_gatepass.getSelectedItem()).toLowerCase());
                ps.setString(5, String.valueOf(sh_service_advisor.getSelectedItem()));
                ps.setString(6, sh_type_mobil.getText());
                ps.setString(7, sh_keterangan.getText());
                ps.setString(8, String.valueOf(sh_status.getSelectedItem()));
                ps.execute();
                getAllTable();
                JOptionPane.showMessageDialog(rootPane, "DATA SHOWROOM BERHASIL DITAMBAHKAN");
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(rootPane, "DATA SHOWROOM GAGAL DITAMBAHKAN" + e);
            }
        }
    }//GEN-LAST:event_sh_btn_addMouseClicked

    private void text_usernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_usernameKeyPressed
        // TODO add your handling code here:
        boolean validation = (evt.getKeyCode() == KeyEvent.VK_ENTER);
        if (validation) {
            text_password.requestFocus();
        }
    }//GEN-LAST:event_text_usernameKeyPressed

    private void text_passwordKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_text_passwordKeyPressed
        // TODO add your handling code here:
        boolean validation = (evt.getKeyCode() == KeyEvent.VK_ENTER);
        if (validation) {
            btn_login.requestFocus();
        }
    }//GEN-LAST:event_text_passwordKeyPressed

    private void usr_btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usr_btn_editActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usr_btn_editActionPerformed

    private void usr_btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usr_btn_refreshActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usr_btn_refreshActionPerformed

    private void sh_btn_deleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sh_btn_deleteMouseClicked
        // TODO add your handling code here:
        int selected_rows = sh_tbl_showroom.getSelectedRow();
        if((sh_btn_delete.isEnabled() == false) && (selected_rows == -1)){
            JOptionPane.showMessageDialog(null, "PILIH SALAH SATU DATA");
        }else {
            String id_showroom = String.valueOf(sh_tbl_showroom.getValueAt(selected_rows, 0));
            try {
                String sql = "DELETE FROM tb_showroom WHERE id_showroom='"+id_showroom+"'";
                java.sql.Connection conn = Connect.configDB();
                java.sql.PreparedStatement pst=conn.prepareStatement(sql);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Penghapusan Data Berhasil");
                getAllTable();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_sh_btn_deleteMouseClicked

    private void sh_btn_editMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sh_btn_editMouseClicked
        // TODO add your handling code here:
        int selected_rows = sh_tbl_showroom.getSelectedRow();
        if(selected_rows == -1 ){
            JOptionPane.showMessageDialog(rootPane, "HARAP PILIH DATA TERLEBIH DAHULU");
        }else if(sh_no_polisi.getText().equals("")){
            sh_no_polisi.requestFocus();
        }else if(String.valueOf(sh_gatepass.getSelectedItem()).equals("PILIH")){
            sh_gatepass.requestFocus();
        }else if(String.valueOf(sh_status.getSelectedItem()).equals("PILIH")){
            sh_status.requestFocus();
        }else if(String.valueOf(sh_service_advisor.getSelectedItem()).equals("PILIH")){
            sh_service_advisor.requestFocus();
        }else if(sh_type_mobil.getText().equals("")){
            sh_type_mobil.requestFocus();
        }else if(sh_keterangan.getText().equals("")){
            sh_keterangan.requestFocus();
        }else {
            try {
                String id_user = String.valueOf(sh_tbl_showroom.getValueAt(selected_rows, 0));
                String sql = "UPDATE `tb_showroom` SET `no_pol` = ?, `gatepass` = ?, `status_service` = ?, `service_advisor` = ?, `type_mobil` = ?, `keterangan` = ? WHERE `id_showroom` = ?";
                java.sql.Connection conn = Connect.configDB();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, sh_no_polisi.getText());
                pst.setString(2, String.valueOf(sh_gatepass.getSelectedItem()));
                pst.setString(3, String.valueOf(sh_status.getSelectedItem()));
                pst.setString(4, String.valueOf(sh_service_advisor.getSelectedItem()));
                pst.setString(5, sh_type_mobil.getText());
                pst.setString(6, sh_keterangan.getText());
                pst.setString(7, id_user);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Pengubahan Data Berhasil");
                getAllTable();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, "UPDATE KUY" + e.getMessage());
            }
        }
    }//GEN-LAST:event_sh_btn_editMouseClicked

    private void sh_btn_logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sh_btn_logoutMouseClicked
        // TODO add your handling code here:
        DefaultTab();
        Jtabbed_utama.setSelectedIndex(0);
        text_username.setText("");
        text_password.setText("");
        getAllTable();
    }//GEN-LAST:event_sh_btn_logoutMouseClicked

    private void sv_btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sv_btn_cetakActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            new Cetak().generatePDF(sv_tbl_service, "DATA SERVICES");
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sv_btn_cetakActionPerformed

    private void sv_no_mesinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sv_no_mesinActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sv_no_mesinActionPerformed

    private void sv_statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sv_statusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sv_statusActionPerformed

    private void cariDate1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cariDate1MouseClicked
        // TODO add your handling code here:
        if(search_showroom.getText().equals("")){
            JOptionPane.showMessageDialog(null, "MASUKAN ID SHOWROOM UNTUK PENCARIAN");
        }else{
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID SHOWROOM");
            model.addColumn("ID SERVICE");
            model.addColumn("WARNA MOBIL");
            model.addColumn("NOMOR RANGKA");
            model.addColumn("NOMOR MESIN");
            model.addColumn("STATUS SERVICE");

            //menampilkan data database kedalam tabel
            try {
                String sql = "SELECT id_showroom, id_service, warna, no_rangka, no_mesin, status_service FROM `tb_service` WHERE id_showroom=?";
                java.sql.Connection conn = Connect.configDB();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, search_showroom.getText());
                java.sql.ResultSet res=pst.executeQuery();
                while(res.next()){
                    model.addRow(new Object[]{ 
                        res.getString(1),
                        res.getString(2),
                        res.getString(3),
                        res.getString(4),
                        res.getString(5),
                        res.getString(6),
                    });
                }
                sv_tbl_service.setModel(model);
                DefaultTabel(sv_tbl_service, 6);
                sv_total_service.setText(String.valueOf(sv_tbl_service.getRowCount()));
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(rootPane, "TABEL SERVICE ERROR : "+e);
            }
        }
    }//GEN-LAST:event_cariDate1MouseClicked

    private void sv_tbl_serviceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sv_tbl_serviceMouseClicked
        // TODO add your handling code here:
        int selected_rows = sv_tbl_service.getSelectedRow();
        sv_id_showroom.setText(String.valueOf(sv_tbl_service.getValueAt(selected_rows, 0)));
        sv_id_service.setText(String.valueOf(sv_tbl_service.getValueAt(selected_rows, 1)));
        sv_warna.setText(String.valueOf(sv_tbl_service.getValueAt(selected_rows, 2)));
        sv_no_rangka.setText(String.valueOf(sv_tbl_service.getValueAt(selected_rows, 3)));
        sv_no_mesin.setText(String.valueOf(sv_tbl_service.getValueAt(selected_rows, 4)));
        sv_status.setSelectedItem(String.valueOf(sv_tbl_service.getValueAt(selected_rows, 5)));
        getDataShowRoom();
    }//GEN-LAST:event_sv_tbl_serviceMouseClicked

    private void sv_btn_addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sv_btn_addMouseClicked
        // TODO add your handling code here:
        if(sv_warna.getText().equals("")){
            sv_warna.requestFocus();
        }else if(sv_no_rangka.getText().equals("")){
            sv_no_rangka.requestFocus();
        }else if(sv_no_mesin.getText().equals("")){
            sv_no_mesin.requestFocus();
        }else if(sv_btn_add.isEnabled()==false){
            JOptionPane.showMessageDialog(null, "TIDAK DAPAT MENAMBAHKAN DATA KARENA TELA SELESAI SERVICE");
        }else if(sv_btn_add.getText().equals("CHECKOUT")){
            Date da = new Date();
            SimpleDateFormat ft = new SimpleDateFormat("YYYYMMddhhmmss");
            String idSRV = "SRV-"+ft.format(da);
            try {
                String SQLQuery = "UPDATE `tb_service` SET `id_service` = ?, `warna` = ?, `no_rangka` = ?, `no_mesin` = ?, `status_service` = 'SELESAI' WHERE `id_showroom` = ?";
                java.sql.Connection conn = Connect.configDB();
                java.sql.PreparedStatement ps = conn.prepareStatement(SQLQuery);
                ps.setString(1, idSRV);
                ps.setString(2, sv_warna.getText());
                ps.setString(3, sv_no_rangka.getText());
                ps.setString(4, sv_no_mesin.getText());
                ps.setString(5, sv_id_showroom.getText());
                ps.execute();
                sv_status.setSelectedIndex(2);
                JOptionPane.showMessageDialog(null, "TERIMAKASIH SUDAH SERVICE HARI INI");
                getAllTable();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "ERROR : " + e);
            }
        }
    }//GEN-LAST:event_sv_btn_addMouseClicked

    private void sv_btn_editKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sv_btn_editKeyPressed
        // TODO add your handling code here:
        int selected_rows = sv_tbl_service.getSelectedRow();
        if(selected_rows == -1 ){
            JOptionPane.showMessageDialog(rootPane, "HARAP PILIH DATA TERLEBIH DAHULU");
        }else if(sv_warna.getText().equals("")){
            sv_warna.requestFocus();
        }else if(sv_no_rangka.getText().equals("")){
            sv_no_rangka.requestFocus();
        }else if(sv_no_mesin.getText().equals("")){
            sv_no_mesin.requestFocus();
        }else if(String.valueOf(sv_status.getSelectedItem()).equals("PILIH")){
            sv_status.requestFocus();
        }else if(sv_btn_add.isEnabled()==false){
            JOptionPane.showMessageDialog(null, "TIDAK DAPAT MENGUBAH DATA KARENA TELAH SELESAI SERVICE");
        }else{
            try {
                // UPDATE DATA SERVICE
                String id_service = String.valueOf(sv_tbl_service.getValueAt(selected_rows, 0));
                String sql = "UPDATE `tb_service` SET `warna` = ?, `no_rangka` = ?, `no_mesin` = ?, `status_service` = ? WHERE `id_service` = ?";
                java.sql.Connection conn = Connect.configDB();
                java.sql.PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, sv_warna.getText());
                pst.setString(2, sv_no_rangka.getText());
                pst.setString(3, sv_no_mesin.getText());
                pst.setString(4, String.valueOf(sv_status.getSelectedItem()));
                pst.setString(5, id_service);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Pengubahan Data Berhasil");
                getAllTable();
            } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(this, "ERROR CODE : " + e.getMessage());
            }
        }
    }//GEN-LAST:event_sv_btn_editKeyPressed

    private void usr_btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usr_btn_logoutActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usr_btn_logoutActionPerformed

    private void sv_btn_logoutKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sv_btn_logoutKeyPressed
        // TODO add your handling code here:
        DefaultTab();
        Jtabbed_utama.setSelectedIndex(0);
        text_username.setText("");
        text_password.setText("");
        getAllTable();
    }//GEN-LAST:event_sv_btn_logoutKeyPressed

    private void sv_btn_refreshMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sv_btn_refreshMouseClicked
        // TODO add your handling code here:
        sv_id_service.setText("");
        sv_id_showroom.setText("");
        sv_tanggal.setText("-");
        sv_no_polisi.setText("");
        sv_type_mobil.setText("");
        sv_warna.setText("");
        sv_no_rangka.setText("");
        sv_no_mesin.setText("");
        sv_keterangan.setText("");
        sv_status.setSelectedIndex(0);
        getAllTable();
    }//GEN-LAST:event_sv_btn_refreshMouseClicked

    private void usr_laporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_usr_laporanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_usr_laporanMouseClicked

    private void usr_laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usr_laporanActionPerformed
        try {
            // TODO add your handling code here:
            new Cetak().generatePDF(usr_tbl_user, "DATA USERS");
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_usr_laporanActionPerformed

    private void sh_btn_cetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sh_btn_cetakActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            new Cetak().generatePDF(sh_tbl_showroom, "DATA SHOWROOMS");
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_sh_btn_cetakActionPerformed

    private void sv_btn_nginapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sv_btn_nginapActionPerformed
        // TODO add your handling code here:
        getTableNginap();
    }//GEN-LAST:event_sv_btn_nginapActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf() );
        } catch( UnsupportedLookAndFeelException ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Home().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Jtabbed_utama;
    private javax.swing.JButton btn_login;
    private javax.swing.JButton cariDate;
    private javax.swing.JButton cariDate1;
    private javax.swing.JLabel countAntrian;
    private javax.swing.JLabel itsTime;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTextField search_polisi;
    private javax.swing.JTextField search_showroom;
    private javax.swing.JButton sh_btn_add;
    private javax.swing.JButton sh_btn_cetak;
    private javax.swing.JButton sh_btn_delete;
    private javax.swing.JButton sh_btn_edit;
    private javax.swing.JButton sh_btn_logout;
    private javax.swing.JButton sh_btn_refresh;
    private javax.swing.JButton sh_check_in;
    private javax.swing.JButton sh_check_out;
    private javax.swing.JComboBox<String> sh_gatepass;
    private javax.swing.JTextField sh_id_showroom;
    private javax.swing.JTextArea sh_keterangan;
    private javax.swing.JSpinner sh_no_antri;
    private javax.swing.JTextField sh_no_polisi;
    private javax.swing.JComboBox<String> sh_service_advisor;
    private javax.swing.JComboBox<String> sh_status;
    private javax.swing.JTable sh_tbl_showroom;
    private javax.swing.JTextField sh_type_mobil;
    private javax.swing.JButton sv_btn_add;
    private javax.swing.JButton sv_btn_cetak;
    private javax.swing.JButton sv_btn_edit;
    private javax.swing.JButton sv_btn_logout;
    private javax.swing.JButton sv_btn_nginap;
    private javax.swing.JButton sv_btn_refresh;
    private javax.swing.JTextField sv_id_service;
    private javax.swing.JTextField sv_id_showroom;
    private javax.swing.JTextArea sv_keterangan;
    private javax.swing.JTextField sv_no_mesin;
    private javax.swing.JTextField sv_no_polisi;
    private javax.swing.JTextField sv_no_rangka;
    private javax.swing.JComboBox<String> sv_status;
    private javax.swing.JButton sv_tanggal;
    private javax.swing.JTable sv_tbl_service;
    private javax.swing.JLabel sv_total_service;
    private javax.swing.JTextField sv_type_mobil;
    private javax.swing.JTextField sv_warna;
    private javax.swing.JTable tbl_days;
    private javax.swing.JPasswordField text_password;
    private javax.swing.JTextField text_username;
    private javax.swing.JLabel toUp;
    private javax.swing.JLabel total_transaksi_days;
    private javax.swing.JLabel txt_antrian;
    private javax.swing.JButton usr_btn_add;
    private javax.swing.JButton usr_btn_add2;
    private javax.swing.JButton usr_btn_delete;
    private javax.swing.JButton usr_btn_edit;
    private javax.swing.JButton usr_btn_logout;
    private javax.swing.JButton usr_btn_refresh;
    private javax.swing.JTextField usr_id_user;
    private javax.swing.JButton usr_laporan;
    private javax.swing.JPasswordField usr_password;
    private javax.swing.JTable usr_tbl_user;
    private javax.swing.JLabel usr_total_showroom;
    private javax.swing.JLabel usr_total_user;
    private javax.swing.JComboBox<String> usr_type;
    private javax.swing.JTextField usr_username;
    // End of variables declaration//GEN-END:variables
}
