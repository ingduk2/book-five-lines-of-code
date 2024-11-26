package practice.puzzle.swing.component;

import practice.puzzle.swing.util.ResourceUtil;

import javax.swing.*;
import java.awt.*;

import static practice.puzzle.swing.PuzzleApplicationSettings.Resource.HOW_TO_PLAY_HTML;
import static practice.puzzle.swing.PuzzleApplicationSettings.Size.HOW_TO_PLAY_LABEL;


public class HowToPlayLabel extends JLabel {

    public HowToPlayLabel() {
        this.setPreferredSize(new Dimension(HOW_TO_PLAY_LABEL.getWidth(), HOW_TO_PLAY_LABEL.getHeight()));

        String content = ResourceUtil.getContent(HOW_TO_PLAY_HTML.getName());
        this.setText(content);
    }
}
