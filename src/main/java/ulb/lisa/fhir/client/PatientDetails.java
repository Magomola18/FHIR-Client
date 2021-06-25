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
public class PatientDetails {
    
    String birthDate, gender, address, city, state, country, line, given, family, prefix;
    String name = family + given;
    long postalCode;
    
    public PatientDetails(String birthDate, String gender, String address, String city, String state, String country, String line, String family, String given, String name, String prefix, long postalCode){
        this.address = address;
        this.birthDate = birthDate;
        this.city = city;
        this.state = state;
        this.gender = gender;
        this.country = country;
        this.line = line;
        this.postalCode = postalCode;
        this.family = family;
        this.given = given;
        this.name = name;
        this.prefix = prefix;
    
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public long getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(long postalCode) {
        this.postalCode = postalCode;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
}
