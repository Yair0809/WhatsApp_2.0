import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class CustomScrollBarUI extends BasicScrollBarUI {

    @Override
    protected void configureScrollBarColors() {
        this.thumbColor = new Color(129, 129, 129); // Set color for scroll thumb (slider)
        this.trackColor = new Color(0, 0, 0); // Set the color for the track background
    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = new JButton(); // Button for decreasing the scroll
        button.setPreferredSize(new Dimension(0, 0)); // Remove button size to hide it
        return button;
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        JButton button = new JButton(); // Button for increasing the scroll
        button.setPreferredSize(new Dimension(0, 0)); // Remove button size to hide it
        return button;
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(thumbColor); // Set thumb color
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10); // Draw rounded rectangle for thumb
        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(trackColor); // Set track color (background color of the scroll bar)
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height); // Draw track
        g2.dispose();
    }
}
