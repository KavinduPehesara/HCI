import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Cuboid3D {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cuboid Drawing Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use the custom panel that extends JPanel
        CustomPanel5 panel = new CustomPanel5();

        frame.add(panel);

        JPanel controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        JButton button = new JButton("Set Cuboid Properties and Save");
        controlPanel.add(button);

        JButton saveButton = new JButton("Save Image");

        controlPanel.add(saveButton);

        frame.setSize(800, 550);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for the dimensions of the cuboid
                String widthStr = JOptionPane.showInputDialog("Enter the width of the cuboid:");
                String heightStr = JOptionPane.showInputDialog("Enter the height of the cuboid:");
                String depthStr = JOptionPane.showInputDialog("Enter the depth of the cuboid:");

                // Prompt the user to select the color
                Color selectedColor = JColorChooser.showDialog(frame, "Choose Cuboid Color", Color.BLUE);

                try {
                    int cuboidWidth = Integer.parseInt(widthStr);
                    int cuboidHeight = Integer.parseInt(heightStr);
                    int cuboidDepth = Integer.parseInt(depthStr);
                    panel.setCuboidDimensionsAndColor(cuboidWidth, cuboidHeight, cuboidDepth, selectedColor);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Using default dimensions and color.");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the cuboid drawing to an image file
                saveImage(panel, "cuboid.png");
            }
        });
    }

    // Method to save the panel as an image
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

class CustomPanel5 extends JPanel {
    private int cuboidWidth = 100; // Default cuboid width
    private int cuboidHeight = 100; // Default cuboid height
    private int cuboidDepth = 100; // Default cuboid depth
    private Color cuboidColor = Color.DARK_GRAY; // Default cuboid color

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int x = (panelWidth - cuboidWidth) / 2;
        int y = (panelHeight - cuboidHeight) / 2;

        // Draw the front face with the selected color
        g.setColor(cuboidColor);
        g.fillRect(x, y, cuboidWidth, cuboidHeight);

        // Draw the back face
        g.setColor(cuboidColor);
        int xBack = x + cuboidDepth;
        int yBack = y - cuboidDepth;
        g.fillRect(xBack, yBack, cuboidWidth, cuboidHeight);

        // Connect the front and back faces to create the sides
        g.setColor(Color.BLACK);
        g.drawLine(x, y, xBack, yBack);
        g.drawLine(x + cuboidWidth, y, xBack + cuboidWidth, yBack);

        // Connect the top and bottom faces
        g.drawLine(x, y, x, y + cuboidHeight);
        g.drawLine(xBack, yBack, xBack, yBack + cuboidHeight);
        g.drawLine(x + cuboidWidth, y, x + cuboidWidth, y + cuboidHeight);
        g.drawLine(xBack + cuboidWidth, yBack, xBack + cuboidWidth, yBack + cuboidHeight);
    }

    public void setCuboidDimensionsAndColor(int newWidth, int newHeight, int newDepth, Color newColor) {
        this.cuboidWidth = newWidth;
        this.cuboidHeight = newHeight;
        this.cuboidDepth = newDepth;
        this.cuboidColor = newColor;
        repaint();
    }
}
