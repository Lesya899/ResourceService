package resources;

import org.xml.sax.SAXException;
import parser.SaxHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class ResourceServer implements ResourceServerMBean {
    private TestResource testResource;

    public ResourceServer(TestResource testResource) {
        this.testResource = testResource;
    }

    public ResourceServer() {
    }

    public void setTestResource(TestResource testResource){
        this.testResource = testResource;
    }

    public void setInstanceFromXml(String xmlFile) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance(); //создаем новый экземпляр фабрики
        SAXParser saxParser = factory.newSAXParser(); //создаем новый экземпляр SAXParser
        SaxHandler saxHandler = new SaxHandler(); //создаем экземпляр обработчика
        saxParser.parse(xmlFile, saxHandler); //передаем в парсер  путь к xml файлу и обработчик
        this.testResource = (TestResource) saxHandler.getObject();
    }


    public int getAge() {
        return testResource.getAge();
    }

    public String getName() {
        return testResource.getName();
    }
}
