package com.spike.engine;

import com.sun.org.apache.xerces.internal.parsers.SAXParser;
import org.junit.platform.engine.EngineDiscoveryRequest;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.UniqueId;
import org.junit.platform.engine.discovery.FileSelector;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;
import org.saxstack.parser.ExceptionHolder;
import org.saxstack.parser.SaxStackParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

class XmlTestDiscoverer {

    TestDescriptor discover(EngineDiscoveryRequest engineDiscoveryRequest, UniqueId uniqueId) {
        EngineDescriptor engineDescriptor = new EngineDescriptor(uniqueId, "JUnit For Fonctional tests");

        List<FileSelector> fileSelectors = engineDiscoveryRequest.getSelectorsByType(FileSelector.class);
        if (!fileSelectors.isEmpty()) {
            FileSelector fileSelector = fileSelectors.get(0);
            discoverFromFile(engineDescriptor, fileSelector.getFile());
        } else {
            //HACK on ne sait pas encore recuperer les resources depuis un package ...
            discoverFromFile(engineDescriptor, new File("D:\\Dev\\env\\sideprojects\\junit5TestProject\\sampleJunit5Test\\src\\test\\java\\com\\spike\\testGipsHistorical.xml"));
        }
        return engineDescriptor;
    }

    private void discoverFromFile(EngineDescriptor engineDescriptor, File file) {
        try {
            SaxStackParser.parse(new SAXParser(),
                    new XmlTestRootNode(engineDescriptor),
                    new BufferedReader(new FileReader(file)));
        } catch (ExceptionHolder exceptionHolder) {
            throw new RuntimeException(exceptionHolder.getInner());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
