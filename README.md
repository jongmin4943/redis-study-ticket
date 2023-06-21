# 레디스를 활용한 콘서트 티켓 구매 최적화

### Jmeter 를 이용해 부하 테스트

- 조건
  - 티켓은 총 1000개
  - 첫 10 티켓은 VIP 석 자동지정

- [Jmeter 설정](https://jongmin4943.notion.site/Test-Plan-180b6428d3bf46b1b25bbcb3047dfb3b?pvs=4)
![설정](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F861a9269-9b80-45ff-8704-5ba2e98615d5%2FUntitled.png?id=ee487af4-27eb-444e-bbfe-2b7ca529a59c&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=1530&userId=&cache=v2)


- Redis 없을때 결과
![NoRedis](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fa1614ade-bc42-4f65-82b8-9539afc1c60d%2FUntitled.png?id=725b4a6b-88af-49ed-bb1d-029453befeec&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=1530&userId=&cache=v2)
![NoRedisResult](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F391aec5f-c359-47fa-8cba-f5476318ab0c%2FUntitled.png?id=97974c55-c651-47f4-961c-d2db8bb9bed2&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=1530&userId=&cache=v2)
![CacheTicketGraph](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fe7ba33e4-e767-498d-aeaa-faf2e2ce1a03%2FUntitled.png?id=bde90d47-b834-47c0-9642-3141c5baf4ca&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=1530&userId=&cache=v2)

- Redis 분산락 적용 후
![DistributeLock](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F5a0d5c14-781c-41eb-944d-19b5bf265f7d%2FUntitled.png?id=a87f9673-076c-4a22-990c-fae99c44b8cf&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=1530&userId=&cache=v2)
![DistributeLockResult](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fc3635b6e-6879-44f0-b205-7f679bc95c48%2FUntitled.png?id=5ddd5f89-a2d0-448c-830f-54042c5116ac&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=1530&userId=&cache=v2)
![CacheTicketGraph](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F3de0632b-bdc9-45b0-a9e6-4bb6fff41cd2%2FUntitled.png?id=9bd0c07a-8a9a-43fc-8d9a-7011d95706a2&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=1530&userId=&cache=v2)

- Redis 분산락과 티켓수 캐싱 적용 후
![CacheTicket](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fc7621708-41f6-4e30-956e-b37b4525e743%2FUntitled.png?id=0d76ae04-b37e-457c-a521-204783e0de65&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=1530&userId=&cache=v2)
![CacheTicketResult](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ff0f36e70-7eae-40e1-a9be-5ededc704bd9%2FUntitled.png?id=c3a01a09-7980-4d76-b7c1-d6d44b0ff5f3&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=1530&userId=&cache=v2)
![CacheTicketGraph](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F3de0632b-bdc9-45b0-a9e6-4bb6fff41cd2%2FUntitled.png?id=9bd0c07a-8a9a-43fc-8d9a-7011d95706a2&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=1530&userId=&cache=v2)