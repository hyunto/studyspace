#[MySQL, MariaDB] PID 파일이 사라지는 이유

##문제점

glibc 보안패치 후 리눅스 시스템 재부팅을 할 때, MySQL/MariaDB 복제 구성이 되어 있는 서버은 직접 작업했다.

재부팅 전에 MySQL/MariaDB 프로세스를 Stop하려고 하였으나 아래와 같은 메시지가 나오며 데몬이 정상적으로 내려가지 않았다.

```apache
[root@Linux ~]# /etc/init.d/mysql stop
MySQL server PID file could not be found!       [FAILED]
```

MySQL/MariaDB 데몬의 Process ID 값을 가지고 있는 PID 파일이 없고, 따라서 어느 데몬을 죽여야 할지 알 수 없기 때문에 발생한 문제이다.

직접 mysqld 데몬의 PID 값을 찾아 PID 파일을 생성하여 입력하면 종료할 수 있지만, 즉시 mysqld 데몬이 다시 올라온다. 

즉, Restart를 한 상황과 동일한 현상이 발생한다.

```apache
[root@Linux ~]# ps -ef | grep mysql
root    3054    1  0 16:00 pts/0  00:00:00 /bin/sh /home/mysql/mysql/bin/mysqld_safe ~생략
mysql   3958 3054  0 16:00 pts/0  00:00:01 /home/mysql /mysql/bin/mysqld ~생략
root    4240 2612  0 16:11 pts/0  00:00:00 grep mysql

[root@Linux ~]# echo 3958 > /tmp/mysql.pid

[root@Linux ~]# cat /tmp/mysql.pid
3958

[root@Linux ~]# /etc/init.d/mysql stop
Shutting down MySQL....The server quit without updating PID[FAILED]tmp/mysql.pid).

[root@Linux ~]# ps -ef | grep mysql
root      3054     1  0 16:00 pts/0    00:00:00 /bin/sh /home/mysql/mysql/bin/mysqld_safe ~생략
mysql     4280  3054  4 16:12 pts/0    00:00:00 /home /mysql/mysql/bin/mysqld ~생략
root      4316  2612  0 16:12 pts/0    00:00:00 grep mysql

[root@Linux ~]# /etc/init.d/mysql stop
Shutting down MySQL..                           [  OK  ]
```


##원인

리눅스는 내부적으로 `/tmp` 및 `/var/tmp` 내 특정 조건에 부합하는 파일들을 Daily Cron 스케쥴링을 통해 삭제하는 프로세스를 가진다.

해당 파일의 경로와 소스코드는 다음과 같다.

```apache
[root@Linux ~]# cat /etc/cron.daily/tmpwatch
#! /bin/sh
flags=-umc
/usr/sbin/tmpwatch "$flags" -x /tmp/.X11-unix -x /tmp/.XIM-unix \
        -x /tmp/.font-unix -x /tmp/.ICE-unix -x /tmp/.Test-unix \
        -X '/tmp/hsperfdata_*' 10d /tmp
/usr/sbin/tmpwatch "$flags" 30d /var/tmp
for d in /var/{cache/man,catman}/{cat?,X11R6/cat?,local/cat?}; do
    if [ -d "$d" ]; then
        /usr/sbin/tmpwatch "$flags" -f 30d "$d"
    fi
done
```

소스를 보면 -x 옵션에 적힌 파일들은 제외하고 `/tmp`는 10일간, `/var/tmp`는 30일간 접근되지 않은 파일들을 삭제한다.

아래는 테스트 결과이다.
VM을 설치 직후 스냅샷(2014/9/29)으로 돌린 후 MySQL을 설치하고 나서, 시간을 동기화(2015/3/4)하고 나서 tmpwatch를 실행하였더니 `mysql.pid`파일이 삭제되었다.

```apache
[root@Linux ~]# ls -lht /tmp
-rw-rw----.  1 mysql mysql    5 Sep 29 15:31 mysql.pid
srwxrwxrwx.  1 mysql mysql    0 Sep 29 15:31 mysql.sock

[root@Linux ~]# rdate -s time.bora.net

[root@Linux ~]# date
Wed Mar  4 16:03:06 KST 2015

[root@Linux ~]# /etc/cron.daily/tmpwatch

[root@Linux ~]# ls -lht /tmp
srwxrwxrwx.  1 mysql mysql    0 Sep 29 15:31 mysql.sock

```


##해결방법

* 방법 1 : my.cnf 파일에 /tmp 디렉터리가 아닌 다른 디렉터리를 사용하도록 설정한다.

* 방법 2 : 계속해서 /tmp 디렉터리를 사용할 것이라면 tmpwatch 소스코드에 `-x /tmp/mysql.pid`를 추가하여 10일 이상 Access 하지 않아도 삭제되지 않도록 한다.


> ###참고
> * [tmpwatch man page](http://linux.die.net/man/8/tmpwatch)