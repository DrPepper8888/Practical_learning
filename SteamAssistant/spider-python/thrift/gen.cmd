@echo off
echo gen Python Thrift program file
thrift --gen py -out ../ spider.thrift
thrift --gen java -out ../../spider-thrift-api/src/main/java spider.thrift