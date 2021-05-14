#!/bin/bash
# This script will build the maven project and run the application
# Accepts parameters
# Usage:        $./build.sh args

mvn package

java -cp ./target/solver-1.0-SNAPSHOT.jar com.bf.solver.App $*
