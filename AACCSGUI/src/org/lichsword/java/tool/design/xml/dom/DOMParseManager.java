/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: DOMParseManager.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午04:20:46
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.java.tool.design.xml.dom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author yuewang
 * 
 */
public class DOMParseManager extends DOMParseHandler {

	private static DOMParseManager sInstance;

	private File mTargetXMLFile = null;

	private DOMParseManager(File file) {
		super();
		mTargetXMLFile = file;
	}

	public static DOMParseManager getInstance(File file) {
		if (null == sInstance) {
			sInstance = new DOMParseManager(file);
		}// end if
		return sInstance;
	}

	private Node handleDocument(Document document, String tagname) {
		Node result = null;
		NodeList parentNodeList = document.getElementsByTagName("preference");
		NodeList nodeList = parentNodeList.item(0).getChildNodes();
		int length = nodeList.getLength();
		Node temp = null;
		for (int index = 0; index < length; index++) {
			temp = nodeList.item(index);
			if (temp.getNodeName().equals(tagname)) {
				result = temp;
				break;
			}// end if
		}// end for
		return result;
	}

	public String getString(String key, String defaultValue) {
		Document document = parseXMLFile(mTargetXMLFile);
		Node node = handleDocument(document, key);
		if (null != node) {
			return node.getTextContent();
		}// end if
		return defaultValue;

	}

	public boolean putString(String key, String value) {
		boolean result = true;
		Document document = parseXMLFile(mTargetXMLFile);
		Element rootElement = document.getDocumentElement();
		// prepared new Node.
		Element element = document.createElement(key);
		element.setTextContent(value);
		if (null != document) {
			NodeList nodeList = rootElement.getElementsByTagName(key);
			if (null != nodeList && null != nodeList.item(0)) {
				// rootElement.removeChild(nodeList.item(0));
				rootElement.replaceChild(element, nodeList.item(0));
			} else {
				// after has ensure no old node, now we create an new one.
				rootElement.appendChild(element);
			}

			// no matter exist, we write again to refresh value.
			output(rootElement, mTargetXMLFile);
			result = true;
		}// end if
		result = false;
		return result;
	}

	public int getInt(String key, int defaultValue) {
		Document document = parseXMLFile(mTargetXMLFile);
		Node node = handleDocument(document, key);
		if (null != node) {
			return Integer.parseInt(node.getTextContent());
		}// end if
		return defaultValue;
	}

	public boolean putInt(String key, int value) {
		String content = String.valueOf(value);
		return putString(key, content);
	}

	public long getLong(String key, long defaultValue) {
		Document document = parseXMLFile(mTargetXMLFile);
		Node node = handleDocument(document, key);
		if (null != node) {
			return Long.parseLong(node.getTextContent());
		}// end if
		return defaultValue;
	}

	public boolean putLong(String key, long value) {
		String content = String.valueOf(value);
		return putString(key, content);
	}

	public boolean putBoolean(String key, boolean value) {
		String content = ((value) ? "true" : "false");
		return putString(key, content);
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		Document document = parseXMLFile(mTargetXMLFile);
		Node node = handleDocument(document, key);
		if (null != node) {
			return Boolean.parseBoolean(node.getTextContent());
		}// end if
		return defaultValue;
	}

	public boolean putFloat(String key, float value) {
		String content = String.valueOf(value);
		return putString(key, content);
	}

	public float getFloat(String key, float defaultValue) {
		Document document = parseXMLFile(mTargetXMLFile);
		Node node = handleDocument(document, key);
		if (null != node) {
			return Float.parseFloat(node.getTextContent());
		}// end if
		return defaultValue;
	}

	private void output(Node node, File xmlFile) {
		try {
			output(node, new FileOutputStream(xmlFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void output(Node node, OutputStream outputStream) {
		TransformerFactory transFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transFactory.newTransformer();
			transformer.setOutputProperty("encoding", "utf-8");
			transformer.setOutputProperty("indent", "yes");

			DOMSource source = new DOMSource();
			source.setNode(node);
			StreamResult result = new StreamResult();
			result.setOutputStream(outputStream);

			transformer.transform(source, result);
			if (null != outputStream) {
				outputStream.close();
			}// end if
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}
	}

	public static void main(String args[]) {
		DOMParseManager manager = DOMParseManager.getInstance(new File(
				"love.xml"));
		manager.putInt("int", 222);
		System.out.println(manager.getInt("int", 888));
	}
}
