/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.fhir.client;

/**
 *
 * @author karabomagomola
 */
public class PatientList {
    String patientName;
    public static Double patientValueQuantity;
    String patientUnit;
    String patientTime;
    String chol = patientValueQuantity +" "+ patientUnit;
    String patientId;
    int diastolicBP;
    int systolicBP; 
    String time; 
    
    public PatientList(String patientName, Double patientValueQuantity, String patientUnit, String patientTime, String chol, String patientId, int diastolicBP, int systolicBP, String time){
        this.patientName = patientName.replaceAll("[0-9]","");
        PatientList.patientValueQuantity = patientValueQuantity;
        this.patientUnit= patientUnit;
        this.patientTime = patientTime;
        this.chol = chol;
        this.patientId = patientId;
        this.diastolicBP = diastolicBP;
        this.systolicBP = systolicBP;    
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDiastolicBP() {
        return diastolicBP;
    }

    public void setDiastolicBP(int diastolicBP) {
        this.diastolicBP = diastolicBP;
    }

    public int getSystolicBP() {
        return systolicBP;
    }

    public void setSystolicBP(int systolicBP) {
        this.systolicBP = systolicBP;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getChol() {
        return chol;
    }

    public void setChol(String chol) {
        this.chol = chol;
    }

    

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Double getPatientValueQuantity() {
        return patientValueQuantity;
    }

    public void setPatientValueQuantity(Double patientValueQuantity) {
        this.patientValueQuantity = patientValueQuantity;
    }

    public String getPatientUnit() {
        return patientUnit;
    }

    public void setPatientUnit(String patientUnit) {
        this.patientUnit = patientUnit;
    }

    public String getPatientTime() {
        return patientTime;
    }

    public void setPatientTime(String patientTime) {
        this.patientTime = patientTime;
    }

    
}
