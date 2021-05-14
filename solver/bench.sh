#!/bin/bash
# This script will create graphs and run our solver to get the execution times of R-CCat
# Accepts parameters to configure the graph generation
# Usage:        $./bench.sh args

now=$(date +"%Y-%m-%d_%X")

if [[ $# -eq 0 ]]; then
	java -cp ./AFGen.jar net.sf.jAFBenchGen.jAFBenchGen.Generator -help
	echo "numargs is already determined"
else
	mkdir test_${now}

	for ((i = 1; 11 - $i; i++)); do
		nbArgs=$((75 * $i))

		mkdir test_${now}/tests_${nbArgs}args

		for ((j = 0; 10 - $j; j++)); do

			java -cp ./AFGen.jar net.sf.jAFBenchGen.jAFBenchGen.Generator -numargs $nbArgs $* >test_${now}/tests_${nbArgs}args/graph$j.apx
			{ time java -cp ./target/solver-1.0-SNAPSHOT.jar com.bf.solver.App -p R-CCat -f test_${now}/tests_${nbArgs}args/graph$j.apx -fo apx >test_${now}/tests_${nbArgs}args/results_graph$j.txt 2>stderr; } 2>>test_${now}/benchtime.txt

		done

	done

	rm stderr

	./bench.py test_${now}/benchtime.txt
fi
