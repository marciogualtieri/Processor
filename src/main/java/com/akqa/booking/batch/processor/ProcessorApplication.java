package com.akqa.booking.batch.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.akqa.booking.components.services.BookingBatchProcessorService;
import com.akqa.booking.exceptions.BookingException;

@SpringBootApplication
@ComponentScan("com.akqa.booking.components")
public class ProcessorApplication implements CommandLineRunner {

    private static final String APPLICATION_FAILURE_MESSAGE_FORMAT = "Batch Booking Request Processing failed with the following exception: %s";

    @Autowired
    private BookingBatchProcessorService bookingBatchProcessorService;
    @Value("${input.file}")
    private String inputFileNameAndPath;
    @Value("${output.file}")
    private String outputFileNameAndPath;

    @Override
    public void run(String... args) {
        try {
            this.bookingBatchProcessorService.processBatchFile(
                    inputFileNameAndPath, outputFileNameAndPath);
        } catch (BookingException e) {
            System.out.println(String.format(
                    APPLICATION_FAILURE_MESSAGE_FORMAT, e.getMessage()));
        }
    }

    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(
                ProcessorApplication.class);
        application
                .setApplicationContextClass(AnnotationConfigApplicationContext.class);
        SpringApplication.run(ProcessorApplication.class, args);
    }
}
