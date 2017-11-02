package com.spike.engine;

import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.engine.support.descriptor.EngineDescriptor;
import org.saxstack.parser.DefaultXmlNode;
import org.saxstack.parser.SilentXmlNode;
import org.saxstack.parser.XmlNode;
import org.saxstack.utils.XmlUtils;
import org.xml.sax.Attributes;

public class XmlTestRootNode extends DefaultXmlNode {
    private final EngineDescriptor parentEngineDescriptor;
    private final String fileName;

    XmlTestRootNode(EngineDescriptor engineDescriptor, String name) {
        parentEngineDescriptor = engineDescriptor;
        fileName = name;
    }

    public XmlNode getSubNode(String childName, Attributes attributes) {
        if (attributeSetToTrue("disabled", attributes)) {
            return SilentXmlNode.INSTANCE;
        }
        if ("test".equals(childName)) {
            return new XmlTestNode(attributes, parentEngineDescriptor, 0);
        }
        if ("testSuite".equals(childName)) {
            return new XmlTestSuiteNode(attributes, parentEngineDescriptor, fileName);
        }
        return super.getSubNode(childName, attributes);
    }

    private static boolean attributeSetToTrue(String attributeName, Attributes attributes) {
        String disabled = getAttrValue(attributeName, attributes, "");
        return disabled.equalsIgnoreCase("yes") || disabled.equalsIgnoreCase("true");
    }

    private static String getAttrValue(String xmlAttrName, Attributes xmlAttributes, String defaultValue) {
        String val = xmlAttributes.getValue(xmlAttrName);
        if (val == null) {
            val = defaultValue;
        }

        return val;
    }

    private class XmlTestNode extends DefaultXmlNode {
        XmlTestNode(Attributes xmlAttrs, TestDescriptor parentEngineDescriptor, int cpt) {
            String testName = XmlUtils.getAttrValue("name", xmlAttrs);
            String updatedDomain = XmlUtils.getAttrValue("domain", xmlAttrs, "defaultDomain");

            boolean disabled = XmlUtils.getBooleanAttrValue("disabled", xmlAttrs, false);

            parentEngineDescriptor.addChild(
                    new XmlTestDescriptor(parentEngineDescriptor.getUniqueId(), cpt, testName, disabled));
        }

        @Override
        public XmlNode getSubNode(String childName, Attributes xmlAttrs) {
            return SilentXmlNode.INSTANCE;
        }
    }

    private class XmlTestSuiteNode extends DefaultXmlNode {
        private final TestDescriptor suiteDescriptor;
        private int nbDeclaredTests;
        static final String DEFAULT_DOMAIN = "[empty]";

        private final String name;
        private final String domain;

        XmlTestSuiteNode(Attributes xmlAttrs, EngineDescriptor parentEngineDescriptor, String fileName) {
            name = getAttrValue("name", xmlAttrs, "");
            domain = getAttrValue("domain", xmlAttrs, DEFAULT_DOMAIN);

            suiteDescriptor = new EngineDescriptor(parentEngineDescriptor.getUniqueId().append("domain", domain), getTestSuiteDisplayName(fileName));
            parentEngineDescriptor.addChild(suiteDescriptor);
        }

        private String getTestSuiteDisplayName(String fileName) {
            return "[" + domain + "] " + name + " (" + fileName + ")";
        }

        public XmlNode getSubNode(String tagName, Attributes xmlAttrs) {
            if ("systemProperties".equals(tagName)) {
//                System.out.println("TODO systemProperties");
                return SilentXmlNode.INSTANCE;
            }
            if ("datasets".equals(tagName)) {
//                System.out.println("TODO datasets");
                return SilentXmlNode.INSTANCE;
            }
            if ("commonInputs".equals(tagName)) {
//                System.out.println("TODO commonInputs");
                return SilentXmlNode.INSTANCE;
            }
            if ("test".equals(tagName)) {
                return new XmlTestNode(xmlAttrs, suiteDescriptor, nbDeclaredTests++);
            }
            if ("commonResults".equals(tagName)) {
//                System.out.println("TODO commonResults");
                return SilentXmlNode.INSTANCE;
            }
            if ("description".equals(tagName)) {
                return SilentXmlNode.INSTANCE;
            }
            throw new RuntimeException("Unknown tag '" + tagName + "'");
        }

        public void complete() {
            super.complete();
            if (nbDeclaredTests == 0) {
                throw new RuntimeException("No tests defined in suite '" + name + "'");
            }
        }
    }
}
