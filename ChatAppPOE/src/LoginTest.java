import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

    @BeforeEach
    public void setUp() {
        // Reset static variables before each test
        Message.clearSentMessages();
    }

    @Test
    public void testCreateMessageHash_Format() {
        Message msg = new Message("+27821234567", "Hello World");
        String hash = msg.createMessageHash();
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