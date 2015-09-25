+-------------------------------------+
| TABLE OF CONTENTS                   |
+-------------------------------------+

- TEST & BUILD THE APPLICATION ........
- EXECUTE THE JAR IN THE COMMAND LINE .
- ECLIPSE .............................
- BEHAVIORAL DRIVEN DEVELOPMENT .......
- JAVADOC     .........................

+-------------------------------------+
| TEST & BUILD THE APPLICATION        |
+-------------------------------------+

$ mvn clean verify package

+-------------------------------------+
| EXECUTE THE JAR IN THE COMMAND LINE |
+-------------------------------------+

java -jar target/processor-2.0.jar --input.file=<input file> --output.file=<output file>

Example:

$ java -jar target/processor-2.0.jar --input.file=./src/test/resources/input.txt --output.file=output.txt

You may provide your own input file and specify a different name for the output file.

+-------------------------------------+
| ECLIPSE                             |
+-------------------------------------+

Due to the application having been developed using lombok and JBehave, the following plugins could be installed to
add syntax highlighting and avoid errors and warnings in the editor:

- https://projectlombok.org/download.html
- http://jbehave.org/eclipse-integration.html

+-------------------------------------+
| BEHAVIORAL DRIVEN DEVELOPMENT       |
+-------------------------------------+

The application has BDD integration tests using JBehave, additionally to JUnit unit tests. After the application is built,
JBehave reports will be available in the following HTML file:

./target/jbehave/view/reports.html

+-------------------------------------+
| JAVADOC                             |
+-------------------------------------+

JavaDocs can be browsed in the following HTML file:

./target/apidocs/index.html
