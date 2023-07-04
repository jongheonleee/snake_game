import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * GamePanel -> 사용자의 입력값에 따른 게임 이미지(JPanel - 뱀, 그리드, 사과)를 연쇄적으로 그려주면서 게임 상황을 기록하는 역할
 */

public class GamePanel extends JPanel implements ActionListener {
    static int cnt = 0;
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    // 네모칸의 크기
    static final int UNIT_SIZE = 25;
    // 총 그리디 개수(Panel 에 그려지는 모든 네모칸)
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    // 연쇄적으로 프로그램이 실행되는 것은 메소드가 연속적으로 호출되는 것, 다음 메소드가 호출될 때의 딜레이
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS]; // GAME_UNITS
    final int y[] = new int[GAME_UNITS]; // GAME_UNITS
    // 기본 뱀의 몸통 크기
    int bodyParts = 6;
    // 뱀이 사과를 먹은 개수
    int applesEaten;
    // 사과의 좌표(x, y)
    int appleX; // 여기서의 x는 열
    int appleY; // y는 행
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        // Dimension 객체를 인자로 전달 받고 해당 컴포넌트의 기본 크기를 결정해줌
        // 여기서 Dimension 클래스는 특정 영역의 사각형과 폭과 높이의 값을 관리할 수 있도록 도와주는 클래스
        // 윈도우 창에서의 기본 크기를 결정
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        // 처음에 윈도우 창이 생성될 때 가운데에 배치되게끔 설정
        this.setFocusable(true);
        // 사용자가 누른 키 값을 적절히 처리해주는 객체를 전달해줌
        // 입력된 키 값에 따른 결과, 행동을 동작
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }


    // start game
    public void startGame() {
        // make a apple in random position
        newApple();

        running = true;
        // Timer continuously runs this program at the corresponding delay time interval
        timer = new Timer(DELAY, this);

        // when timer call start() and running is true, call draw() at the delay interval
        timer.start();
    }

    // 컴포넌트가 그려져야 하는 시점 혹은 크기나 위치가 변경되는 등, 변경이 일어나는 시점에 그려줘야함
    // 해당 메서드가 윈도우에 배치되어 있는 JPanel 을 그려줌(뱀, 그리드, 사과 ,,,)
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (running) {

            // 1. 그리드 그리기
            // draw grids in the screen of game
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                // fix the column of "i*UNIT_SIZE" and draw line down(0~SCREEN_HEIGHT)
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                // fix the row of "i*UNIT_SIZE" and draw line sideways(0~SCREEN_HEIGHT)
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            // 2. 사과 그리기
            // fill oval red in apple's position
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);


            // 3. 뱀 그리기
            for (int i = 0; i < bodyParts; i++) {
                // head of snake is green
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                // body of snake is colorful
                else {
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            // write "Score : xx" at the top of the center of game screen
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH-metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        }
        else {
            // if running is false then game over
            gameOver(g);
        }
    }

    public void newApple() {
        // draw a apple in random position
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    // snake is moving depending on what direction user gives
    public void move() {
        // move snake from head to tail in order
        for(int i=bodyParts; i>0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        // move snake's head to the next position
        switch(direction) {
            case 'U' :
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D' :
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L' :
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R' :
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }

    // check whether snake haves a apple
    public void checkApple() {
        if ((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        // checks if head collides with body
        for (int i=bodyParts; i>0; i--) {
            if ((x[0] == x[i]) && (y[0] == y[i])) {
                running = false;
            }
        }

        // refactor -> check if head of snake touches border(width)
        if (!(0<= x[0] && x[0]<= SCREEN_WIDTH)) {
            running = false;
        }

        // refactor -> check if head of snake touches border(height)
        if (!(0<= y[0] && y[0] <= SCREEN_HEIGHT)) {
            running = false;
        }


        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        // Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH-metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());

        // Game Over Text
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game over", (SCREEN_WIDTH-metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }

        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        // 사용자가 누른 방향키에 따라서 이동 방향 전환
        // 다만, 뱀의 기존 이동 방향과 반대되는 방향을 누른 경우 해당 방향으로 전환 안됨
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
