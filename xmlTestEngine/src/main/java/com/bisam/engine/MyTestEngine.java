package com.bisam.engine;

import org.junit.platform.engine.*;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;

import static org.junit.platform.engine.TestExecutionResult.successful;

public class MyTestEngine implements TestEngine {
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
        EngineExecutionListener engineExecutionListener = executionRequest.getEngineExecutionListener();
        TestDescriptor engineTestDescriptor = executionRequest.getRootTestDescriptor();
        engineExecutionListener.executionStarted(engineTestDescriptor);

        System.out.println("ARNO-->  MyTestEngine.execute");
//        RunnerExecutor runnerExecutor = new RunnerExecutor(engineExecutionListener);
//        executeAllChildren(runnerExecutor, engineTestDescriptor);
        engineExecutionListener.executionFinished(engineTestDescriptor, successful());
    }

    private class XmlTestDiscoverer {
        public TestDescriptor discover(EngineDiscoveryRequest engineDiscoveryRequest, UniqueId uniqueId) {
            EngineDescriptor engineDescriptor = new EngineDescriptor(uniqueId, "JUnit For Fonctional tests");
            //TODO customize
            return engineDescriptor;
        }
    }
}
