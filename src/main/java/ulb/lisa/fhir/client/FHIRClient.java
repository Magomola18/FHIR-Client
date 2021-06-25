/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ulb.lisa.fhir.client;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.api.IGenericClient;
/**
 *
 * @author karabomagomola
 */
public class FHIRClient {
    public FhirContext ctx;
    public IGenericClient client;
    public String serverBase;
        
    public FHIRClient(){
        ctx = FhirContext.forR4();
        serverBase = "https://fhir.monash.edu/hapi-fhir-jpaserver/fhir";
        client = ctx.newRestfulGenericClient(serverBase);
    }
    
    
  
}

