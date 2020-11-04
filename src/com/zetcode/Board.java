package com.zetcode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 20);

    private Image ii;
    private final Color dotColor = new Color(192, 192, 0);
    private Color mazeColor;

    private boolean inGame = false;
    int step = 1;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int PAC_ANIM_DELAY = 2;
    private final int PACMAN_ANIM_COUNT = 4;

    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;
    private int score;
    private int[] ghost_x, ghost_y;

    private Image ghost;
    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;
    private Image pacman4up, pacman4down, pacman4left, pacman4right;

    private int pacman_x, pacman_y;
    private int view_dx, view_dy;


    private final short levelData[][] = {
            {3, 10, 10, 10, 2, 2, 2, 2, 2, 2, 2, 2, 2, 10, 6},
            {5, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 4, 0, 5},
            {5, 0, 0, 0, 1, 0, 0, 8, 0, 0, 0, 8, 12, 0, 5},
            {5, 0, 0, 0, 1, 0, 4, 0, 1, 0, 4, 0, 0, 0, 5},
            {1, 2, 2, 2, 0, 0, 4, 0, 1, 0, 4, 0, 0, 0, 5},
            {1, 0, 0, 0, 0, 0, 4, 0, 1, 0, 4, 0, 0, 0, 5},
            {9, 0, 8, 0, 8, 8, 12, 0, 9, 8, 8, 6, 0, 0, 5},
            {0, 5, 0, 5, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 5},
            {0, 5, 0, 1, 2, 2, 6, 0, 3, 10, 10, 8, 6, 0, 5},
            {0, 5, 0, 1, 0, 0, 4, 0, 5, 0, 0, 0, 1, 2, 4},
            {0, 5, 0, 1, 0, 0, 4, 0, 5, 0, 0, 0, 1, 0, 4},
            {0, 5, 0, 9, 0, 0, 8, 10, 12, 0, 0, 0, 1, 0, 4},
            {0, 5, 0, 0, 1, 4, 0, 0, 0, 0, 0, 0, 1, 0, 4},
            {0, 9, 10, 10, 8, 8, 10, 10, 10, 10, 6, 0, 1, 0, 4},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 10, 8, 8, 12}
    };

    private final short blocksData[][] = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1},
            {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1},
            {0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1},
            {0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1},
            {0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1},
            {0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1}
    };

    private short[][] screenData;
    private Timer timer;

    public Board() {

        loadImages();
        initVariables();
        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
    }

    private void initVariables() {

        screenData = new short[N_BLOCKS][N_BLOCKS];
        mazeColor = new Color(5, 100, 5);
        d = new Dimension(400, 400);

        ghost_x = new int[2];
        ghost_y = new int[2];

        ghost_x[0] = BLOCK_SIZE * 8;
        ghost_y[0] = BLOCK_SIZE * 6;

        ghost_x[1] = BLOCK_SIZE * 6;
        ghost_y[1] = BLOCK_SIZE * 8;

        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                if (blocksData[y][x] != 0) {
                    levelData[y][x] += 16;
                }
            }
        }

        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();
        initGame();
    }

    private void doAnim() {

        pacAnimCount--;

        if (pacAnimCount <= 0) {
            pacAnimCount = PAC_ANIM_DELAY;
            pacmanAnimPos = pacmanAnimPos + pacAnimDir;

            if (pacmanAnimPos == (PACMAN_ANIM_COUNT - 1) || pacmanAnimPos == 0) {
                pacAnimDir = -pacAnimDir;
            }
        }
    }

    private void playGame(Graphics2D g2d) {

        inGame = false;
        for (short i = 0; i < 15; i++) {
            for (short j = 0; j < 15; j++) {
                if ((screenData[i][j] & 16) != 0) {
                    inGame = true;
                }
            }
        }

        for (int i = 0; i < ghost_x.length; i++) {
            if (pacman_x == ghost_x[i] && pacman_y == ghost_y[i]) {
                inGame = false;
            }
        }

        drawPacman(g2d);
        drawGhosts(g2d);
    }

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 60, SCREEN_SIZE - 100, 80);
        g2d.setColor(Color.white);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 60, SCREEN_SIZE - 100, 80);

        String s = "Press S to start";
        String s2 = "Space to move";

        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2 - metr.getHeight()/2*3);
        g2d.drawString(s2, (SCREEN_SIZE - metr.stringWidth(s2)) / 2, SCREEN_SIZE / 2);
    }

    private void drawScore(Graphics2D g) {
        String s;
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 - 150, SCREEN_SIZE + 5);
    }

    private int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x2 - x1) + Math.abs(y2 - y1);
    }

    private int pointCost(MyPoint p) {

        short ch = screenData[p.getY()][p.getX()];
        if ((ch & 16) != 0) {
            return 3;
        }
        return 0;
    }


    private void moveGhosts() {

        PointHeuristic[] next = min(pacman_x / BLOCK_SIZE, pacman_y / BLOCK_SIZE, ghost_x, ghost_y, 3);
        for (int i = 0; i < ghost_x.length; i++) {
            ghost_x[i] = next[i].getP().getX() * BLOCK_SIZE;
            ghost_y[i] = next[i].getP().getY() * BLOCK_SIZE;
        }
        step++;
    }

    private PointHeuristic[] min(int pacX, int pacY, int[] ghost_x, int[] ghost_y, int depth) {
        MyPoint crt;
        MyPoint next;
        int value;
        PointHeuristic[] res = new PointHeuristic[ghost_x.length];

        List<PointHeuristic> pointHeuristics = new ArrayList<PointHeuristic>();

        if (depth == 0) {
            return new PointHeuristic[]{new PointHeuristic(0), new PointHeuristic(0)};
        } else {

            for (int i = 0; i < ghost_x.length; i++) {
                pointHeuristics.clear();

                int[] ghost_x_next = Arrays.copyOf(ghost_x, ghost_x.length);
                int[] ghost_y_next = Arrays.copyOf(ghost_y, ghost_y.length);

                crt = new MyPoint(ghost_x[i] / BLOCK_SIZE, ghost_y[i] / BLOCK_SIZE, MyPoint.Direction.STILL);

                next = crt.moveLeft();
                if (canMoveLeft(ghost_x[i], ghost_y[i])) {
                    ghost_x_next[i] = next.getX() * BLOCK_SIZE;
                    ghost_y_next[i] = next.getY() * BLOCK_SIZE;
                    value = manhattanDistance(next.getX(), next.getY(), pacX, pacY);
                    pointHeuristics.add(new PointHeuristic(next, value));
                }

                next = crt.moveUp();
                if (canMoveUp(ghost_x[i], ghost_y[i])) {
                    ghost_x_next[i] = next.getX() * BLOCK_SIZE;
                    ghost_y_next[i] = next.getY() * BLOCK_SIZE;
                    value = manhattanDistance(next.getX(), next.getY(), pacX, pacY);
                    pointHeuristics.add(new PointHeuristic(next, value));
                }

                next = crt.moveRight();
                if (canMoveRight(ghost_x[i], ghost_y[i])) {
                    ghost_x_next[i] = next.getX() * BLOCK_SIZE;
                    ghost_y_next[i] = next.getY() * BLOCK_SIZE;
                    value = manhattanDistance(next.getX(), next.getY(), pacX, pacY);
                    pointHeuristics.add(new PointHeuristic(next, value));
                }

                next = crt.moveDown();
                if (canMoveDown(ghost_x[i], ghost_y[i])) {
                    ghost_x_next[i] = next.getX() * BLOCK_SIZE;
                    ghost_y_next[i] = next.getY() * BLOCK_SIZE;
                    value = manhattanDistance(next.getX(), next.getY(), pacX, pacY);
                    pointHeuristics.add(new PointHeuristic(next, value));
                }

                Collections.sort(pointHeuristics);

                Random r = new Random();
                int ran = r.nextInt(2);

                res[i] = pointHeuristics.get(ran);
            }

        }
        return res;
    }

    private PointHeuristic max(MyPoint pacCords, int[] ghost_x, int[] ghost_y, int depth) {

        MyPoint next;
        int distTo0, distTo1;
        List<PointHeuristic> pointHeuristics = new ArrayList<PointHeuristic>();
        int gh_x0 = ghost_x[0];
        int gh_y0 = ghost_y[0];
        int gh_x1 = ghost_x[1];
        int gh_y1 = ghost_y[1];
        int value;
        PointHeuristic[] ghosts_next;

        if (depth == 0) {
            return new PointHeuristic(0);
        } else {

            next = pacCords.moveLeft();
            if (canMoveLeft(pacCords.getX() * BLOCK_SIZE, pacCords.getY() * BLOCK_SIZE) && notGhost(next.getX(), next.getY())) {
                distTo0 = manhattanDistance(next.getX(), next.getY(), gh_x0 / BLOCK_SIZE, gh_y0 / BLOCK_SIZE);
                distTo1 = manhattanDistance(next.getX(), next.getY(), gh_x1 / BLOCK_SIZE, gh_y1 / BLOCK_SIZE);
                ghosts_next = min(next.getX(), next.getY(), ghost_x, ghost_y, 1);

                if (ghosts_next[0].getP().equals(next) || ghosts_next[1].getP().equals(next)) {
                    pointHeuristics.add(new PointHeuristic(next, -20));
                }else {
                    value = pointCost(next) + ((distTo0 < distTo1) ? distTo0 : distTo1) +
                            max(next, new int[]{BLOCK_SIZE * ghosts_next[0].getP().getX(), BLOCK_SIZE * ghosts_next[1].getP().getX()},
                                    new int[]{BLOCK_SIZE * ghosts_next[0].getP().getY(), BLOCK_SIZE * ghosts_next[1].getP().getY()}, depth - 1).getValue();
                    pointHeuristics.add(new PointHeuristic(next, value));
                }
            }

            next = pacCords.moveUp();
            if (canMoveUp(pacCords.getX() * BLOCK_SIZE, pacCords.getY() * BLOCK_SIZE) && notGhost(next.getX(), next.getY())) {
                distTo0 = manhattanDistance(next.getX(), next.getY(), gh_x0 / BLOCK_SIZE, gh_y0 / BLOCK_SIZE);
                distTo1 = manhattanDistance(next.getX(), next.getY(), gh_x1 / BLOCK_SIZE, gh_y1 / BLOCK_SIZE);
                ghosts_next = min(next.getX(), next.getY(), ghost_x, ghost_y, 1);

                if (ghosts_next[0].getP().equals(next) || ghosts_next[1].getP().equals(next)) {
                    pointHeuristics.add(new PointHeuristic(next, -20));
                }else {
                    value = pointCost(next) + ((distTo0 < distTo1) ? distTo0 : distTo1) +
                            max(next, new int[]{BLOCK_SIZE * ghosts_next[0].getP().getX(), BLOCK_SIZE * ghosts_next[1].getP().getX()},
                                    new int[]{BLOCK_SIZE * ghosts_next[0].getP().getY(), BLOCK_SIZE * ghosts_next[1].getP().getY()}, depth - 1).getValue();
                    pointHeuristics.add(new PointHeuristic(next, value));
                }
            }

            next = pacCords.moveRight();
            if (canMoveRight(pacCords.getX() * BLOCK_SIZE, pacCords.getY() * BLOCK_SIZE) && notGhost(next.getX(), next.getY())) {
                distTo0 = manhattanDistance(next.getX(), next.getY(), gh_x0 / BLOCK_SIZE, gh_y0 / BLOCK_SIZE);
                distTo1 = manhattanDistance(next.getX(), next.getY(), gh_x1 / BLOCK_SIZE, gh_y1 / BLOCK_SIZE);
                ghosts_next = min(next.getX(), next.getY(), ghost_x, ghost_y, 1);

                if (ghosts_next[0].getP().equals(next) || ghosts_next[1].getP().equals(next)) {
                    pointHeuristics.add(new PointHeuristic(next, -20));
                }else {
                    value = pointCost(next) + ((distTo0 < distTo1) ? distTo0 : distTo1) +
                            max(next, new int[]{BLOCK_SIZE * ghosts_next[0].getP().getX(), BLOCK_SIZE * ghosts_next[1].getP().getX()},
                                    new int[]{BLOCK_SIZE * ghosts_next[0].getP().getY(), BLOCK_SIZE * ghosts_next[1].getP().getY()}, depth - 1).getValue();
                    pointHeuristics.add(new PointHeuristic(next, value));
                }
            }

            next = pacCords.moveDown();
            if (canMoveDown(pacCords.getX() * BLOCK_SIZE, pacCords.getY() * BLOCK_SIZE) && notGhost(next.getX(), next.getY())) {
                distTo0 = manhattanDistance(next.getX(), next.getY(), gh_x0 / BLOCK_SIZE, gh_y0 / BLOCK_SIZE);
                distTo1 = manhattanDistance(next.getX(), next.getY(), gh_x1 / BLOCK_SIZE, gh_y1 / BLOCK_SIZE);
                ghosts_next = min(next.getX(), next.getY(), ghost_x, ghost_y, 1);

                if (ghosts_next[0].getP().equals(next) || ghosts_next[1].getP().equals(next)) {
                    pointHeuristics.add(new PointHeuristic(next, -20));
                }else {
                    value = pointCost(next) + ((distTo0 < distTo1) ? distTo0 : distTo1) +
                            max(next, new int[]{BLOCK_SIZE * ghosts_next[0].getP().getX(), BLOCK_SIZE * ghosts_next[1].getP().getX()},
                                    new int[]{BLOCK_SIZE * ghosts_next[0].getP().getY(), BLOCK_SIZE * ghosts_next[1].getP().getY()}, depth - 1).getValue();
                    pointHeuristics.add(new PointHeuristic(next, value));
                }
            }

            Collections.sort(pointHeuristics, Collections.reverseOrder());

            return pointHeuristics.get(0);
        }
    }

    private void movePacman() {

        MyPoint crt, next, moveTo;
        int distTo0, distTo1;
        List<PointHeuristic> pointHeuristics = new ArrayList<PointHeuristic>();
        int gh_x0 = ghost_x[0];
        int gh_y0 = ghost_y[0];
        int gh_x1 = ghost_x[1];
        int gh_y1 = ghost_y[1];

        crt = new MyPoint(pacman_x / BLOCK_SIZE, pacman_y / BLOCK_SIZE, MyPoint.Direction.STILL);

        distTo0 = manhattanDistance(crt.getX(), crt.getY(), gh_x0 / BLOCK_SIZE, gh_y0 / BLOCK_SIZE);
        distTo1 = manhattanDistance(crt.getX(), crt.getY(), gh_x1 / BLOCK_SIZE, gh_y1 / BLOCK_SIZE);

        if (distTo0 > 6 && distTo1 > 6) {
            next = crt.moveLeft();
            if (canMoveLeft(pacman_x, pacman_y) && notGhost(next.getX(), next.getY())) {
                pointHeuristics.add(new PointHeuristic(next, pointCost(next)));
            }

            next = crt.moveUp();
            if (canMoveUp(pacman_x, pacman_y) && notGhost(next.getX(), next.getY())) {
                pointHeuristics.add(new PointHeuristic(next, pointCost(next)));
            }

            next = crt.moveRight();
            if (canMoveRight(pacman_x, pacman_y) && notGhost(next.getX(), next.getY())) {
                pointHeuristics.add(new PointHeuristic(next, pointCost(next)));
            }

            next = crt.moveDown();
            if (canMoveDown(pacman_x, pacman_y) && notGhost(next.getX(), next.getY())) {
                pointHeuristics.add(new PointHeuristic(next, pointCost(next)));
            }

            Collections.sort(pointHeuristics, Collections.reverseOrder());
            moveTo = pointHeuristics.get(0).getP();
        } else {
            moveTo = max(crt, ghost_x, ghost_y, 3).getP();
        }

        moveTo(moveTo.getX(), moveTo.getY(), moveTo.getD());

        step++;
    }

    private void moveTo(int x, int y, MyPoint.Direction d) {

        isGoalReached();

        switch (d) {
            case LEFT:
                view_dx = -1;
                break;
            case RIGHT:
                view_dx = 1;
                break;
            case UP:
                view_dy = -1;
                break;
            case DOWN:
                view_dy = 1;
                break;
        }
        pacman_x = BLOCK_SIZE * x;
        pacman_y = BLOCK_SIZE * y;

    }


    private void isGoalReached() {
        int pos_i, pos_j;
        short ch;
        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
            pos_i = pacman_x / BLOCK_SIZE;
            pos_j = pacman_y / BLOCK_SIZE;
            ch = screenData[pos_j][pos_i];

            if ((ch & 16) != 0) {
                screenData[pos_j][pos_i] = (short) (ch & 15);
                score++;
            }
        }
    }

    private boolean canMoveLeft(int cord_x, int cord_y) {
        int pos_i, pos_j;
        short ch;
        pos_i = cord_x / BLOCK_SIZE;
        pos_j = cord_y / BLOCK_SIZE;
        ch = screenData[pos_j][pos_i];

        return ((ch & 1) == 0);
    }

    private boolean canMoveUp(int cord_x, int cord_y) {
        int pos_i, pos_j;
        short ch;
        pos_i = cord_x / BLOCK_SIZE;
        pos_j = cord_y / BLOCK_SIZE;
        ch = screenData[pos_j][pos_i];

        return ((ch & 2) == 0);
    }

    private boolean canMoveRight(int cord_x, int cord_y) {
        int pos_i, pos_j;
        short ch;
        pos_i = cord_x / BLOCK_SIZE;
        pos_j = cord_y / BLOCK_SIZE;
        ch = screenData[pos_j][pos_i];

        return ((ch & 4) == 0);
    }

    private boolean canMoveDown(int cord_x, int cord_y) {
        int pos_i, pos_j;
        short ch;
        pos_i = cord_x / BLOCK_SIZE;
        pos_j = cord_y / BLOCK_SIZE;
        ch = screenData[pos_j][pos_i];

        return ((ch & 8) == 0);
    }

    private boolean notGhost(int cord_x, int cord_y) {
        for (int i = 0; i < ghost_x.length; i++) {
            if (cord_x == ghost_x[i] && cord_y == ghost_y[i]) {
                return false;
            }
        }
        return true;
    }

    private void drawPacman(Graphics2D g2d) {

        if (view_dx == -1) {
            drawPacmanLeft(g2d);
        } else if (view_dx == 1) {
            drawPacmanRight(g2d);
        } else if (view_dy == -1) {
            drawPacmanUp(g2d);
        } else {
            drawPacmanDown(g2d);
        }
    }

    private void drawGhosts(Graphics2D g2d) {
        g2d.drawImage(ghost, ghost_x[0] - 3, ghost_y[0] - 3, this);
        g2d.drawImage(ghost, ghost_x[1] + 3, ghost_y[1] + 3, this);
    }

    private void drawPacmanUp(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2up, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3up, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4up, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanDown(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2down, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3down, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4down, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanLeft(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2left, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3left, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4left, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawPacmanRight(Graphics2D g2d) {

        switch (pacmanAnimPos) {
            case 1:
                g2d.drawImage(pacman2right, pacman_x + 1, pacman_y + 1, this);
                break;
            case 2:
                g2d.drawImage(pacman3right, pacman_x + 1, pacman_y + 1, this);
                break;
            case 3:
                g2d.drawImage(pacman4right, pacman_x + 1, pacman_y + 1, this);
                break;
            default:
                g2d.drawImage(pacman1, pacman_x + 1, pacman_y + 1, this);
                break;
        }
    }

    private void drawMaze(Graphics2D g2d) {

        short i;
        int x, y;

        for (y = 0; y < SCREEN_SIZE; y += BLOCK_SIZE) {
            int j = y / BLOCK_SIZE;
            i = 0;

            for (x = 0; x < SCREEN_SIZE; x += BLOCK_SIZE) {


                g2d.setColor(mazeColor);
                g2d.setStroke(new BasicStroke(2));

                if ((screenData[j][i] & 1) != 0) {
                    g2d.drawLine(x, y, x, y + BLOCK_SIZE - 1);
                }

                if ((screenData[j][i] & 2) != 0) {
                    g2d.drawLine(x, y, x + BLOCK_SIZE - 1, y);
                }

                if ((screenData[j][i] & 4) != 0) {
                    g2d.drawLine(x + BLOCK_SIZE - 1, y, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[j][i] & 8) != 0) {
                    g2d.drawLine(x, y + BLOCK_SIZE - 1, x + BLOCK_SIZE - 1,
                            y + BLOCK_SIZE - 1);
                }

                if ((screenData[j][i] & 16) != 0) {
                    g2d.setColor(dotColor);
                    g2d.fillRect(x + 11, y + 11, 2, 2);
                }

                i++;
            }
        }

    }

    private void initGame() {
        score = 0;
        initLevel();
    }

    private void initLevel() {
        for (int i = 0; i < N_BLOCKS; i++) {
            for (int j = 0; j < N_BLOCKS; j++) {
                screenData[i][j] = levelData[i][j];
            }
        }
        continueLevel();
    }

    private void continueLevel() {

        ghost_x[0] = BLOCK_SIZE * 8;
        ghost_y[0] = BLOCK_SIZE * 6;

        ghost_x[1] = BLOCK_SIZE * 6;
        ghost_y[1] = BLOCK_SIZE * 8;

        pacman_x = 0;
        pacman_y = 0;
        view_dx = -1;
        view_dy = 0;
    }

    private void loadImages() {

        ghost = new ImageIcon("src/resources/images/ghost.png").getImage();
        pacman1 = new ImageIcon("src/resources/images/pacman.png").getImage();
        pacman2up = new ImageIcon("src/resources/images/up1.png").getImage();
        pacman3up = new ImageIcon("src/resources/images/up2.png").getImage();
        pacman4up = new ImageIcon("src/resources/images/up3.png").getImage();
        pacman2down = new ImageIcon("src/resources/images/down1.png").getImage();
        pacman3down = new ImageIcon("src/resources/images/down2.png").getImage();
        pacman4down = new ImageIcon("src/resources/images/down3.png").getImage();
        pacman2left = new ImageIcon("src/resources/images/left1.png").getImage();
        pacman3left = new ImageIcon("src/resources/images/left2.png").getImage();
        pacman4left = new ImageIcon("src/resources/images/left3.png").getImage();
        pacman2right = new ImageIcon("src/resources/images/right1.png").getImage();
        pacman3right = new ImageIcon("src/resources/images/right2.png").getImage();
        pacman4right = new ImageIcon("src/resources/images/right3.png").getImage();

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, d.width, d.height);

        drawMaze(g2d);
        drawScore(g2d);
        doAnim();

        if (inGame) {
            playGame(g2d);
        } else {
            showIntroScreen(g2d);
        }

        g2d.drawImage(ii, 5, 5, this);
        Toolkit.getDefaultToolkit().sync();
        g2d.dispose();
    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (inGame) {
                if (key == KeyEvent.VK_SPACE) {
                    if (step % 2 == 0) {
                        moveGhosts();
                    } else {
                        movePacman();
                    }
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                }
            } else {
                if (key == 's' || key == 'S') {
                    inGame = true;
                    initGame();
                }
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }


}