# test_driven_development_book
테스트 주도 개발(TDD)  실천법과 도구 스터디

# 노트
> **Java의 리플렉션(Reflection)**
>
> Java에서는 리플렉션이라는 기능을 통해 인스턴스화된 객체로부터 원래 클래스의 구조를 파악해내어 동적으로 조작하는 것이 가능하다. 마치 기계를 분해해서 마음대로 재구성하는 것처럼 말이다. 리플렉션을 이용하면 private 메소드나 필드까지도 마음대로 조작할 수 있다. 리플렉션을 사용하면 이런식으로 Java의 일반적인 규칙들을 무시할 수도 있기 때문에 보통 한정적으로만 사용할 것을 권장한다. 그리고 시스템 비용이 매우 많이 드는 기능이다.

---
> **JUnit4의 특징**
>
> 1. Java 5 Annotation 지원
> 2. test라는 글자로 method 이름을 시작해야 한다는 제약 해소 : Test 메소드는 `@Test`를 붙인다.
> 3. 좀 더 유연한 픽스쳐 : `@BeforeClass`, `@AfterClass`, `@Before`, `@After`
> 4. 예외 테스트 : `@Test(expected=NumberFormatException.class)`
> 5. 시간 제한 테스트 : `@Test(timeout=1000)`
> 6. 테스트 무시 : `Ignore("this method isn't working yet")`
> 7. 배열 지원 : `assertArrayEquals([message], expected, actual);`
> 8. @RunWith(클래스이름.class) : JUnit Test 클래스를 실행하기 위한 러너(Runner)를 명시적으로 지정한다. @RunWith는 junit.runner.Runner를 구현한 외부 클래스를 인자로 갖는다.
> 9. @SuiteClasses(Class[]) : 보통 여러 개의 테스트 클래스를 수행하기 위해 쓰인다. `@RunWith`를 이용해 Suite.class를 러너로 사용한다.
> 10. 비교 표현을 위한 테스트 매처 라이브러리(Test Macher Library)인 **Hamcrest** 도입
> 11. 파라미터를 이용한 테스트
> ```java
@RunWith(Parameterized.class)
@Parameters
public static Collection data() {
	...
}
```



# TDD 개발 진행 방식
![TDD Global Lifecycle](https://upload.wikimedia.org/wikipedia/commons/0/0b/TDD_Global_Lifecycle.png)

TDD를 이용한 개발은 크게 **질문 -> 응답 -> 정제** 과정을 반복적으로 수행하며 이뤄진다.

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

