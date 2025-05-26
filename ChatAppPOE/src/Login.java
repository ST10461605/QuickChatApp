import java.util.regex.Pattern;

public class Login {
    private String username;
    private String password;
    private String cellphone;

    private static final Pattern PASSWORD_PATTERN = 
        Pattern.compile("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    private static final Pattern CELL_PATTERN = 
        Pattern.compile("^\\+27\\d{9}$");  // Specific to South Africa: +27 followed by 9 digits

    public Login(String username, String password, String cellphone) {
        this.username = username;
        this.password = password;
        this.cellphone = cellphone;
    }

    public static boolean checkUserName(String username) {
        return username != null && username.contains("_") && username.length() <= 5;
    }

    public static boolean checkPasswordComplexity(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean checkCellPhoneNumber(String cellphone) {
        return cellphone != null && CELL_PATTERN.matcher(cellphone).matches();
    }

    public static String registerUser(String username, String password, String cellphone) {
        if (!checkUserName(username)) {
            return "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
        }
        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }
        if (!checkCellPhoneNumber(cellphone)) {
            return "Cell phone number is incorrectly formatted or does not contain an international code.";
        }
        return "Registration successful!";
    }

    public boolean loginUser(String inputUsername, String inputPassword) {
        if (inputUsername == null || inputPassword == null) {
            return false;
        }
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }

    public String returnLoginStatus(String inputUsername, String inputPassword, String firstName, String lastName) {
        if (loginUser(inputUsername, inputPassword)) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}