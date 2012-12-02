/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.pp.gustafson.marten.ant;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;
import org.jaxen.JaxenException;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import se.pp.gustafson.marten.JaxenJson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class XPathTask extends Task {

    private String expr;

    private String json;

    private File jsonFile;

    private String xml;

    private File xmlFile;

    private String property;

    private String propertyPrefix;

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setJsonFile(File jsonFile) {
        this.jsonFile = jsonFile;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public void setXmlFile(File xmlFile) {
        this.xmlFile = xmlFile;
    }

    @Override
    public void execute() throws BuildException {
        if (expr == null) {
            throw new BuildException("Missing attribute 'expr'");
        }

        if (property == null && propertyPrefix == null) {
            throw new BuildException("One of 'property' and 'propertyPrefix' attribute is required");
        }

        Object node;
        if (json != null) {
            if (jsonFile != null || xml != null || xmlFile != null) {
                throw new BuildException("Only one of 'json', 'jsonFile', 'xml' and 'xmlFile' attribute is supported");
            }
            try {
                node = new ObjectMapper().readTree(json);
            } catch (JsonProcessingException e) {
                throw new BuildException("Incorrect json: " + json, e);
            } catch (IOException e) {
                throw new BuildException("The json could not be read " + json, e);
            }
        } else if (jsonFile != null) {
            if (json != null || xml != null || xmlFile != null) {
                throw new BuildException("Only one of 'json', 'jsonFile', 'xml' and 'xmlFile' attribute is supported");
            }
            try {
                node = new ObjectMapper().readTree(jsonFile);
            } catch (JsonProcessingException e) {
                throw new BuildException("Incorrect json in the file " + jsonFile, e);
            } catch (IOException e) {
                throw new BuildException("The json file " + jsonFile + " could not be read", e);
            }
        } else if (xml != null) {
            if (json != null || jsonFile != null || xmlFile != null) {
                throw new BuildException("Only one of 'json', 'jsonFile', 'xml' and 'xmlFile' attribute is supported");
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new BuildException("Error while configuring the DOM parser", e);
            }
            Document doc;
            try {
                doc = dBuilder.parse(new InputSource(new StringReader(xml)));
            } catch (SAXException e) {
                throw new BuildException("Error while parsing the xml " + xml, e);
            } catch (IOException e) {
                throw new BuildException("Error while reading the xml " + xml, e);
            }
            node = doc.getDocumentElement();
        } else if (xmlFile != null) {
            if (json != null || jsonFile != null || xml != null) {
                throw new BuildException("Only one of 'json', 'jsonFile', 'xml' and 'xmlFile' attribute is supported");
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder;
            try {
                dBuilder = dbFactory.newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new BuildException("Error while configuring the DOM parser", e);
            }
            Document doc;
            try {
                doc = dBuilder.parse(xmlFile);
            } catch (SAXException e) {
                throw new BuildException("Error while parsing the xml file " + xmlFile, e);
            } catch (IOException e) {
                throw new BuildException("Error while reading the xml file " + xmlFile, e);
            }
            node = doc.getDocumentElement();
        } else {
            throw new BuildException("One of 'json', 'jsonFile', 'xml', 'xmlFile' attribute is required");
        }

        XPath xpath;
        try {
            if (json != null || jsonFile != null) {
                xpath = new JaxenJson(expr);
            } else {
                xpath = new DOMXPath(expr);
            }
        } catch (JaxenException e) {
            throw new BuildException("The xpath expression '" + expr + "' could not be parsed");
        }

        try {
            if (property != null) {
                Object res;
                res = xpath.selectSingleNode(node);
                if (res != null) {
                    getProject().setNewProperty(property, res.toString());
                } else {
                    log("No result, " + property + " is not set", Project.MSG_VERBOSE);
                }
            } else {
                List<?> res = xpath.selectNodes(node);
                if (res != null && !res.isEmpty()) {
                    int i = 0;
                    for (Object resElement : res) {
                        getProject().setNewProperty(propertyPrefix + i, resElement.toString());
                    }
                } else {
                    log("No result, no property starting with " + propertyPrefix + " is not set", Project.MSG_VERBOSE);
                }
            }
        } catch (JaxenException e) {
            throw new BuildException("Error on the evaluation of the expression on the node", e);
        }
    }
}
