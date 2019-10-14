all: clean compile run

clean:
	$(RM) *.class

compile:
	javac A3.java

run:
	java A3 30 3 ../SampleInputOutput/S1/process1.txt ../SampleInputOutput/S1/process2.txt ../SampleInputOutput/S1/process3.txt ../SampleInputOutput/S1/process4.txt;\
	java A3 30 3 ../SampleInputOutput/S2/process1.txt ../SampleInputOutput/S2/process2.txt ../SampleInputOutput/S2/process3.txt

report:
	pandoc Report.md -s --highlight-style=pygments -o Report.pdf
