#!/bin/bash
# This script will build the maven project and create a temporary alias
# Accepts no parameters
# Usage:        $./build.sh
#               $solver args

mvn package
#shopt -s expand_aliases
#alias a="echo b"
#type a
#a
#alias solver='
java -cp ./target/solver-1.0-SNAPSHOT.jar com.bf.solver.App $*
#'

