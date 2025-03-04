#!/bin/bash



cd "$(dirname "$0")"
source /home/saewill/jdk1.8.0_172/setup.sh
mvn package -e -T12 -ff -f pom8.xml
