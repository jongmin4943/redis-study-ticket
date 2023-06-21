# 레디스를 활용한 콘서트 티켓 구매 최적화

### Jmeter 를 이용해 부하 테스트

- 조건
  - 티켓은 총 1000개
  - 첫 10 티켓은 VIP 석 자동지정

- [Jmeter 설정](https://jongmin4943.notion.site/Test-Plan-180b6428d3bf46b1b25bbcb3047dfb3b?pvs=4)
![설정](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F861a9269-9b80-45ff-8704-5ba2e98615d5%2FUntitled.png?id=ee487af4-27eb-444e-bbfe-2b7ca529a59c&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=1530&userId=&cache=v2)


- Redis 없을때 결과
![NoRedis](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fa1614ade-bc42-4f65-82b8-9539afc1c60d%2FUntitled.png?id=725b4a6b-88af-49ed-bb1d-029453befeec&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=1530&userId=&cache=v2)