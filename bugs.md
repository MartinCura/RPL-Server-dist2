BUGS
====

* Si levanto 2 daemons, se traba todo el sistema
* Si recibe varios submits al mismo tiempo, lee todos solo lanzando el primero
* Después de algunos tests, quedó mucha memoria ocupada aun si no se estaba usando
* 2 daemon alcanzan para sobrepasar la memoria, o se cuelga o lo mata (es más resilient)

java.util.NoSuchElementException
	at java.util.Scanner.throwFor(Scanner.java:862)
	at java.util.Scanner.next(Scanner.java:1371)
	at com.rpl.service.util.FileUtils.fileToString(FileUtils.java:19)
	at com.rpl.daemon.Tester.runSubmission(Tester.java:29)
	at com.rpl.daemon.Daemon.main(Daemon.java:30)

* Recomendar el cambio de contraseña, o pedir el input de una
* Hay problemas en los pom.xml (mirar build de maven)
* Ver los problemas que tira maven y wildfly
