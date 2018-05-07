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
java -Dcom.sun.management.jmxremote \
	 -Dcom.sun.management.jmxremote.local.only=false \
     -Dcom.sun.management.jmxremote.port=8001 \ 
     -Dcom.sun.management.jmxremote.rmi.port=8001 \
     -Dcom.sun.management.jmxremote.authenticate=false \
	 -Dcom.sun.management.jmxremote.ssl=false\
     -Djava.rmi.server.hostname=95.216.8.184 \
     -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=n -jar target/libgdx-site-jar-with-dependencies.jar
