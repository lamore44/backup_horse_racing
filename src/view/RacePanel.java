package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import model.RaceHorse;
import model.User;
import model.RaceHistory;
import utils.UserManager;
import utils.HorseAssets;

public class RacePanel extends JPanel {
    private GameFrame gameFrame;
    private UserManager userManager;
    private static final int TRACK_LENGTH = 600;
    private static final int NUM_COMPETITORS = 5;
    private static final int LANE_HEIGHT = 80;
    
    private List<RaceHorse> horses;
    private List<Thread> raceThreads;
    private JPanel trackPanel;
    private JButton startButton;
    private JButton backButton;
    private boolean raceInProgress;
    private List<RaceHorse> finishOrder;
    
    private String[] horseNames = {"Thunder", "Lightning", "Storm", "Blaze", "Shadow"};
    private String[] colors = {"Red", "Orange", "Green", "Blue", "Purple"};
    private Color[] colorValues = {
        new Color(220, 20, 60),
        new Color(255, 140, 0),
        new Color(34, 139, 34),
        new Color(30, 144, 255),
        new Color(138, 43, 226)
    };
    
    public RacePanel(GameFrame gameFrame, UserManager userManager) {
        this.gameFrame = gameFrame;
        this.userManager = userManager;
        this.horses = new ArrayList<>();
        this.raceThreads = new ArrayList<>();
        this.finishOrder = new ArrayList<>();
        this.raceInProgress = false;
        
        setLayout(new BorderLayout());
        setBackground(new Color(139, 69, 19));
        
        initComponents();
    }
    
    private void initComponents() {
        // Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(101, 67, 33));
        JLabel titleLabel = new JLabel("HORSE RACE");
        titleLabel.setFont(new Font("roboto", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel);
        add(topPanel, BorderLayout.NORTH);
        
        // Track Panel
        trackPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawTrack(g);
                drawHorses(g);
            }
        };
        trackPanel.setPreferredSize(new Dimension(800, NUM_COMPETITORS * LANE_HEIGHT + 50));
        trackPanel.setBackground(new Color(210, 180, 140));
        add(trackPanel, BorderLayout.CENTER);
        
        // Bottom Panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(101, 67, 33));
        
        startButton = new JButton("START RACE");
        startButton.setFont(new Font("roboto", Font.BOLD, 18));
        startButton.setBackground(new Color(34, 139, 34));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> startRace());
        
        backButton = new JButton("BACK TO MENU");
        backButton.setFont(new Font("roboto", Font.BOLD, 18));
        backButton.setBackground(new Color(178, 34, 34));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            if (!raceInProgress) {
                gameFrame.showPanel("mainMenu");
            }
        });
        
        bottomPanel.add(startButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void drawTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        
        // Draw lanes
        for (int i = 0; i <= NUM_COMPETITORS; i++) {
            int y = i * LANE_HEIGHT + 25;
            g2d.setColor(Color.BLACK);
            g2d.drawLine(50, y, TRACK_LENGTH + 100, y);
        }
        
        // Draw start line
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(3));
        g2d.drawLine(50, 25, 50, NUM_COMPETITORS * LANE_HEIGHT + 25);
        
        // Draw finish line
        g2d.setColor(Color.RED);
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.WHITE);
            }
            g2d.fillRect(TRACK_LENGTH + 100, 25 + (i * (NUM_COMPETITORS * LANE_HEIGHT / 10)), 
                        10, NUM_COMPETITORS * LANE_HEIGHT / 10);
        }
        
        // Draw lane numbers
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("roboto", Font.BOLD, 14));
        for (int i = 0; i < NUM_COMPETITORS; i++) {
            int y = i * LANE_HEIGHT + 25 + LANE_HEIGHT / 2;
            g2d.drawString("Lane " + (i + 1), 5, y);
        }
    }
    
    private void drawHorses(Graphics g) {
        if (horses.isEmpty()) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("roboto", Font.BOLD, 12));
        
        for (int i = 0; i < horses.size(); i++) {
            RaceHorse horse = horses.get(i);
            int x = 50 + horse.getPosition();
            int y = i * LANE_HEIGHT + 25 + 20;
            
            // Get horse color
            Color horseColor = getColorForHorse(horse.getColor());
            
            // Draw horse image using HorseAssets
            BufferedImage horseImage = HorseAssets.createHorseImage(horseColor, 60, 50);
            g2d.drawImage(horseImage, x, y - 10, null);
            
            // Draw name
            g2d.setColor(Color.BLACK);
            g2d.drawString(horse.getName(), x, y - 15);
            
            // Mark player's horse
            if (horse.isPlayer()) {
                g2d.setColor(Color.YELLOW);
                g2d.setFont(new Font("roboto", Font.BOLD, 14));
                g2d.drawString("★ YOU", x, y + 50);
            }
        }
    }
    
    private Color getColorForHorse(String colorName) {
        for (int i = 0; i < colors.length; i++) {
            if (colors[i].equals(colorName)) {
                return colorValues[i];
            }
        }
        return Color.GRAY;
    }
    
    public void initializeRace() {
        horses.clear();
        raceThreads.clear();
        finishOrder.clear();
        
        User currentUser = gameFrame.getCurrentUser();
        
        // Add player's horse
        horses.add(new RaceHorse(
            currentUser.getHorse().getName(),
            currentUser.getHorse().getColor(),
            currentUser.getHorse().calculateRaceSpeed(),
            true
        ));
        
        // Add competitor horses
        for (int i = 1; i < NUM_COMPETITORS; i++) {
            int randomSpeed = 40 + (int)(Math.random() * 40); // Speed 40-80
            horses.add(new RaceHorse(
                horseNames[i],
                colors[i],
                randomSpeed,
                false
            ));
        }
        
        trackPanel.repaint();
    }
    
    private void startRace() {
        if (raceInProgress) return;
        
        initializeRace();
        raceInProgress = true;
        startButton.setEnabled(false);
        backButton.setEnabled(false);
        finishOrder.clear();
        
        // Create and start threads for each horse
        for (RaceHorse horse : horses) {
            Thread raceThread = new Thread(() -> {
                while (horse.getPosition() < TRACK_LENGTH && raceInProgress) {
                    horse.move();
                    
                    SwingUtilities.invokeLater(() -> trackPanel.repaint());
                    
                    if (horse.getPosition() >= TRACK_LENGTH && !horse.isFinished()) {
                        horse.setFinished(true);
                        synchronized (finishOrder) {
                            finishOrder.add(horse);
                        }
                    }
                    
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            });
            
            raceThreads.add(raceThread);
            raceThread.start();
        }
        
        // Monitor thread to check when race is complete
        new Thread(() -> {
            for (Thread thread : raceThreads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            SwingUtilities.invokeLater(() -> {
                raceInProgress = false;
                showRaceResults();
                startButton.setEnabled(true);
                backButton.setEnabled(true);
            });
        }).start();
    }
    
    private void showRaceResults() {
        User currentUser = gameFrame.getCurrentUser();
        int playerPosition = -1;
        
        // Find player's position
        for (int i = 0; i < finishOrder.size(); i++) {
            if (finishOrder.get(i).isPlayer()) {
                playerPosition = i + 1;
                break;
            }
        }
        
        // Calculate coins earned
        int coinsEarned = 0;
        if (playerPosition == 1) {
            coinsEarned = 100;
        } else if (playerPosition == 2) {
            coinsEarned = 50;
        } else if (playerPosition == 3) {
            coinsEarned = 25;
        }
        
        currentUser.addCoins(coinsEarned);
        userManager.updateUser(currentUser);
        
        // Save race history
        RaceHistory history = new RaceHistory(
            currentUser.getUserId(),
            currentUser.getHorse().getName(),
            playerPosition,
            NUM_COMPETITORS,
            coinsEarned
        );
        userManager.addRaceHistory(history);
        
        // Show results dialog
        StringBuilder results = new StringBuilder();
        results.append("RACE RESULTS\n\n");
        for (int i = 0; i < finishOrder.size(); i++) {
            RaceHorse horse = finishOrder.get(i);
            results.append(String.format("%d. %s %s\n", 
                i + 1, 
                horse.getName(),
                horse.isPlayer() ? "(YOU)" : ""));
        }
        results.append("\n");
        
        if (playerPosition == 1) {
            results.append("🏆 CONGRATULATIONS! YOU WON! 🏆\n");
        } else if (playerPosition <= 3) {
            results.append("🎉 Good race! You placed " + playerPosition + "!\n");
        } else {
            results.append("Keep practicing!\n");
        }
        
        results.append("Coins earned: " + coinsEarned);
        
        JOptionPane.showMessageDialog(this, results.toString(), 
            "Race Finished", JOptionPane.INFORMATION_MESSAGE);
        
        gameFrame.updateMainMenu();
    }
}
