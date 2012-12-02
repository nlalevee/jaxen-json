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

import java.util.Iterator;

import org.jaxen.FunctionCallException;
import org.jaxen.NamedAccessNavigator;
import org.jaxen.UnsupportedAxisException;
import org.jaxen.XPath;
import org.jaxen.saxpath.SAXPathException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ContainerNode;

@SuppressWarnings("serial")
public final class JsonNavigator implements NamedAccessNavigator {

    @Override
    public Iterator<?> getAttributeAxisIterator(final Object contextNode, final String localName, final String namespacePrefix,
            final String namespaceURI) throws UnsupportedAxisException {
        return (contextNode instanceof JsonNode) ? ((JsonNode) contextNode).findValues(localName).iterator() : null;
    }

    @Override
    public Iterator<?> getChildAxisIterator(final Object contextNode, final String localName, final String namespacePrefix, final String namespaceURI)
            throws UnsupportedAxisException {
        return (contextNode instanceof JsonNode) ? ((JsonNode) contextNode).findValues(localName).iterator() : null;
    }

    @Override
    public String getAttributeName(Object arg0) {
        return null;
    }

    @Override
    public String getAttributeNamespaceUri(Object arg0) {
        return null;
    }

    @Override
    public String getAttributeQName(Object arg0) {
        return null;
    }

    @Override
    public String getAttributeStringValue(Object arg0) {
        return null;
    }

    @Override
    public String getCommentStringValue(Object arg0) {
        return null;
    }

    @Override
    public String getElementName(final Object contextNode) {
        return null;
    }

    @Override
    public String getElementNamespaceUri(final Object contextNode) {
        return null;
    }

    @Override
    public String getElementQName(Object arg0) {
        return null;
    }

    @Override
    public String getElementStringValue(Object arg0) {
        return null;
    }

    @Override
    public String getNamespacePrefix(Object arg0) {
        return null;
    }

    @Override
    public String getNamespaceStringValue(Object arg0) {
        return null;
    }

    @Override
    public String getTextStringValue(Object arg0) {
        return null;
    }

    @Override
    public boolean isAttribute(final Object contextNode) {
        return (contextNode instanceof JsonNode) ? ((JsonNode) contextNode).isValueNode() : false;
    }

    @Override
    public boolean isElement(final Object contextNode) {
        return (contextNode instanceof JsonNode) ? ((JsonNode) contextNode).isValueNode() : false;
    }

    @Override
    public boolean isText(final Object contextNode) {
        return false;
    }

    @Override
    public XPath parseXPath(String arg0) throws SAXPathException {
        return null;
    }

    @Override
    public Iterator<?> getAncestorAxisIterator(Object arg0) throws UnsupportedAxisException {
        return null;
    }

    @Override
    public Iterator<?> getAncestorOrSelfAxisIterator(Object arg0) throws UnsupportedAxisException {
        return null;
    }

    @Override
    public Iterator<?> getAttributeAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return (contextNode instanceof JsonNode) ? ((JsonNode) contextNode).elements() : null;
    }

    @Override
    public Iterator<?> getChildAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return (contextNode instanceof ArrayNode) ? ((ArrayNode) contextNode).iterator() : null;
    }

    @Override
    public Iterator<?> getDescendantAxisIterator(Object arg0) throws UnsupportedAxisException {
        return null;
    }

    @Override
    public Iterator<?> getDescendantOrSelfAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return (contextNode instanceof ContainerNode) ? ((ContainerNode<?>) contextNode).iterator() : null;
    }

    @Override
    public Object getDocument(String arg0) throws FunctionCallException {
        return null;
    }

    @Override
    public Object getDocumentNode(final Object contextNode) {
        return contextNode;
    }

    @Override
    public Object getElementById(Object arg0, String arg1) {
        return null;
    }

    @Override
    public Iterator<?> getFollowingAxisIterator(Object arg0) throws UnsupportedAxisException {
        return null;
    }

    @Override
    public Iterator<?> getFollowingSiblingAxisIterator(final Object contextNode) throws UnsupportedAxisException {
        return (contextNode instanceof JsonNode) ? ((JsonNode) contextNode).elements() : null;
    }

    @Override
    public Iterator<?> getNamespaceAxisIterator(Object arg0) throws UnsupportedAxisException {
        return null;
    }

    @Override
    public short getNodeType(Object arg0) {
        return 0;
    }

    @Override
    public Iterator<?> getParentAxisIterator(Object arg0) throws UnsupportedAxisException {
        return null;
    }

    @Override
    public Object getParentNode(final Object contextNode) throws UnsupportedAxisException {
        return null;
    }

    @Override
    public Iterator<?> getPrecedingAxisIterator(Object arg0) throws UnsupportedAxisException {
        return null;
    }

    @Override
    public Iterator<?> getPrecedingSiblingAxisIterator(Object arg0) throws UnsupportedAxisException {
        return null;
    }

    @Override
    public String getProcessingInstructionData(Object arg0) {
        return null;
    }

    @Override
    public String getProcessingInstructionTarget(Object arg0) {
        return null;
    }

    @Override
    public Iterator<?> getSelfAxisIterator(Object arg0) throws UnsupportedAxisException {
        return null;
    }

    @Override
    public String translateNamespacePrefixToUri(String arg0, Object arg1) {
        return null;
    }

    @Override
    public boolean isComment(Object arg0) {
        return false;
    }

    @Override
    public boolean isNamespace(Object arg0) {
        return false;
    }

    @Override
    public boolean isProcessingInstruction(Object arg0) {
        return false;
    }

    @Override
    public boolean isDocument(Object arg0) {
        return false;
    }
}