@echo off
echo gen java thrift interface
thrift --gen java -out ../src/main/java DataHanding.thrift