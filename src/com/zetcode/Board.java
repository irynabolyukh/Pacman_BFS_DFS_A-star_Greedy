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
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {

    PathSearcher searcher;
    List path;

    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;
    private final Color dotColor = new Color(192, 192, 0);
    private Color mazeColor;

    private boolean inGame = false;
    private boolean dying = false;

    private final int BLOCK_SIZE = 24;
    private final int N_BLOCKS = 15;
    private final int SCREEN_SIZE = N_BLOCKS * BLOCK_SIZE;
    private final int PAC_ANIM_DELAY = 2;
    private final int PACMAN_ANIM_COUNT = 4;
    int num = 0;
    private int DOT_X;
    private int DOT_Y;

    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;
    private int score;

    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;
    private Image pacman4up, pacman4down, pacman4left, pacman4right;

    private int pacman_x, pacman_y;
    private int view_dx, view_dy;

    private final short levelData[][] = {
            {3, 10, 10, 10,  2, 10, 10, 10, 10, 10, 10, 10,  2, 10,  6},
            {5,  0,  0,  0,  5,  0,  0,  0,  0,  0,  0,  0,  5,  0,  5},
            {5,  0,  0,  0,  5,  0,  3, 10,  6,  0,  3, 10, 12,  0,  5},
            {5,  0,  0,  0,  5,  0,  5,  0,  5,  0,  5,  0,  0,  0,  5},
            {1, 10, 10, 10, 12,  0,  5,  0,  5,  0,  5,  0,  0,  0,  5},
            {5,  0,  0,  0,  0,  0,  5,  0,  5,  0,  5,  0,  0,  0,  5},
            {9,  2, 10,  2, 10, 10, 12,  0,  9, 10,  8,  6,  0,  0,  5},
            {0,  5,  0,  5,  0,  0,  0,  0,  0,  0,  0,  5,  0,  0,  5},
            {0,  5,  0,  1, 10, 10,  6,  0,  3, 10, 10,  8,  6,  0,  5},
            {0,  5,  0,  5,  0,  0,  5,  0,  5,  0,  0,  0,  5,  0,  5},
            {0,  5,  0,  5,  0,  0,  5,  0,  5,  0,  0,  0,  5,  0,  5},
            {0,  5,  0,  9, 10, 10,  8, 10, 12,  0,  0,  0,  5,  0,  5},
            {0,  5,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  9, 10,  4},
            {0,  9, 10, 10, 10, 10, 10, 10, 10, 10,  6,  0,  0,  0,  5},
            {0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  9, 10, 10, 10, 12}
    };

    private final short blocksData[][] = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 1},
            {0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
            {0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1},
            {0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
            {0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 1},
            {0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 1},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1},
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

        Random r = new Random();
        int x, y;

        do{
            x = r.nextInt(15);
            y = r.nextInt(15);
        }while(blocksData[y][x]==0);

        DOT_X = x*24;
        DOT_Y = y*24;

        levelData[y][x] +=16;

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
        drawPacman(g2d);
        checkMaze();
    }

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);

        String s = "Press B for BFS or D for DFS";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
    }

    private void drawScore(Graphics2D g) {
        String s;
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Amount of Steps: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 30, SCREEN_SIZE + 16);
    }

    private void checkMaze() {

        boolean finished = true;

        while (finished) {

            for(short i = 0; i< 15; i++){
                for(short j =0; j< 15; j++){
                    if ((screenData[i][j] & 16) != 0) {
                        finished = false;
                    }
                }
            }
        }

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
            int j = y/BLOCK_SIZE;
            i=0;

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
                    g2d.fillRect(DOT_X + 11, DOT_Y + 11, 2, 2);
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
            for(int j =0; j<N_BLOCKS; j++){
                screenData[i][j] = levelData[i][j];
            }
        }
    }


    private void loadImages() {
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

    private void moveTo(int x, int y, MyPoint.Direction d){

        switch (d){
            case LEFT: view_dx = -1;
            break;
            case RIGHT: view_dx = 1;
            break;
            case UP: view_dy = -1;
            break;
            case DOWN: view_dy = 1;
            break;
        }
            pacman_x = 24*x;
            pacman_y = 24*y;
            num++;
            score++;
    }

    public void moveTest (List<MyPoint> path, int num){

        moveTo(path.get(num).getX(),path.get(num).getY(),path.get(num).getD());

    }

    class TAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if(inGame) {

                if (key == KeyEvent.VK_ENTER) {
                    if(num < path.size()) {
                        moveTest(path, num);
                    }

                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;

                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            }
            else {
                if (key == 'b' || key == 'B') {
                    inGame = true;
                    searcher = new PathSearcherBFS();
                    chosenSearcher();
                }
                if (key == 'd' || key == 'D') {
                    inGame = true;
                    searcher = new PathSearcherDFS();
                    chosenSearcher();
                }
            }
        }

        private void chosenSearcher() {
            for (int i = 0; i < N_BLOCKS; i++) {
                for(int j =0; j<N_BLOCKS; j++){
                    searcher.screenData[i][j] = levelData[i][j];
                }
            }
            searcher.searchForPath();

            path = searcher.path;
            initGame();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}
