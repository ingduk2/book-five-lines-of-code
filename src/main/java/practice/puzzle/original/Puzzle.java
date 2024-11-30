package practice.puzzle.original;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static practice.puzzle.original.Puzzle.Tile.*;

public class Puzzle {
    private static final int TILE_SIZE = 30;
    private static final int FPS = 30;
    private static final int SLEEP = 1000 / FPS;
    private final JPanel jPanel;

    public Puzzle(JPanel jPanel, JFrame jFrame) {
        jPanel.setPreferredSize(new Dimension(map[0].length, map.length));
        this.jPanel = jPanel;
        addEventListener(jFrame);
    }

    enum Tile {
        AIR,
        FLUX,
        UNBREAKABLE,
        PLAYER,
        STONE, FALLING_STONE,
        BOX, FALLING_BOX,
        KEY1, LOCK1,
        KEY2, LOCK2
    }

    enum Input {
        UP, DOWN, LEFT, RIGHT
    }

    private int playerX = 1;
    private int playerY = 1;
    private final Tile[][] map = {
            {Tile.values()[2], Tile.values()[2], Tile.values()[2], Tile.values()[2], Tile.values()[2], Tile.values()[2], Tile.values()[2], Tile.values()[2]},
            {Tile.values()[2], Tile.values()[3], Tile.values()[0], Tile.values()[1], Tile.values()[1], Tile.values()[2], Tile.values()[0], Tile.values()[2]},
            {Tile.values()[2], Tile.values()[4], Tile.values()[2], Tile.values()[6], Tile.values()[1], Tile.values()[2], Tile.values()[0], Tile.values()[2]},
            {Tile.values()[2], Tile.values()[8], Tile.values()[4], Tile.values()[1], Tile.values()[1], Tile.values()[2], Tile.values()[0], Tile.values()[2]},
            {Tile.values()[2], Tile.values()[4], Tile.values()[1], Tile.values()[1], Tile.values()[1], Tile.values()[9], Tile.values()[0], Tile.values()[2]},
            {Tile.values()[2], Tile.values()[2], Tile.values()[2], Tile.values()[2], Tile.values()[2], Tile.values()[2], Tile.values()[2], Tile.values()[2]},
    };

    private final Stack<Input> inputs = new Stack<>();

    private void remove(Tile tile) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == tile) {
                    map[y][x] = AIR;
                }
            }
        }
    }

    private void moveToTile(int newX, int newY) {
        map[playerY][playerX] = AIR;
        map[newY][newX] = PLAYER;
        playerX = newX;
        playerY = newY;
    }

    private void moveHorizontal(int dx) {
        if (map[playerY][playerX + dx] == FLUX
                || map[playerY][playerX + dx] == AIR) {
            moveToTile(playerX + dx, playerY);
        } else if ((map[playerY][playerX + dx] == STONE
                || map[playerY][playerX + dx] == BOX)
                && map[playerY][playerX + dx + dx] == AIR
                && map[playerY + 1][playerX + dx] != AIR) {
            map[playerY][playerX + dx + dx] = map[playerY][playerX + dx];
            moveToTile(playerX + dx, playerY);
        } else if (map[playerY][playerX + dx] == KEY1) {
            remove(LOCK1);
            moveToTile(playerX + dx, playerY);
        } else if (map[playerY][playerX + dx] == KEY2) {
            remove(LOCK2);
            moveToTile(playerX + dx, playerY);
        }
    }

    public void moveVertical(int dy) {
        if (map[playerY + dy][playerX] == FLUX
                || map[playerY + dy][playerX] == AIR) {
            moveToTile(playerX, playerY + dy);
        } else if (map[playerY + dy][playerX] == KEY1) {
            remove(LOCK1);
            moveToTile(playerX, playerY + dy);
        } else if (map[playerY + dy][playerX] == KEY2) {
            remove(LOCK2);
            moveToTile(playerX, playerY + dy);
        }
    }

    private void update() {
        handleInputs();
        updateMap();
    }

    private void handleInputs() {
        while (!inputs.isEmpty()) {
            Input current = inputs.pop();
            handleInput(current);
        }
    }

    private void handleInput(Input input) {
        if (input == Input.LEFT)
            moveHorizontal(-1);
        else if (input == Input.RIGHT)
            moveHorizontal(1);
        else if (input == Input.UP)
            moveVertical(-1);
        else if (input == Input.DOWN)
            moveVertical(1);
    }

    private void updateMap() {
        for (int y = map.length - 1; y >= 0; y--) {
            for (int x = 0; x < map[y].length; x++) {
                updateTitle(y, x);
            }
        }
    }

    private void updateTitle(int y, int x) {
        if ((map[y][x] == STONE || map[y][x] == FALLING_STONE) && map[y + 1][x] == AIR) {
            map[y + 1][x] = FALLING_STONE;
            map[y][x] = AIR;
        } else if ((map[y][x] == BOX || map[y][x] == FALLING_BOX) && map[y + 1][x] == AIR) {
            map[y + 1][x] = FALLING_BOX;
            map[y][x] = AIR;
        } else if (map[y][x] == FALLING_STONE) {
            map[y][x] = STONE;
        } else if (map[y][x] == FALLING_BOX) {
            map[y][x] = BOX;
        }
    }

    private void draw() {
        Graphics g = createGraphics();
        drawMap(g);
        drawPlayer(g);
    }

    private Graphics createGraphics() {
        Graphics g = jPanel.getGraphics();
        g.clearRect(0, 0, jPanel.getWidth(), jPanel.getHeight());
        return g;
    }

    private void drawMap(Graphics g) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if (map[y][x] == FLUX)
                    g.setColor(Color.decode("#ccffcc"));
                else if (map[y][x] == UNBREAKABLE)
                    g.setColor(Color.decode("#999999"));
                else if (map[y][x] == STONE || map[y][x] == FALLING_STONE)
                    g.setColor(Color.decode("#0000cc"));
                else if (map[y][x] == BOX || map[y][x] == FALLING_BOX)
                    g.setColor(Color.decode("#8b4513"));
                else if (map[y][x] == KEY1 || map[y][x] == LOCK1)
                    g.setColor(Color.decode("#ffcc00"));
                else if (map[y][x] == KEY2 || map[y][x] == LOCK2)
                    g.setColor(Color.decode("#00ccff"));

                if (map[y][x] != AIR && map[y][x] != PLAYER)
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }

    private void drawPlayer(Graphics g) {
        g.setColor(Color.decode("#ff0000"));
        g.fillRect(playerX * TILE_SIZE, playerY * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    private void gameLoop() {
        var before = System.currentTimeMillis();
        update();
        draw();
        var after = System.currentTimeMillis();
        var frameTime = after - before;
        var sleep = SLEEP - frameTime;

        scheduledExecutorService.schedule(
                this::gameLoop,
                sleep,
                TimeUnit.MILLISECONDS
        );
    }

    public void onLoad() {
        gameLoop();
    }

    private void addEventListener(JFrame jFrame) {
        jFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) inputs.push(Input.LEFT);
                else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) inputs.push(Input.UP);
                else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) inputs.push(Input.RIGHT);
                else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) inputs.push(Input.DOWN);
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }
}