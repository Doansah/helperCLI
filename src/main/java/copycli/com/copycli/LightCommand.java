package copycli.com.copycli;

import org.springframework.http.MediaType;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@ShellComponent
public class LightCommand {
    private final String govee_baseURL = "https://developer-api.govee.com";



    @ShellMethod(value = "Turn my lightbulb off and on", key = "light")
    public String toggleLight(String apiKey, String deviceMac, String model) {
        try {
            WebClient client = WebClient.builder()
                .defaultHeader("Govee-API-Key", apiKey)
                .build();
            
            // First, get device state
            String getResponse = client.get()
                .uri(uriBuilder -> uriBuilder
                    .path("/v1/devices/state")
                    .queryParam("device", deviceMac)
                    .queryParam("model", model)
                    .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
            
            // Parse current state (simplified - assumes powerState is in response)
            boolean isCurrentlyOn = getResponse != null && getResponse.contains("\"powerState\":1");
            
            // Toggle the light
            String controlUrl = govee_baseURL + "/v1/devices/control";
            String requestBody = String.format(
                "{\"device\":\"%s\",\"model\":\"%s\",\"cmd\":{\"name\":\"turn\",\"value\":\"%s\"}}",
                deviceMac, model, isCurrentlyOn ? "off" : "on"
            );
            
            String controlResponse = client.put()
                .uri(controlUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .bodyToMono(String.class)
                .block();
            
            return String.format(" Light turned %s! Response: %s", 
                isCurrentlyOn ? "OFF" : "ON", 
                controlResponse != null ? "Success" : "Failed");
                
        } catch (Exception e) {
            return " Error controlling light: " + e.getMessage();
        }
    }
    
    @ShellMethod(value = "Get device list from Govee", key = "devices")
    public String getDevices(String apiKey) {
        try {
            WebClient client = WebClient.builder()
                .defaultHeader("Govee-API-Key", apiKey)
                .build();
            
            String response = client.get()
                .uri(govee_baseURL + "/v1/devices")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
            
            return "📱 Your devices:\n" + (response != null ? response : "No devices found");
            
        } catch (Exception e) {
            return "❌ Error getting devices: " + e.getMessage();
        }
    }


}