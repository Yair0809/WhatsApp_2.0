import javax.swing.*;
import java.awt.*;

class RoundedPanel extends JPanel {
    private int cornerRadius = 15; // Radius of the panel's corners

    public RoundedPanel(LayoutManager layout, int radius) {
        super(layout); // Call JPanel constructor with layout manager
        cornerRadius = radius; // Set the radius for rounded corners
        setOpaque(false); // Make panel background transparent
    }

    public RoundedPanel(int radius) {
        this(null, radius); // Constructor for panel without layout manager
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Call JPanel's paintComponent method
        Graphics2D g2 = (Graphics2D) g; // Cast Graphics to Graphics2D for advanced control
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing
        g2.setColor(getBackground()); // Set color to panel's background color
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius); // Draw rounded rectangle with corner radius
    }
}