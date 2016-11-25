# RPL-Server

## Installation

#### Compile application
Web application (for a container deployment): `mvn clean install -P webApp`
Standalone Application (default profile): `mvn clean install`


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


## Test
Run Daemon
`sudo java -jar rpl-daemon/target/rpl-daemon-0.0.1.jar`

Create Activity
`curl -X post -H "Content-Type: application/json" -d '{"name": "Print hello", "language": "PYTHON", "points": 10, "testType": "INPUT", "input": "", "output": "hello"}' http://localhost:8080/rpl-server-api/courses/1/activities`

Create Submission
`curl -X post -H "Content-Type: application/json" -d '{"code": "print \"hello\""}' http://localhost:8080/rpl-server-api/activities/1/submission`

Get Activity
`curl http://localhost:8080/rpl-server-api/activities/1`

Get Submission
`curl http://localhost:8080/rpl-server-api/submissions/1`