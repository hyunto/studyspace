# test_driven_development_book
테스트 주도 개발(TDD)  실천법과 도구 스터디



# TDD 개발 진행 방식
![TDD Global Lifecycle](https://upload.wikimedia.org/wikipedia/commons/0/0b/TDD_Global_Lifecycle.png)

TDD를 이용한 개발은 크게 ** 질문 -> 응답 -> 정제 ** 과정을 반복적으로 수행하며 이뤄진다.

* 질문
	* 작성하고자 하는 메소드나 기능이 무엇인지 선별하고 작성 완료 조건을 정해서 실패하는 테스트 케이스를 작성하는 것
	* 설계 문서(산출물)이 있다면 리턴 타입을 초기값(null, 0 등)으로 설정한 스켈레톤(Skeleton) 클래스를 만들어 메소드 외양을 만든다.
	* 설계 문서가 없다면 대략적인 설계를 먼저 진행한 후 질문을 시작한다.
	* 테스트 케이스를 엉성하게 만들면 테스트 자체를 신뢰할 수 없게 된다.
	* 테스트 케이스를 통한 제품 코드 구현을 하드코딩으로 시작하는 것도 괜찮은 출발점이다.
* 응답
	* 앞서 만든 테스트 케이스를 통과하는 코드를 작성한다.	
* 정제
	* 리팩토링을 적용할 부분이 있는지 찾아본다.
	* TODO 목록에서 완료된 부분을 지운다.



# Robert C. Martin 이 반드시 따르길 권장하는 TDD 원칙
1. 실패하는 테스트를 작성하기 전에는 절대로 제품 코드를 작성하지 않는다.
2. 실패하는 테스트 코드를 한 번에 하나 이상 작성하지 않는다.
3. 현재 실패하고 있는 테스트를 통과하기에도 충분한 정도를 넘어서는 제품 코드를 작성하지 않는다.

> 출처 : [Coplien and Martin Debate TDD, CDD and Professionalism](https://www.infoq.com/interviews/coplien-martin-tdd)

