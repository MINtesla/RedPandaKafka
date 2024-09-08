# Produce the executable jar file
mvn package
# Create the topic
java -jar target/redpanda-client-1.0-SNAPSHOT-shaded.jar
# Produce some data
java -cp target/redpanda-client-1.0-SNAPSHOT-shaded.jar org.example.Producer
# Consume it back
java -cp target/redpanda-client-1.0-SNAPSHOT-shaded.jar org.example.Consumer