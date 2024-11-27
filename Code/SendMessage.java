import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.text.SimpleDateFormat; // Import for date formatting
import java.util.Date; // Import for getting current date

public class SendMessage {
    private final JPanel messagePanelContainer; // Panel to hold messages

    public SendMessage(JPanel messagePanelContainer) {
        this.messagePanelContainer = messagePanelContainer; // Initialize message panel container
    }

    // Method to send message by creating a custom message bubble
    public void sendMessage(String text) {
        JLabel label = new JLabel(text); // Create label with message text
        label.setFont(new Font("Arial", Font.PLAIN, 20)); // Set font style and size
        label.setForeground(Color.WHITE); // Set text color

        // Create custom rounded panel for message bubble
        RoundedPanel messagePanel = new RoundedPanel(new BorderLayout(), 30); // 30-pixel rounded corners
        messagePanel.add(label, BorderLayout.CENTER); // Add label to message panel
        messagePanel.setBackground(new Color(0x15570E)); // Set bubble color
        messagePanel.setBorder(new EmptyBorder(10, 12, 10, 12)); // Add padding inside bubble

        // Set message bubble size based on label content
        messagePanel.setMaximumSize(new Dimension(label.getPreferredSize().width + 30, label.getPreferredSize().height + 20));

        // Create a label for the timestamp
        String timeStamp = new SimpleDateFormat("HH:mm").format(new Date()); // Get current time in hours and minutes
        JLabel timeLabel = new JLabel(timeStamp); // Create label for the time
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 10)); // Set smaller font for the timestamp
        timeLabel.setForeground(Color.LIGHT_GRAY); // Set time label color

        // Create a panel for the message and time
        JPanel messageWithTimePanel = new JPanel();
        messageWithTimePanel.setLayout(new BoxLayout(messageWithTimePanel, BoxLayout.X_AXIS)); // Set layout to horizontal alignment
        messageWithTimePanel.setOpaque(false); // Make background transparent
        messageWithTimePanel.add(messagePanel, BorderLayout.CENTER); // Add message panel
        messageWithTimePanel.add(timeLabel, BorderLayout.SOUTH); // Add time label at the bott om

        // Wrap message panel in another panel to align it to the right
        JPanel rightAlignedPanel = new JPanel();
        rightAlignedPanel.setLayout(new BoxLayout(rightAlignedPanel, BoxLayout.X_AXIS)); // Set layout to horizontal alignment
        rightAlignedPanel.setOpaque(false); // Set transparent background

        rightAlignedPanel.add(Box.createHorizontalGlue()); // Push message to right side
        rightAlignedPanel.add(messageWithTimePanel); // Add message and time panel

        // Add space between messages and add new message panel
        messagePanelContainer.add(Box.createVerticalStrut(5)); // Add vertical space between messages
        messagePanelContainer.add(rightAlignedPanel); // Add right-aligned panel to container
        messagePanelContainer.revalidate(); // Update container layout

        // Auto-scroll to the bottom after adding the message
        JScrollPane scrollPane = (JScrollPane) messagePanelContainer.getParent().getParent(); // Get the scroll pane that holds the message panel
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum())); // Scroll to the bottom
    }
}