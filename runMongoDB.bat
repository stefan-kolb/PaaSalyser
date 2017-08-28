@echo off
title Starting MongoDB
echo MongoDB is being started...
c:
cd "C:\Program Files\MongoDB\Server\3.4\bin"
start mongod.exe
echo MongoDB is ready
pause