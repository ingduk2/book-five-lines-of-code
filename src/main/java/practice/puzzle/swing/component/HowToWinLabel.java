package practice.puzzle.swing.component;

import practice.puzzle.swing.util.ResourceUtil;

import javax.swing.*;
import java.awt.*;

import static practice.puzzle.swing.PuzzleApplicationSettings.Resource.GOAL_IMG;
import static practice.puzzle.swing.PuzzleApplicationSettings.Resource.HOW_TO_WIN_HTML;
import static practice.puzzle.swing.PuzzleApplicationSettings.Size.HOW_TO_WIN_LABEL;

public class HowToWinLabel extends JLabel {

    public HowToWinLabel() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(HOW_TO_WIN_LABEL.getWidth(), HOW_TO_WIN_LABEL.getHeight()));

        String path = ResourceUtil.getPath(GOAL_IMG.getName());
        String content = ResourceUtil.getContent(HOW_TO_WIN_HTML.getName());
        this.setText(content.formatted(path));
    }
}
