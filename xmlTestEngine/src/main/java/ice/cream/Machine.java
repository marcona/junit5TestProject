/*
 * Copyright 2015-2017 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package ice.cream;

import org.junit.platform.engine.*;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;

/**
 * Simple test engine implementation.
 */
public class Machine implements TestEngine {
    private static final String DEFAULT_SCOOP = Integer.toString(5);

    @Override
    public String getId() {
        return "ice-cream-machine";
    }

    @Override
    public TestDescriptor discover(EngineDiscoveryRequest discoveryRequest, UniqueId uniqueId) {
        TestDescriptor engine = new EngineDescriptor(uniqueId, getCaption());
        for (int i = 0; i < getScoops(discoveryRequest); i++) {
            engine.addChild(new Scoop(engine.getUniqueId(), i, Flavor.random()));
        }
        return engine;
    }

    @Override
    public void execute(ExecutionRequest request) {
        System.out.println("ARNO-->  Machine.execute");

        TestDescriptor engine = request.getRootTestDescriptor();
        EngineExecutionListener listener = request.getEngineExecutionListener();
        listener.executionStarted(engine);
        for (TestDescriptor child : engine.getChildren()) {
            listener.executionStarted(child);
            listener.executionFinished(child, TestExecutionResult.successful());
        }
        listener.executionFinished(engine, TestExecutionResult.successful());
    }

    /**
     * Build caption used as the engine's display name.
     */
    private String getCaption() {
        StringBuilder builder = new StringBuilder();
        builder.append("Ice Cream Machine");
        if (getVersion().isPresent()) {
            builder.append(" ").append(getVersion().get());
        }
        if (getArtifactId().isPresent()) {
            builder.append(" (").append(getArtifactId().get()).append(")");
        }
        return builder.toString();
    }

    /**
     * Extract amount of scoops to generate.
     */
    private int getScoops(EngineDiscoveryRequest discoveryRequest) {
        ConfigurationParameters parameters = discoveryRequest.getConfigurationParameters();
        String scoops = parameters.get("scoops").orElse(DEFAULT_SCOOP);
        return Integer.valueOf(scoops);
    }
}
