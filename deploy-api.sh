#!/bin/bash
# sudo!

cd $BASEDIR/repo
mvn clean install
sudo docker build -t rpl rpl-runner
mvn clean install -PwebApp
#mvn package -PstandaloneApp
cp $BASEDIR/repo/rpl-server-api/target/rpl-server-api.war $BASEDIR/wildfly/wildfly-10.1.0.Final/standalone/deployments/
touch $BASEDIR/wildfly/wildfly-10.1.0.Final/standalone/deployments/rpl-server-api.war.dodeploy
