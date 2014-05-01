git pull
cd src/main/webapp
./build.sh
cd ../../..
mvn clean package
cp -r src/main/webapp/* /usr/share/nginx/html/
java -jar target/libgdx-site-jar-with-dependencies.jar
