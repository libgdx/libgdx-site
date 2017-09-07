git pull

rm -r libgdx-skins
curl -o libgdx-skins.zip -Lk  https://github.com/libgdx/libgdx-skins/archive/master.zip
unzip libgdx-skins.zip
mv libgdx-skins-master libgdx-skins
rm libgdx-skins.zip

cd libgdx-skins
chmod +x dist.sh
./dist.sh
cd ..
rm -r src/main/webapp/libgdx-skins-dist
mkdir src/main/webapp/libgdx-skins-dist
cp -r libgdx-skins/dist/* src/main/webapp/libgdx-skins-dist/

rm -r /opt/libgdx-skins/
mkdir /opt/libgdx-skins/
mv libgdx-skins/* /opt/libgdx-skins/

cd src/main/docs
npm install
gulp deploy

rm -r ../webapp/documentation/
mkdir ../webapp/documentation
cp -r deploy/documentation/* ../webapp/documentation/

cd ../../../


cd src/main/webapp
./build.sh
cd ../../..
mvn clean package
cp -r src/main/webapp/* /usr/share/nginx/html/
cp -r src/main/webapp/documentation/** /usr/share/nginx/html/documentation/
java -jar target/libgdx-site-jar-with-dependencies.jar
