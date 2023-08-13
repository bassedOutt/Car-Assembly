package program.configurator;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class Configurator {
    private final String configFileName = "config.xml";

    private File configFile = new File(configFileName);

    private int storageSize;

    private int collectorsCount;

    private int dealersCount;

    private int providersCount;

    public int GetStorageSize() {
        return storageSize;
    }

    public int GetCollectorsCount() {
        return collectorsCount;
    }

    public int GetDealersCount() {
        return dealersCount;
    }

    public int GetProvidersCount() {
        return providersCount;
    }

    public void LoadConfigFromFile(File configFile) {
        this.configFile = configFile;
        getConfigurationFromFile();

        this.configFile = new File(this.configFileName);
        setConfigurationToFile();
    }

    public Configurator() {
        if (configFile.exists()) {
            getConfigurationFromFile();
        }
        else {
            this.storageSize = 20;
            this.collectorsCount = 5;
            this.dealersCount = 5;
            this.providersCount = 5;

            setConfigurationToFile();
        }
    }

    private void getConfigurationFromFile() {
        try {
            getConfiguration();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getConfiguration() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        Document configDocument = documentBuilder.parse(configFile);
        configDocument.getDocumentElement().normalize();

        NodeList nodeList;
        Node node;

        nodeList = configDocument.getElementsByTagName(String.valueOf(TagNames.storage));
        node = nodeList.item(0);
        storageSize =
                Integer.parseInt(((Element) node)
                        .getAttribute(String.valueOf(TagAttributes.size)));

        nodeList = configDocument.getElementsByTagName(String.valueOf(TagNames.collector));
        node = nodeList.item(0);
        collectorsCount =
                Integer.parseInt(((Element) node)
                        .getAttribute(String.valueOf(TagAttributes.count)));

        nodeList = configDocument.getElementsByTagName(String.valueOf(TagNames.dealer));
        node = nodeList.item(0);
        dealersCount =
                Integer.parseInt(((Element) node)
                        .getAttribute(String.valueOf(TagAttributes.count)));

        nodeList = configDocument.getElementsByTagName(String.valueOf(TagNames.provider));
        node = nodeList.item(0);
        providersCount =
                Integer.parseInt(((Element) node)
                        .getAttribute(String.valueOf(TagAttributes.count)));
    }

    public void setConfigurationToFile() {
        try {
            setConfiguration();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setConfiguration() throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        Element factory = document.createElement(String.valueOf(TagNames.factory));
        document.appendChild(factory);

        Element storage = document.createElement(String.valueOf(TagNames.storage));
        storage.setAttribute(String.valueOf(TagAttributes.size), Integer.toString(storageSize));
        factory.appendChild(storage);

        Element collector = document.createElement(String.valueOf(TagNames.collector));
        collector.setAttribute(String.valueOf(TagAttributes.count), Integer.toString(collectorsCount));
        factory.appendChild(collector);

        Element provider = document.createElement(String.valueOf(TagNames.provider));
        provider.setAttribute(String.valueOf(TagAttributes.count), Integer.toString(providersCount));
        factory.appendChild(provider);

        Element dealer = document.createElement(String.valueOf(TagNames.dealer));
        dealer.setAttribute(String.valueOf(TagAttributes.count), Integer.toString(dealersCount));
        factory.appendChild(dealer);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        DOMSource domSource = new DOMSource(document);

        StreamResult streamResult = new StreamResult(new File(configFileName));
        transformer.transform(domSource, streamResult);
    }
}
