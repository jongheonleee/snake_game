import javax.swing.*;

/**
 * GameFrame -> snake_game 의 윈도우와 그 내부 구조를 초기화해주는 역할
 */
// JFrame 은 윈도우 창, 전체 그림 판
public class GameFrame extends JFrame {

    GameFrame() {
        // 그 위에(JFrame) 조각인 JPanel 배치 시킴
        this.add(new GamePanel());
        this.setTitle("Snake");
        // 윈도우 창 종료 시 프로세스까지 종료해줌
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // "Resizable = 크기 조정 가능", 크기 조정 불가능
        this.setResizable(false);
        // pack() : JFrame 의 내용물에 알맞게 윈도우 크기 조절, 밑에 주석처리하면 화면이 엉망진창이됨
        this.pack();
        // setVisible() : 창을 띄워 줄지말지 결정, true 이면 디스플레이함
        this.setVisible(true);
        // 윈도우 창을 화면의 가운데에 띄우는 역할을 함
        this.setLocationRelativeTo(null);
    }
}
