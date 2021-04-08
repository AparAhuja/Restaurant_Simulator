all:
	javac -d classes/ -cp classes/ IllegalNumberException.java
	javac -d classes/ -cp classes/ MMBurgersInterface.java
	javac -d classes/ -cp classes/ MMBurgers.java
	javac -d classes/ -cp classes/ Tester2.java
	java -cp classes/ Tester2

clean:
	rm classes/*.class
 
