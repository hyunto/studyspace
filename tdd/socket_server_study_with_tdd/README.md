# TDD를 이용한 파일 전송 웹서버 개발

###정의
* 클라이언트로 부터 URL로 파일명을 전달 받으면 해당 파일명을 찾아 전달하는 Web Server 제작

###목적
* Web Server 개발을 통해 웹 서버의 역할과 통신 방법을 터득한다.
* TDD 방식으로 개발하여 테스트 주도 개발 방법론을 익힌다.
* 매일 History를 작성하여 기록을 남기는 습관을 기른다.

###기간
* 기간 : 2016-11-14 ~ 2016-11-18

###사전준비
* [moreunit](http://moreunit.sourceforge.net/) 설치 : 단위 테스트 코드 작성시 도움을 주는 플러그인
* [eclemma](http://www.eclemma.org/) 설치 : Java Code Coverage Analysis Tool

###참고자료
아직 없음.


##Work Flow
1. `9999번 포트`를 사용하여 웹 서버를 생성한다.
2. 클라이언트는 URL을 사용하여 웹 서버를 제어한다.
	* /download/filename : 파일을 다운로드 한다.
	* /upload/filename : 파일을 업로드 한다. (upload 폴더)
	* /quit : 웹 서버를 종료한다.
	* 기타 : 에러 메시지를 출력한다.
3. `파일 다운로드 요청`시 웹 서버의 모든 경로에서 일치하는 파일들을 찾아 다운로드 하도록 한다.
4. TDD 방식으로 개발을 한다


##History
###2016-11-14
* 아직 자바를 이용한 서버 구축이 익숙치 않기 때문에 간단한 서버를 만들어 보았다.
	* 자바에서 저수준 레벨의 처리는 알아서 해주기 때문에 나는 고수준 레벨에서만 컨트롤을 하면 간단히 서버 구축이 가능했다. 서버 구축에 필요한 주요 패키지는 `java.net.*`에 포함되어 있었고, 서버-클라이언트 간 데이터 전송은 `java.io.*` 패키지에 포함되어 있다.
	* 클라이언트는 `Telnet`을 이용해 서버에 접속하여 **문자열**을 입력하면 **그대로 리턴하여 출력**하도록 작성하였다.

	```java
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(9999);
		Socket client = server.accept();
	
		while(true) {	
			/* Input Stream */
			InputStreamReader stream = new InputStreamReader(client.getInputStream());
			BufferedReader reader = new BufferedReader(stream);
		
			/* Output Stream */
			PrintWriter writer = new PrintWriter(client.getOutputStream());
			
			String msg = reader.readLine();
			writer.println(msg);
			writer.flush();
			System.out.println(msg);
			
			if (msg.equals("quit")) {
				break;
			}
		}
	
		client.close();
		server.close();
	}
	```
* 서버를 만들어 Request를 받는 방법까지는 숙지하였는데, 웹 브라우저에서 URL 파라미터를 이용한 접근을 하였을때 어떤 형태로 요청이 들어오는지 알지 못하고 있다. 위 소스코드를 이용하여 웹 브라우저에서 어떤 형태로 HTTP Request를 수신하는지 확인하였다.
	* HTTP Request Format : `[Method] [URI] [HTTP Version]`
	* Method에는 GET, POST, PUT, DELETE 등이 있으며 웹 브라우저에서 요청시 기본적으로 GET으로 수신한다.
	* URI는 http://127.0.0.1로 요청하면 `/`이고, http://127.0.0.1/download?filename=flower.jpg로 요청하면 `/download?filename=flower.jpg` 이다.
* 앞서 확인한 HTTP Request Message를 통해 요청 URI 포맷을 결정한 후 작업을 나눈다. 제일 먼저 `파일 검색 후 다운로드 기능`을 우선으로 만든다.
	* Usage : http://hostname:port/[upload | download]/filename
	* http://hostname:port/quit 입력시 서버 종료
	* Unknown Parameter 입력시 Usage 입력 
	