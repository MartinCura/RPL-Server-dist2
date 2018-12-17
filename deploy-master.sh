#!/bin/bash
# Run with sudo!
# Adapt for the location of your copy of the source code and WildFly installation
BASEDIR="$HOME"
cd ${BASEDIR}/repo

mvn clean install -PwebApp
cp ${BASEDIR}/repo/rpl-server-api/target/rpl-server-api.war ${BASEDIR}/wildfly/wildfly-10.1.0.Final/standalone/deployments/
touch ${BASEDIR}/wildfly/wildfly-10.1.0.Final/standalone/deployments/rpl-server-api.war.dodeploy
mvn package -Pmonitor
