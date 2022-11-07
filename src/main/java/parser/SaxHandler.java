
/* Создадим класс обработчика событий для анализа XML-документа*/

package parser;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import reflection.ReflectionHelper;

public class SaxHandler extends DefaultHandler {
    private static final String CLASSNAME = "class";
    private String element = null;
    private Object object = null;

    //вызывается, когда начинается синтаксический анализ
    public void startDocument() throws SAXException {
        System.out.println("Start document");
    }

    public void endDocument() throws SAXException {
        System.out.println("End document ");
    }

    /* startElement() вызывается, когда начинается синтаксический анализ элемента.
      uri — это пространство, в котором находится элемент, localName — это имя элемента без префикса,
      qName — это имя элемента с префиксом (если он есть, иначе просто имя элемента)
     */
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException  {
        if (!qName.equals(CLASSNAME)) {
            element = qName;
        } else {
            String className = attributes.getValue(0);
            System.out.println("Class name: " + className);
            object = ReflectionHelper.createInstance(className);
        }
    }

    //вызывается, когда синтаксический анализ элемента заканчивается
    public void endElement(String uri, String localName, String qName) throws SAXException {
        element = null;
    }

    //метод получает символы с границами т.е. текст между элементами
    public void characters(char ch[], int start, int length) throws SAXException {
        if (element != null) {
            String value = new String(ch, start, length);
            System.out.println(element + " = " + value);
            ReflectionHelper.setFieldValue(object, element, value);
        }
    }

    public Object getObject() {
        return object;
    }
}
