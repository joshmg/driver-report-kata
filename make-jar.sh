
mkdir -p bin
./gradlew makeJar && cp build/libs/driver-report-all-0.1.0.jar bin/main.jar && chmod 777 bin/main.jar

echo "Done."
echo 

