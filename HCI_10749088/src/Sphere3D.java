import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Sphere3D {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sphere Drawing Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use the custom panel that extends JPanel
        CustomPanel10 panel = new CustomPanel10();

        frame.add(panel);

        JPanel controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        JButton button = new JButton("Set Sphere Properties and Save");
        controlPanel.add(button);

        JButton saveButton = new JButton("Save Image");

        controlPanel.add(saveButton);

        frame.setSize(800, 550);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for the size (diameter) of the sphere
                String diameterStr = JOptionPane.showInputDialog("Enter the diameter of the sphere:");

                // Prompt the user to select the color
                Color selectedColor = JColorChooser.showDialog(frame, "Choose Sphere Color", Color.RED);

                try {
                    int sphereDiameter = Integer.parseInt(diameterStr);
                    panel.setSphereDimensionsAndColor(sphereDiameter, selectedColor);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Using default diameter and color.");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the sphere drawing to an image file
                saveImage(panel, "sphere.png");
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

class CustomPanel10 extends JPanel {
    private int sphereDiameter = 100; // Default sphere diameter
    private Color sphereColor = Color.RED; // Default sphere color

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int x = (panelWidth - sphereDiameter) / 2;
        int y = (panelHeight - sphereDiameter) / 2;

        // Draw the sphere (ellipse) with the selected color
        g.setColor(sphereColor);
        g.fillOval(x, y, sphereDiameter, sphereDiameter);
    }

    public void setSphereDimensionsAndColor(int newDiameter, Color newColor) {
        this.sphereDiameter = newDiameter;
        this.sphereColor = newColor;
        repaint();
    }
}
