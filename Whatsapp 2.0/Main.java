import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Main extends JFrame {
    private final JPanel messagePanelContainer;
    private final JScrollPane scrollPane;
    private PrintWriter out; // To send messages to the server
    private BufferedReader in; // To receive messages from the server

    public Main() {
        super("WhatsApp 2.0");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setLayout(null);

        ImageIcon logoIcon = new ImageIcon("pics/logo.png");
        setIconImage(logoIcon.getImage());

        JTextField textField = new JTextField();
        textField.setBounds(220, 620, 850, 50);
        textField.setFont(new Font("Arial", Font.PLAIN, 25));
        textField.setForeground(Color.WHITE);
        textField.setBackground(Color.DARK_GRAY);
        add(textField);

        messagePanelContainer = new JPanel();
        messagePanelContainer.setLayout(new BoxLayout(messagePanelContainer, BoxLayout.Y_AXIS));
        messagePanelContainer.setBackground(Color.BLACK);

        scrollPane = new JScrollPane(messagePanelContainer);
        scrollPane.setBounds(220, 20, 850, 580);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        add(scrollPane);

        RoundedPanel buttonPanel = new RoundedPanel(new BorderLayout(), 30);
        buttonPanel.setBounds(1100, 620, 100, 50);
        buttonPanel.setBackground(Color.GREEN);
        add(buttonPanel);

        JButton button = new JButton("Send");
        button.setForeground(Color.BLACK);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        buttonPanel.add(button);

        SendMessage sendMessageHandler = new SendMessage(messagePanelContainer);

        // Handle "Send" button clicks
        button.addActionListener(e -> sendMessage(sendMessageHandler, textField));

        // Handle Enter key presses
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage(sendMessageHandler, textField);
                }
            }
        });

        // Start communication with the server
        startClient(sendMessageHandler);
    }

    private void sendMessage(SendMessage sendMessageHandler, JTextField textField) {
        String inputText = textField.getText().trim();
        if (!inputText.isEmpty()) {
            sendMessageHandler.sendMessage(inputText, true); // Send message on the right
            out.println(inputText); // Send to server
            textField.setText("");
        }
    }

    private void startClient(SendMessage sendMessageHandler) {
        try {
            Socket socket = new Socket("localhost", 9090);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread listenerThread = new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        sendMessageHandler.sendMessage(serverMessage, false); // Display message on the left
                    }
                } catch (IOException e) {
                    System.err.println("Connection to server lost.");
                }
            });
            listenerThread.start();
        } catch (IOException e) {
            System.err.println("Error connecting to server: " + e.getMessage());
        }
    }


    public static void main(String[] args) {
        Main app = new Main();
        app.setVisible(true);
    }
}
