package com.akqa.booking.jbehave.components;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.batch.test.AssertFile.assertFileEquals;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.codehaus.plexus.util.FileUtils;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.model.ExamplesTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.akqa.booking.components.services.BookingBatchProcessorService;
import com.akqa.booking.exceptions.BookingException;

@Component
public class BatchProcessorSteps {

    private static final String INPUT_FILE_NAME_AND_PATH = "target/input.txt";
    private static final String OUTPUT_FILE_NAME_AND_PATH = "target/output.txt";
    private static final String EXPECTED_OUTPUT_FILE_NAME_AND_PATH = "target/expected_output.txt";

    @Autowired
    private BookingBatchProcessorService bookingBatchProcessor;

    @BeforeScenario
    public void beforeScenario() {
        FileUtils.fileDelete(INPUT_FILE_NAME_AND_PATH);
        FileUtils.fileDelete(OUTPUT_FILE_NAME_AND_PATH);
        FileUtils.fileDelete(EXPECTED_OUTPUT_FILE_NAME_AND_PATH);
    }

    @Given("an input file with the following lines: $inputFileAsTable")
    public void givenAnInputFileWithTheFollowingBookingRequests(
            @Named("inputFileAsTable") ExamplesTable inputFileAsTable)
            throws IOException {
        String inputFileAsString = extractStringFromExamplesTable(inputFileAsTable);
        FileUtils.fileWrite(INPUT_FILE_NAME_AND_PATH, inputFileAsString);
    }

    @When("I process the booking requests in the file")
    public void whenIProcessTheBookingRequestsInTheFile()
            throws BookingException {
        bookingBatchProcessor.processBatchFile(INPUT_FILE_NAME_AND_PATH,
                OUTPUT_FILE_NAME_AND_PATH);
    }

    @Then("the output file has the following lines: $ouputFileTable")
    public void thenTheOutputFileHasTheFollowingLines(
            @Named("outputFileTable") ExamplesTable outputFileAsTable)
            throws Exception {
        File outputFile = new File(OUTPUT_FILE_NAME_AND_PATH);
        File expectedOutputFile = new File(EXPECTED_OUTPUT_FILE_NAME_AND_PATH);
        String outputFileAsString = extractStringFromExamplesTable(outputFileAsTable);
        FileUtils.fileWrite(expectedOutputFile, outputFileAsString);
        assertThat(outputFile, is(not(nullValue())));
        assertFileEquals(expectedOutputFile, outputFile);
    }

    public String extractStringFromExamplesTable(ExamplesTable examplesTable) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map<String, String> row : examplesTable.getRows()) {
            String value = row.get(examplesTable.getHeaders().get(0));
            stringBuilder.append(value);
            stringBuilder.append("\n");
        }
        String value = stringBuilder.toString();
        return value.substring(0, value.length() - 1);
    }
}