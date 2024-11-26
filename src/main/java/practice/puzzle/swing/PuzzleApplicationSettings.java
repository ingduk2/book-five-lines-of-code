package practice.puzzle.swing;

public class PuzzleApplicationSettings {
    public enum Title {
        PuzzleGUI
    }

    public enum Size {
        PUZZLE_FRAME(600, 850),
        HOW_TO_PLAY_LABEL(600, 300),
        HOW_TO_WIN_LABEL(600, 300)
        ;

        private final int width;
        private final int height;

        Size(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }
    }

    public enum Resource {
        GOAL_IMG("goal.png"),
        HOW_TO_PLAY_HTML("howToPlay.html"),
        HOW_TO_WIN_HTML("howToWin.html")
        ;

        private final String name;

        Resource(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
