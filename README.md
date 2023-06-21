# 레디스를 활용한 콘서트 티켓 구매 최적화

### Jmeter 를 이용해 부하 테스트

- 조건
  - 5만 이상의 request
  - 티켓은 총 1000개
  - 첫 10 티켓은 VIP 석 자동지정

- [Jmeter 설정](https://jongmin4943.notion.site/Test-Plan-180b6428d3bf46b1b25bbcb3047dfb3b?pvs=4)
![설정](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F8fe41dee-909f-4654-a99a-385073c2055e%2FUntitled.png?id=9e9ccba4-465f-4b89-bf7d-490bf24884ce&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=2000&userId=&cache=v2)


- Redis 없을때 결과
![NoRedis](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fe63cb8d0-d0b4-40a7-b544-cf8f21342091%2FUntitled.png?id=59b752e4-10d2-4bbb-8ec4-417c4cf55ecc&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=2000&userId=&cache=v2)