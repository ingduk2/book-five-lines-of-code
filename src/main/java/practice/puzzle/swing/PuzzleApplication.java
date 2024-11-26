package practice.puzzle.swing;

import practice.puzzle.original.Puzzle;
import practice.puzzle.swing.component.HowToPlayPanel;
import practice.puzzle.swing.component.HowToWinPanel;
import practice.puzzle.swing.component.PuzzleGamePanel;
import practice.puzzle.swing.component.PuzzleJFrame;

import javax.swing.*;

public class PuzzleApplication {
    public void run() {
        JFrame jFrame = PuzzleJFrame.createPuzzleJFrame();

        JPanel puzzleGamePanel = new PuzzleGamePanel();
        JPanel howToPlayPanel = new HowToPlayPanel();
        JPanel howToWinPanel = new HowToWinPanel();

        jFrame.add(puzzleGamePanel);
        jFrame.add(howToPlayPanel);
        jFrame.add(howToWinPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);

        Puzzle puzzle = new Puzzle(puzzleGamePanel, jFrame);
        puzzle.onLoad();
    }
}
