# RPL-Server

## Installation

#### Compile application
Web application (for a container deployment): mvn clean install -P webApp
Standalone Application (default profile): mvn clean install


#### RabbitMQ
##### Install
```
echo 'deb http://www.rabbitmq.com/debian/ testing main' | sudo tee /etc/apt/sources.list.d/rabbitmq.list
wget -O- https://www.rabbitmq.com/rabbitmq-release-signing-key.asc | sudo apt-key add -
sudo apt-get update
sudo apt-get install rabbitmq-server
```
##### Set user and permissions
```
sudo rabbitmqctl add_user rpl rpl
sudo rabbitmqctl set_permissions -p / rpl ".*" ".*" ".*"
```

#### PostgreSQL
`psql -d rpldb -U rpl < rpl-datasource/src/main/resources/scripts.sql`


#### Docker
`docker build -t rpl .`


## Test:
```
sudo java -jar rpl-daemon/target/rpl-daemon-0.0.1.jar
curl -X post -d "print 'hello'" http://localhost:8080/rpl-server-api/activities/1/submission
curl http://localhost:8080/rpl-server-api/submissions/1
```


