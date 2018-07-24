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
sudo echo 'deb http://www.rabbitmq.com/debian/ testing main' | sudo tee /etc/apt/sources.list.d/rabbitmq.list
sudo wget -O- https://www.rabbitmq.com/rabbitmq-release-signing-key.asc | sudo apt-key add -
sudo apt-get update
sudo apt-get install rabbitmq-server

sudo rabbitmqctl add_user rpl rpl
sudo rabbitmqctl set_permissions -p / rpl ".*" ".*" ".*"
```
### Postgres
```
sudo apt-get install postgresql postgresql-contrib
sudo -u postgres psql
create user rpl with password 'rpl';
create database rpldb;
grant all privileges on database rpldb to rpl;
\q
```
El nombre de base de datos y las credenciales de usuario descriptas
son las default de la aplicación. Si se quiere utilizar otros, será nece-
sario cambiarlo en la configuración de la aplicación, en el archivo RPL-
Server/rpl-datasource/src/main/resources/META-INF/persistence.xml.

Para lograr loguearse es necesario editar la configuracion de postgresql. Ir al directorio de instalacion de postgre (generalmente /etc/postgresql/${VERSION}) al cual llamaremos POSTGRE_HOME.
```
sudo vim ${POSTGRE_HOME}/main/pg_hba.conf
```

Cambiar peer por md5 abajo de donde dice 
"local" is for Unix domain socket connections only

Luego reiniciar Postgres con:
```
sudo service postgresql restart 
```

### Docker
```
sudo apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
sudo apt-add-repository 'deb https://apt.dockerproject.org/repo ubuntu-xenial main'
sudo apt-get update
sudo apt-get install docker-engine
```

### Wildfly

Instalacion de widlfly
```
wget http://download.jboss.org/wildfly/10.1.0.Final/wildfly-10.1.0.Final.tar.gz
tar -xvzf wildfly-10.1.0.Final.tar.gz
```

Ejecucion de wildfly. Si es la primera vez que se instala el proyecto, primero ejecutar los pasos de 'Creacion de un usuario para desplegar'
```
./{WILDFLY_HOME}/bin/standalone.sh -b=0.0.0.0 -bmanagement=0.0.0.0
```

## Installation

### Generacion del esquema de base de datos
```
psql -d rpldb -U rpl < {RPL_SERVER_REPO}/rpl-datasource/src/main/resources/scripts.sql
```
### Compilar el proyecto
Es necesario antes de seguir para generar el jar del daemon.
```
cd {RPL_SERVER_REPO}
mvn clean install
```
### Generacion de la imagen de docker
```
docker build -t rpl {RPL_SERVER_REPO}/rpl-runner
```
### Creacion de un usuario para desplegar
```
./{WILDFLY_HOME}/bin/add-user.sh
What type of user do you wish to add?
a) Management User (mgmt-users.properties)
b) Application User (application-users.properties)
(a): a
Enter the details of the new user to add.
Using realm 'ManagementRealm' as discovered from the existing property files.
Username :{USERNAME}
Password recommendations are listed below. To modify
these restrictions edit the add-user.properties
configuration file.
- The password should be different from the username
- The password should not be one of the following
restricted values {root, admin, administrator}
- The password should contain at least 8 characters, 1
alphabetic character(s), 1 digit(s), 1 non-alphanumeric symbol(s)
Password : {PASSWORD}
Re-enter Password : {PASSWORD}
What groups do you want this user to belong to? (Please
enter a comma separated list, or leave blank for
none)[ ]:
About to add user '{USERNAME}' for realm
'ManagementRealm'
Is this correct yes/no? yes
Is this new user going to be used for one AS process to
connect to another AS process?
e.g. for a slave host controller connecting to the master
or for a Remoting connection for server to server EJB
calls.
yes/no? yes
```

Borrar en `{WILDFLY_HOME}/standalone/configuration/standalone.xml`:
```
<default-bindings context-service="java:jboss/ee/concurrency/context/default" datasource="java:jboss/datasources/ExampleDS" managed-executor-service="java:jboss/ee/concurrency/executor/default" managed-scheduled-executor-service="java:jboss/ee/concurrency/scheduler/default" managed-thread-factory="java:jboss/ee/concurrency/factory/default"/>
```

Por las dudas al finalizar, reiniciar el servidor.

## Despliegue del empaquetado en Wildfly
Para iniciar el despliegue, nos conectaremos a http://localhost:9990 Primero es necesario iniciar sesion con el usuario de despliegue que creamos en el paso anterior. Nos pide user y pass.

Es necesario compilar como una aplicacion web
```
cd {RPL_SERVER_REPO}
mvn clean install -PwebApp
```

Luego entrar a la solapa 'Deployments'. Boton 'Add'. Check a 'Upload a new deployment'. Boton 'Next'. Choose file para seleccionar el war de backend que se encuentra en {RPL_SERVER_REPO}/rpl-server-api/target/rpl-server-api.war. Luego 'Finish'.

Considerar que falta desplegar el frontend.

Una vez terminado el despliegue ya se puede empezar a utilizar la aplicación
desde la URL HOST:PORT/web-app.



## Creacion de un daemon para la compilacion y tests
Este paso es opcional, solo si se quiere compilar los ejercicios y probar localmente.
```
cd {RPL_SERVER_REPO}
mvn package -PstandaloneApp
sudo java -jar rpl-daemon/target/rpl-daemon-0.0.1.jar
```
Considerar que si se quiere volver a hacer un deployment de server-api, es necesario recompilar con otro perfil.





