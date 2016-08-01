#Rename Database in MySQL

##RENAME DATABASE Syntax (Deprecated)

```sql
RENAME {DATABASE | SCHEMA} db_name TO new_db_name;
```

얼마전에 데이터베이스 이름 변경할 일이 있어서 이 명령어를 사용하려 했는데 Syntax Error가 나면서 되지 않았다.

찾아보니 MySQL 5.1.7에서 추가된 문법이지만 데이터베이스 내용물이 손실될 위험성이 발견되어 MySQL 5.1.23에서 제거되었다고 한다.

데이터베이스 명을 변경하려면 `ALTER DATABASE db_name UPGRADE DATA DIRECTORY NAME`을 대신 사용하면 된다. 하지만 아직 직접 테스트해보진 않아서 다른 방법으로 데이터베이스 명을 변경했다.


##방법 1 : 내가 사용한 방법

새로운 데이터베이스를 생성한 후, 이전 데이터베이스에 있는 모든 테이블의 이름을 변경한다. 이때 테이블명 앞에 반드시 데이터베이스 명을 함께 붙여준다.

```SQL
CREATE database new_db_name;
RENAME TABLE db_name.table1 TO new_db_name, db_name.table2 TO new_db_name;
DROP database db_name;
```

##방법 2 : 사이즈가 작을 경우

mysqldump를 이용해 이전 데이터베이스를 백업한 후, 새로 생성한 데이터베이스로 복원한다.

이전 데이터베이스의 사이즈가 컸기 때문에 백업/복원에 많은 시간이 소요될 것이라 판단해 이 방법을 사용하지는 않았다.

```powershell
mysqldump -uxxxx -pxxxx -h xxxx db_name > db_name_dump.sql
mysql -uxxxx -pxxxx -h xxxx -e "CREATE DATABASE new_db_name"
mysql -uxxxx -pxxxx -h xxxx new_db_name < db_name_dump.sql
mysql -uxxxx -pxxxx -h xxxx -e "DROP DATABASE db_name"
```

##방법 3 : 모든 테이블의 스토리지 엔진이 MyISAM일 경우

스토리지 엔진이 MyISAM 일 경우, 데이터베이스 폴더명을 변경하면 된다. 

작업 순서는 다음과 같다 :
1. MySQL 서버 중지
2. 데이터베이스 폴더명 변경
3. MySQL 서버 시작

---
>**Reference**
>[How to rename a database in mysql](http://www.rndblog.com/how-to-rename-a-database-in-mysql/)
>[MySQL 5.1 Reference Manual - 13.1.32 RENAME DATABASE Syntax](https://dev.mysql.com/doc/refman/5.1/en/rename-database.html)
>[MySQL 5.1 Reference Manual - 13.1.1 ALTER DATABASE Syntax](https://dev.mysql.com/doc/refman/5.1/en/alter-database.html)