package com.spike.engine;

import org.junit.platform.engine.TestSource;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.support.descriptor.AbstractTestDescriptor;
import org.junit.platform.engine.support.descriptor.FilePosition;
import org.junit.platform.engine.support.descriptor.FileSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

class XmlTestDescriptor extends AbstractTestDescriptor {
    XmlTestDescriptor(UniqueId uniqueId, int counter, String displayName) {
        super(uniqueId.append("xmlTest", "#" + counter), displayName, buildFileSource(displayName));
    }

    private static TestSource buildFileSource(String displayName) {
        return FileSource.from(new File(XmlTestDiscoverer.PATHNAME), getFilePositionFromName(displayName));
    }

    private static FilePosition getFilePositionFromName(String displayName) {
        System.out.println("displayName = " + displayName);
        int lineNumber = 0;
        try {
            List<String> allLines = Files.readAllLines(Paths.get(XmlTestDiscoverer.PATHNAME));
            for (String line : allLines) {
                System.out.println("line = " + line);
                lineNumber++;
                if (line.contains(displayName)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FilePosition.from(lineNumber);
    }

    @Override
    public Type getType() {
        return Type.TEST;
    }
}
