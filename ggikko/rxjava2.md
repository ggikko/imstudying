![alt tag](https://d379ifj7s9wntv.cloudfront.net/reactivemanifesto/images/ribbons/we-are-reactive-blue-left.png)

# Rxjava 2.x


## Rxjava 2.x에 대한 생각

요즘 RxJava 코드를 보면서 조금씩 커밋해보고있다. ReactiveX에 대해서 우선 느낀점으로는 하나의 패러다임임과 동시에 모든 언어가 비슷한 스펙으로 유사한 구현방식을 따라가고 있다고 느낀다.(물론 구현방식은 조금씩 다르겠지만..) 이것만으로도 큰 장점을 갖는다고 생각하는 부분은 Java 개발자가 굳이 Swift를 알지 못하더라도 RxSwift 코드를 보고 이해할 수 있고 반대로 Swift 개발자들은 Java를 알지 못해도 Rxjava코드를 보고 흐름을 파악할 수 있다. 로직을 모두가 볼 수 있게 통합하는 느낌이 든다..

그렇다면,, RxJava 1.x는 왜 deprecated되고 RxJava 2.x를 만들고 있는가. 물론 RxJava 1.x는 UI이벤트와 같은 hot source들에 대한 backpressure가 잘되지 않아서 이기도 하지만  RxJava 2.x는 [reactive-streams](http://www.reactive-streams.org/)와 유사하게 구현하고 있다는 것에 큰 의미가 있다고 생각한다. 그 이유는 reactive-streams가 java 8 + 에 친화적이고 java 9의 flow를 지원하기 위함이다. 안드로이드에서는 java 버전이 낮기 때문에 여러가지 제약사항이 있는데 이렇게 모든 것에 호환시키려다보니 조금의 미래를 바라보지 못한 것 같다. 변경된 점으로는 기존에 Rxjava 1.x function들도 Java 8+에 친화적으로 변경되었다. Action1, Action2... 그리고 Func0, Func1... 이런 메소드들이 Consumer, Function, Predicate java 8+ 메소드 명으로 변경되었다. 즉 안드로이드가 java 8 + 를 지원해도 문제 없이 포팅되도록 유도하려는 의도인 것 같다.

- 1.x에서 backpressure에 왜 실패한걸까(추측..)

궁금해하실 분도 있을 것 같아 1.x가 왜 backpressure에 실패했는지 간략히 적으면 구현하면서 뒤늦게 backpressure을 도입하고 operator들도 급하게 만들어지다보니 어떤 거는 도입되고 어떤거는 도입하지 못하는 구조가 되어버렸다. 즉 크게 아키텍처를 설계하고 작업한게 아니라 하나씩 추가되다보니 돌이킬 수 없어 아에 전체 구조를 변경하고 2.x로 간 것 같다.

이 외에도 다양한 이유가 있는데 의존성이 적다,가볍다 등등 다양한 이유로 reactive-streams를 따라가고 있다. (아래 링크 참조)

여기서 이해가 안될 수 있다. 아니 왜 굳이 라이브러리를 둘로 나누어서 서로 시간낭비하고 있는걸까 하나만들면 더 좋은 라이브러리가 되지 않겠어? 라고 생각할 수 있지만 reactive streams는 java 8+에 친화적이다. 즉 android developer에게는 러닝커브가 존재하고 아직 안드로이드는 1.6(물론 이제 1.7 본격지원한다하지만..)을 기본으로 지원하고 있기 때문에 그러하다.(이미 라이브러리 개발자들도 알고 있음)

[RxJava wiki](
https://github.com/ReactiveX/RxJava/wiki/Reactive-Streams)


reactive streams 알아보면 일관성을 갖기 위해 manifesto도 존재한다 어떤 방식으로 구현해나가자 라는 방법론도 있고 매력있다.

1. Responsive
2. Resilient
3. Elastic
4. Message Driven

이렇게 4가지의 기준이 있다.

## RxJava 2.x는..?
RxJava2를 보면 Observable, Flowable을 나누어 놓았다. 위에서 잠깐 언급했다시피 hot source들에 대한 backpressure가 잘되지 않아서이다. 그래서 공식적으로는 `io.reactivex.Observable`은 non-backpressured 이고 `io.reactivex.Flowable`은 backpressure base다.  Observable은 계속해서 새롭게 새롭게 짜여지고있다. 여러가지 커스텀 타입을 쓰다보니 backPressure(이는 매우 중요하다. 위에 reactive Streams manifesto의 4번째의 주된 이유이기도 하다)도 구현이 안되있고, 이를 구현하는것이 현재로서 구조를 많이 변경해야 하기 때문이다.

3.x가 나오지 않을까 생각해본다.

이미 Rxjava issue thread에서는 3.x에 대한 언급이 나오고 있다.
JakeWharton이 다음과 같은 차트[(Jakewharton's naming chart)](https://github.com/ReactiveX/RxJava/issues/4044#issuecomment-238066355)를 작성하였는데 이 또한 불분명하고 타입을 막 만들고 쓰다보니 backpressure를 적용하기에 모호하다는 것..

아직 모든 소스를 보지 못했지만 조금씩 조금씩.. 보려한다 시간날때마다 :) RxJava의 내부 소스를 보면 Reactive streams 기반으로 모두 Observable, Disposable을 상속받아서 돌려돌려 쓰기 하고 있다. 음.. 구현방법이나 앞으로 나아가는 방향, 어떻게 써야할지 등에 다음 글을 적을까한다. 시간나면..ㅠ

기록용
