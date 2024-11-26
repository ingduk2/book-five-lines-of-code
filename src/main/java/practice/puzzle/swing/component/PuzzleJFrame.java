package practice.puzzle.swing.component;

import practice.puzzle.swing.PuzzleApplicationSettings;

import javax.swing.*;
import java.awt.*;

import static practice.puzzle.swing.PuzzleApplicationSettings.Size.PUZZLE_FRAME;

public class PuzzleJFrame {
    public static JFrame createPuzzleJFrame() {
        JFrame jFrame = new JFrame(PuzzleApplicationSettings.Title.PuzzleGUI.name());
        jFrame.setPreferredSize(new Dimension(PUZZLE_FRAME.getWidth(), PUZZLE_FRAME.getHeight()));
        jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
        return jFrame;
    }
}
