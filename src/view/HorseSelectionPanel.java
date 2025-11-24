package view;

import javax.swing.*;
import java.awt.*;
import model.Horse;
import model.User;
import utils.UserManager;

public class HorseSelectionPanel extends JPanel {
    private GameFrame gameFrame;
    private UserManager userManager;
    private JTextField horseNameField;
    private ButtonGroup colorGroup;
    private String[] colors = {"Red", "Orange", "Green", "Blue", "Purple"};
    private Color[] colorValues = {
        new Color(220, 20, 60),
        new Color(255, 140, 0),
        new Color(34, 139, 34),
        new Color(30, 144, 255),
        new Color(138, 43, 226)
    };
    
    public HorseSelectionPanel(GameFrame gameFrame, UserManager userManager) {
        this.gameFrame = gameFrame;
        this.userManager = userManager;
        
        setLayout(new GridBagLayout());
        setBackground(new Color(139, 69, 19));
        
        initComponents();
    }
    
    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("SELECT YOUR HORSE");
        titleLabel.setFont(new Font("roboto", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        // Horse Name
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel("Horse Name:");
        nameLabel.setFont(new Font("roboto", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        add(nameLabel, gbc);
        
        gbc.gridx = 1;
        horseNameField = new JTextField(20);
        horseNameField.setFont(new Font("roboto", Font.PLAIN, 14));
        add(horseNameField, gbc);
        
        // Color Selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel colorLabel = new JLabel("Select Color:");
        colorLabel.setFont(new Font("roboto", Font.BOLD, 16));
        colorLabel.setForeground(Color.WHITE);
        add(colorLabel, gbc);
        
        gbc.gridx = 1;
        JPanel colorPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        colorPanel.setOpaque(false);
        colorGroup = new ButtonGroup();
        
        for (int i = 0; i < colors.length; i++) {
            JRadioButton colorButton = new JRadioButton(colors[i]);
            colorButton.setFont(new Font("roboto", Font.BOLD, 14));
            colorButton.setForeground(Color.WHITE);
            colorButton.setOpaque(false);
            colorButton.setActionCommand(colors[i]);
            
            // Create colored square indicator
            JPanel indicatorPanel = new JPanel();
            indicatorPanel.setBackground(colorValues[i]);
            indicatorPanel.setPreferredSize(new Dimension(20, 20));
            colorButton.setIcon(new ColorIcon(colorValues[i]));
            
            colorGroup.add(colorButton);
            colorPanel.add(colorButton);
            
            if (i == 0) {
                colorButton.setSelected(true);
            }
        }
        
        add(colorPanel, gbc);
        
        // Stats Info
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        JLabel statsLabel = new JLabel("<html><center>Starting Stats:<br>Speed: 50 | Stamina: 50 | Acceleration: 50</center></html>");
        statsLabel.setFont(new Font("roboto", Font.PLAIN, 14));
        statsLabel.setForeground(Color.YELLOW);
        add(statsLabel, gbc);
        
        // Confirm Button
        gbc.gridy = 4;
        JButton confirmButton = new JButton("Confirm Selection");
        confirmButton.setFont(new Font("roboto", Font.BOLD, 16));
        confirmButton.setBackground(new Color(34, 139, 34));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setFocusPainted(false);
        confirmButton.addActionListener(e -> handleConfirm());
        add(confirmButton, gbc);
    }
    
    private void handleConfirm() {
        String horseName = horseNameField.getText().trim();
        
        if (horseName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a horse name!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (horseName.length() < 2) {
            JOptionPane.showMessageDialog(this, "Horse name must be at least 2 characters!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String selectedColor = colorGroup.getSelection().getActionCommand();
        
        User currentUser = gameFrame.getCurrentUser();
        Horse horse = new Horse(horseName, selectedColor);
        currentUser.setHorse(horse);
        userManager.updateUser(currentUser);
        
        JOptionPane.showMessageDialog(this, 
            "Horse created successfully!\nWelcome, " + horseName + "!", 
            "Success", JOptionPane.INFORMATION_MESSAGE);
        
        gameFrame.showPanel("mainMenu");
    }
    
    // Custom icon for color selection
    private class ColorIcon implements Icon {
        private Color color;
        
        public ColorIcon(Color color) {
            this.color = color;
        }
        
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            g.setColor(color);
            g.fillRect(x, y, 16, 16);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, 16, 16);
        }
        
        @Override
        public int getIconWidth() {
            return 16;
        }
        
        @Override
        public int getIconHeight() {
            return 16;
        }
    }
}
