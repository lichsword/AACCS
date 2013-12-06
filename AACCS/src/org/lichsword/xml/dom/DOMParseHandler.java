/***********************************************************************
 * 
 *     Copyright: 2011, BAINA Technologies Co. Ltd.
 *     Classname: DOMParseHandler.java
 *     Author:    yuewang
 *     Description:    TODO
 *     History:
 *         1.  Date:   下午04:37:26
 *             Author:    yuewang
 *             Modifycation:    create the class.       
 *
 ***********************************************************************/

package org.lichsword.xml.dom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author wangyue.wy
 * @data 2013-12-3
 */
public class DOMParseHandler {

    private final String PREPARED_LINE_1 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
    private final String PREPARED_LINE_2 = "<preference>";
    private final String PREPARED_LINE_3 = "</preference>";

    private void ensureFile(File file) {
        FileOutputStream fos;
        StringBuilder sb = new StringBuilder();
        sb.append(PREPARED_LINE_1);
        sb.append('\n');
        sb.append(PREPARED_LINE_2);
        sb.append('\n');
        sb.append(PREPARED_LINE_3);
        try {
            fos = new FileOutputStream(file);
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected Document parseXMLFile(File file) {

        Document result = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        if (null != file) {
            if (!file.exists()) {
                System.out.println("Detected Param File not exist...");
                ensureFile(file);
                System.out.println("Create file " + file.getAbsolutePath());
            }// end if
            try {
                builder = factory.newDocumentBuilder();
                result = builder.parse(file);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Param file should not be null...ERROR!");
        }

        return result;
    }

    protected Document parseXMLFile(InputStream inputStream) {
        Document result = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            result = builder.parse(inputStream);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
