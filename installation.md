# INSTALLATION GUIDE

## Prerequisites

### Java
```
sudo apt-get install -y software-properties-common
sudo add-apt-repository -y ppa:webupd8team/java
sudo echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
sudo echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections
sudo apt-get update -y
sudo apt-get install -y oracle-java8-installer
```
### Rabbitmq
```
echo 'deb http://www.rabbitmq.com/debian/ testing main' | tee /etc/apt/sources.list.d/rabbitmq.list
wget -O- https://www.rabbitmq.com/rabbitmq-release-signing-key.asc | apt-key add -
apt-get update
apt-get install rabbitmq-server

rabbitmqctl add_user rpl rpl
rabbitmqctl set_permissions -p / rpl ".*" ".*" ".*"
```
### Postgres
```
apt-get install postgresql postgresql-contrib
sudo -u postgres psql
create user rpl with password 'rpl';
create database rpldb;
grant all privileges on database rpldb to rpl;
editar sudo vi /etc/postgresql/10/main/pg_hba.conf 
sacar peer y poner md5 en local 
```
### Docker
```
apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
apt-add-repository 'deb https://apt.dockerproject.org/repo ubuntu-xenial main'
apt-get update
apt-get install docker-engine
```
## Files

* Dockerfile
* extras/
* rpl-daemon-0.0.1.jar
* rpl-runner-0.0.1.jar
* script.sql
* rpl-server-api.war
* web-app.war

## Installation

### Core
```
psql -d rpldb -U rpl < script.sql
docker build -t rpl .
sudo java -jar rpl-daemon-0.0.1.jar
```
### Wildfly
```
wget http://download.jboss.org/wildfly/10.1.0.Final/wildfly-10.1.0.Final.tar.gz
tar -xvzf wildfly-10.1.0.Final.tar.gz
./wildfly-10.1.0.Final/bin/add-user.sh
./wildfly-10.1.0.Final/bin/standalone.sh -b=0.0.0.0 -bmanagement=0.0.0.0
```
