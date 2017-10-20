package com.bisam.engine;

import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;

class XmlTestDescriptor extends AbstractTestDescriptor {
    XmlTestDescriptor(UniqueId uniqueId, int counter, String displayName) {
        this(uniqueId, "xmlTest", "#" + counter, displayName);
    }

    private XmlTestDescriptor(UniqueId uniqueId, String segmentType, String value, String displayName) {
        super(uniqueId.append(segmentType, value), displayName);
    }

    @Override
    public Type getType() {
        return Type.TEST;
    }
}
