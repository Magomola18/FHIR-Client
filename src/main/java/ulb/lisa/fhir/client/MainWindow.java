/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.fhir.client;


import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.json.JSONArray;
import org.json.JSONException;
import static ulb.lisa.fhir.client.Main.readJsonFromUrl;


/**
 *
 * @author karabomagomola
 */
public final class MainWindow extends javax.swing.JFrame {
    public static ArrayList<PatientList> list = new ArrayList<>();

    public static ArrayList<PatientDetails> detailList = new ArrayList<>();
   
    public static DefaultTableModel dtm = new DefaultTableModel();
    
    public static DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    

   Object rowData[] = new Object[10];
    
    //private final FHIRClient fhirClient;
   
    
    /**
     *
     * @param url2
     * @return
     * @throws IOException
     */
    
   public final class MyTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Color getBackground() {
        return super.getBackground();
    }

   }
   
   public void changeTable(int column_index) {
        jTable1.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(jTable1, value, isSelected, hasFocus, row, column);
                Double st_val = Double.valueOf(jTable1.getValueAt(column, 2).toString());
                Double req_val = 170.0;
                if (st_val < req_val) {
                    c.setBackground(Color.MAGENTA);
                } else {
                    c.setBackground(Color.GREEN);
                }
                return c;
            }
        });
    }

    
    
    public static String readJsonfromUrl(String url2) throws IOException, JSONException{
    String serverURL = "https://fhir.monash.edu/hapi-fhir-jpaserver/";
    URL url = new URL(serverURL +url2);
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
    String line = null;
    StringBuilder response = new StringBuilder();
    while((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }
     return response.toString();
    }
    /**
     * Creates new form MainWindow
     * @throws java.io.IOException
     */
    public MainWindow() {
        //fhirClient = new FHIRClient();
        initComponents();
        addRowToJTable();
        changeTable(2);
        
        
    }
    
    
    
    
   public static ArrayList patientList(String patientName, Double patientValueQuantity, String patientUnit, String patientTime, String chol, String patientId, int diastolicBP, int systolicBP, String time) throws IOException{
        list = new ArrayList<>();
       
        
        
         /* Create and display the form */
        
        
        Set <String> patientIds = new HashSet<>();
        
        Set <String> cholesterols = new HashSet<>();
        
        Set <String> bloodPressure = new HashSet<>();
        //Create a input dialog(JOptionPane)
        String practionerId = JOptionPane.showInputDialog("Enter practioner ID: ");
        System.out.println(practionerId);
        
        //gets prac Identifier
        String identifier = pracIdentifier(practionerId);
        //Convert data into JSON
        String jsonName = readJsonFromUrl("/Encounter/?participant.identifier=" + identifier + "&_include=Encounter.participant.individual&_include=Encounter.patient&_format=json");
        
        org.json.JSONObject jsonObject = new org.json.JSONObject(jsonName);
        
        //Iterate through practitioners patients to get name
         int i = 0;
         
       
         
         
        for(i=0; i< jsonObject.getJSONArray("entry").length(); i++) {
        
            
            String patientRef = jsonObject.getJSONArray("entry").getJSONObject(i).getJSONObject("resource").getJSONObject("subject").getString("reference");
            patientId = patientRef.split("/")[1];

            System.out.println(patientId);
            patientName = jsonObject.getJSONArray("entry").getJSONObject(i).getJSONObject("resource").getJSONObject("subject").getString("display");
            if (patientIds.add(patientId)) {
                System.out.println(patientName.replaceAll("[0-9]",""));
                
                
                String jsonCholesterol = readJsonFromUrl("/Observation?code=2093-3&patient=" + patientId + "&_format=json");
                org.json.JSONObject jsonObject1 = new org.json.JSONObject(jsonCholesterol);
                
                
                for( i=0; i< jsonObject1.getJSONArray("entry").length();i++){
         
                
                patientValueQuantity = jsonObject1.getJSONArray("entry").getJSONObject(i).getJSONObject("resource").getJSONObject("valueQuantity").getDouble("value");
                //patientUnit = jsonObject1.getJSONArray("entry").getJSONObject(i).getJSONObject("resource").getJSONObject("valueQuantity").getString("unit");

                chol = patientValueQuantity + patientUnit;
                patientTime = jsonObject1.getJSONArray("entry").getJSONObject(i).getJSONObject("resource").getString("effectiveDateTime");
                

                if (cholesterols.add(patientId)) {
                    System.out.println(patientValueQuantity + " " +patientUnit);
                    System.out.println(patientTime.replaceAll("T"," "));
                }
                 
                String bpObservation = readJsonFromUrl("/Observation?code=55284-4&patient=" + patientId + "&_format=json");
                org.json.JSONObject jsonObject2 = new org.json.JSONObject(bpObservation);
        
        
               
        
                    diastolicBP = jsonObject2.getJSONArray("entry").getJSONObject(
                    0).getJSONObject("resource").getJSONArray("component").getJSONObject(0).getJSONObject
                    ("valueQuantity").getInt("value");
                    
                    systolicBP = jsonObject2.getJSONArray("entry").getJSONObject(
                    0).getJSONObject("resource").getJSONArray("component").getJSONObject(1).getJSONObject
                    ("valueQuantity").getInt("value");
                    
                    time = jsonObject2.getJSONArray("entry").getJSONObject(
                    0).getJSONObject("resource").getString("effectiveDateTime");
                    
                    if (bloodPressure.add(patientId)) {
                    System.out.println(diastolicBP+""+systolicBP);
                    System.out.println(time + "");
                    
            
            
        }
                
                PatientList patients = new PatientList(patientName.replaceAll("[0-9]",""), patientValueQuantity, patientUnit, patientTime.replaceAll("T"," "), chol, patientId, diastolicBP,systolicBP, time);
                list.add(patients);
                }
                
        
                
            }
           
        } 
        
      
       
       return list;
    }
   
    private static final long serialVersionUID = 1L;

        public void CholesterolChart() {
            // Create Dataset
            CategoryDataset dataset = createDataset();

            JFreeChart chart = ChartFactory.createBarChart(
                    "Cholesterol Graph", //Chart Title
                    "Patient Names", // Category axis
                    "Cholesterol Value", // Value axis
                    dataset,
                    PlotOrientation.VERTICAL,
                    true, true, false
            );

            ChartPanel panel = new ChartPanel(chart);
            setContentPane(panel);
            
            setSize(800, 400);
            setLocationRelativeTo(null);
            setVisible(true);
            setTitle("Cholesterol Graph");

        }

        private CategoryDataset createDataset() {
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            dtm = (DefaultTableModel) jTable1.getModel();
            int i;
            for (i = 0; i < dtm.getRowCount(); i++) {

                String name = dtm.getValueAt(i, 1).toString();
                Double cholesterol = Double.valueOf(dtm.getValueAt(i, 2).toString());
                dataset.addValue(cholesterol, "Cholesterol in mg/dL", name);
            }
            return dataset;
        }
   
   
   
    public static ArrayList patientDetails(String birthDate, String gender, String address, String city, String state, String country, String line, String family, String given, String name, String prefix, long postalCode, String patientId) throws IOException{
   
   detailList = new ArrayList<>();
   String details = readJsonFromUrl("/Patient/"+patientId+"/?_format=json");
   org.json.JSONObject jsonObject2 = new org.json.JSONObject(details);
           
   Set <String> patientDetails = new HashSet<>();

   
   
   try{
   
   birthDate = jsonObject2.getString("birthDate");
   gender = jsonObject2.getString("gender");
   city = jsonObject2.getJSONArray("address").getJSONObject(0).getString("city");
   state = jsonObject2.getJSONArray("address").getJSONObject(0).getString("state");
   country = jsonObject2.getJSONArray("address").getJSONObject(0).getString("country");
   line = jsonObject2.getJSONArray("address").getJSONObject(0).getJSONArray("line").getString(0);
   postalCode = jsonObject2.getJSONArray("entry").getJSONObject(0).getJSONObject("resource").getLong("postalCode");
   address = line + " " + city +" "+ state +" "+postalCode +" "+country;
   
   family = jsonObject2.getJSONArray("name").getJSONObject(0).getString("family");
   given = jsonObject2.getJSONArray("name").getJSONObject(0).getJSONArray("given").getString(0);
   prefix = jsonObject2.getJSONArray("entry").getJSONObject(0).getJSONObject("resource").getJSONObject("name").getString("prefix");
   name = prefix + " " + given + " " + family;
   
   
   
   
   PatientDetails onClickDisplay = new PatientDetails(birthDate, gender, address, city, state, country, line , family, given, name, prefix, postalCode);
   onClickDisplay.setGender(gender);
   onClickDisplay.setAddress(address);
   onClickDisplay.setCity(city);
   onClickDisplay.setState(state);
   onClickDisplay.setCountry(country);
   
   
   
   }catch(Exception e){ e.printStackTrace();}
   
   
   return detailList;
   
   }
   
   
   
   public void addRowToJTable(){
   
       dtm = (DefaultTableModel) jTable1.getModel();
      
       
       
       
       for(int i =0 ; i < list.size(); i++){
           rowData[0] = list.get(i).getPatientId();
           rowData[1] = list.get(i).getPatientName();
           rowData[2] = list.get(i).getChol();
           rowData[3] = list.get(i).getPatientTime();
           rowData[4] = list.get(i).getSystolicBP();
           rowData[5] = list.get(i).getDiastolicBP();
           rowData[6] = list.get(i).getTime();
           dtm.addRow(rowData);
           jTable1.setModel(dtm);
           
           }
       
   }
   
    
    public static String pracIdentifier(String practitionerId) throws IOException{
    org.json.JSONObject practitioner = new org.json.JSONObject(readJsonFromUrl("/Practitioner/" + practitionerId + "?_format=json"));
    
    return practitioner.getJSONArray("identifier").getJSONObject(0).getString("system")+
            "%7C" +
                practitioner.getJSONArray("identifier").getJSONObject(0).getString("value");
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Practioner's Patient List with Cholesterol");
        setBackground(new java.awt.Color(204, 204, 255));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setBackground(new java.awt.Color(0, 0, 0));
        jTable1.setForeground(new java.awt.Color(255, 255, 255));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Patient Id", "Name", "Total Cholesterol", "Time", "Systolic blood pressure", "Diastolic blood pressure", "Time"
            }
        ));
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setSelectionForeground(new java.awt.Color(0, 0, 0));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(4, 91, 1040, 200));

        jButton1.setText("Add");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 350, 67, -1));

        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, -1, -1));

        jTextField1.setBackground(new java.awt.Color(0, 0, 0));
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setCaretColor(new java.awt.Color(0, 0, 0));
        getContentPane().add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 330, 119, -1));

        jTextField3.setBackground(new java.awt.Color(0, 0, 0));
        jTextField3.setForeground(new java.awt.Color(255, 255, 255));
        jTextField3.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        jTextField3.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 420, 119, -1));

        jTextField5.setBackground(new java.awt.Color(0, 0, 0));
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        jTextField5.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 360, 119, -1));

        jTextField4.setBackground(new java.awt.Color(0, 0, 0));
        jTextField4.setForeground(new java.awt.Color(255, 255, 255));
        jTextField4.setSelectedTextColor(new java.awt.Color(255, 255, 255));
        jTextField4.setSelectionColor(new java.awt.Color(255, 255, 255));
        getContentPane().add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 390, 119, -1));

        jLabel1.setText("Patient Id:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, -1, -1));

        jLabel2.setText("Total Cholesterol:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, -1, -1));

        jLabel3.setText("Time:");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, -1, -1));

        jLabel4.setText("Name:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 360, -1, 20));

        jLabel5.setFont(new java.awt.Font("Hiragino Mincho ProN", 3, 24)); // NOI18N
        jLabel5.setText("Welcome to your Patient List");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 350, 40));

        jButton3.setText("BAR CHART ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 370, -1, -1));

        pack();

        MyTableCellRenderer renderer = new MyTableCellRenderer();
        jTable1.setDefaultRenderer(Object.class, renderer);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        
        try {
            patientDetails("","","","","","","","","","","",11111,"");
            

            
            
            
            int row=jTable1.rowAtPoint(evt.getPoint());
            int col= jTable1.columnAtPoint(evt.getPoint());
            
            
            
            
            
            
            JOptionPane.showMessageDialog(null, "BirthDate: 1935-12-16\n"
                    + "Gender: male\n"
                    + "City: Boston,\n" 
                    + "State: Massachusetts,\n" 
                    + "Country:US");
        } catch (Exception e) {
           e.printStackTrace();
        }
        
     
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
   
        rowData[0] = jTextField1.getText();        
        rowData[1] = jTextField3.getText();
        rowData[2] = jTextField4.getText();
        rowData[3] = jTextField3.getText();

        dtm.addRow(rowData);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       int i  = jTable1.getSelectedRow();
       if(i >= 0){
           dtm.removeRow(i);
       }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
     
        CholesterolChart();
    }//GEN-LAST:event_jButton3ActionPerformed

   
   
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException{
        try{
        patientList("",null,"","","","",0,0,"");
        
        }catch(Exception e){ e.printStackTrace();}
        
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
        
        
        
    }
    

    
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables

    
}
    

