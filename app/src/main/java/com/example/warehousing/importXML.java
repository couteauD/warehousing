package com.example.warehousing;

import android.content.Context;
import android.net.Uri;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

class importXML implements importFile{

    public List<ModuleOrder> importFile(Context context, String path) {
        List<ModuleOrder> list = new ArrayList<>();
        try {
            File file = new File(path);
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder;
            builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(file);
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getElementsByTagName("order");
            for(int i=0;i<nodeList.getLength();i++){
                Element order = (Element) nodeList.item(i);
                String orderID = order.getElementsByTagName("orderID").item(0).getTextContent();
                String clothingID = order.getElementsByTagName("clothingID").item(0).getTextContent();
                String count = order.getElementsByTagName("count").item(0).getTextContent();
                ModuleOrder moduleOrder = new ModuleOrder(orderID,clothingID,count);
                list.add(moduleOrder);
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
