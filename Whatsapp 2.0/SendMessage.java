import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendMessage {
    private final JPanel messagePanelContainer;

    public SendMessage(JPanel messagePanelContainer) {
        this.messagePanelContainer = messagePanelContainer;
    }

    // Method to send message with alignment based on sender
    public void sendMessage(String text, boolean isMine) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 20));
        label.setForeground(Color.WHITE);

        // Create message bubble
        RoundedPanel messagePanel = new RoundedPanel(new BorderLayout(), 30);
        messagePanel.add(label, BorderLayout.CENTER);
        messagePanel.setBackground(isMine ? new Color(0x15570E) : new Color(0x3A3A3A)); // Green for yours, gray for friend
        messagePanel.setBorder(new EmptyBorder(10, 12, 10, 12));
        messagePanel.setMaximumSize(new Dimension(label.getPreferredSize().width + 30, label.getPreferredSize().height + 20));

        // Add timestamp
        String timeStamp = new SimpleDateFormat("HH:mm").format(new Date());
        JLabel timeLabel = new JLabel(timeStamp);
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        timeLabel.setForeground(Color.LIGHT_GRAY);

        JPanel messageWithTimePanel = new JPanel();
        messageWithTimePanel.setLayout(new BoxLayout(messageWithTimePanel, BoxLayout.Y_AXIS));
        messageWithTimePanel.setOpaque(false);
        messageWithTimePanel.add(messagePanel);
        messageWithTimePanel.add(timeLabel);

        // Align messages
        JPanel alignedPanel = new JPanel();
        alignedPanel.setLayout(new BoxLayout(alignedPanel, BoxLayout.X_AXIS));
        alignedPanel.setOpaque(false);

        if (isMine) {
            alignedPanel.add(Box.createHorizontalGlue()); // Push to right
            alignedPanel.add(messageWithTimePanel);
        } else {
            alignedPanel.add(messageWithTimePanel); // Push to left
            alignedPanel.add(Box.createHorizontalGlue());
        }

        // Add spacing and update layout
        messagePanelContainer.add(Box.createVerticalStrut(5));
        messagePanelContainer.add(alignedPanel);
        messagePanelContainer.revalidate();

        // Auto-scroll to bottom
        JScrollPane scrollPane = (JScrollPane) messagePanelContainer.getParent().getParent();
        SwingUtilities.invokeLater(() -> scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum()));
    }
}