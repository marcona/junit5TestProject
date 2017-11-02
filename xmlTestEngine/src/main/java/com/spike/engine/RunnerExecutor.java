package com.spike.engine;

import org.junit.platform.engine.EngineExecutionListener;
import org.junit.platform.engine.TestDescriptor;

import static org.junit.platform.engine.TestExecutionResult.failed;
import static org.junit.platform.engine.TestExecutionResult.successful;

/**
 * Inspired from VintageEngine
 */
class RunnerExecutor {
    private static final String TEST_FAILED = "two Months - March loaded (Composite AND Portfolio level), April computed from Portfolio Results";
    private final EngineExecutionListener engineExecutionListener;

    RunnerExecutor(EngineExecutionListener engineExecutionListener) {
        this.engineExecutionListener = engineExecutionListener;
    }

    void execute(TestDescriptor runnerTestDescriptor) {
        engineExecutionListener.executionStarted(runnerTestDescriptor);

        if (runnerTestDescriptor instanceof XmlTestDescriptor) {
            if (((XmlTestDescriptor) runnerTestDescriptor).isDisabled()) {
                engineExecutionListener.executionSkipped(runnerTestDescriptor, "Disabled in Xml File");
            } else {
                if (TEST_FAILED.equals(runnerTestDescriptor.getDisplayName())) {
                    engineExecutionListener.executionFinished(runnerTestDescriptor, failed(new AssertionError("Fake assertion Error on second test (to test re-run failed tests)")));
                } else {
                    engineExecutionListener.executionFinished(runnerTestDescriptor, successful());
                }
            }
        }
    }
    }
