import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static int cnt = 0;

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 10; // 화면에 나타나는 그리드 단위의 크기를 의미함, 즉 해당 숫자가 작을수록 많은 그리드를 만듦
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE; // 화면에 나타나는 그리드(네모칸)을 의미함
    static final int DELAY = 1000;
    final int x[] = new int[GAME_UNITS]; // GAME_UNITS
    final int y[] = new int[GAME_UNITS]; // GAME_UNITS
    int bodyParts = 6;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    // 게임 세팅을 담당(외부요소 설정 - 프레임, 색상,,,,)
    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.white);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }


    // 게임 시작
    public void startGame() {
        newApple();
        running = true;
        // 타이머가 해당 딜레이 시간 간격으로 이 객체를 지속적으로 실행해줌
        timer = new Timer(DELAY, this);
        // start 메서드를 통해서 실행
        // 어떻게 실행함? ->timer.start()가 호출되면 DELAY 간격으로 draw를 그려줌 이때 running 이 true 이여야함
        timer.start();
    }

    // 게임 화면 그림
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    // 추측 1 : timer.start()에 의해 호출되면서 running 이 true 인 경우 delay 간격으로 지속적으로 그려줌
    // 증명 1 : 맞음, cv로 cnt 선언하고 호출되는 횟수를 기록하며 출력해보니깐 draw 가 delay 간격으로 해당 게임판을 그려줌
    public void draw(Graphics g) {
//        cnt++;
//        System.out.println(cnt);
        if (running) {
            // 화면에 그리드를 그려줌
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                // i * UNIT_SIZE 열을 고정시키고 0~600까지 밑으로 쭈욱 선을 그음
                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }

            // draw a apple
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);


            for (int i = 0; i < bodyParts; i++) {
                // 머리는 초록색
                if (i == 0) {
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                // 몸 부분을 알록달록하게 표현
                else {
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }

            // 게임 창 중심 상단부에 Score : -- 형태 표현
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            // 중아에 Score : -- 그려주기
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH-metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
        }
        else {
            // if running is false then game over
            gameOver(g);
        }
    }

    public void newApple() {
        // make a apple in random space
        appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }

    // 사용자 입력값에 따라 움직이기
    public void move() {
        // 이 부분을 통해서 머리랑 몸통을 연결함
        // 누적값 계산 방식
        // 예를 들어 길이가 5인 뱀이 있음
        // 해당 뱀의 x 좌표 값을 나타내면 [0, 0, 0, 0, 0]
        // idx = 0인 부분이 머리, lastIdx 가 꼬리임

        // 사용자가 > 방향을 누르고 그 이후에 아무것도 안누름
        // direction = 'R' 가 저장됨
        // 해당 방향을 기준으로 +/-UNUT_SIZE 를 해줌, 이때 'R'이므로 -> 이동하니깐
        // + UNIT_SIZE해줌
        // 첫번째로 머리부터 + UNIT_SIZE 를 해줌
        // [25, 0, 0, 0, 0]
        // 그 이후에 해당 함수가 또 호출되고 밑에 for문에 의해
        // [50, 25, 0, 0, 0]
        // [75, 50, 25, 0, 0]
        // [100, 75, 50, 25, 0]
        // [125, 100, 75, 50, 25]
        // ,,, 프레임에 부딪힐때까지 해당 과정을 반복함
        for(int i=bodyParts; i>0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

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

        // check if head touches left border
        if (x[0] < 0) {
            running = false;
        }

        // check if head touches right border
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }

        // check if head touches top border
        if (y[0] < 0) {
            running = false;
        }

        // check if head touches bottom border
        if (y[0] > SCREEN_HEIGHT) {
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
