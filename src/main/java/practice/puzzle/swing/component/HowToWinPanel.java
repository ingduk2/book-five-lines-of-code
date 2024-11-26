package practice.puzzle.swing.component;

import javax.swing.*;
import java.awt.*;

public class HowToWinPanel extends JPanel {

    public HowToWinPanel() {
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel howToWinLabel = new HowToWinLabel();
        this.add(howToWinLabel);
    }
}
