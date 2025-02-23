#!/bin/bash

# Stop on error
set -e

# keep the stdin
/bin/bash

# start app
java -jar /opt/app/app.jar --spring.profiles.active=docker