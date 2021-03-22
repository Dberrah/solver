#!/bin/bash
# This script will build the maven project and create a temporary alias
# Accepts no parameters
# Usage:        $./bench.sh

if [[ $# -eq 0 ]];
then
    java -cp ./AFGen.jar net.sf.jAFBenchGen.jAFBenchGen.Generator -help
    echo "numargs is already determined"
else
    touch benchtime.txt
    touch graph_history.apx
    touch results_history.txt

    rm benchtime.txt
    rm graph_history.apx
    rm results_history.txt

    for ((i = 1; 11 - $i; i++)); do
        nbArgs=$((75 * $i))

        java -cp ./AFGen.jar net.sf.jAFBenchGen.jAFBenchGen.Generator -numargs $nbArgs $* >benchtest.apx

        { time java -cp ./target/solver-1.0-SNAPSHOT.jar com.bf.solver.App -p R-CCat -f benchtest.apx -fo apx >stdout 2>stderr; } 2>>benchtime.txt

        benchtest.apx >> graph_history.apx
        stdout >> results_history.txt

        echo "" >> graph_history.apx
        echo "" >> results_history.txt
    done

    rm stdout
    rm stderr

    ./bench.py
fi
