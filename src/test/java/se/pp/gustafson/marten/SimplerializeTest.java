/*
 * Copyright 2012 the original author or authors.
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
package se.pp.gustafson.marten;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.util.Iterator;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Node;

import org.jaxen.JaxenException;
import org.jaxen.xom.XOMXPath;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Do some basic tests against JSON and XML output from <a href="https://github.com/Hitta/simplerialize">simplerialize</a>.
 */
public final class SimplerializeTest {

    private JsonNode json;

    private Document xml;

    @Before
    public void setUp() throws Exception {
        this.json = new ObjectMapper().readValue(load("simplerialize.json"), JsonNode.class);
        this.xml = new Builder().build(load("simplerialize.xml"));
    }

    public InputStream load(final String name) {
        return getClass().getClassLoader().getResourceAsStream(name);
    }

    @Test
    public void pathToNamedKeyValue() throws Exception {
        test("/root/objects/@foo", "bar");
        test("/root/objects/@bar", "baz");
        test("/root/a/b/@name", "value");
    }

    @Test
    public void pathToEntryListWithIndex() throws Exception {
        test("/root/primitives/*[3]", "true");
    }

    public void test(final String expression, final String... expected) throws Exception {
        final Iterator<?> xml = xml(expression);
        final Iterator<?> json = json(expression);
        assertTrue("xml, no match: " + expression, xml.hasNext());
        assertTrue("json, no match: " + expression, json.hasNext());
        for (final Object value : expected) {
            assertEquals("xml", value, ((Node) xml.next()).getValue());
            assertEquals("json", value, ((JsonNode) json.next()).asText());
        }
    }

    private Iterator<?> json(final String expression) throws JaxenException {
        return new JaxenJson(expression).selectNodes(this.json).iterator();
    }

    private Iterator<?> xml(final String expression) throws JaxenException {
        return new XOMXPath(expression).selectNodes(this.xml).iterator();
    }

}