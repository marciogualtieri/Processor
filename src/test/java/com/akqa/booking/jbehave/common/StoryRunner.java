package com.akqa.booking.jbehave.common;

import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.core.reporters.Format.HTML;
import static org.jbehave.core.reporters.Format.STATS;
import static org.jbehave.core.reporters.Format.TXT;
import static org.jbehave.core.reporters.Format.XML;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import org.jbehave.core.Embeddable;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.i18n.LocalizedKeywords;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.ExamplesTableFactory;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.ParameterConverters;
import org.jbehave.core.steps.ParameterConverters.DateConverter;
import org.jbehave.core.steps.ParameterConverters.ExamplesTableConverter;
import org.jbehave.core.steps.spring.SpringApplicationContextFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.springframework.context.ApplicationContext;

public class StoryRunner extends JUnitStories {

    private static String APPLICATION_CONTEXT = "spring/applicationContext.xml";
    private static String DATE_FORMAT_STRING = "yyyy-MM-dd";
    private static String STORY_FILES = "**/*.story";

    @Override
    public Configuration configuration() {
        Class<? extends Embeddable> embeddableClass = this.getClass();
        ParameterConverters parameterConverters = new ParameterConverters();
        ExamplesTableFactory examplesTableFactory = createExamplesTableFactory(
                embeddableClass, parameterConverters);
        ExamplesTableConverter examplesTableConverter = new ExamplesTableConverter(
                examplesTableFactory);
        Properties viewProperties = createViewProperties();
        StoryReporterBuilder storyReporterBuilder = createStoryReporterBuilder(
                embeddableClass, viewProperties, CONSOLE, TXT, HTML, XML, STATS);
        parameterConverters.addConverters(new DateConverter(
                new SimpleDateFormat(DATE_FORMAT_STRING)),
                examplesTableConverter);
        return createConfiguration(embeddableClass, parameterConverters,
                examplesTableFactory, storyReporterBuilder);
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        ApplicationContext context = new SpringApplicationContextFactory(
                APPLICATION_CONTEXT).createApplicationContext();
        return new SpringStepsFactory(configuration(), context);
    }

    @Override
    protected List<String> storyPaths() {
        return new StoryFinder().findPaths(
                codeLocationFromClass(this.getClass()), STORY_FILES, "");
    }

    private Properties createViewProperties() {
        Properties viewProperties = new Properties();
        viewProperties.setProperty("decorateNonHtml", "false");
        return viewProperties;
    }

    private ExamplesTableFactory createExamplesTableFactory(
            Class<? extends Embeddable> embeddableClass,
            ParameterConverters parameterConverters) {
        return new ExamplesTableFactory(new LocalizedKeywords(),
                new LoadFromClasspath(embeddableClass), parameterConverters);
    }

    StoryReporterBuilder createStoryReporterBuilder(
            Class<? extends Embeddable> embeddableClass,
            Properties viewProperties, Format... formats) {
        StoryReporterBuilder storyReporterBuilder = new StoryReporterBuilder()
                .withViewResources(viewProperties)
                .withCodeLocation(codeLocationFromClass(embeddableClass))
                .withDefaultFormats().withFormats(formats);
        return storyReporterBuilder;
    }

    private Configuration createConfiguration(
            Class<? extends Embeddable> embeddableClass,
            ParameterConverters parameterConverters,
            ExamplesTableFactory examplesTableFactory,
            StoryReporterBuilder storyReporterBuilder) {
        Configuration configuration = new MostUsefulConfiguration()
                .useStoryLoader(new LoadFromClasspath(embeddableClass))
                .useStoryParser(new RegexStoryParser(examplesTableFactory))
                .useStoryReporterBuilder(storyReporterBuilder)
                .useParameterConverters(parameterConverters)
                .usePendingStepStrategy(new FailingUponPendingStep());
        return configuration;
    }
}