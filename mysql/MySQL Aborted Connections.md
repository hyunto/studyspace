#MySQL Aborted Connections

##현상
운영중인 서버의 에러로그에 지속적으로 아래와 같은 경고 메시지가 남았다. 

```powershell
[Warning] Aborted connection xxxxxxx to db: 'database' user: 'user' host: 'xxx.xxx.xxx.xxx' (Got an error reading communication packets)
```
##원인

클라이언트가 정상적으로 연결되었지만 이후 부적절하게 또는 강제적으로 종료되었을 때 `Aborted_clients` 상태값의 카운트를 증가한 후 에러 로그에 남게 된다. 이때 에러 로그에 기록을 남기려면 `--log-warnings` 옵션을 활성화해야 한다.

MySQL Client에서 `Aborted_clients` 상태값을 확인하니 다음과 같이 나왔다.

```powershell
mysql> show status like 'aborted_clients';
+-----------------+--------+
| Variable_name   | Value  |
+-----------------+--------+
| Aborted_clients | 3461   |
+-----------------+--------+
```

MySQL Reference Manual에 따른 정확한 주요 원인은 다음과 같다 :

* 클라이언트 프로그램이 종료 전에 `mysql_close()`를 호출하지 않은 경우
* 클라이언트가 `wait_timeout` 또는 `interactive_timeout`에 설정된 시간(초) 이상으로 동작이 없는 경우
* 데이터 전송 중에 클라이언트 프로그램이 갑자기 종료된 경우

클라이언트가 서버에 접속할 수 없을 경우에도 `Aborted_connects` 상태 값이 증가하게 되는데, 클라이언트가 접속하지 못하는 원인은 다음과 같다 :

* 클라이언트가 데이터베이스에 접근할 권한이 없는 경우
* 접속시 사용자 비밀번호를 틀렸을 경우
* Connection Packet이 올바른 정보를 가지고 있지 않은 경우
* Connection Packet을 얻을 때 `connect_timeout` 시간보다 오래 걸릴 경우 (Server System Variables 참고)

위와 같은 경우는 주로 부적절한 사용자가 악의적인 목적으로 서버를 접근하려는 행위일 경우가 많다. 따라서 ==General Log==를 활성화하여 정확한 원인을 파악해야 한다.

또다른 원인은 다음과 같다 :

* `max_allowed_packet` 변수 값이 너무 작게 설정되어 있거나 쿼리가 mysqld에 할당한 메모리보다 많은 양을 요구할 경우 (Packet Too Large 참고)
* 리눅스에서 Ethernet 프로토콜을 사용하는 경우. 리눅스의 많은 이더넷 드라이버들은 이 버그를 가지고 있다. 이 버그는 FTP를 이용해 큰 사이즈의 파일을 전송해 테스트 할 수 있다. 만약 `burst-pause-burst-pause` 모드로 전송되면 ==Linux Duplex Syndrome== 문제이다. 서버/클라이언트 NIC의 Duplex 모드를 Full 또는 Half로 변경하여 최적의 설정을 찾도록 한다.
* Read 인터럽트를 일으키는 쓰레드 라이브러리에 문제가 있는 경우
* 잘못 설정된 TCP/IP
* 네트워크(Ethernet, Hub, Switch, Cable 등)에 문제가 있는 경우. 이 경우 장비 교체를 고려한다.

==우리가 운영중인 서버의 경우 에러 로그에 매일 동일한 시간대(07~08시)에 기록이 남았고, Abort 된 계정을 본 결과 단순히 클라이언트가 연결된 후 새벽시간동안 사용하지 않아 wait_timeout이 지나 연결이 종료된 것으로 보인다. (설정된 wait_timeout과 interactive_timeout 값은 28800초로 8시간이다.)==

##해결책

우리의 경우 크게 문제되지 않는 이슈라 딱히 변경한 부분은 없지만 추후 동일한 에러를 볼 경우, 위에 나온 원인을 대조하여 정확한 분석을 한 후 적절한 대응이 필요하다.

---

> **Reference**
> [MySQL Reference Manual - Communication Errors and Aborted Connections](https://dev.mysql.com/doc/refman/5.5/en/communication-errors.html)
> [MySQL Reference Manual - Server System Variables](https://dev.mysql.com/doc/refman/5.5/en/server-system-variables.html)
> [MySQL Reference Manual - Packet Too Large](https://dev.mysql.com/doc/refman/5.5/en/server-system-variables.html)
> [Linux change the speed and duplex setting of an Ethernet card](http://www.cyberciti.biz/faq/linux-change-the-speed-and-duplex-settings-of-an-ethernet-card/)
> [Linux add ethtool duplex settings to a network card permanently](http://www.cyberciti.biz/tips/howto-linux-add-ethtool-duplex-settings-permanent.html)