#!/bin/bash
BASEDIR="$HOME"
. "$HOME"/repo/rpl.env
sudo "$BASEDIR"/wildfly/wildfly-10.1.0.Final/bin/standalone.sh -b=0.0.0.0 -bmanagement=0.0.0.0
