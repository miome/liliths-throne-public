#!/bin/bash



cd "$(dirname "$0")"

source /home/saewill/jdk-11.0.15+10/setup.sh
mvn package -e -T12 -ff
