
# 자바 미니 프로젝트 : snake_game

### 참고 자료
- https://www.youtube.com/watch?v=bI6e6qjJ8JQ
  <img width="671" alt="image" src="https://github.com/jongheonleee/snake_game/assets/87258372/21cfcaaa-2984-4ca9-b015-19284231b119">


#### 뱀이 움직이는 원리
![image](https://github.com/jongheonleee/snake_game/assets/87258372/90ea4769-f988-44d9-82b0-a1e8446e7dfa)
위의 사진을 기반으로 설명
- 해당 뱀의 크기는 6
- 사용자가 >(오른쪽 방향키)를 눌렀다고 가정하면 클래스 GamePanel의 인스턴스 변수의 값이 'R'로 저장됨
  <img width="186" alt="image" src="https://github.com/jongheonleee/snake_game/assets/87258372/d5f0369c-1672-418a-ad6d-19de37071a88">

- 노란색 부분이 다음 위치
- 뱀의 위치 정보는 int[] x, int[] y로 표현함
  <img width="298" alt="image" src="https://github.com/jongheonleee/snake_game/assets/87258372/1c39c365-f5e3-47a6-aa97-e16d29aac6b9">
- 현재 사진을 보면 행(y)는 고정되어 있고 열(x)로 움직이는 상황
- 게임 화면에 부딪히지 않는 경우 direction 방향으로 계속해서 뱀의 위치 정보를 업데이트함
- 즉, 위의 사진의 경우에는 뱀의 시작 위치가 [0, 0, 0, 0, 0, 0] 이라고 했을때(첫번재 원소는 머리 위치 마지막 원소는 꼬리 위치)
- [25, 0, 0, 0, 0, 0] -> [50, 25, 0, 0, 0, 0] -> [75, 50, 25, 0, 0, 0] -> [100, 75, 50, 25, 0, 0] -> [125, 100, 75, 50, 25, 0] ,,,, 으로 위치 정보가 업데이트됨
  <img width="302" alt="image" src="https://github.com/jongheonleee/snake_game/assets/87258372/b6072dff-83c1-4ee6-96a3-ddd9f5e3ae68">
(위치 정보 업데이트 관련 move() 메서드의 코드)

#### > 결론 : 사용자가 방향키를 입력하면 GamePanel의 direction 인스턴스 변수가 입력된 방향 정보를 저장하고 move()메서드를 통해서 뱀의 위치 정보를 업데이트함, 이때 머리부터 UNIT_SIZE를 더해주고 각각의 바디 부분에 차례로 더해줌
