# TDD를 이용한 파일 전송 웹서버 개발

###정의
* 클라이언트로 부터 URL로 파일명을 전달 받으면 해당 파일명을 찾아 전달하는 Web Server 제작

###목적
* Web Server 개발을 통해 웹 서버의 역할과 통신 방법을 터득한다.
* TDD 방식으로 개발하여 테스트 주도 개발 방법론을 익힌다.
* 매일 History를 작성하여 기록을 남기는 습관을 기른다.

###기간
* 기간 : 2016-11-11 ~ 2016-11-18

###사전준비
* [moreunit](http://moreunit.sourceforge.net/) 설치 : 단위 테스트 코드 작성시 도움을 주는 플러그인
* [eclemma](http://www.eclemma.org/) 설치 : Java Code Coverage Analysis Tool

###참고자료
아직 없음.


##Work Flow
* Web Server를 만든 후 URL 파라미터로 `전달 받을 파일명`을 지정한다.
* Web Server는 전달 받은 파일명을 `로컬에서 검색`하여 `클라이언트에게 전달`한다.
* 개발시 TDD 방식으로 진행한다.


##History
###2016-11-11
