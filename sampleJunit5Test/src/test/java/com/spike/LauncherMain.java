package com.spike;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.EngineFilter;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

/**
 * Classe utilitaire pour lancer facilement les TestEngine ET debugger
 */
public class LauncherMain {
    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        DiscoverySelectors.selectFile(
                                "D:\\Dev\\env\\sideprojects\\junit5TestProject\\sampleJunit5Test\\src\\test\\java\\com\\spike\\testGipsHistorical.xml")
//                        selectPackage("com.spike"),
//                        selectClass(SimpleUnitTestWithLambdaTest.class)
                )
                .filters(
                        EngineFilter.includeEngines("xml-funtional-tests"),
                        EngineFilter.excludeEngines("ice-cream-machine")
//                        includeClassNamePatterns(".*Lambda")
                )
                .build();

        Launcher launcher = LauncherFactory.create();

// Register a listener of your choice
        TestExecutionListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);

        launcher.execute(request);

        ((SummaryGeneratingListener) listener).getSummary().printTo(new PrintWriter(System.out));
    }

}
