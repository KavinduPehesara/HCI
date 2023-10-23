import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Pyramid3D {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pyramid Drawing Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use the custom panel that extends JPanel
        CustomPanel12 panel = new CustomPanel12();

        frame.add(panel);

        JPanel controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        JButton button = new JButton("Set Pyramid Properties");
        controlPanel.add(button);

        JButton saveButton = new JButton("Save Image");

        controlPanel.add(saveButton);

        frame.setSize(800, 550);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for the size (base width and height) of the pyramid
                String baseWidthStr = JOptionPane.showInputDialog("Enter the base width of the pyramid:");
                String baseHeightStr = JOptionPane.showInputDialog("Enter the base height of the pyramid:");
                String pyramidHeightStr = JOptionPane.showInputDialog("Enter the height of the pyramid:");

                // Prompt the user to select the color
                Color selectedColor = JColorChooser.showDialog(frame, "Choose Pyramid Color", Color.ORANGE);

                try {
                    int baseWidth = Integer.parseInt(baseWidthStr);
                    int baseHeight = Integer.parseInt(baseHeightStr);
                    int pyramidHeight = Integer.parseInt(pyramidHeightStr);
                    panel.setPyramidPropertiesAndColor(baseWidth, baseHeight, pyramidHeight, selectedColor);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Using default properties and color.");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the pyramid drawing to an image file
                saveImage(panel, "pyramid.png");
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

class CustomPanel12 extends JPanel {
    private int baseWidth = 100; // Default base width of the pyramid
    private int baseHeight = 100; // Default base height of the pyramid
    private int pyramidHeight = 100; // Default height of the pyramid
    private Color pyramidColor = Color.ORANGE; // Default pyramid color

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int x = (panelWidth - baseWidth) / 2;
        int y = panelHeight - pyramidHeight - 10; // Position above the bottom of the panel

        // Draw the pyramid with the selected color
        g.setColor(pyramidColor);
        g.fillPolygon(new int[] {x, x + baseWidth, x + (baseWidth / 2)}, new int[] {y + pyramidHeight, y + pyramidHeight, y}, 3);
    }

    public void setPyramidPropertiesAndColor(int newBaseWidth, int newBaseHeight, int newPyramidHeight, Color newColor) {
        this.baseWidth = newBaseWidth;
        this.baseHeight = newBaseHeight;
        this.pyramidHeight = newPyramidHeight;
        this.pyramidColor = newColor;
        repaint();
    }
}