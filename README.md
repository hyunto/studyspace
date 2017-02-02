# test_driven_development_book
[테스트 주도 개발 (고품질 쾌속개발을 위한 TDD 실천법과 도구)](http://book.naver.com/bookdb/book_detail.nhn?bid=6291557) 스터디



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

---

> **Stub의 의미**
>
> Stub은 많이 사용되지만 제대로 해석된 적이 거의 없다. 사전에서는 "그루터기", "꼬투리", "토막", "남은 부분" 등의 의미로 사용되지만 IT에서는 종종 "껍데기"로 사용된다.
> 여기선 의미를 더 확장해서 "오리지널을 확인하거나 유추할 수 있는 수준의 껍데기"를 뜻한다.

---

> **fake vs shunt**
> 

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



# 테스트 케이스 작성
초창기 어떤 것을 테스트로 삼을지 찾기가 쉽지 않을 때, 다음 시나리오대로 작성하면 도움이 될 것 같다 :

1. 해피데이 시나리오(Happy Day Scenario) 작성 : 정상적인 흐름일 때 동작해야 하는 결과값을 선정하는 방식
2. 블루데이 시나리오(Blue Day Scenario) 작성 : 발생할 수 있는 예외나 에러 상황에 대한 결과값을 작성
3. 삼각측량법 : 예를 들어 "두 숫자 곱하기(a,b)" 메소드는 a*b와 같은지 비교해보고, a를 b번 더한 것과도 같은지 비교해보는 방식

무엇을 테스트 할지 결정하기 어려울 때, 다음 질문을 해보자 : 
1. 결과가 옳은가?
2. 모든 경계조건이 옳은가?
3. 역(inverse) 관계를 확인할 수 있는가?
4. 다른 수단을 사용해서 결과를 교차확인할 수 있는가?
5. 에러 조건을 강제로 만들어낼 수 있는가?
6. 성능이 한도 내에 있는가?

> 출처 : [실용주의 프로그래머를 위한 단위 테스트 with JUnit](http://book.naver.com/bookdb/book_detail.nhn?bid=1472269)



# Mock
## 언제 Mock 객체를 사용할까?
1. 테스트 작성을 위한 환경 구축이 어려워서
	* 환경 구축을 위한 작업 시간이 많이 필요한 경우에 Mock 객체를 사용한다.
	* 경우에 따라서는 특정 모듈을 아직 갖고 있지 않아서 테스트 환경을 구축하지 못할 수도 있다.
	* 타 부서와의 협의나 정책이 필요한 경우에도 Mock이 필요하다.
2. 테스트가 특정 경우나 순간에 의존적이라서
3. 테스트 시간이 오래 결러셔

## 테스트 더블(Test Double)
"대역, 스턴트맨"을 지칭하는 스턴트 더블(Stunt Double)이라는 단어에서 차용해온 단어로, 오리지널 객체를 사용해서 테스트를 진행하기 어려울 경우 이를 대신해서 테스트를 진행할 수 있도록 만들어주는 객체를 지칭한다.

![Test Double 분류](http://xunitpatterns.com/Types%20Of%20Test%20Doubles.gif)

> 참고
> * [Test Double written by Martin Fowler](https://martinfowler.com/bliki/TestDouble.html)
> * [Test Double in xUnit Patterns](http://xunitpatterns.com/Test%20Double.html)

### 더미 객체(Dummy Object)
말 그대로 멍청한 모조품, 단순한 껍데기에 해당한다.
오로지 인스턴스화 될 수 있는 수준으로만 구현할 뿐 해당 객체의 기능까지는 구현하지 않는다. 따라서 해당 더미 객체의 메소드가 호출됬을 때의 정상 동작은 보장되지 않는다.

```java
public class DummyCoupon implements ICoupon {

	public String getName() {
		return null;
	}

	public boolean isValid() {
		return false;
	}

	public int getDiscountPercent() {
		return 0;
	}

	public void doExpire() {

	}

	public boolean isAppliable(Item item) {
		return false;
	}

}

```

### 테스트 스텁(Test Stub)
더미 객체(Dummy Object)가 마치 실제로 동작하는 것처럼 보이게 만들어 놓은 객체다.
즉, 객체의 특정 상태를 가정해서 만들어 놓은 단순 구현체이다. 따라서 특정한 값을 리턴해주거나 특정 메세지를 출력하는 등의 작업을 수행한다.

```java
public class StubCoupon implements ICoupon {

	public String getName() {
		return "VIP 고객 한가위 감사쿠폰";
	}

	public boolean isValid() {
		return true;
	}

	public int getDiscountPercent() {
		return 7;
	}

	public void doExpire() {

	}

	public boolean isAppliable(Item item) {
		return true;
	}

}
```
위의 예제는 테스트에 사용 여부에 관계없이 모든 값을 지정하였지만, 실제로 스텁을 사용할 때는 테스트에 필요한 메소드 부분만 하드코딩 해놓으면 된다.

스텁은 특정 객체가 상태를 대신해주고 있지만, 거의 하드코딩 된 형태이기 때문에 로직이 들어가는 부분은 테스트 할 수 없다.

> **더미 객체와 스텁 객체의 차이점**
>
> * 단지 인스턴스화 될 수 있는 객체 수준 : 더미 객체
> * 인스턴스화 된 객체가 특정 상태나 모습을 출력 : 스텁 객체

### 페이크 객체(Fake Object)
**스텁**은 하나의 인스턴스를 대표하는데 주로 쓰이고, **페이크**는 여러 개의 인스턴스를 대표할 수 있는 경우이거나, 좀 더 복잡한 구현이 들어가 있는 객체를 지칭한다.

예를 들어 DB를 사용한 로직이 있다면 리스트(List)나 맵(Map)을 이용해서 DB 의존 환경을 대체하는 것이다.

```java
public class FakeCoupon implements ICoupon {
	
	List<String> categoryList = new ArrayList<String>();
	
	public FakeCoupon() {
		categoryList.add("부엌칼");
		categoryList.add("아동 장난감");
		categoryList.add("조리기구");
	}
	
	public boolean isAppliable(Item item) {
		if (this.categoryList.contains(item.getCategory())) {
			return true;
		}
		return false;
	}
	
	...
}
```

**페이크** 객체는 복잡한 로직이나 객체 내부에서 필요로 하는 다른 외부 객체들의 동작을 비교적 단순화하여 구현한 객체다. 결과적으로 테스트 케이스 작성을 진행하기 위해 필요한 다른 객체(혹은 클래스)들과의 의존성을 제거하기 위해 사용된다.

페이크 객체를 만들 때는 적절한 수준에수 구현을 접고, Mock 프레임워크 등을 사용하던지 실제 객체를 직접 가져와서 테스트 케이스 작성에 사용하는 것을 권장한다.

### 테스트 스파이(Test Spy)
**테스트 스파이**는 특정 객체가 사용되었는지 그리고 그 객체의 예상된 메소드가 정상적으로 호출되었는지 확인해야 할 때, 호출 여부를 기록했다가 나중에 요청이 들어오면 해당 기록 정보를 전달할 목적으로 만들어 졌다.

특정 메소드의 정상호출 여부 확인을 목적으로 구현되며, 테스트 더블로 구현된 객체 전 범위에 걸쳐 **테스트 스파이** 기능을 추가할 수 있다.

```java
public class SpyCoupon implements ICoupon {
	
	List<String> categoryList = new ArrayList<String>();
	
	private int isAppliableCallCount;
	
	public SpyCoupon() {
		categoryList.add("부엌칼");
		categoryList.add("아동 장난감");
		categoryList.add("조리기구");
	}
	
	public boolean isAppliable(Item item) {
		isAppliableCallCount++;	// 호출되면 증가
		if (this.categoryList.contains(item.getCategory())) {
			return true;
		}
		return false;
	}
	
	public int getIsAppliableCallCount() {	// 몇 번 호출됬나?
		return this.isAppliableCallCount;
	}

	...
}

@Test
public void testGetOrderPrice_discountableItem() throws Exception {
	PriceCalculator calculator = new PriceCalculator();

	// new Item(이름, 카테고리, 가격)
	Item item = new Item("LightSavor", "부엌칼", 100000);
	ICoupon coupon = new SpyCoupon();

	assertEquals("쿠폰으로 인해 할인된 가격", 93000, calculator.getOrderPrice(item, coupon));

	int methodCallCount = ((SpyCoupon)coupon).getIsAppliableCallCount();
	assertEquals("coupon.isAppliable 메소드 호출 횟수", 1, methodCallCount);
}
```

보통 테스트 스파이는 Mock 프레임워크에서 제공하는 기능을 사용하는 것이 더 쉽고 일반적이다.

### Mock 객체(Mock Object)
행위를 검증하기 위해 사용되는 객체를 지칭한다.
주로 Mock 프레임워크를 사용한다.

일반적인 테스트 더블은 상태 기반 테스트 케이스를 작성한다. Mock 객체는 행위 기반 테스트 케이스를 작성한다.
그러나 보통 행위 기반 테스트는 복잡한 시나리오가 사용되는 경우가 많고, 모양 및 작성 측면에서 어색한 경우가 많다. 따라서 만일 상태 기반 테스트를 수행할 수 있는 상황이라면 굳이 행위 기반 테스트는 만들지 않는 것이 좋다.

> **상태 기반 테스트(State Base Test)**
> 
> 상태 기반 테스트는 테스트 대상 클래스의 메소드를 호출하고, 그 결과값과 예상값을 비교하는 식으로 동작한다.
> 
> ---
> **행위 기반 테스트(Behavior Base Test)**
> 
> 행위 기반 테스트는 올바른 로직 수행에 판단의 근거로 "특정한 동작 수행 여부"를 이용한다.
> 보통은 메소드의 리턴값이 없거나 리턴값을 확인하는 것만으로는 예상대로 동작했음을 증명하기 어려운 경우에 사용한다.
> 즉, 예상되는 행위들을 미리 시나리오로 만들어 놓고 해당 시나리오대로 동작이 발생했는지 여부를 확인하는 것이 핵심이다.
> 초창기 Mock 프레임워크들은 행위 기반 테스트를 지원하기 위해 만들어졌었다.

최근엔 Mock 프레임워크 자체가 테스트 더블 전체에 걸쳐 지원을 한다.

Mock 프레임워크를 이용해 간단히 클래스만 인스턴스화하면 **더미 객체**와 동일하고, 추가적으로 특정 리턴값을 돌려주도록 만들면 **스텁**, 호출 확인(verify) 기능을 사용하면 **테스트 스파이** 등으로 불린다.

![테스트 더블의 분포 범위](https://i.stack.imgur.com/R8kD3.gif)

**따라서 최근엔 Mocking 한다라고 하면 앞서 적은 행위 기반 테스트 객체를 의미하는 것이 아닌 테스트 더블 객체를 의미한다.**


## Mock 프레임워크
### 장점
* Mock 객체를 직접 작성해서 명시적인 클래스로 만들지 않아도 된다.
* Mock 객체에 대해서 행위까지도 테스트 케이스에 포함시킬 수 있다.

### Mockito
간단히 말해서 EasyMock, jMock 등 이전의 Mock 프레임워크의 단점을 보완하기 위해 나온 Mock 프레임워크.
이전의 Mock 프레임워크는 행위 기반 테스트에 집중했다면 Mockito는 상태 기반 테스트에 집중한다.

### Mockito의 장점
1. 테스트 자체에 집중한다 : 테스트의 행위와 반응(Interaction)에만 집중해서 테스트 메소드를 작성할 수 있게 한다.
2. 테스트 스텁을 만드는 것과 검증을 분리시켰다.
3. Mock 만드는 방법을 단일화했다.
4. 테스트 스텁을 만들기 쉽다.
5. API가 간단하다.
6. 프레임워크가 지원해주지 않으면 안되는 코드를 최대한 배제했다
7. 실패 시에 발생하는 에러 추적(Stack Trace)가 깔끔하다.

### Mockito 사용법
기존 프레임워크는 **스텁->예상->수행->검증**의 단계를 거쳤다면 Mockito는 **스텁->수행->검증**으로 단순화했다.

Mockito는 Stub 작성과 Verify가 중심을 이루며 다음 순서로 진행된다 :

| 순서 | 내용 | 예제 |
| --- | --- | --- |
| Mock 객체  생성 | 인터페이스에 해당하는 Mock 객체를 만든다. | `Mockito.mock(타깃 인터페이스);` |
| Stub 생성 | 테스트에 필요한 Mock 객체의 동작을 지정한다. (단, 필요시에만) | `when(Mock_객체의_메소드).thenReturn(리턴값);` `when(Mock_객체의_메소드).thenThrow(예외);` |
| 수행 (Exercise) | 테스트 메소드 내에서 Mock 객체를 사용한다. |  |
| 검증 (Verify) | 메소드가 예상대로 호출됐는지 검증한다. | `verify(Mock_객체).Mock_객체의_메소드;` `verify(Mock_객체, 호출횟수지정_메소드).Mock_객체의_메소드;` |

### Argument Matcher

| 종류 | 내용 |
| --- | --- |
| **any 타입** | anyInteger(), anyBoolean(), any() 등의 Java 타입. null이거나 해당 타입이면 만족한다. |
| anyCollection, anyCollectionOf | List, Map, Set 등 Collection 객체면 무방하다. 자연스런 문장을 위해 사용한다. |
| argThat(HamcrestMatcher) | HamcrestMatcher 부분에  Hamcrest Matcher를 사용하려 할때 사용할 수 있다. |
| **eq** | Argument Matcher에서 한번 사용된 Java 타입을 그대로는 쓸 수 없다. 이때 사용된다. |
| **anyVararg** | 여러 개의 인자를 지칭할 때 사용한다. |
| matches(String regex) | 정규표현식으로 인자(Argument) 대상을 지칭한다. |
| startwith(String), endWith(String) | 특정 문자열로 시작하거나 끝이면 OK |
| anyList, anyMap, anySet | anyCollection의 디테일 버전. 해당 타입이면 만족한다. |
| isA(Class) | 해당 클래스 타입인지 체크 |
| isNull | Null 이면 OK |
| isNotNull | Null 아니면 OK |

### 순서 검증
Stub으로 만들어진 Mock 객체의 메소드 호출 순서까지 검증하려 한다면 `InOrder 클래스`를 사용한다.

```java
List firstMock = mock(List.class);
List secondMock = mock(List.class);

firstMock.add("item1");
secondMock.add("item2");

InOrder inOrder = inOrder(firstMock, secondMock);
inOrder.verify(firstMock).add("item1");
inOrder.verify(secondMock).add("item2");
```

### Mockito의 특징적인 기능
#### 1. void 메소드를 Stub으로 만들기
일반적으로 void 메소드는 리턴할 내용이 없어 Stub으로 만들지 않지만, 예외(Exception) 처리에 대한 Stub이 필요할 경우 doThrow를 사용한다 : `doThrow(예외).when(Mock_객체).voidMethod();`

#### 2. 콜백으로 Stub 만들기 : thenAnswer
Mock은 보통 하드코딩된 값만 돌려주도록 만들지만, 특정 Mock 메소드에 대해 실제 로직을 구현하려 할때 콜백(CallBack) 기법을 사용한다. (비권장)

```java
when(rs.getInt("no")).thenAnswer( new Answer<Integer>() {
	public Integer answer(InvocationOnMock invocation) throws Throwable {
		... (생략) ...
	}
});
```

#### 3. 실제 객체를 Stub으로 만들기 : SPY
실 객체도 Mock으로 만들 수 있는 강력한 기능으로 부분 Mocking이라고 불린다.
서드파티 제품, 고칠 수 없는 라이브러리만 남아 있는 코드 등에 대해서만 한정적으로 사용하는 것을 권장한다.
Mockito 저자는 spy 기능을 사용하면 이미 잘못된 코드를 건드리고 있다는 증거라고 이야기 하였다.

#### 4. 똑똑한 NULL 처리 : SMART NULLS
Stub으로 만들지 않은 메소드는 기본적으로 null을 리턴하며 이는 NullPointerException을 유발한다. SMART NULLS는 null 대신 규칙에 의거하여 좀 더 유용한 값으로 기본값을 리턴하도록 해준다.

* SMART NULLS 규칙
	* Primitive Wrapper 클래스는 해당 기본형 값으로 바꾼다.
	* String은 ""로 바꾼다.
	* 배열은 크기 0인 기본 배열 객체로 만들어준다.
	* Collection 계열은 빈 Collection 객체로 만든다.

#### 5. 행위 주도 개발(BDD) 스타일 지원
Mockito는 `//given  //when  //then` 스타일의 행위 주도 개발(Behavior-Driven Development, BDD) 방식으로 테스트 케이스를 작성할 수 있게 지원한다.

이를 사용하려면 Mockito 클래스 대신 BDDMockito를 import해야 한다.


## Mock 사용시 유의사항
* Mock 프레임워크가 정말 필요한지 잘 따져본다.
	* 개발하면서 자연스럽게 Mock 객체가 필요한 부분이 나오는게 아니라 Mock 객체가 적용될만한 부분을 찾으려는 역전현상이 발생할 수도 있다.
	* Mock 프레임워크를 사용하면 테스트 케이스 유지에 많은 비용이 들 수도 있다.
	* Mock 객체들은 깨지기 쉬운 테스트 케이스가 되는 경향이 있다.
	* 가능하다면 설계를 바꿔서라도 Mock이 필요 없는 구조로 만드는게 좋다.
* 투자 대비 수익(ROI)이 확실할 때만 사용한다.
	* 테스트용 DB를 세팅하는게 나을까? 아니면 DB 연관 기능을 Mock 객체로 만드는 것이 나을까?
	* Mock을 사용하면 빠르게 테스트가 가능하지만 장기적으로 Mock 객체가 늘어나면서 관리가 어려워 질 수도 있다.
	* fake vs shunt
* 어떤 Mock 프레임워크를 사용하느냐는 핵심적인 문제가 아니다.
* Mock은 Mock일 뿐이다.
	* Mock 객체로 아무리 잘 동작하는 코드를 만들어도 실제 객체를 사용했을 때도 100% 잘 동작하리란 보장은 없다.
	* 초반부터 실제 객체를 테스트에 사용할 수 있으며 그 비용이 크지 않다면 Mock 객체를 사용하지 말아라.

## 궁극의 TDD 템플릿
Mockito 개발자가 제안한 BDD 스타일의 템플릿.
다음과 같이 주석을 달아 Context를 나누어 테스트 케이스를 작성하는 방식이다.

```java
@Test
public void shouldDoSomethingCool() throws Exception {
	// given : 선행조건 기술
	
	// when : 기능 수행
	
	// then : 결과 확인
}
```

> 참고 : [//given //when //then forever](https://monkeyisland.pl/2009/12/07/given-when-then-forever/)