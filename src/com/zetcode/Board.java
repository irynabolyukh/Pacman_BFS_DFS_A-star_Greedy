package com.zetcode;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Board extends JPanel implements ActionListener {



    PathSearcher searcher;
    List path;


//    ArrayList<Integer> path;
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
    private int DOT_X;
    private int DOT_Y;
    private boolean CAN_MOVE = false;
    //    private final int MAX_GHOSTS = 12;
    private final int PACMAN_SPEED = 6;

    private int pacAnimCount = PAC_ANIM_DELAY;
    private int pacAnimDir = 1;
    private int pacmanAnimPos = 0;
    //    private int N_GHOSTS = 6;
    private int pacsLeft, score;
    private String gameover;
    private int[] dx, dy;
//    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;

    //    private Image ghost;
    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;
    private Image pacman4up, pacman4down, pacman4left, pacman4right;

    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int req_dx, req_dy, view_dx, view_dy;

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
            {0,  5,  0,  0,  0,  0,  0,  0,  0,  0,  0,  0,  9,  5,  4},
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
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1}
    };

    private final int validSpeeds[] = {1, 2, 3, 4, 6, 8};
    private final int maxSpeed = 6;

    private int currentSpeed = 3;
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

        dx = new int[4];
        dy = new int[4];

        int x, y;

        Random r = new Random();

        do{
            x = r.nextInt(15);
            y = r.nextInt(15);
        }while(blocksData[y][x]==0);

        DOT_X = x*24;
        DOT_Y = y*24;

        levelData[y][x] +=16;

////        searcher = new PathSearcherBFS();
//
//        for (int i = 0; i < N_BLOCKS; i++) {
//            for(int j =0; j<N_BLOCKS; j++){
//                searcher.screenData[i][j] = levelData[i][j];
//            }
//        }
//        searcher.searchForPath();
//
//        path = searcher.path;

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
        drawInfo(g2d);
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

        int i;
        String s;

        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Amount of Steps: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 30, SCREEN_SIZE + 16);

//            for (i = 0; i < pacsLeft; i++) {
//            g.drawImage(pacman3left, i * 28 + 8, SCREEN_SIZE + 1, this);
//        }
    }
    private void drawInfo(Graphics2D g) {
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        gameover = "";
        g.drawString(gameover, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);
    }

    private void checkMaze() {

//        short i = 0;
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

        if (finished) {

            score += 50;

            initLevel();
        }
    }

    int num = 0;

    private void moveLeft(){
        isGoalReached();
        if(canMoveLeft()){
            pacman_x -=24;
            view_dx = -1;
            num++;
        }
    }
    private void moveRight(){
        isGoalReached();
        if(canMoveRight()){
            pacman_x +=24;
            view_dx = 1;
            num++;
        }
    }
    private void moveUp(){
        isGoalReached();
        if(canMoveUp()){
            pacman_y -=24;
            view_dy = -1;
            num++;
        }
    }
    private void moveDown(){
        isGoalReached();
        if(canMoveDown()){
            pacman_y += 24;
            view_dy = 1;
            num++;
        }
    }

    private void isGoalReached(){
        int pos_i, pos_j;
        short ch;
        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
            pos_i = pacman_x / BLOCK_SIZE;
            pos_j = pacman_y / BLOCK_SIZE;
            ch = screenData[pos_j][pos_i];

            if ((ch & 16) != 0) {
                screenData[pos_j][pos_i] = (short) (ch & 15);

            }
        }
    }

    private boolean canMoveLeft(){
        int pos_i, pos_j;
        short ch;
        pos_i = pacman_x / BLOCK_SIZE;
        pos_j = pacman_y / BLOCK_SIZE;
        ch = screenData[pos_j][pos_i];
        return ((ch & 1) == 0);
    }
    private boolean canMoveUp(){
        int pos_i, pos_j;
        short ch;
        pos_i = pacman_x / BLOCK_SIZE;
        pos_j = pacman_y / BLOCK_SIZE;
        ch = screenData[pos_j][pos_i];
        return ((ch & 2) == 0);
    }
    private boolean canMoveRight(){
        int pos_i, pos_j;
        short ch;
        pos_i = pacman_x / BLOCK_SIZE;
        pos_j = pacman_y / BLOCK_SIZE;
        ch = screenData[pos_j][pos_i];
        return ((ch & 4) == 0);
    }
    private boolean canMoveDown(){
        int pos_i, pos_j;
        short ch;
        pos_i = pacman_x / BLOCK_SIZE;
        pos_j = pacman_y / BLOCK_SIZE;
        ch = screenData[pos_j][pos_i];
        return ((ch & 8) == 0);
    }
//    private void movePacman() {
//
//        int pos_i, pos_j;
//
//        short ch;
//
//        if (req_dx == -pacmand_x && req_dy == -pacmand_y) {
//            pacmand_x = req_dx;
//            pacmand_y = req_dy;
//            view_dx = pacmand_x;
//            view_dy = pacmand_y;
//        }
//
//        if (pacman_x % BLOCK_SIZE == 0 && pacman_y % BLOCK_SIZE == 0) {
//            pos_i = pacman_x / BLOCK_SIZE;
//            pos_j = pacman_y / BLOCK_SIZE;
//            ch = screenData[pos_j][pos_i];
//
//            if ((ch & 16) != 0) {
//                screenData[pos_j][pos_i] = (short) (ch & 15);
//                score++;
//            }
//
//            if (req_dx != 0 || req_dy != 0) {
//                if (!((req_dx == -1 && req_dy == 0 && (ch & 1) != 0)
//                        || (req_dx == 1 && req_dy == 0 && (ch & 4) != 0)
//                        || (req_dx == 0 && req_dy == -1 && (ch & 2) != 0)
//                        || (req_dx == 0 && req_dy == 1 && (ch & 8) != 0))) {
//                    pacmand_x = req_dx;
//                    pacmand_y = req_dy;
//                    view_dx = pacmand_x;
//                    view_dy = pacmand_y;
//                }
//            }
//
//            // Check for standstill
//            if ((pacmand_x == -1 && pacmand_y == 0 && (ch & 1) != 0)
//                    || (pacmand_x == 1 && pacmand_y == 0 && (ch & 4) != 0)
//                    || (pacmand_x == 0 && pacmand_y == -1 && (ch & 2) != 0)
//                    || (pacmand_x == 0 && pacmand_y == 1 && (ch & 8) != 0)) {
//                pacmand_x = 0;
//                pacmand_y = 0;
//            }
//        }
//        pacman_x = pacman_x + PACMAN_SPEED * pacmand_x;
//        pacman_y = pacman_y + PACMAN_SPEED * pacmand_y;
//    }

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

//        pacsLeft = 3;
        score = 0;
        initLevel();
//        N_GHOSTS = 6;
        currentSpeed = 3;
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
        drawInfo(g2d);
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
                if (key == 'g' || key == 'G') {
                    inGame = true;
                    searcher = new PathSearcherGreedy();
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

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
                req_dx = 0;
                req_dy = 0;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }
}
