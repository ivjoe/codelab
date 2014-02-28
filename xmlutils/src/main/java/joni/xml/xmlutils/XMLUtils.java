package joni.xml.xmlutils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
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
    private static Map<Class<?>, JAXBContext> contextCache = new HashMap<>();

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

    /**
     * Unmarshals an XML ({@link String}) to a bean.
     * 
     * @param xml {@link String}
     * @param clazz {@link Class}
     * @return bean
     * @throws JAXBException
     */
    public static <T> T unmarshal(String xml, Class<T> clazz) throws JAXBException {
        return unmarshal(new ByteArrayInputStream(xml.getBytes()), clazz);
    }

    /**
     * Unmarshals an XML ({@link File}) to a bean.
     * 
     * @param file {@link File}
     * @param clazz {@link Class}
     * @return bean
     * @throws JAXBException
     * @throws IOException
     */
    public static <T> T unmarshal(File file, Class<T> clazz) throws JAXBException, IOException {
        return unmarshal(new StreamSource(file), clazz);
    }

    /**
     * Unmarshals an XML ({@link InputSource}) to a bean.
     * 
     * @param is {@link InputSource}
     * @param clazz {@link Class}
     * @return bean
     * @throws JAXBException
     */
    public static <T> T unmarshal(InputSource is, Class<T> clazz) throws JAXBException {
        return unmarshal(is.getByteStream(), clazz);
    }

    /**
     * Unmarshals an XML ({@link InputStream}) to a bean.
     * 
     * @param is {@link InputStream}
     * @param clazz {@link Class}
     * @return bean
     * @throws JAXBException
     */
    public static <T> T unmarshal(InputStream is, Class<T> clazz) throws JAXBException {
        return unmarshal(new StreamSource(is), clazz);
    }
    
    /**
     * Unmarshals an XML ({@link Source}) to a bean.
     * 
     * @param source {@link Source}
     * @param clazz {@link Class}
     * @return bean
     * @throws JAXBException
     */
    public static <T> T unmarshal(Source source, Class<T> clazz) throws JAXBException {
        return createUnmarshaller(clazz).unmarshal(source, clazz).getValue();
    }

    /**
     * Marshals a bean to XML.
     * 
     * @param bean
     * @return XML {@link String}
     * @throws JAXBException
     */
    public static <T> String marshal(T bean) throws JAXBException {
        StringWriter stw = new StringWriter();
        createMarshaller(bean.getClass()).marshal(bean, stw);
        
        return stw.toString();
    }

    /**
     * Marshals a bean to XML.
     * 
     * @param bean
     * @param namespaceURI
     * @param localPart
     * @return XML {@link String}
     * @throws JAXBException
     */
    @SuppressWarnings("unchecked")
    public static <T> String marshal(T bean, String namespaceURI, String localPart) throws JAXBException {
        QName qName = new QName(namespaceURI, localPart);
        Class<T> clazz = (Class<T>) bean.getClass();
        Marshaller marshaller = createMarshaller(clazz);
        Writer stw = new StringWriter();
        marshaller.marshal(new JAXBElement<T>(qName, clazz, bean), stw);
        
        return stw.toString();
    }

    /**
     * Creates an {@link Unmarshaller} for the given {@link Class}.
     * 
     * @param clazz {@link Class}
     * @return {@link Unmarshaller}
     * @throws JAXBException
     */
    private static <T> Unmarshaller createUnmarshaller(Class<T> clazz) throws JAXBException {
        return getContext(clazz).createUnmarshaller();
    }

    /**
     * Creates a {@link Marshaller} for the given {@link Class}.
     * 
     * @param clazz {@link Class}
     * @return {@link Marshaller}
     * @throws JAXBException
     */
    private static <T> Marshaller createMarshaller(Class<T> clazz) throws JAXBException {
        return getContext(clazz).createMarshaller();
    }

    /**
     * Returns the corresponding {@link JAXBContext} for the given {@link Class}.
     * 
     * @param clazz {@link Class}
     * @return {@link JAXBContext}
     * @throws JAXBException
     */
    private static <T> JAXBContext getContext(Class<T> clazz) throws JAXBException {
        synchronized (contextCache) {
            if (!contextCache.containsKey(clazz)) {
                contextCache.put(clazz, JAXBContext.newInstance(clazz));
            }
        }
        
        return contextCache.get(clazz);
    }
}
