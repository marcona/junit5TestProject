package com.spike.engine;

import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.TestDescriptor;

import static org.junit.platform.engine.TestExecutionResult.failed;
import static org.junit.platform.engine.TestExecutionResult.successful;

/**
 * Inspired from VintageEngine
 */
class RunnerExecutor {
    private int nbExecutions = 0;
    private final EngineExecutionListener engineExecutionListener;

    RunnerExecutor(EngineExecutionListener engineExecutionListener) {
        this.engineExecutionListener = engineExecutionListener;
    }

    void execute(TestDescriptor runnerTestDescriptor) {
        System.out.println("runnerTestDescriptor = " + runnerTestDescriptor);
        engineExecutionListener.executionStarted(runnerTestDescriptor);
        if (nbExecutions == 1) {
            engineExecutionListener.executionFinished(runnerTestDescriptor, failed(new AssertionError("toto")));
        } else {
            engineExecutionListener.executionFinished(runnerTestDescriptor, successful());
        }
        nbExecutions++;

    }

}
