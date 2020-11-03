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
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

    private Dimension d;
    private final Font smallFont = new Font("Helvetica", Font.BOLD, 14);

    private Image ii;
    private final Color dotColor = new Color(192, 192, 0);
    private Color mazeColor;

    private boolean inGame = false;
    private boolean dying = false;

    private final int STARS_AMOUNT = 1;

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
    private int[] ghost_x, ghost_y, ghost_dx, ghost_dy, ghostSpeed;

    private Image ghost;
    private Image pacman1, pacman2up, pacman2left, pacman2right, pacman2down;
    private Image pacman3up, pacman3down, pacman3left, pacman3right;
    private Image pacman4up, pacman4down, pacman4left, pacman4right;

    //   private int ghost_x, ghost_y;
    private int pacman_x, pacman_y, pacmand_x, pacmand_y;
    private int view_dx, view_dy;

    //    private final short levelData[] = {
//            19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
//            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
//            21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
//            21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
//            17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
//            17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
//            25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
//            1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
//            1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
//            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
//            1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
//            1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
//            1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
//            1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
//            9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28
//    };
    private final short levelData[][] = {
            {3, 10, 10, 10, 2, 10, 10, 10, 10, 10, 10, 10, 2, 10, 6},
            {5, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 5, 0, 5},
            {5, 0, 0, 0, 5, 0, 3, 10, 6, 0, 3, 10, 12, 0, 5},
            {5, 0, 0, 0, 5, 0, 5, 0, 5, 0, 5, 0, 0, 0, 5},
            {1, 10, 10, 10, 12, 0, 5, 0, 5, 0, 5, 0, 0, 0, 5},
            {5, 0, 0, 0, 0, 0, 5, 0, 5, 0, 5, 0, 0, 0, 5},
            {9, 2, 10, 2, 10, 10, 12, 0, 9, 10, 8, 6, 0, 0, 5},
            {0, 5, 0, 5, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 5},
            {0, 5, 0, 1, 10, 10, 6, 0, 3, 10, 10, 8, 6, 0, 5},
            {0, 5, 0, 5, 0, 0, 5, 0, 5, 0, 0, 0, 5, 0, 5},
            {0, 5, 0, 5, 0, 0, 5, 0, 5, 0, 0, 0, 5, 0, 5},
            {0, 5, 0, 9, 10, 10, 8, 10, 12, 0, 0, 0, 5, 0, 5},
            {0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 0, 5},
            {0, 9, 10, 10, 10, 10, 10, 10, 10, 10, 6, 0, 0, 0, 5},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 10, 10, 10, 12}
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

        ghost_x = new int[2];
        ghost_y = new int[2];

        ghost_x[0] = 24 * 8;
        ghost_y[0] = 24 * 6;

        ghost_x[1] = 24 * 6;
        ghost_y[1] = 24 * 8;

        dx = new int[4];
        dy = new int[4];

//      int x, y;

//      Random r = new Random();

        for (int x = 0; x < 15; x++) {
            for (int y = 0; y < 15; y++) {
                if (blocksData[y][x] != 0) {
                    levelData[y][x] += 16;
                }
            }
        }
//      System.out.println(Arrays.toString(levelData));
//      do {
//         x = r.nextInt(15);
//         y = r.nextInt(15);
//      } while (blocksData[y][x] == 0);
//
//      DOT_X = x * 24;
//      DOT_Y = y * 24;
//
//      levelData[y][x] += 16;

        timer = new Timer(40, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        initGame();
    }

    //закривать відкривать рот
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

//        if (dying) {
//
//            death();
//
//        } else {


//            movePacman();  // moving Pacman without pressing keys
        drawPacman(g2d);
        drawGhosts(g2d);
        drawInfo(g2d);
//            moveGhosts(g2d);   // moving ghosts without pressing keys
        checkMaze();
//        }
    }

    private void showIntroScreen(Graphics2D g2d) {

        g2d.setColor(new Color(0, 32, 48));
        g2d.fillRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);
        g2d.setColor(Color.white);
        g2d.drawRect(50, SCREEN_SIZE / 2 - 30, SCREEN_SIZE - 100, 50);

        String s = "Press s to start.";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics metr = this.getFontMetrics(small);

        g2d.setColor(Color.white);
        g2d.setFont(small);
        g2d.drawString(s, (SCREEN_SIZE - metr.stringWidth(s)) / 2, SCREEN_SIZE / 2);
    }

//    private void drawScore(Graphics2D g) {
//
//        int i;
//        String s;
//
//        g.setFont(smallFont);
//        g.setColor(new Color(96, 128, 255));
//        s = "Score: " + score;
//        g.drawString(s, SCREEN_SIZE / 2 + 96, SCREEN_SIZE + 16);

//        for (i = 0; i < pacsLeft; i++) {
//            g.drawImage(pacman3left, i * 28 + 8, SCREEN_SIZE + 1, this);
//        }
//    }

    private void drawScore(Graphics2D g) {
        String s;
        g.setFont(smallFont);
        g.setColor(new Color(96, 128, 255));
        s = "Score: " + score;
        g.drawString(s, SCREEN_SIZE / 2 + 30, SCREEN_SIZE + 16);
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
//
//      if(score == STARS_AMOUNT){
//         finished = false;
//      }

        while (finished) {
//
//         if (score < STARS_AMOUNT) {
//            finished = false;
//         }
//
            for (short i = 0; i < 15; i++) {
                for (short j = 0; j < 15; j++) {
                    if ((screenData[i][j] & 16) != 0) {
                        finished = false;
                    }
                }
            }
        }

        if (finished) {

            score += 50;

//            if (N_GHOSTS < MAX_GHOSTS) {
//                N_GHOSTS++;
//            }

            if (currentSpeed < maxSpeed) {
                currentSpeed++;
            }

            initLevel();
        }
    }

//    private void death() {
//
//        pacsLeft--;
//
//        if (pacsLeft == 0) {
//            inGame = false;
//        }
//
//        continueLevel();
//    }

    private int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x2-x1) + Math.abs(y2-y1);
    }

//    private int distBtwPacAndGhost(int ghostNum){
//        return manhattanDistance(pacman_x, pacman_y, ghost_x[ghostNum], ghost_y[ghostNum]);
//    }

    private int pointCost(int x, int y){

        short ch = screenData[y][x];

        if ((ch & 16) == 0) {
            return 5;
        }
        return 0;
    }

    private int pointCost(MyPoint p){

        short ch = screenData[p.getY()][p.getX()];

        if ((ch & 16) != 0) {
            return 100;
        }
        return 0;
    }

//    private int[] closestStar(int ghostNum ){
//
//    }

    private void moveGhosts() {
        for (int i = 0; i < ghost_x.length; i++) {
            if (canMoveDown(ghost_x[i], ghost_y[i])) {
                moveGhostDown(i);
            } else if (canMoveLeft(ghost_x[i], ghost_y[i])){
                moveGhostLeft(i);
            } else if(canMoveUp(ghost_x[i], ghost_y[i])){
                moveGhostUp(i);
            }else{
                moveGhostRight(i);
            }
        }
    }

    private void movePacman(){

        MyPoint crt;
        MyPoint next;
        double distTo0, distTo1;
        boolean wentSomewhere;
        List<PointHeuristic> pointHeuristics = new ArrayList<PointHeuristic>();

//        Stack<MyPoint> path_stack = new Stack<MyPoint>();
//        path_stack.push(new MyPoint(pacman_x,pacman_y));

//        while (!path_stack.empty()) {

        crt = new MyPoint(pacman_x/BLOCK_SIZE,pacman_y/BLOCK_SIZE, MyPoint.Direction.STILL);

//            path.add(crt);

//            if (isGoalReached(crt)) {
//                break;
//            }

//            markVisited(crt);

//            wentSomewhere = false;

        next = crt.moveLeft();
        if (canMoveLeft(pacman_x,pacman_y)) {
            distTo0 = manhattanDistance(next.getX(),next.getY(),ghost_x[0]/BLOCK_SIZE,ghost_y[0]/BLOCK_SIZE);
            distTo1 = manhattanDistance(next.getX(),next.getY(),ghost_x[1]/BLOCK_SIZE,ghost_y[1]/BLOCK_SIZE);
            pointHeuristics.add(new PointHeuristic(next,pointCost(next)+((distTo0<distTo1)?distTo0:distTo1)));
        }

        next = crt.moveUp();
        if (canMoveUp(pacman_x,pacman_y)) {
            distTo0 = manhattanDistance(next.getX(),next.getY(),ghost_x[0]/BLOCK_SIZE,ghost_y[0]/BLOCK_SIZE);
            distTo1 = manhattanDistance(next.getX(),next.getY(),ghost_x[1]/BLOCK_SIZE,ghost_y[1]/BLOCK_SIZE);
            pointHeuristics.add(new PointHeuristic(next,pointCost(next)+((distTo0<distTo1)?distTo0:distTo1)));
        }

        next = crt.moveRight();
        if (canMoveRight(pacman_x,pacman_y)) {
            distTo0 = manhattanDistance(next.getX(),next.getY(),ghost_x[0]/BLOCK_SIZE,ghost_y[0]/BLOCK_SIZE);
            distTo1 = manhattanDistance(next.getX(),next.getY(),ghost_x[1]/BLOCK_SIZE,ghost_y[1]/BLOCK_SIZE);
            pointHeuristics.add(new PointHeuristic(next,pointCost(next)+((distTo0<distTo1)?distTo0:distTo1)));
        }

        next = crt.moveDown();
        if (canMoveDown(pacman_x,pacman_y)) {
            distTo0 = manhattanDistance(next.getX(),next.getY(),ghost_x[0]/BLOCK_SIZE,ghost_y[0]/BLOCK_SIZE);
            distTo1 = manhattanDistance(next.getX(),next.getY(),ghost_x[1]/BLOCK_SIZE,ghost_y[1]/BLOCK_SIZE);
            pointHeuristics.add(new PointHeuristic(next,pointCost(next)+((distTo0<distTo1)?distTo0:distTo1)));
        }

        Collections.sort(pointHeuristics, Collections.reverseOrder());
        System.out.println(pointHeuristics);

        MyPoint moveTo = pointHeuristics.get(0).getP();

        moveTo(moveTo.getX(), moveTo.getY(), moveTo.getD());

//            for (PointHeuristic point : pointHeuristics){
//                path_stack.push(point.getP());
//            }

//            pointHeuristics.clear();

//        }
    }

    private void moveTo(int x, int y, MyPoint.Direction d){

        isGoalReached();

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

    }


    private void moveGhostLeft(int ghostNum) {
        if (canMoveLeft(ghost_x[ghostNum], ghost_y[ghostNum])) {
            ghost_x[ghostNum] -= 24;
        }
    }

    private void moveGhostRight(int ghostNum) {
        if (canMoveRight(ghost_x[ghostNum], ghost_y[ghostNum])) {
            ghost_x[ghostNum] += 24;
        }
    }

    private void moveGhostUp(int ghostNum) {
        if (canMoveUp(ghost_x[ghostNum], ghost_y[ghostNum])) {
            ghost_y[ghostNum] -= 24;
        }
    }

    private void moveGhostDown(int ghostNum) {
        if (canMoveDown(ghost_x[ghostNum], ghost_y[ghostNum])) {
            ghost_y[ghostNum] += 24;
        }
    }

    private void movePackLeft() {
        isGoalReached();
        if (canMoveLeft(pacman_x, pacman_y)) {
            pacman_x -= 24;
            view_dx = -1;
        }
    }

    private void movePackRight() {
        isGoalReached();
        if (canMoveRight(pacman_x, pacman_y)) {
            pacman_x += 24;
            view_dx = 1;
        }
    }

    private void movePackUp() {
        isGoalReached();
        if (canMoveUp(pacman_x, pacman_y)) {
            pacman_y -= 24;
            view_dy = -1;
        }
    }

    private void movePackDown() {
        isGoalReached();
        if (canMoveDown(pacman_x, pacman_y)) {
            pacman_y += 24;
            view_dy = 1;
        }
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

    private void drawGhosts(Graphics2D g2d) {

        for (int i = 0; i < ghost_x.length; i++) {
            g2d.drawImage(ghost, ghost_x[i] + 1, ghost_y[i] + 1, this);
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

//        pacsLeft = 3;
        score = 0;
        initLevel();
//        N_GHOSTS = 6;
        currentSpeed = 3;
    }

    private void initLevel() {
//        int i;
        for (int i = 0; i < N_BLOCKS; i++) {
            for (int j = 0; j < N_BLOCKS; j++) {
                screenData[i][j] = levelData[i][j];
            }
        }
//        continueLevel();
    }

//    private void continueLevel() {
//
//        short i;
//        int dx = 1;
//        int random;

//        for (i = 0; i < N_GHOSTS; i++) {
//
//            ghost_y[i] = 4 * BLOCK_SIZE;
//            ghost_x[i] = 4 * BLOCK_SIZE;
//            ghost_dy[i] = 0;
//            ghost_dx[i] = dx;
//            dx = -dx;
//            random = (int) (Math.random() * (currentSpeed + 1));
//
//            if (random > currentSpeed) {
//                random = currentSpeed;
//            }
//
//            ghostSpeed[i] = validSpeeds[random];
//        }
//
//        pacman_x = 7 * BLOCK_SIZE;
//        pacman_y = 11 * BLOCK_SIZE;
//        pacmand_x = 0;
//        pacmand_y = 0;
//        req_dx = 0;
//        req_dy = 0;
//        view_dx = -1;
//        view_dy = 0;
//        dying = false;
//    }

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
        drawInfo(g2d);
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
                if (key == KeyEvent.VK_LEFT) {
                    movePacman();
//                    movePackLeft();
//                    moveGhosts();
//               moveGhostLeft();
                } else if (key == KeyEvent.VK_RIGHT) {
                    movePackRight();
                    moveGhosts();
//               moveGhostRight();
                } else if (key == KeyEvent.VK_UP) {
                    movePackUp();
                    moveGhosts();
//               moveGhostUp();
                } else if (key == KeyEvent.VK_DOWN) {
                    movePackDown();
                    moveGhosts();
//               moveGhostDown();
                } else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) {
                    inGame = false;
                } else if (key == KeyEvent.VK_PAUSE) {
                    if (timer.isRunning()) {
                        timer.stop();
                    } else {
                        timer.start();
                    }
                }
            } else {
                if (key == 's' || key == 'S') {
                    inGame = true;
                    initGame();
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == Event.LEFT || key == Event.RIGHT
                    || key == Event.UP || key == Event.DOWN) {
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }


}