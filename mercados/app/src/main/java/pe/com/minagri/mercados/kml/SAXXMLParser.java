package pe.com.minagri.mercados.kml;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

public class SAXXMLParser {
    public static List<ParsingStructure> parse(InputStream is) {
        List<ParsingStructure> parsingStru = null;
        try {
            // create a XMLReader from SAXParser
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();

            SAXXMLHandler saxHandler = new SAXXMLHandler();
            xmlReader.setContentHandler(saxHandler);
            xmlReader.parse(new InputSource(is));
            // get the `get list`
            parsingStru = saxHandler.getParsingvalues();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return parsingStru;
    }
}
