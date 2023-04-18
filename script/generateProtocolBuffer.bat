protoc --java_out=..\app\src\main\java chat.proto
protoc --plugin=protoc-gen-grpc-java=protoc-gen-grpc-java-1.54.1-windows-x86_64.exe --grpc-java_out=..\app\src\main\java chat.proto
pause
