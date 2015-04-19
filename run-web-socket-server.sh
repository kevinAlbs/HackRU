#Compiles and runs web socket server
javac -cp Java-WebSocket/dist/java_websocket.jar MyWebSocketServer.java InputEmulator.java
java -cp Java-WebSocket/dist/java_websocket.jar:. MyWebSocketServer 
