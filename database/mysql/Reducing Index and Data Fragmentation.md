#Reducing Index and Data Fragmentation

##단편화

####인덱스 단편화
B-Tree 인덱스는 Leaf 페이지로 데이터를 찾아 들어가는 과정에서 Randon Disk Access가 필요하다. 
이때 물리적인 디스크에 순차적으로 데이터가 있다면 더 나은 성능을 보이지만, 그렇지 않은 경우엔 성능 저하가 발생하며 이를 ==단편화==라고 한다.
단편화가 발생할 경우 ==Range Scane==이나 ==Full Index Scan==시 더 많은 시간이 소요된다.

####데이터 단편화
데이터 역시 단편화가 발생하며, 인덱스 단편화 보다 좀 더 복잡하다.

데이터 단편화에는 총 세가지 종류가 있다 : 

1. Row Fragmentation
Row가 이곳 저곳에 조각이 나서 저장될 때 발생하는 단편화 타입. Row Fragmentation은 인덱스로부터 단일 행만 필요한 쿼리일 경우에도 성능 저하가 발생한다.

2. Intra-Row Fragmentation
논리적인 순차 페이지나 행이 디스크에 순차적으로 저장되어 있지 않을 경우 발생하는 단편화 타입. 일반적으로 연속적으로 데이터가 저장되어 있을때 이점이 있는 ==Full Table Scan==과 ==Clustered Index Range Scan==에 영향을 미친다.

3. Free Space Fragmentation
데이터 페이지에 많은 빈공간이 존재할 때 발생하는 단편화 타입. 서버가 불필요한 데이터를 읽음으로써 자원을 낭비하게 된다.

MyISAM 테이블은 모든 단편화 타입에 영향을 받는다. 그러나 InnoDB 테이블의 경우 짧은 행의 경우  내부적으로 단일 조각으로 만드는 과정이 진행되어 단편화가 발생하지 않는다. 

##단편화 해결책 : InnoDB, MyISAM 스토리지 엔진

`OPTIMIZE TABLE` 명령어를 사용하거나 `DUMP & RESTORE` 를 하여 데이터 단편화를 해결할 수 있다. 이는 대부분의 스토리지 엔진에서 사용할 수 있다.

MyISAM과 같은 일부 스토리지 엔진은 정렬 알고리즘과 함께 Rebuilding 하여 인덱스 조각화를 할 수 있다.

옛날 버전의 InnoDB는 데이터 조각화를 할 수 없었다. 그러나 최신 버전은 인덱스를 온라인으로 Drop & Build 하는 기능이 포함됬다. ~~따라서 전체 테이블을 Rebuilding 할 필요가 없고, 조각화를 위해 인덱스를 Drop & Recreate 할 수 있다.~~


##단편화 해결책 : 기타 스토리지 엔진

`OPTIMIZE TABLE` 명령어를 지원하지 않는 스토리지 엔진은 다음과 같이 `ALTER TABLE` 명령어를 이용하면 된다. 이때 조각화를 할 테이블 명과 사용중인 스토리지 엔진 이름을 함께 넣어주면 된다.

```powershell
mysql> ALTER TABLE <table> ENGINE=<engine>;
```

##==expand_fast_index_creation== 옵션

==Percona Server==에서 지원하는 옵션으로 위와 같은 방법으로 테이블 Rebuilding을 하면 InnoDB 테이블과 인덱스를 함께 조각화 할 수 있도록 해준다. 

Percona Server가 아닌 일반적인 MySQL, MariaDB는 테이블만 조각화를 한다.

>참조
>[Dealing with InnoDB Fragmentation](https://www.facebook.com/notes/mysql-at-facebook/dealing-with-innodb-fragmentation/10150397873640933)


##단편화 확인 방법

Percona Xtrabackup은 백업 모드로 동작하지 않는 `--stat` 옵션이 있다. 이 옵션을 사용하면 데이터 양과 페이지의 남은 공간을 포함한 인덱스와 테이블의 통계를 출력해준다. 이는 얼마나 단편화가 발생했는지 알 수 있는 지표이다.


##MariaDB 10.1.1의 InnoDB Defragmentation



>참조
>[Defragmentin InnoDB Tablespaces](https://mariadb.com/kb/en/mariadb/defragmenting-innodb-tablespaces/)