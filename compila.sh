#!/bin/bash
file_name=$1
method=$2
  
# First, compile the Java programs into Java classes
javac App.java
  
# Now pass the arguments to the Java classes
java App ${file_name} ${method}