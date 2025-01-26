package helper;


import enums.DocumentType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;

import static enums.DocumentType.*;
import static enums.DocumentType.XML;

public class DocumentHelper {

    private final Logger log = LogManager.getLogger(DocumentHelper.class);

    protected DocumentType isJsonOrXml(String body) {
        if (body.startsWith("<")) {
            return XML;
        } else if (body.startsWith("{") || body.startsWith("[")) {
            return JSON;
        } else {
            log.info("{}, is not a xml or json", body);
        }
        return UNDEFINED_TYPE;
    }

    public Object updateDocument(String body, String selector, String newValue) {
        JsonHelper jsonHelper = new JsonHelper();
        if (isJsonOrXml(body) == XML) {
            XmlHelper xmlHelper = new XmlHelper();
            Document newBody = xmlHelper.updateXmlNodesByXpath(body, selector, newValue);
            return xmlHelper.docToXmlString(newBody);
        } else if (isJsonOrXml(body) == JSON) {
            return jsonHelper.updateJsonValue(body, selector, newValue);
        } else if (isJsonOrXml(body) == UNDEFINED_TYPE) {
            return body;
        } else {
            return null;
        }
    }

    public String getValueByName(String body, String selector) {
        JsonHelper jsonHelper = new JsonHelper();
        if (isJsonOrXml(body) == XML) {
            //XmlHelper xmlHelper = new XmlHelper();
            //return xmlHelper.getXmlValueByXpath(body, selector);
        } else if (isJsonOrXml(body) == JSON) {
            return jsonHelper.getJsonValue(body, selector);
        } else if (isJsonOrXml(body) == UNDEFINED_TYPE) {
            return null;
        } else {
            return null;
        }
        return null;
    }

}
