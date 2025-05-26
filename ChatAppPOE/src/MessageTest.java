import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    public void resetMessageState() {
        Message.clearSentMessages(); // Reset before each test
    }

    @Test
    public void testCheckMessageId_Valid() {
        Message msg = new Message("+27821234567", "Hello");
        assertTrue(msg.checkMessageId());
    }

    @Test
    public void testCheckRecipientCell_Valid() {
        Message msg = new Message("+27821234567", "Hello");
        assertTrue(msg.checkRecipientCell());
    }

    @Test
    public void testCheckRecipientCell_Invalid() {
        Message msg = new Message("27821234567", "Hello");
        assertFalse(msg.checkRecipientCell());
    }

    @Test
    public void testCheckMessageLength_Valid() {
        Message msg = new Message("+27821234567", "Short");
        assertTrue(msg.checkMessageLength());
    }

    @Test
    public void testCheckMessageLength_Invalid() {
        String longMessage = "a".repeat(251);
        Message msg = new Message("+27821234567", longMessage);
        assertFalse(msg.checkMessageLength());
    }

    @Test
    public void testCreateMessageHash_Format() {
        Message msg = new Message("+27821234567", "Hello World");
        String hash = msg.createMessageHash();
        assertTrue(hash.startsWith(msg.getMessageId().substring(0, 2)));
        assertTrue(hash.contains("HELLO"));
        assertTrue(hash.contains("WORLD"));
    }

    @Test
    public void testSentMessage_Send() {
        Message msg = new Message("+27821234567", "Test");
        assertEquals("Message successfully sent.", msg.sentMessage(1));
        assertTrue(msg.isSent());
    }

    @Test
    public void testPrintMessages_AfterSending() {
        Message msg = new Message("+27821234567", "Test");
        msg.sentMessage(1);
        String output = Message.printMessages();
        assertTrue(output.contains("Test"));
    }

    @Test
    public void testReturnTotalMessages() {
        new Message("+27821234567", "Msg1").sentMessage(1);
        new Message("+27821234567", "Msg2").sentMessage(1);
        assertEquals(2, Message.returnTotalMessages());
    }
}