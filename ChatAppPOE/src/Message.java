import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class Message {
    private static int totalMessages = 0;
    private static List<Message> sentMessages = new ArrayList<>();
    private static final Pattern CELL_PATTERN = Pattern.compile("^\\+\\d{10,12}$");

    private String messageId;
    private int numSentMessages;
    private String recipient;
    private String message;
    private String messageHash;
    private boolean sent;

    public Message(String recipient, String message) {
        this.messageId = generateMessageId();
        this.recipient = recipient;
        this.message = message;
        this.numSentMessages = ++totalMessages;
        this.messageHash = createMessageHash();
        this.sent = false;
    }

    private String generateMessageId() {
        Random rand = new Random();
        return String.format("%010d", rand.nextInt(1_000_000_000));
    }

    public boolean checkMessageId() {
        return messageId.length() <= 10;
    }

    public boolean checkRecipientCell() {
        return CELL_PATTERN.matcher(recipient).matches();
    }

    public boolean checkMessageLength() {
        return message.length() <= 250;
    }

    public String createMessageHash() {
        String[] words = message.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 0 ? words[words.length - 1] : "";
        return String.format("%s:%d:%s%s",
                messageId.substring(0, 2),
                numSentMessages,
                firstWord.toUpperCase(),
                lastWord.toUpperCase());
    }

    public String sentMessage(int option) {
        switch (option) {
            case 1:
                this.sent = true;
                sentMessages.add(this);
                return "Message successfully sent.";
            case 2:
                return "Message disregarded.";
            case 3:
                // For simplicity, just store message (no separate queue)
                return "Message successfully stored.";
            default:
                return "Invalid option.";
        }
    }

    public static String printMessages() {
        if (sentMessages.isEmpty()) {
            return "No messages have been sent yet.";
        }

        StringBuilder result = new StringBuilder();
        for (Message msg : sentMessages) {
            result.append(String.format("MessageID: %s, Hash: %s, Recipient: %s, Message: %s\n",
                    msg.messageId, msg.messageHash, msg.recipient, msg.message));
        }
        return result.toString();
    }

    public static int returnTotalMessages() {
        return totalMessages;
    }

    // Getters
    public String getMessageId() { return messageId; }
    public int getNumSentMessages() { return numSentMessages; }
    public String getRecipient() { return recipient; }
    public String getMessage() { return message; }
    public String getMessageHash() { return messageHash; }
    public boolean isSent() { return sent; }
}
