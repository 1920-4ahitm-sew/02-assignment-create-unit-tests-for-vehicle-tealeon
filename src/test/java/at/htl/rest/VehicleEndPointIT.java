package at.htl.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;


public class VehicleEndPointIT {

    private Client client;
    private WebTarget target;

    @BeforeEach
    public void initClient(){
        this.client = ClientBuilder.newClient();
        this.target = client.target("http://localhost:8080/vehicle/api");
    }

    @Test
    public void fetchVehicle(){
        Response response = this.target.request(MediaType.TEXT_PLAIN).get();
        assertThat(response.getStatus(), is(200));
        String payload = response.readEntity(String.class);
        System.out.println("payload = " + payload);
    }

    @Test
    public void fetchId(){
        JsonObject dedicatedVehicle = this.target.path("42")
                .request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertThat(dedicatedVehicle.getString("brand"), containsString("42"));
        assertThat(dedicatedVehicle.getString("brand"),equalTo("Opel 42"));
    }

    @Test
    public void fetchType(){
        JsonObject dedicatedVehicle = this.target.path("42")
                .request(MediaType.APPLICATION_JSON).get(JsonObject.class);
        assertThat(dedicatedVehicle.getString("type"),equalTo("Commodore"));
    }

    @Test
    public void deleteVehicle(){
        Response deleteResponse = this.target.path("42")
                .request(MediaType.APPLICATION_JSON).delete();
        assertThat(deleteResponse.getStatus(),is(204));
    }
}
