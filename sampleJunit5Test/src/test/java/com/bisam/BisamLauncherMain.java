package com.bisam;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;

public class BisamLauncherMain {
    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder.request()
                .selectors(
                        selectPackage("com.bisam"),
                        selectClass(SimpleUnitTestWithLambdaTest.class)
                )
                .filters(
                        includeClassNamePatterns(".*Lambda")
                )
                .build();

        Launcher launcher = LauncherFactory.create();

// Register a listener of your choice
        TestExecutionListener listener = new SummaryGeneratingListener();
        launcher.registerTestExecutionListeners(listener);

        launcher.execute(request);

        System.out.println("((SummaryGeneratingListener)listener).getSummary() = "
                + ((SummaryGeneratingListener) listener).getSummary().getTestsSucceededCount());
    }

}
