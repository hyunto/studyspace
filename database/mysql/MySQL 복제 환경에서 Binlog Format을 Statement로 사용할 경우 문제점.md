##MySQL 복제 환경에서 Binlog Format을 Statement로 사용할 경우 문제점

###현상황

운영중이던 서버의 에러로그에 이전부터 지속적으로 다음과 같은 경고문구가 남는 것을 보았다.

```powershell
[Warning] Statement is not safe to log in statement format. Statement: <실행된 SQL문>
```

###원인

해당 서버의 Binlog Format은 Statement로 설정되어 있기 때문에 발생하였다.

에러 로그에 기록된 SQL문을 보면 대부분 LIMIT 구문을 사용하고 있다.

Statement 포맷에서 LIMIT 구문을 사용하면 마스터와 슬레이브에서 해당 SQL문을 동시에 실행한다 하더라도 100% 동일한 결과를 보장할 수 없기 때문에 데이터 정합성에 문제가 생길 수 있기 때문에 경고 메시지가 나왔다.


###해결책

1. 개발사 측에 Statement 포맷 사용시 안전하지 않은 SQL문을 사용하지 않도록 권고. 
(Determination of Safe and Unsafe Statement in Binary Logging 참조)

2. Binlog Format을 Row나 Mixed를 사용하도록 수정.

---
>참조
>[MySQL Reference Manual - Determination of Safe and Unsafe Statement in Binary Logging](https://dev.mysql.com/doc/refman/5.5/en/replication-rbr-safe-unsafe.html)
>[Replication and LIMIT](https://dev.mysql.com/doc/refman/5.5/en/replication-features-limit.html)