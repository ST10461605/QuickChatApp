import javax.swing.*;

public class QuickChatApp {
    private static Login currentUser;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> showRegistrationForm());
    }

    private static void showRegistrationForm() {
        JFrame frame = new JFrame("QuickChat Registration");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new java.awt.GridLayout(4, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JLabel phoneLabel = new JLabel("Cellphone:");
        JTextField phoneField = new JTextField();

        JButton registerBtn = new JButton("Register");
        JLabel statusLabel = new JLabel("");

        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(phoneLabel);
        frame.add(phoneField);
        frame.add(registerBtn);
        frame.add(statusLabel);

        registerBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());
            String cellphone = phoneField.getText();

            String result = Login.registerUser(username, password, cellphone);
            statusLabel.setText(result);
            System.out.println("Registration result: " + result);  // Debug

            if (result.equals("Registration successful!")) {
                currentUser = new Login(username, password, cellphone);
                frame.dispose();
                showLoginForm();
            }
        });

        frame.setVisible(true);
    }

    private static void showLoginForm() {
        JFrame frame = new JFrame("QuickChat Login");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new java.awt.GridLayout(3, 2, 10, 10));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");
        JLabel statusLabel = new JLabel("");

        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(loginBtn);
        frame.add(statusLabel);

        loginBtn.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            if (currentUser != null && currentUser.loginUser(username, password)) {
                statusLabel.setText("Login successful!");
                frame.dispose();
                showChatInterface();
            } else {
                statusLabel.setText("Login failed!");
            }
        });

        frame.setVisible(true);
    }

    private static void showChatInterface() {
        JOptionPane.showMessageDialog(null, "Welcome to QuickChat.");

        int messagesSent = 0;
        int numMessages = 0;

        while (true) {
            String inputNum = JOptionPane.showInputDialog("How many messages do you want to send?");
            if (inputNum == null) {
                JOptionPane.showMessageDialog(null, "Exiting program.");
                return;
            }
            try {
                numMessages = Integer.parseInt(inputNum);
                if (numMessages <= 0) {
                    JOptionPane.showMessageDialog(null, "Please enter a positive number.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
        }

        while (true) {
            String menu = "Choose an option:\n" +
                          "1) Send Messages\n" +
                          "2) Show recently sent messages\n" +
                          "3) Quit";
            String choiceInput = JOptionPane.showInputDialog(menu);
            if (choiceInput == null || choiceInput.equals("3")) {
                JOptionPane.showMessageDialog(null, "Goodbye! Total messages sent: " + Message.returnTotalMessages());
                break;
            }

            switch (choiceInput) {
                case "1":
                    if (messagesSent >= numMessages) {
                        JOptionPane.showMessageDialog(null, "You have reached your message limit.");
                        continue;
                    }
                    String recipient = JOptionPane.showInputDialog("Enter recipient cell number (include international code):");
                    if (recipient == null) continue;
                    String messageText = JOptionPane.showInputDialog("Enter your message (max 250 chars):");
                    if (messageText == null) continue;

                    Message msg = new Message(recipient, messageText);

                    if (!msg.checkMessageId()) {
                        JOptionPane.showMessageDialog(null, "Message ID is too long.");
                        continue;
                    }
                    if (!msg.checkRecipientCell()) {
                        JOptionPane.showMessageDialog(null, "Recipient cell number must start with '+' and be 10-12 digits.");
                        continue;
                    }
                    if (!msg.checkMessageLength()) {
                        int excess = messageText.length() - 250;
                        JOptionPane.showMessageDialog(null, "Message exceeds 250 characters by " + excess + ", please reduce size.");
                        continue;
                    }

                    String options = "Choose an option:\n1) Send\n2) Disregard\n3) Store to send later";
                    String actionInput = JOptionPane.showInputDialog(options);
                    if (actionInput == null) continue;
                    int action;
                    try {
                        action = Integer.parseInt(actionInput);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Invalid option.");
                        continue;
                    }

                    String result = msg.sentMessage(action);
                    if (result.equals("Message successfully sent.")) {
                        messagesSent++;
                        JOptionPane.showMessageDialog(null,
                            "MessageID: " + msg.getMessageId() +
                            "\nMessage Hash: " + msg.getMessageHash() +
                            "\nRecipient: " + msg.getRecipient() +
                            "\nMessage: " + msg.getMessage());
                    }
                    JOptionPane.showMessageDialog(null, result);
                    break;

                case "2":
                    String recentMessages = Message.printMessages();
                    JOptionPane.showMessageDialog(null, recentMessages);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }
}
