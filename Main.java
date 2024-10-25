import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    private final JPanel messagePanelContainer; // Panel to hold all messages
    private final JScrollPane scrollPane; // ScrollPane to make messages scrollable

    public Main() {
        super("WhatsApp 2.0"); // Set title of the main frame
        setSize(1280, 720); // Set frame size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Set close operation
        getContentPane().setBackground(Color.BLACK); // Set background color of content
        setLayout(null); // Set layout to null for custom component positioning

        ImageIcon logoIcon = new ImageIcon("pics/logo.png"); // Load logo image
        setIconImage(logoIcon.getImage()); // Set logo as window icon

        // Create a text field for message input with custom font and color
        JTextField textField = new JTextField();
        textField.setBounds(220, 620, 850, 50); // Set position and size
        textField.setFont(new Font("Arial", Font.PLAIN, 25)); // Set font style and size
        textField.setForeground(Color.WHITE); // Set text color
        textField.setBackground(Color.DARK_GRAY); // Set background color
        add(textField); // Add text field to frame

        // Create panel for holding all messages and set layout and background color
        messagePanelContainer = new JPanel();
        messagePanelContainer.setLayout(new BoxLayout(messagePanelContainer, BoxLayout.Y_AXIS)); // Set layout to vertical alignment
        messagePanelContainer.setBackground(Color.BLACK); // Set background color

        scrollPane = new JScrollPane(messagePanelContainer);
        scrollPane.setBounds(220, 20, 850, 580);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Apply the custom UI to the vertical scrollbar
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        add(scrollPane); // Add scroll pane to frame


        // Create a rounded panel to hold the "Send" button
        RoundedPanel buttonPanel = new RoundedPanel(new BorderLayout(), 30); // 30-pixel rounded corners
        buttonPanel.setBounds(1100, 620, 100, 50); // Position and size of button panel
        buttonPanel.setBackground(Color.GREEN); // Set panel background color
        add(buttonPanel); // Add panel to frame

        // Create "Send" button with custom font and color, no border
        JButton button = new JButton("Send");
        button.setForeground(Color.BLACK); // Set text color
        button.setBorderPainted(false); // Remove button border
        button.setContentAreaFilled(false); // Remove button background fill
        button.setFocusPainted(false); // Remove focus border when clicked
        button.setFont(new Font("Arial", Font.PLAIN, 18)); // Set font style and size
        buttonPanel.add(button); // Add button to rounded panel

        // Create SendMessage instance to handle message sending
        SendMessage sendMessageHandler = new SendMessage(messagePanelContainer);

        // Add action listener for the "Send" button
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendMessage(sendMessageHandler, textField); // Call sendMessage method
            }
        });

        // Add key listener for Enter key to trigger message sending
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) { // Check if Enter was pressed
                    sendMessage(sendMessageHandler, textField); // Call sendMessage method
                }
            }
        });
    }

    // Method to send message by passing text from textField to SendMessage
    private void sendMessage(SendMessage sendMessageHandler, JTextField textField) {
        String inputText = textField.getText().trim(); // Get text and trim whitespace
        if (!inputText.isEmpty()) { // Check if text is not empty
            sendMessageHandler.sendMessage(inputText); // Send message to handler
            textField.setText(""); // Clear text field
        }
    }

    public static void main(String[] args) {
        Main app = new Main(); // Create Main frame instance
        app.setVisible(true); // Make frame visible
    }
}