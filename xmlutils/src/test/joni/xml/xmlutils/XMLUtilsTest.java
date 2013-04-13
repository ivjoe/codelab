package joni.xml.xmlutils;

import java.io.File;
import java.io.FileInputStream;

import joni.xml.xmlutils.beans.Note;

import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * @author Jonatan Ivanov
 */
public class XMLUtilsTest {

    @Test
    public void bean2XMLTest() throws Exception {
        String xml = XMLUtils.bean2XML(getNote());
        System.out.println("bean2XMLTest: " + xml + "\n");
    }

    @Test
    public void bean2XMLTestWithNamespace() throws Exception {
        String xml = XMLUtils.bean2XML(getNote(), "aaa", "bbb");
        System.out.println("bean2XMLTestWithNamespace: " + xml + "\n");
    }

    @Test
    public void document2StringTest() throws Exception {
        Document doc = XMLUtils.xml2Document(getXML());
        String xml = XMLUtils.document2String(doc);
        System.out.println("document2StringTest: " + xml + "\n");
    }

    @Test
    public void xml2StringTestFromFile() throws Exception {
        String xml = XMLUtils.xml2String(getXML());
        System.out.println("xml2StringTestFromFile: " + xml + "\n");
    }

    @Test
    public void xml2StringTestFromInputSteram() throws Exception {
        String xml = XMLUtils.xml2String(new FileInputStream(getXML()));
        System.out.println("xml2StringTestFromInputSteram: " + xml + "\n");
    }

    @Test
    public void xml2BeanTestFromFile() throws Exception {
        Note note = XMLUtils.xml2Bean(getXML(), Note.class);
        System.out.println("xml2BeanTestFromFile: " + note + "\n");
    }

    @Test
    public void xml2BeanTestFromInputSource() throws Exception {
        Note note = XMLUtils.xml2Bean(new InputSource(new FileInputStream(getXML())), Note.class);
        System.out.println("xml2BeanTestFromInputSource: " + note + "\n");
    }

    @Test
    public void xml2BeanTestFromInputStream() throws Exception {
        Note note = XMLUtils.xml2Bean(new FileInputStream(getXML()), Note.class);
        System.out.println("xml2BeanTestFromInputStream: " + note + "\n");
    }

    @Test
    public void xml2BeanTestFromString() throws Exception {
        String xml = XMLUtils.xml2String(new FileInputStream(getXML()));
        Note note = XMLUtils.xml2Bean(xml, Note.class);
        System.out.println("xml2BeanTestFromString: " + note + "\n");
    }

    @Test
    public void xml2DocumentTestFromFile() throws Exception {
        Document doc = XMLUtils.xml2Document(getXML());
        System.out.println("xml2DocumentTestFromFile: " + XMLUtils.document2String(doc) + "\n");
    }

    @Test
    public void xml2DocumentTestFromInputSource() throws Exception {
        Document doc = XMLUtils.xml2Document(new InputSource(new FileInputStream(getXML())));
        System.out.println("xml2DocumentTestFromInputSource: " + XMLUtils.document2String(doc) + "\n");
    }

    @Test
    public void xml2DocumentTestFromInputStream() throws Exception {
        Document doc = XMLUtils.xml2Document(new FileInputStream(getXML()));
        System.out.println("xml2DocumentTestFromInputStream: " + XMLUtils.document2String(doc) + "\n");
    }

    @Test
    public void xml2DocumentTestFromString() throws Exception {
        String xml = XMLUtils.xml2String(new FileInputStream(getXML()));
        Document doc = XMLUtils.xml2Document(xml);
        System.out.println("xml2DocumentTestFromString: " + XMLUtils.document2String(doc) + "\n");
    }

    private File getXML() {
        return new File("src/test/resources/note.xml");
    }

    private Note getNote() {
        Note note = new Note();
        note.setBody("with love");
        note.setFrom("me");
        note.setTo("you");
        note.setHeading("Beatles");

        return note;
    }
}
