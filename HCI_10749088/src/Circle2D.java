import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Circle2D {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Circle Drawing Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        CustomPanel panel = new CustomPanel();
        frame.add(panel);

        JPanel controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        JButton button = new JButton("Set Circle Radius and Color");
        controlPanel.add(button);

        JButton saveButton = new JButton("Save Image");
        controlPanel.add(saveButton);

        frame.setSize(800, 550);
        frame.setVisible(true);

        button.addActionListener(e -> {
            String radiusStr = JOptionPane.showInputDialog(frame, "Enter the radius of the circle:");
            Color selectedColor = JColorChooser.showDialog(frame, "Choose Circle Color", Color.BLUE);

            try {
                int userRadius = Integer.parseInt(radiusStr);
                panel.setRadiusAndColor(userRadius, selectedColor);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input. Using the default radius and color.");
            }
        });

        saveButton.addActionListener(e -> saveImage(panel, "circle.png"));
    }

    private static void saveImage(JPanel panel, String fileName) {
        int width = panel.getWidth();
        int height = panel.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        panel.paint(g2d);
        g2d.dispose();

        try {
            ImageIO.write(image, "PNG", new File(fileName));
            JOptionPane.showMessageDialog(null, "Image saved to " + fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error while saving the image.");
        }
    }
}

class CustomPanel extends JPanel {
    private int radius = 50;
    private Color color = Color.BLUE;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        int x = (width - 2 * radius) / 2;
        int y = (height - 2 * radius) / 2;
        g.setColor(color);
        g.fillOval(x, y, 2 * radius, 2 * radius);
    }

    public void setRadiusAndColor(int newRadius, Color newColor) {
        this.radius = newRadius;
        this.color = newColor;
        repaint();
    }
}