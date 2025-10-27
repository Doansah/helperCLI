package copycli.com.copycli.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.web.reactive.function.client.WebClient;

@ShellComponent
public class DadJokeCommand {

    private final String dadJokeAPI = "https://icanhazdadjoke.com";

    @ShellMethod(value = "Tells a random dad joke!", key = "dadjoke")
    public String tellDadJoke() {
        try {
            WebClient client = WebClient.builder()
                .defaultHeader("Accept", "application/json")
                .defaultHeader("User-Agent", "CopyCLI (https://github.com/yourrepo)")
                .build();

            // Get the response as a JSON string
            String response = client.get()
                .uri(dadJokeAPI)
                .retrieve()
                .bodyToMono(String.class)
                .block();

            if (response != null && response.contains("joke")) {
                // Parse the JSON manually (simple approach)
                String joke = extractJokeFromJson(response);
                return "😂 " + joke;
            } else {
                return "😅 Sorry, couldn't fetch a joke right now!";
            }
            
        } catch (Exception e) {
            return "❌ Error fetching dad joke: " + e.getMessage();
        }
    }
    
    // Simple JSON parser for the joke
    private String extractJokeFromJson(String json) {
        try {
            // Find the joke field in JSON: {"id":"...","joke":"THE JOKE","status":200}
            int startIndex = json.indexOf("\"joke\":\"") + 8;
            int endIndex = json.indexOf("\"", startIndex);
            
            if (startIndex > 7 && endIndex > startIndex) {
                return json.substring(startIndex, endIndex)
                    .replace("\\\"", "\"")  // Unescape quotes
                    .replace("\\n", "\n")   // Unescape newlines
                    .replace("\\\\", "\\"); // Unescape backslashes
            }
            return "Joke parsing failed!";
        } catch (Exception e) {
            return "Could not parse joke!";
        }
    }
    
    @ShellMethod(value = "Get multiple dad jokes", key = "jokes")
    public String getMultipleJokes(int count) {
        if (count < 1 || count > 5) {
            return "❌ Please specify 1-5 jokes";
        }
        
        StringBuilder jokes = new StringBuilder("😂 Here are " + count + " dad jokes:\n\n");
        
        WebClient client = WebClient.builder()
            .defaultHeader("Accept", "application/json")
            .defaultHeader("User-Agent", "CopyCLI (https://github.com/yourrepo)")
            .build();
        
        for (int i = 1; i <= count; i++) {
            try {
                String response = client.get()
                    .uri(dadJokeAPI)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

                if (response != null && response.contains("joke")) {
                    String joke = extractJokeFromJson(response);
                    jokes.append(i).append(". ").append(joke).append("\n\n");
                } else {
                    jokes.append(i).append(". Joke unavailable\n\n");
                }
                
                // Small delay to be nice to the API
                if (i < count) {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
                
            } catch (Exception e) {
                jokes.append(i).append(". Error fetching joke\n\n");
            }
        }
        
        return jokes.toString();
    }

}