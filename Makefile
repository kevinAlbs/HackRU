# simple makefile for compiling three java classes
#
## define a makefile variable for the java compiler
#
JCC = javac

# define a makefile variable for compilation flags
# the -g flag compiles with debugging information
 
JFLAGS = -g

CLASSPATH = Java-WebSocket/dist/java_websocket.jar

default: Start.class

Start.class: MyWebSocketServer.java InputEmulator.java Start.java RunServer.java
	$(JCC) $(JFLAGS) -cp $(CLASSPATH) MyWebSocketServer.java InputEmulator.java Start.java RunServer.java

clean:
	$(RM) *.class mod_ui.html mod_rotate.html

