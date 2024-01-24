#!/bin/bash

# Compile the Java files
javac interpreter_gui/ArithmeticInterpreter.java interpreter_gui/Expression.java

# Create the JAR file
jar cvfm ArithmeticInterpreter.jar Manifest.txt interpreter_gui/*.class

# Run the JAR file
java -jar ArithmeticInterpreter.jar
