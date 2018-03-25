#!/bin/sh

if [ $1 = "compile" ]; then
	javac -cp ".:./libs/*" ./src/*.java
fi

if [ $1 = "q1" ] || [ $1 = "q2" ] || [ $1 = "q3" ]; then
        java -cp ".:./libs/*:./src" $1 $2 $3
fi

