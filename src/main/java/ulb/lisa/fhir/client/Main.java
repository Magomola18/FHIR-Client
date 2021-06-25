/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.fhir.client;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
/**
 *
 * @author karabomagomola
 */


public class Main {



    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static String readJsonFromUrl(String url2) throws IOException, JSONException {
        String serverURL = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir";
        URL url = new URL(serverURL + url2);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line = null;
        StringBuilder response = new StringBuilder();
        while((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }

        return response.toString();
    }


    public static void main(String[] args) throws IOException, JSONException {
        Set <String> patientIds = new HashSet<>();

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Enter practitioner ID: ");

        String practitionerId = myObj.nextLine();  // Read user input

        //gets prac Identifier
        String identifier = pracIdentifier(practitionerId);
        //Convert data into JSON
        String json = readJsonFromUrl("/Encounter/?participant.identifier=" + identifier + "&_include=Encounter.participant.individual&_include=Encounter.patient&_format=json");
        JSONObject jsonObject = new JSONObject(json);

        //Iterate through practitioners patients to get name
        int i = 0;
        while (i< jsonObject.getJSONArray("entry").length()) {
            String patientRef = jsonObject.getJSONArray("entry").getJSONObject(i).getJSONObject("resource").getJSONObject("subject").getString("reference");
            String patientId = patientRef.split("/")[1];

            String patientName = jsonObject.getJSONArray("entry").getJSONObject(i).getJSONObject("resource").getJSONObject("subject").getString("display");
            if (patientIds.add(patientId)) {
                System.out.println(patientName);
            }


            i++;
        }

    }

    public static String pracIdentifier(String practitionerId) throws IOException {
        JSONObject practitioner = new JSONObject(readJsonFromUrl("/Practitioner/" + practitionerId + "?_format=json"));

        return practitioner.getJSONArray("identifier").getJSONObject(0).getString("system") +
                "%7C" +
                practitioner.getJSONArray("identifier").getJSONObject(0).getString("value");
    }
}
