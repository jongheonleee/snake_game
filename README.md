
# 자바 미니 프로젝트 : snake_game

## 참고 자료
- https://www.youtube.com/watch?v=bI6e6qjJ8JQ
  <img width="671" alt="image" src="https://github.com/jongheonleee/snake_game/assets/87258372/21cfcaaa-2984-4ca9-b015-19284231b119">
<br/>



## 게임 동작 원리 분석

### 📌 1. 사용자 입력값(방향키)에 따른 뱀의 움직이는 원리
![image](https://github.com/jongheonleee/snake_game/assets/87258372/90ea4769-f988-44d9-82b0-a1e8446e7dfa)

위의 사진을 기반으로 설명
- 해당 뱀의 크기는 6
- 사용자가 >(오른쪽 방향키)를 눌렀다고 가정하면 클래스 GamePanel의 인스턴스 변수의 값이 'R'로 저장됨
- <img width="186" alt="image" src="https://github.com/jongheonleee/snake_game/assets/87258372/d5f0369c-1672-418a-ad6d-19de37071a88">

- 노란색 부분이 다음 위치
- 뱀의 위치 정보는 int[] x, int[] y로 표현함
- <img width="298" alt="image" src="https://github.com/jongheonleee/snake_game/assets/87258372/1c39c365-f5e3-47a6-aa97-e16d29aac6b9">
- 현재 사진을 보면 행(y)는 고정되어 있고 열(x)로 움직이는 상황
- 게임 화면에 부딪히지 않는 경우 direction 방향으로 계속해서 뱀의 위치 정보를 업데이트함
- 즉, 위의 사진의 경우에는 뱀의 시작 위치가 [0, 0, 0, 0, 0, 0] 이라고 했을때(첫번재 원소는 머리 위치 마지막 원소는 꼬리 위치)
- [25, 0, 0, 0, 0, 0] -> [50, 25, 0, 0, 0, 0] -> [75, 50, 25, 0, 0, 0] -> [100, 75, 50, 25, 0, 0] -> [125, 100, 75, 50, 25, 0] ,,,, 으로 위치 정보가 업데이트됨
- <img width="302" alt="image" src="https://github.com/jongheonleee/snake_game/assets/87258372/b6072dff-83c1-4ee6-96a3-ddd9f5e3ae68">
(위치 정보 업데이트 관련 move() 메서드의 코드)

### 📝 결론 : 사용자가 방향키를 입력하면 GamePanel의 direction 인스턴스 변수가 입력된 방향 정보를 저장하고 move()메서드를 통해서 뱀의 위치 정보를 업데이트함, 이때 머리부터 UNIT_SIZE를 더해주고 각각의 바디 부분에 차례로 더해줌



### 📌 2. 사과가 게임판에 랜덤한 위치에 생성되는 원리
![image](https://github.com/jongheonleee/snake_game/assets/87258372/15f6cffb-3f6f-4c8a-85fa-842bb520a27a)

위의 사진을 기반으로 설명
-  newApple() 메서드를 통해서 사과의 위치를 랜덤하게 생성함
- <img width="573" alt="image" src="https://github.com/jongheonleee/snake_game/assets/87258372/f493f9d5-9ea3-4b7c-9756-b43d0232c238">
- 위의 메서드에서는 0 에서부터 스크린의 크기를 각각 유닛의 사이즈(사과의 크기)로 나눈 값까지의 수 중에서 랜덤하게 선택하고 그 값에 다시 유닛의 사이즈를 곱해줌
- 나눈 이유는 그리드를 생각하면 그리드의 총 개수는 해당 나눈 값이 됨 또한, 유닛 사이즈를 곱해준 이유는 각 그리드의 라인들이 유닛 사이즈 간격 만큼 띄워져 있기 때문임
- 사과를 그려주는 부분을 확인해 보면 생성된 사과의 위치를 기준으로 사과의 크기(유닛 사이즈)만큼 빨간색을 채워줌
- <img width="426" alt="image" src="https://github.com/jongheonleee/snake_game/assets/87258372/ab37e918-fa6d-4b49-bd53-51508a340a7b">
- 만약 사과의 크기(유닛 사이즈)보다 작은 값만큼 색을 칠해줄 경우 밑에 사진 처럼 채워짐
- ![image](https://github.com/jongheonleee/snake_game/assets/87258372/72189ff2-3510-41d3-a1dd-596b7323053d)

### 📝 결론 : newApple() 메서드를 통해서 해당 그리드에서 랜덤하게 (x, y)를 생성하고 fillOval(x, y, UNIT_SIZE, UNIT_SIZE)를 통해서 (x, y) 기준으로 유닛 사이즈만큼의 특정 색깔을 채워짐




## 🛠 새롭게 배운 클래스들

### Jpanel

### 참고 자료
https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=highkrs&logNo=220575148557

- 위의 블로그 글 설명 기반으로 공부한 내용 정리
- <img width="529" alt="image" src="https://github.com/jongheonleee/snake_game/assets/87258372/1191a2f2-1e8f-48ed-874e-ee59ded1ad0f">
- 각종 UI를 쉽게 배치할 수 있게끔 도와줌
- 예를 들어서, 밑 바탕이 Jframe이 되고 그 위에 Jpanel 그리고 JLabel, JTextFile ,,, 등 을 통해서 내가 만들고자 하는 애플리케이션의 UI를 손 쉽게 배치할 수 있음
- 현재 게임에서는 기본적으로 게임 화면의 바탕이 되는 UI를 담달하고 있음


