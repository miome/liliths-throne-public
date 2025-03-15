#!/bin/bash



cd "$(dirname "$0")"
source /home/saewill/jdk1.8.0_172/setup.sh
mvn package -e -T16 -ff 
