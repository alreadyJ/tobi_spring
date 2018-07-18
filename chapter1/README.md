# Chapter 1
<hr/>

- java bean이란?
<br> 자바빈은 원래 비주얼 툴에서 조작 가능한 컴포넌트를 말한다.
<br> 이제는 디폴트 생성자와 프로퍼티 접근자 메소드 이 두 가지를 갖는 오브젝트를 가리킨다.
<br> 스프링에서의 빈은 스프링이 제어권을 가지고 직접 만들고 관계를 부여하는 오브젝트이며 IoC가 적용된 오브젝트이다.

- interface와 상속을 이용한 DI 적용
<br> 이는 design pattern 중에 factory method pattern과 template method pattern으로 구현할 수 있다.
<br><br>• 기능의 일부를 abstract 로 override 하도록 하는 패턴이 ````template method pattern````이다.
<br>• Sub-class 에서 구체적인 생성 방법을 결정하도록 하는 것이 ````factory method pattern````이다.
<br>• UserDaoFactory-UserDao-ConnectionMaker의 구조는 ````strategy pattern````에 해당한다.
그 이유는 인터페이스를 통해 필요한 로직을 외부로 분리시켜 독립적인 책임을 갖도록 만들기 때문에 대체 가능한 전략이라고 볼 수 있다.
<br><br>
정리를 하자면 컨텍스트(UserDao)를 사용할 클라이언트(UserDaoFactory)는 컨텍스트가 사용할 전략(ConnectionMaker)을 컨텍스트의 생성자 등을 통해 제공해주는 게 일반적이다. 
<br><br>
• 스프링에서는 빈의 생성과 관계설정 같은 ````제어````를 담당하는 IoC 오브젝트를 빈 팩토리 또는 애플리케이션 컨텍스트라고 부른다.
<br>• 애플리케이션 컨텍스트는 별도의 정보를 참고해서 빈의 생성 관계 설정 등의 제어 작업을 총괄한다.