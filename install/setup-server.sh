#!/bin/bash
# Setup del RPL-Server. RUN WITH SUDO
BASEDIR="$HOME"
# Assumes source code is in $BASEDIR/repo
# Will install WildFly in $BASEDIR/wildfly

echo 'export LC_ALL="en_US.UTF-8"' >> "$HOME"/.profile
cd "$BASEDIR"/repo

# Java
sudo apt-get update -q
sudo add-apt-repository -y ppa:webupd8team/java
sudo echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
sudo echo debconf shared/accepted-oracle-license-v1-1 seen true | sudo debconf-set-selections
sudo apt-get update -y -q
sudo apt-get install -y -qq oracle-java8-installer
echo

# RabbitMQ
sudo echo 'deb http://www.rabbitmq.com/debian/ testing main' | sudo tee /etc/apt/sources.list.d/rabbitmq.list
sudo wget -nv -O- https://www.rabbitmq.com/rabbitmq-release-signing-key.asc | sudo apt-key add -
sudo apt-get update -q
sudo apt-get install -y -qq rabbitmq-server
sudo rabbitmqctl add_user rpl rpl
sudo rabbitmqctl set_user_tags rpl administrator
sudo rabbitmqctl set_permissions -p / rpl ".*" ".*" ".*"

# PostgreSQL
sudo apt-get install -y -qq postgresql postgresql-contrib
sudo -u postgres psql -f "$BASEDIR"/repo/rpl-datasource/src/main/resources/createDb.sql
# Configurar PostgreSQL
for F in /etc/postgresql/*/main/pg_hba.conf; do
    sudo sed -i "$((`sudo sed -n '/Unix domain socket connections only/=' ${F}`+1))s/peer/md5/" "$F"
done
sudo service postgresql restart

# Docker
sudo apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
sudo apt-add-repository 'deb https://apt.dockerproject.org/repo ubuntu-xenial main'
sudo apt-get update -q
sudo apt-get install -y -qq docker-engine

# Maven
sudo apt-get install -y -qq maven

# Wildfly
cd "$BASEDIR"
mkdir wildfly/
cd wildfly/
wget -nv http://download.jboss.org/wildfly/10.1.0.Final/wildfly-10.1.0.Final.tar.gz
tar -xzf wildfly-10.1.0.Final.tar.gz
cd "$BASEDIR"/repo

# Generación del esquema de base de datos
PGPASSWORD='rpl' psql -d rpldb -U rpl < "$BASEDIR"/repo/rpl-datasource/src/main/resources/scripts.sql

# Creación de usuario para desplegar
"$BASEDIR"/wildfly/wildfly-10.1.0.Final/bin/add-user.sh "rpl" "rplMM!"  # No es lo mismo que correr sin los argumentos, ver installation.md
# Se recomienda cambiar la contraseña default
sed -i 's~<default-bindings context-service="java:jboss/ee/concurrency/context/default" datasource="java:jboss/datasources/ExampleDS" managed-executor-service="java:jboss/ee/concurrency/executor/default" managed-scheduled-executor-service="java:jboss/ee/concurrency/scheduler/default" managed-thread-factory="java:jboss/ee/concurrency/factory/default"/>~~' "$BASEDIR"/wildfly/wildfly-10.1.0.Final/standalone/configuration/standalone.xml

# Enable RabbitMQ management - runs on port 15672
sudo rabbitmq-plugins enable rabbitmq_management


# Compilar el proyecto
mvn -q clean install
