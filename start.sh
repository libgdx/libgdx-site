#!/bin/sh
set -e

while :
do
	git pull
	
	cd src/main/docs
	npm install
	gulp deploy
	
	rm -r ../webapp/documentation/
	mkdir ../webapp/documentation
	cp -r dist/* ../webapp/
	
	cd ../../../
	
	
	cd src/main/webapp
	./build.sh
	cd ../../..
	mvn clean package
	java -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n -jar target/libgdx-site-jar-with-dependencies.jar
done