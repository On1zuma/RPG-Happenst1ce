package org.main;
import javax.swing.*;

public class Main {
    public static  JFrame window;
    public static void main( String[] args )
    {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Happenst1ce - by On1zuma");

        // Set favicon
        ImageIcon icon = new ImageIcon(Main.class.getResource("/logo/favicon.png"));
        window.setIconImage(icon.getImage());

        // 1Zuna //fal1tass studio //for1tess studio
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack(); //pack allow us to fit the preferred size from the subcomponents

        gamePanel.config.loadConfig();

        window.setLocationRelativeTo(null);

        gamePanel.setupGame();
        gamePanel.playSE(6);

        gamePanel.startGameThread();

        // full screen
        if(gamePanel.setFullScreenSet ==1){
            window.setUndecorated(true);
        }

        window.setVisible(true);
    }
}
