# kafka-simple-producer
kafka-simple-producer

# Pre-requisites
## Zookeeper running on localhost (port 2181)
## Kafka broker is running on localhost (port 9092)

# To run the application use below command. By default the application will not produce anything.. 
```
./mvnw spring-boot:run
```

# If you want to simulate sending dummy messages onto the topic named "sample-topic"
```
./mvnw spring-boot:run -Dspring-boot.run.arguments="--produce.dummy.payload=true"
```

# If you want to simulate sending OMS messages onto the topic named "oms-topic"
```
./mvnw spring-boot:run -Dspring-boot.run.arguments="--produce.oms.payload=true"
```
