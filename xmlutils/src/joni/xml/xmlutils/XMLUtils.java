package joni.xml.xmlutils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Jonatan Ivanov
 */
public class XMLUtils {
    public static String xml2String(File file) throws TransformerException, ParserConfigurationException, SAXException, IOException {
        return document2String(xml2Document(file));
    }

    public static String xml2String(InputStream is) throws TransformerException, ParserConfigurationException, SAXException, IOException {
        return document2String(xml2Document(is));
    }

    public static String document2String(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        StringWriter buffer = new StringWriter();
        transformer.transform(new DOMSource(doc), new StreamResult(buffer));

        return buffer.toString();
    }

    public static Document xml2Document(String xml) throws ParserConfigurationException, SAXException, IOException {
        return getDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes()));
    }

    public static Document xml2Document(File file) throws ParserConfigurationException, SAXException, IOException {
        return XMLUtils.getDocumentBuilder().parse(file);
    }

    public static Document xml2Document(InputSource is) throws ParserConfigurationException, SAXException, IOException {
        return XMLUtils.getDocumentBuilder().parse(is);
    }

    public static Document xml2Document(InputStream is) throws ParserConfigurationException, SAXException, IOException {
        return XMLUtils.getDocumentBuilder().parse(is);
    }

    private static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    public static <T> T xml2Bean(String xml, Class<T> clazz) throws JAXBException {
        return xml2Bean(new ByteArrayInputStream(xml.getBytes()), clazz);
    }

    public static <T> T xml2Bean(File file, Class<T> clazz) throws JAXBException, IOException {
        return xml2Bean(new FileInputStream(file), clazz);
    }

    public static <T> T xml2Bean(InputSource is, Class<T> clazz) throws JAXBException {
        return xml2Bean(is.getByteStream(), clazz);
    }

    public static <T> T xml2Bean(InputStream is, Class<T> clazz) throws JAXBException {
        return getUnmarshaller(clazz).unmarshal(new StreamSource(is), clazz).getValue();
    }

    public static <T> String bean2XML(T bean) throws JAXBException {
        StringWriter stw = new StringWriter();
        getMarshaller(bean.getClass()).marshal(bean, stw);

        return stw.toString();
    }

    @SuppressWarnings("unchecked")
    public static <T> String bean2XML(T bean, String namespaceURI, String localPart) throws JAXBException {
        QName qName = new QName(namespaceURI, localPart);
        Class<T> clazz = (Class<T>) bean.getClass();
        JAXBElement<T> jaxbElement = new JAXBElement<T>(qName, clazz, bean);

        Marshaller marshaller = getMarshaller(clazz);
        Writer stw = new StringWriter();
        marshaller.marshal(jaxbElement, stw);

        return stw.toString();
    }

    private static <T> Unmarshaller getUnmarshaller(Class<T> clazz) throws JAXBException {
        return JAXBContext.newInstance(clazz).createUnmarshaller();
    }

    private static <T> Marshaller getMarshaller(Class<T> clazz) throws JAXBException {
        return JAXBContext.newInstance(clazz).createMarshaller();
    }
}
