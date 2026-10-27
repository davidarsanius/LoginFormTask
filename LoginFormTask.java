import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class User {

    private String username;
    private String password;
    private String role;

    User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}

class WelcomePage extends Frame {

    WelcomePage(User user) {
        super("Welcome");
        setLayout(new GridLayout(2, 1, 5, 5));

        Label hello = new Label("Hello, " + user.getUsername(), Label.CENTER);
        hello.setFont(new Font("SansSerif", Font.BOLD, 20));
        Label role = new Label("Role: " + user.getRole(), Label.CENTER);

        add(hello);
        add(role);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { dispose(); }
        });
        setSize(320, 160);
        setLocationRelativeTo(null);
    }
}

 public class LoginFormTask extends Frame {

    private static final int MAX_ATTEMPTS = 3;

    private ArrayList<User> users = new ArrayList<>();
    private int failedAttempts = 0;

    public LoginFormTask() {
        super("Login");
        loadUsers();

        setLayout(new GridBagLayout());
        Panel content = new Panel(new BorderLayout(10, 15));

        Label title = new Label("Welcome back", Label.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        content.add(title, BorderLayout.NORTH);

        Panel form = new Panel(new GridLayout(3, 2, 8, 8));
        form.add(new Label("Username:", Label.RIGHT));
        TextField username = new TextField(18);
        form.add(username);

        form.add(new Label("Password:", Label.RIGHT));
        TextField password = new TextField(18);
        password.setEchoChar('*');
        form.add(password);

        form.add(new Label("Role:", Label.RIGHT));
        Choice role = new Choice();
        role.add("Student");
        role.add("TA");
        role.add("Professor");
        form.add(role);

        content.add(form, BorderLayout.CENTER);

        Label message = new Label("", Label.CENTER);
        message.setForeground(Color.RED);

        Panel buttons = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        Button cancel = new Button("Cancel");
        Button login = new Button("Login");
        buttons.add(cancel);
        buttons.add(login);

        Panel south = new Panel(new GridLayout(2, 1, 0, 8));
        south.add(message);
        south.add(buttons);
        content.add(south, BorderLayout.SOUTH);

        add(content);

        login.addActionListener(e -> {
            String name = username.getText().trim();
            String pass = password.getText();

            if (name.isEmpty() || pass.isEmpty()) {
                message.setText("Please fill in all fields");
                return;
            }

            User user = findUser(name);
            if (user == null || !user.getPassword().equals(pass)) {
                failedAttempts++;
                if (failedAttempts >= MAX_ATTEMPTS) {
                    login.setEnabled(false);
                    message.setText("Too many failed attempts. Login disabled");
                } else {
                    message.setText("Invalid username or password");
                }
                return;
            }

            dispose();
            new WelcomePage(user).setVisible(true);
        });

        cancel.addActionListener(e -> dispose());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) { dispose(); }
        });
        setSize(420, 340);
        setLocationRelativeTo(null);

    }

    private void loadUsers() {
        users.add(new User("David", "1234", "Student"));
        users.add(new User("Divo", "abcd", "TA"));
        users.add(new User("Dr.David", "pass123", "Professor"));
    }

    private User findUser(String name) {
        for (User u : users) {
            if (u.getUsername().equals(name)) {
                return u;
            }
        }
        return null;
    }


    public static void main(String[] args) {
        new LoginFormTask().setVisible(true);

    }
}
