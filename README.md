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

- Redis sorted set 적용
![SortedSetTicket](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fb0608474-2fc2-44ab-bbec-ed42d50cffc7%2FUntitled.png?id=edad9a57-eda8-4426-b941-c5c81690b8f2&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=2000&userId=&cache=v2)
![SortedSetTicketDb](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F259e96c2-031a-4a97-8fbb-143754c768b2%2FUntitled.png?id=df946afd-da18-470e-b4d4-9151688f0dc2&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=2000&userId=&cache=v2)
![SortedSetTicketResult](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Fbeace1e7-c3bc-4946-bb8e-8ba82b582f27%2FUntitled.png?id=42bc7082-cfd1-4a13-8081-f0e92a073c64&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=2000&userId=&cache=v2)
![SortedSetTicketGraph](https://jongmin4943.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F09bf00fb-35ec-460b-a8f4-d2351b248ce8%2FUntitled.png?id=3c7e506d-d443-429f-8bee-45b07a5465d7&table=block&spaceId=2344cac3-8428-47dd-9f85-7231f04a2c47&width=2000&userId=&cache=v2)

## 결론

레디스 적용 전 처리량은 약 30,000/분이지만 중복구매, 총 티켓수 초과 등 오류가 많다.
분산락을 적용하면 처리량은 약 75/분. 중복구매나 티켓수는 정확하나 요청순 으로 선착순구매가 안된다.
분산락에 db 쿼리들을 캐싱하면 처리량이 약 6000/분 까지 올라가는것을 보아 db 캐싱이 성능이 좋다.
단순 sorted set 만 사용할 시에는 다른 서비스에 티켓구매 로직을 넘겨야하기에 batch 나 socket 같은것을 사용해야하지만 처리량은 압도적으로 100,000/분 으로 높으며 또한 선착순구매도 가능하고 중복구매, 티켓수 오류도 없다.
