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
> 상태 기반 테스트는 테스트 대상 클래스의 메소드를 호출하고, 그 결과값과 예상값을 비교하는 식으로 동작한다.
> 
> **행위 기반 테스트(Behavior Base Test)**
> 행위 기반 테스트는 올바른 로직 수행에 판단의 근거로 "특정한 동작 수행 여부"를 이용한다.
> 보통은 메소드의 리턴값이 없거나 리턴값을 확인하는 것만으로는 예상대로 동작했음을 증명하기 어려운 경우에 사용한다.
> 즉, 예상되는 행위들을 미리 시나리오로 만들어 놓고 해당 시나리오대로 동작이 발생했는지 여부를 확인하는 것이 핵심이다.
> 초창기 Mock 프레임워크들은 행위 기반 테스트를 지원하기 위해 만들어졌었다.

최근엔 Mock 프레임워크 자체가 테스트 더블 전체에 걸쳐 지원을 한다.

Mock 프레임워크를 이용해 간단히 클래스만 인스턴스화하면 **더미 객체**와 동일하고, 추가적으로 특정 리턴값을 돌려주도록 만들면 **스텁**, 호출 확인(verify) 기능을 사용하면 **테스트 스파이** 등으로 불린다.

![테스트 더블의 분포 범위](https://i.stack.imgur.com/R8kD3.gif)

**따라서 최근엔 Mocking 한다라고 하면 앞서 적은 행위 기반 테스트 객체를 의미하는 것이 아닌 테스트 더블 객체를 의미한다.**

