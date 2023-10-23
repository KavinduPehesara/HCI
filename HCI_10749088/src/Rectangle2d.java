import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Rectangle2d {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Rectangle Drawing Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use the custom panel that extends JPanel
        CustomPanel1 panel = new CustomPanel1();

        frame.add(panel);

        JPanel controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        JButton button = new JButton("Set Rectangle Dimensions and Color");
        controlPanel.add(button);

        JButton saveButton = new JButton("Save Image");

        controlPanel.add(saveButton);

        frame.setSize(800, 550);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for the width and height of the rectangle
                String widthStr = JOptionPane.showInputDialog("Enter the width of the rectangle:");
                String heightStr = JOptionPane.showInputDialog("Enter the height of the rectangle:");

                // Prompt the user to select the color
                Color selectedColor = JColorChooser.showDialog(frame, "Choose Rectangle Color", Color.BLUE);

                try {
                    int userWidth = Integer.parseInt(widthStr);
                    int userHeight = Integer.parseInt(heightStr);
                    panel.setDimensionsAndColor(userWidth, userHeight, selectedColor);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Using the default dimensions and color.");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the rectangle drawing to an image file
                saveImage(panel, "rectangle.png");
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

class CustomPanel1 extends JPanel {
    private int width = 300; // Default width
    private int height = 200; // Default height
    private Color color = Color.BLUE; // Default color

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int x = (panelWidth - width) / 2;
        int y = (panelHeight - height) / 2;

        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

    public void setDimensionsAndColor(int newWidth, int newHeight, Color newColor) {
        this.width = newWidth;
        this.height = newHeight;
        this.color = newColor;
        repaint();
    }
}
