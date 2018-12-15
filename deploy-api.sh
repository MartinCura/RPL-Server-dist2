#!/bin/bash
# sudo!

BASEDIR="$HOME"
cd $BASEDIR/repo
# Instala tanto el webApp como el monitor
mvn clean install
cp $BASEDIR/repo/rpl-server-api/target/rpl-server-api.war $BASEDIR/wildfly/wildfly-10.1.0.Final/standalone/deployments/
touch $BASEDIR/wildfly/wildfly-10.1.0.Final/standalone/deployments/rpl-server-api.war.dodeploy

### Para el daemon:
# sudo docker build -t rpl rpl-runner
# mvn clean package -PstandaloneApp
