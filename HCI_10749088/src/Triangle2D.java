import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Triangle2D {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Triangle Drawing Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use the custom panel that extends JPanel
        CustomPanel3 panel = new CustomPanel3();

        frame.add(panel);

        JPanel controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        JButton button = new JButton("Set Triangle Dimensions and Color");
        controlPanel.add(button);

        JButton saveButton = new JButton("Save Image");

        controlPanel.add(saveButton);

        frame.setSize(800, 550);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for the base and height of the triangle
                String baseStr = JOptionPane.showInputDialog("Enter the base of the triangle:");
                String heightStr = JOptionPane.showInputDialog("Enter the height of the triangle:");

                // Prompt the user to select the color
                Color selectedColor = JColorChooser.showDialog(frame, "Choose Triangle Color", Color.BLUE);

                try {
                    int userBase = Integer.parseInt(baseStr);
                    int userHeight = Integer.parseInt(heightStr);
                    panel.setDimensionsAndColor(userBase, userHeight, selectedColor);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Using the default dimensions and color.");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the triangle drawing to an image file
                saveImage(panel, "triangle.png");
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

class CustomPanel3 extends JPanel {
    private int base = 200; // Default base
    private int height = 150; // Default height
    private Color shapeColor = Color.BLUE; // Default color

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int x = (panelWidth - base) / 2;
        int y = (panelWidth - height) / 2;

        int[] xPoints = {x, x + base, x + (base / 2)};
        int[] yPoints = {y + height, y + height, y};

        g.setColor(shapeColor);
        g.fillPolygon(xPoints, yPoints, 3);
    }

    public void setDimensionsAndColor(int newBase, int newHeight, Color color) {
        this.base = newBase;
        this.height = newHeight;
        this.shapeColor = color;
        repaint();
    }
}