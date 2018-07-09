/*
method Dispatch: 어떤 메소드를 호출할 것인가 결정하는 과정
dispatch는 static과 dynamic 이 존재 함.
*/

public class Dispatch {
    static class Service {
        void run(int number) {
            System.out.println("run("+ number +")");
        }

        void run(String msg) {
            System.out.println("run( " + msg + ")");
        }

//------------------------------------------------------------//

        static abstract class Service {
            abstract void run();
        }

        static class MyService1 extents Service {
            @Override
            void run() {
                System.out.println("run(1)");
            }
        }


        static class MyService2 extents Service {
            @Override
            void run() {
                System.out.println("run(2)");
            }
        }
    }
  
    public static void main(Stirng[] args) {
         new Service().run(1);
         new Service().run("Dispatch");

         MyService1 svc = new MyService1();
         svc.run();
         //>> run(1)
         MyService2 svc2 = new MyService2();
         svc2.run();
         //>> run(2)
         // 아래의 경우를 보자

        Service svcc = new MyService1();
        svcc.run();
        //>> run(1)
        /*
          이 경우, MyService1의 run()인지 MyService2의 run()인지 알 수
          없음. 

          이 때, 일어나는 것이 dynamic dispatching 이다.
          Object가 뭔지 보고 매칭을 하는 것.

          메소드 호출 과정에 receiver parameter라는 것이 있음.
          svcc라는 Object의 this에 해당하는 Object(MyService1)가 
          receiver parameter에 들어 있음.
          따라서 이 경우에는 동적으로 결정을 하게 되는 것임.
        */

        List<Service> svcl = Arrays.asList(new MyService1(), new Myservice2());
        //svcl.forEach(s -> s.run());
        svcl.forEach(Service::run;
        
    }

    /*
      compiler가 compile 시점에 run()이 실행되는 것을 알 수 있는가? 
      알고 있음. 이런 것을 static dispatching이라고 함.
    */
    
}
