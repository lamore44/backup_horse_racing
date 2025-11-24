package view;

import javax.swing.*;
import java.awt.*;
import model.User;
import model.Horse;

public class MainMenuPanel extends JPanel {
    private GameFrame gameFrame;
    private JLabel userInfoLabel;
    private JLabel horseInfoLabel;
    private JLabel coinsLabel;
    
    public MainMenuPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        
        setLayout(new BorderLayout());
        setBackground(new Color(139, 69, 19));
        
        initComponents();
    }
    
    private void initComponents() {
        // Top Panel - User Info
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(101, 67, 33));
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
        
        userInfoLabel = new JLabel();
        userInfoLabel.setFont(new Font("roboto", Font.BOLD, 16));
        userInfoLabel.setForeground(Color.WHITE);
        
        horseInfoLabel = new JLabel();
        horseInfoLabel.setFont(new Font("roboto", Font.BOLD, 16));
        horseInfoLabel.setForeground(Color.YELLOW);
        
        coinsLabel = new JLabel();
        coinsLabel.setFont(new Font("roboto", Font.BOLD, 16));
        coinsLabel.setForeground(new Color(255, 215, 0));
        
        topPanel.add(userInfoLabel);
        topPanel.add(horseInfoLabel);
        topPanel.add(coinsLabel);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Center Panel - Menu Buttons
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(139, 69, 19));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel("HORSE RACING GAME");
        titleLabel.setFont(new Font("roboto", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(titleLabel, gbc);
        
        // Race Button
        JButton raceButton = createMenuButton("START RACE", new Color(220, 20, 60));
        raceButton.addActionListener(e -> gameFrame.showPanel("race"));
        gbc.gridy = 1;
        centerPanel.add(raceButton, gbc);
        
        // Upgrade Button
        JButton upgradeButton = createMenuButton("UPGRADE HORSE", new Color(255, 140, 0));
        upgradeButton.addActionListener(e -> gameFrame.showPanel("upgrade"));
        gbc.gridy = 2;
        centerPanel.add(upgradeButton, gbc);
        
        // History Button
        JButton historyButton = createMenuButton("RACE HISTORY", new Color(30, 144, 255));
        historyButton.addActionListener(e -> gameFrame.showPanel("history"));
        gbc.gridy = 3;
        centerPanel.add(historyButton, gbc);
        
        // Logout Button
        JButton logoutButton = createMenuButton("LOGOUT", new Color(178, 34, 34));
        logoutButton.addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to logout?", 
                "Confirm Logout", 
                JOptionPane.YES_NO_OPTION);
            
            if (result == JOptionPane.YES_OPTION) {
                gameFrame.setCurrentUser(null);
                gameFrame.showPanel("login");
            }
        });
        gbc.gridy = 4;
        centerPanel.add(logoutButton, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private JButton createMenuButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("roboto", Font.BOLD, 20));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(300, 60));
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    public void updateUserInfo() {
        User user = gameFrame.getCurrentUser();
        if (user != null) {
            userInfoLabel.setText("Player: " + user.getUsername());
            coinsLabel.setText("💰 Coins: " + user.getCoins());
            
            Horse horse = user.getHorse();
            if (horse != null) {
                horseInfoLabel.setText("🐎 " + horse.getName() + " (Lvl " + horse.getLevel() + ")");
            }
        }
    }
}
