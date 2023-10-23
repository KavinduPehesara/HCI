import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Cube3d {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Cube Drawing Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Use the custom panel that extends JPanel
        CustomPanel9 panel = new CustomPanel9();

        frame.add(panel);

        JPanel controlPanel = new JPanel();
        frame.add(controlPanel, BorderLayout.SOUTH);

        JButton button = new JButton("Set Cube Properties and Save");
        controlPanel.add(button);

        JButton saveButton = new JButton("Save Image");

        controlPanel.add(saveButton);

        frame.setSize(800, 550);
        frame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for the size (side length) of the cube
                String sizeStr = JOptionPane.showInputDialog("Enter the size of the cube:");

                // Prompt the user to select the color
                Color selectedColor = JColorChooser.showDialog(frame, "Choose Cube Color", Color.BLUE);

                try {
                    int cubeSize = Integer.parseInt(sizeStr);
                    panel.setCubeSizeAndColor(cubeSize, selectedColor);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid input. Using the default size and color.");
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Save the cube drawing to an image file
                saveImage(panel, "cube.png");
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

class CustomPanel9 extends JPanel {
    private int cubeSize = 100; // Default cube size
    private Color cubeColor = Color.DARK_GRAY; // Default cube color

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int x = (panelWidth - cubeSize) / 2;
        int y = (panelHeight - cubeSize) / 2;

        int depth = cubeSize / 2; // Depth of the cube

        // Draw the front face with the selected color
        g.setColor(cubeColor);
        g.fillRect(x, y, cubeSize, cubeSize);

        // Draw the back face
        g.setColor(cubeColor);
        int xBack = x + depth;
        int yBack = y - depth;
        g.fillRect(xBack, yBack, cubeSize, cubeSize);

        // Connect the front and back faces
        g.setColor(Color.BLACK);
        g.drawLine(x, y, xBack, yBack);
        g.drawLine(x + cubeSize, y, xBack + cubeSize, yBack);
        g.drawLine(x, y + cubeSize, xBack, yBack + cubeSize);
        g.drawLine(x + cubeSize, y + cubeSize, xBack + cubeSize, yBack + cubeSize);
    }

    public void setCubeSizeAndColor(int newSize, Color newColor) {
        this.cubeSize = newSize;
        this.cubeColor = newColor;
        repaint();
    }
}
