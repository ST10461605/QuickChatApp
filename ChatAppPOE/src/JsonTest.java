import org.json.JSONObject;
import org.json.JSONArray;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class JsonTest {
    public static void main(String[] args) {
        // 1. Create a JSON Object
        JSONObject message = new JSONObject();
        message.put("sender", "Lusanda");
        message.put("recipient", "Thubelihle");
        message.put("message", "Hey, hope your day is going well!");
        message.put("id", "MSG123");

        // 2. Create a JSON Array and add the message
        JSONArray messagesArray = new JSONArray();
        messagesArray.put(message);

        // 3. Save JSON to file
        try (FileWriter file = new FileWriter("messages.json")) {
            file.write(messagesArray.toString(4)); // pretty print
            System.out.println("✅ JSON saved to messages.json");
        } catch (IOException e) {
            System.out.println("❌ Error writing JSON: " + e.getMessage());
        }

        // 4. Read JSON back from file
        System.out.println("\n📥 Reading from messages.json:");
        try (BufferedReader reader = new BufferedReader(new FileReader("messages.json"))) {
            StringBuilder jsonText = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonText.append(line);
            }

            // 5. Parse the JSON
            JSONArray readArray = new JSONArray(jsonText.toString());
            JSONObject readMessage = readArray.getJSONObject(0);
            System.out.println("Sender: " + readMessage.getString("sender"));
            System.out.println("Recipient: " + readMessage.getString("recipient"));
            System.out.println("Message: " + readMessage.getString("message"));
            System.out.println("ID: " + readMessage.getString("id"));

        } catch (IOException e) {
            System.out.println("❌ Error reading JSON: " + e.getMessage());
        }
    }
}
