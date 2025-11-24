package view;

import javax.swing.*;
import java.awt.*;
import model.User;
import model.Horse;
import utils.UserManager;

public class UpgradePanel extends JPanel {
    private GameFrame gameFrame;
    private UserManager userManager;
    private JLabel coinsLabel;
    private JLabel speedLabel;
    private JLabel staminaLabel;
    private JLabel accelerationLabel;
    private JLabel levelLabel;
    
    private static final int UPGRADE_COST = 50;
    private static final int UPGRADE_AMOUNT = 10;
    
    public UpgradePanel(GameFrame gameFrame, UserManager userManager) {
        this.gameFrame = gameFrame;
        this.userManager = userManager;
        
        setLayout(new BorderLayout());
        setBackground(new Color(139, 69, 19));
        
        initComponents();
    }
    
    private void initComponents() {
        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(101, 67, 33));
        JLabel titleLabel = new JLabel("UPGRADE YOUR HORSE");
        titleLabel.setFont(new Font("roboto", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);
        
        // Center Panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(139, 69, 19));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 20, 15, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Coins Display
        coinsLabel = new JLabel();
        coinsLabel.setFont(new Font("roboto", Font.BOLD, 20));
        coinsLabel.setForeground(new Color(255, 215, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        centerPanel.add(coinsLabel, gbc);
        
        // Level Display
        levelLabel = new JLabel();
        levelLabel.setFont(new Font("roboto", Font.BOLD, 18));
        levelLabel.setForeground(Color.CYAN);
        gbc.gridy = 1;
        centerPanel.add(levelLabel, gbc);
        
        // Info Label
        JLabel infoLabel = new JLabel("Upgrade Cost: " + UPGRADE_COST + " coins each");
        infoLabel.setFont(new Font("roboto", Font.PLAIN, 14));
        infoLabel.setForeground(Color.YELLOW);
        gbc.gridy = 2;
        centerPanel.add(infoLabel, gbc);
        
        // Speed Upgrade
        gbc.gridwidth = 1;
        gbc.gridy = 3;
        speedLabel = new JLabel();
        speedLabel.setFont(new Font("roboto", Font.BOLD, 16));
        speedLabel.setForeground(Color.WHITE);
        centerPanel.add(speedLabel, gbc);
        
        gbc.gridx = 1;
        JProgressBar speedBar = new JProgressBar(0, 200);
        speedBar.setPreferredSize(new Dimension(200, 25));
        speedBar.setStringPainted(true);
        speedBar.setName("speedBar");
        centerPanel.add(speedBar, gbc);
        
        gbc.gridx = 2;
        JButton upgradeSpeedBtn = createUpgradeButton("Upgrade Speed");
        upgradeSpeedBtn.addActionListener(e -> upgradeSpeed());
        centerPanel.add(upgradeSpeedBtn, gbc);
        
        // Stamina Upgrade
        gbc.gridx = 0;
        gbc.gridy = 4;
        staminaLabel = new JLabel();
        staminaLabel.setFont(new Font("roboto", Font.BOLD, 16));
        staminaLabel.setForeground(Color.WHITE);
        centerPanel.add(staminaLabel, gbc);
        
        gbc.gridx = 1;
        JProgressBar staminaBar = new JProgressBar(0, 200);
        staminaBar.setPreferredSize(new Dimension(200, 25));
        staminaBar.setStringPainted(true);
        staminaBar.setName("staminaBar");
        centerPanel.add(staminaBar, gbc);
        
        gbc.gridx = 2;
        JButton upgradeStaminaBtn = createUpgradeButton("Upgrade Stamina");
        upgradeStaminaBtn.addActionListener(e -> upgradeStamina());
        centerPanel.add(upgradeStaminaBtn, gbc);
        
        // Acceleration Upgrade
        gbc.gridx = 0;
        gbc.gridy = 5;
        accelerationLabel = new JLabel();
        accelerationLabel.setFont(new Font("roboto", Font.BOLD, 16));
        accelerationLabel.setForeground(Color.WHITE);
        centerPanel.add(accelerationLabel, gbc);
        
        gbc.gridx = 1;
        JProgressBar accelerationBar = new JProgressBar(0, 200);
        accelerationBar.setPreferredSize(new Dimension(200, 25));
        accelerationBar.setStringPainted(true);
        accelerationBar.setName("accelerationBar");
        centerPanel.add(accelerationBar, gbc);
        
        gbc.gridx = 2;
        JButton upgradeAccelerationBtn = createUpgradeButton("Upgrade Acceleration");
        upgradeAccelerationBtn.addActionListener(e -> upgradeAcceleration());
        centerPanel.add(upgradeAccelerationBtn, gbc);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(101, 67, 33));
        
        JButton backButton = new JButton("BACK TO MENU");
        backButton.setFont(new Font("roboto", Font.BOLD, 18));
        backButton.setBackground(new Color(178, 34, 34));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> gameFrame.showPanel("mainMenu"));
        
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JButton createUpgradeButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("roboto", Font.BOLD, 14));
        button.setBackground(new Color(34, 139, 34));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(180, 30));
        return button;
    }
    
    public void updateStats() {
        User currentUser = gameFrame.getCurrentUser();
        if (currentUser == null || currentUser.getHorse() == null) return;
        
        Horse horse = currentUser.getHorse();
        
        coinsLabel.setText("💰 Available Coins: " + currentUser.getCoins());
        levelLabel.setText("🐎 " + horse.getName() + " - Level " + horse.getLevel());
        speedLabel.setText("Speed: " + horse.getSpeed());
        staminaLabel.setText("Stamina: " + horse.getStamina());
        accelerationLabel.setText("Acceleration: " + horse.getAcceleration());
        
        // Update progress bars
        Component[] components = ((JPanel)getComponent(1)).getComponents();
        for (Component comp : components) {
            if (comp instanceof JProgressBar) {
                JProgressBar bar = (JProgressBar) comp;
                if (bar.getName() != null) {
                    if (bar.getName().equals("speedBar")) {
                        bar.setValue(horse.getSpeed());
                    } else if (bar.getName().equals("staminaBar")) {
                        bar.setValue(horse.getStamina());
                    } else if (bar.getName().equals("accelerationBar")) {
                        bar.setValue(horse.getAcceleration());
                    }
                }
            }
        }
    }
    
    private void upgradeSpeed() {
        User currentUser = gameFrame.getCurrentUser();
        if (currentUser.getCoins() >= UPGRADE_COST) {
            if (currentUser.spendCoins(UPGRADE_COST)) {
                currentUser.getHorse().upgradeSpeed(UPGRADE_AMOUNT);
                checkLevelUp(currentUser.getHorse());
                userManager.updateUser(currentUser);
                updateStats();
                gameFrame.updateMainMenu();
                JOptionPane.showMessageDialog(this, "Speed upgraded successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Not enough coins!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void upgradeStamina() {
        User currentUser = gameFrame.getCurrentUser();
        if (currentUser.getCoins() >= UPGRADE_COST) {
            if (currentUser.spendCoins(UPGRADE_COST)) {
                currentUser.getHorse().upgradeStamina(UPGRADE_AMOUNT);
                checkLevelUp(currentUser.getHorse());
                userManager.updateUser(currentUser);
                updateStats();
                gameFrame.updateMainMenu();
                JOptionPane.showMessageDialog(this, "Stamina upgraded successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Not enough coins!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void upgradeAcceleration() {
        User currentUser = gameFrame.getCurrentUser();
        if (currentUser.getCoins() >= UPGRADE_COST) {
            if (currentUser.spendCoins(UPGRADE_COST)) {
                currentUser.getHorse().upgradeAcceleration(UPGRADE_AMOUNT);
                checkLevelUp(currentUser.getHorse());
                userManager.updateUser(currentUser);
                updateStats();
                gameFrame.updateMainMenu();
                JOptionPane.showMessageDialog(this, "Acceleration upgraded successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Not enough coins!", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void checkLevelUp(Horse horse) {
        int totalStats = horse.getSpeed() + horse.getStamina() + horse.getAcceleration();
        int expectedLevel = (totalStats - 150) / 30 + 1;
        
        if (expectedLevel > horse.getLevel()) {
            horse.levelUp();
            JOptionPane.showMessageDialog(this, 
                "🎉 Congratulations! Your horse reached Level " + horse.getLevel() + "!", 
                "Level Up!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
