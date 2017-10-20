package com.bisam.engine;

import org.junit.platform.engine.*;

import static org.junit.platform.engine.TestExecutionResult.successful;

public class XmlTestEngine implements TestEngine {
    @Override
    public String getId() {
        return "bisam-funtional-tests";
    }

    @Override
    public TestDescriptor discover(EngineDiscoveryRequest engineDiscoveryRequest, UniqueId uniqueId) {
        return new XmlTestDiscoverer().discover(engineDiscoveryRequest, uniqueId);
    }

    @Override
    public void execute(ExecutionRequest executionRequest) {
        TestDescriptor xmlTestEngine = executionRequest.getRootTestDescriptor();

        EngineExecutionListener engineExecutionListener = executionRequest.getEngineExecutionListener();
        engineExecutionListener.executionStarted(xmlTestEngine);

        RunnerExecutor runnerExecutor = new RunnerExecutor(engineExecutionListener);
        executeAllChildren(runnerExecutor, xmlTestEngine, engineExecutionListener);

        engineExecutionListener.executionFinished(xmlTestEngine, successful());
    }

    private void executeAllChildren(RunnerExecutor runnerExecutor, TestDescriptor engineTestDescriptor, EngineExecutionListener engineExecutionListener) {
        // @formatter:off
        engineTestDescriptor.getChildren()
                .stream()
                .map(TestDescriptor.class::cast)
                .forEach(testDescriptor -> {
                    if (testDescriptor.isTest()) {
                        runnerExecutor.execute(testDescriptor);
                    } else {
                        engineExecutionListener.executionStarted(testDescriptor);
                        executeAllChildren(runnerExecutor, testDescriptor, engineExecutionListener);
                        engineExecutionListener.executionFinished(testDescriptor, successful());
                    }
                });
        // @formatter:on
    }

}
