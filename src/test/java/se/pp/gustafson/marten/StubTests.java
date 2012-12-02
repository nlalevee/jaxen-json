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

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.TextNode;

public final class StubTests {

    private JsonNode nodes;

    private static final String json = "{ \"person\": { \"name\": { \"first\": \"mårten\", \"last\": \"gustafson\" } }, \"drinks\": [ \"whiskey\", \"beer\", \"water\" ] }";

    @Before
    public void setUp() throws Exception {
        this.nodes = new ObjectMapper().readValue(json, JsonNode.class);
    }

    @Test
    public void firstLevelPath() throws Exception {
        test("//name", this.nodes.get("person").get("name"));
    }

    @Test
    public void twoLevelPath() throws Exception {

        test("/person/name/first", new TextNode("mårten"));
    }

    @Test
    public void threeLevelPath() throws Exception {
        test("/person/name/last", new TextNode("gustafson"));
    }

    @Test
    public void array() throws Exception {
        final ArrayNode array = new ArrayNode(JsonNodeFactory.instance);
        array.add(new TextNode("whiskey"));
        array.add(new TextNode("beer"));
        array.add(new TextNode("water"));
        test("/drinks", array);
    }

    @Test
    public void attributeWildcardWithIndex() throws Exception {
        test("/drinks/@*[2]", new TextNode("beer"));
    }

    public void test(final String expression, final JsonNode... expected) throws Exception {
        final Iterator<?> actual = new JaxenJson(expression).selectNodes(this.nodes).iterator();
        for (final Object value : expected) {
            final Object next = actual.next();
            assertEquals("Class", value.getClass().getSimpleName(), next.getClass().getSimpleName());
            assertEquals(expression, value, next);
        }
    }

}