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
java java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=9000 -jar target/libgdx-site-jar-with-dependencies.jar
