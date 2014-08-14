#makefile
#
#For use in CMPSCI 383 (Artificial Intelligence)
#
#Lily Li
#cs383
#Assignment 1: Search
#02/14/2014

compileProg:	
		javac asearch.java

params =
stdinFile = inFile.txt
stdoutFile = outFile.txt

runProg:	
		java asearch $(params) < $(stdinFile) > $(stdoutFile)

#End makefile
