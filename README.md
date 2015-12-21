#### Table of Contents
##### [TEST & BUILD THE APPLICATION](#test-and-build-the-application)
##### [EXECUTE THE JAR IN THE COMMAND LINE](#execute-the-jar-in-the-command-line)
##### [IDE SETUP](#ide-setup)
##### [BEHAVIORAL DRIVEN DEVELOPMENT](#behavioral-driven-development)
##### [JAVADOC](#javadoc)

### <a name="test-and-build-the-application"></a> TEST & BUILD THE APPLICATION

    $ mvn clean verify package

### <a name="execute-the-jar-in-the-command-line"></a> EXECUTE THE JAR IN THE COMMAND LINE

    $ java -jar target/processor-2.0.jar --input.file=<input file> --output.file=<output file>

Example:

    $ java -jar target/processor-2.0.jar --input.file=./src/test/resources/input.txt --output.file=output.txt

You may provide your own input file and specify a different name for the output file.

### <a name="ide-setup"></a> IDE SETUP

Due to the application having been developed using lombok and JBehave, plugins for the following could be installed to
add syntax highlighting and avoid errors and warnings in the editor:

* [Project Lombok](https://projectlombok.org)
* [JBehave](http://jbehave.org)

### <a name="behavioral-driven-development"></a> BEHAVIORAL DRIVEN DEVELOPMENT

The application has BDD integration tests using JBehave, additionally to JUnit unit tests. After the application is built,
JBehave reports will be available in the following HTML file:

    ./target/jbehave/view/reports.html

### <a name="javadoc"></a> JAVADOC

JavaDocs can be browsed in the following HTML file:

    ./target/apidocs/index.html
