package com.metadata.LibraryDomain;

/**
 * Created by chris on 1/20/2017.
 **/



import java.io.*;

import org.json.simple.*;
import org.json.simple.parser.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class FileProcessor {

    /**
     *      Variables
     */

    private Library library = new Library();
    private MemberList memberList = new MemberList();
    private InventoryItem libItem = null;
    private Member member = null;
    private File file;
    private JSONObject jsonObject = null;

    //static variables uses by JSON methods
    private enum jsonStrings {item_artist, item_author, library_items, item_name, item_id, item_type, CD, DVD, Book, Magazine, item_isCheckedOut, item_dueDate, item_checkoutDate, item_checkedOutTo}
    private static final String ITEM_ARTIST = "item_artist";
    private static final String ITEM_AUTHOR = "item_author";
    private static final String LIBRARY_ITEMS = "library_items";
    private static final String ITEM_NAME = "item_name";
    private static final String ITEM_ID = "item_id";
    private static final String ITEM_TYPE = "item_type";
    private static final String CD = "CD";
    private static final String DVD = "DVD";
    private static final String BOOK = "Book";
    private static final String MAGAZINE = "Magazine";

    //static variables used by XML methods
    private enum xmlStrings {Item, id, type, CD, DVD, MAGAZINE, BOOK, Name, Artist, Author, Volume, item_isCheckedOut, item_dueDate, item_checkoutDate, item_checkedOutTo, item_status}
    private static final String ITEM = "Item";
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String XML_CD = "CD";
    private static final String XML_DVD = "DVD";
    private static final String XML_MAGAZINE = "MAGAZINE";
    private static final String XML_BOOK = "BOOK";
    private static final String NAME = "Name";
    private static final String ARTIST = "Artist";
    private static final String AUTHOR = "Author";
    private static final String VOLUME = "Volume";

    // static variables shared by XML and JSON file methods
    private static final String ITEM_ISCHECKEDOUT = "item_isCheckedOut";
    private static final String ITEM_DUEDATE = "item_dueDate";
    private static final String ITEM_CHECKOUTDATE = "item_checkoutDate";
    private static final String ITEM_CHECKEDOUTTO = "item_checkedOutTo";
    private static final String ITEM_STATUS = "item_status";

    //variables
    String itemName = "";
    String itemID = "";
    String itemType = "";
    String artist = "";
    String author = "";
    String volume = "";
    String itemDueDate = null;
    String itemCheckOutDate = null;
    boolean isCheckedOut = false;
    String checkedOutTo = null;
    String itemStatus = null;


    String memberID;
    String memberName;
    String memberPassword;
    String memberPrivilege;

    String staffPassword;
    String staffName;
    String staffUserName;
    /**
     *      Constructor
     *      Called by the UIController class
     */

    public FileProcessor(File f){
        file = f;
    }
    public FileProcessor(){}
    
    /**
     *      Method name : processJSONData
     *      Retrieves JSON importedJSONData from cardRepo file (which is received through the constructor)
     *      This is called by the FileProcesspor (this) processJSONData method
     *      Processes JSON file importedJSONData and generates library items from JSON object info and returns cardRepo Library list
     *      throws cardRepo ParseException and DateTimeParseException that can be caught within the UIController's loadFile method,
     *      which displays cardRepo meaningful error message to the user
     **/

    public Library processJSONData() throws ParseException, FileNotFoundException, IOException{
        return processJSONData(new FileInputStream(file));
    }

    public Library processJSONData(InputStream inputStream) throws ParseException, FileNotFoundException, IOException {
        //instantiate JSON parser - throws FileNotFOundException and IOException

        JSONParser parser = new JSONParser();
        FileReader fileReader = new FileReader(this.file.getPath());
        jsonObject = (JSONObject) parser.parse(new InputStreamReader(inputStream));

        //collection info from JSON file
        if (file.exists()) {
            //loop through JSON array, creating 'Library Item" objects, adding them to an inventory list to be returned
            if (jsonObject.get(LIBRARY_ITEMS) != null) {
                for (Object jArrayItem : (JSONArray) jsonObject.get(LIBRARY_ITEMS)) {

                    JSONObject arrayItem = (JSONObject) jArrayItem;

                    //variables to be used when instantiating InventoryItem objects
                    itemName = (String) arrayItem.get(ITEM_NAME);
                    itemID = (String) arrayItem.get(ITEM_ID);
                    itemType = (String) arrayItem.get(ITEM_TYPE);
                    if (arrayItem.containsKey(ITEM_ARTIST)) artist = (String) arrayItem.get(ITEM_ARTIST);
                    else if (arrayItem.containsKey(ITEM_AUTHOR)) artist = (String) arrayItem.get(ITEM_AUTHOR);
                    else artist = "";
                    itemDueDate = null;
                    itemCheckOutDate = null;
                    isCheckedOut = false;
                    if (arrayItem.containsKey(ITEM_STATUS))itemStatus = (String) arrayItem.get(ITEM_STATUS);
                    else itemStatus = "";

                    //generate library items from JSON object info and return an InventoryItem
                    if (arrayItem.containsKey(ITEM_ISCHECKEDOUT)) {
                        // IF arrayItem.containsKey(ITEM_ISCHECKEDOUT), then we are using JSON file file than has been processed at least once by the application
                        // Variables will be populates with values retrieved from the JSON file

                        itemDueDate = (String) arrayItem.get(ITEM_DUEDATE);
                        itemCheckOutDate = (String) arrayItem.get(ITEM_CHECKOUTDATE);
                        isCheckedOut = Boolean.parseBoolean(arrayItem.get(ITEM_ISCHECKEDOUT).toString());
                        checkedOutTo = (String) arrayItem.get(ITEM_CHECKEDOUTTO);
                    }

                    if (itemStatus == null || itemStatus.equals(""))itemStatus = "Available";
                    libItem = new InventoryItem(itemID,itemName,itemType,artist,itemDueDate,itemCheckOutDate,checkedOutTo,itemStatus);

                    //Add inventory item to the Library list
                    library.add(libItem);
                }
            }
        }
        //library.sort();
        return library;
    }

    /**
     *      Method name : writeJSONData
     *      @param lib
     *
     *      Saves Inventory Items to the previously loaded JSON file
     *      Loops through Library list, adds each item to the JSON array
     *      Uses FileWriter to write the InventoryItem data to the previously loaded JSON file
     *      Throws IOException that is caught by the UIController save() method
     */

    public void writeJSONData(Library lib) throws IOException {
        JSONObject parentOutputJObject = new JSONObject();
        JSONArray outputJArray = new JSONArray();

        for (InventoryItem i : lib) {
            JSONObject outputChildObject = new JSONObject();
            if (i.getType().equals(CD)) {
                outputChildObject.put(ITEM_ARTIST, i.getAuthor());

            } else if (i.getType().equals(BOOK)) {
                outputChildObject.put(ITEM_AUTHOR,i.getAuthor());

            }
            outputChildObject.put(ITEM_NAME, i.getName());
            outputChildObject.put(ITEM_TYPE, i.getType());
            outputChildObject.put(ITEM_ID, i.getID());
            outputChildObject.put(ITEM_ISCHECKEDOUT, i.isCheckedOut());
            outputChildObject.put(ITEM_DUEDATE, i.getDueDate());
            outputChildObject.put(ITEM_CHECKOUTDATE, i.getCheckoutDate());
            outputChildObject.put(ITEM_CHECKEDOUTTO, i.getCheckedOutToUserCardNumber());
            outputChildObject.put(ITEM_STATUS, i.getStatus());
            outputJArray.add(outputChildObject);
        }

        parentOutputJObject.put(LIBRARY_ITEMS, outputJArray);

        //write file - throws IOException
        FileWriter file = new FileWriter(this.file.getPath());
        file.write(parentOutputJObject.toJSONString());
        file.flush();
    }


    /**
     *  Method name: processXMLData()
     *
     *  Reads XML data from a file
     *  Throws ParserConfigurationException, SAXException, IOException that is caught by the UIControlloer save() method
     */

    public Library processXMLData() throws ParserConfigurationException, SAXException, IOException{
        return processXMLData(new FileInputStream(file));
    }

    public Library processXMLData(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputStream);

        NodeList itemList = doc.getElementsByTagName(ITEM);
        //loop through each parent element
        for(int i = 0; i < itemList.getLength(); i++){
            libItem  = null;
            Node node = itemList.item(i);
            if(node.getNodeType()==Node.ELEMENT_NODE){
                Element libItemElement = (Element) node;
                itemID = libItemElement.getAttribute(ID);
                itemType = libItemElement.getAttribute(TYPE);

                //get all child nodes
                NodeList libItemList = libItemElement .getChildNodes();

                //loop through all child nodes, defining variable where possible
                for(int j = 0; j< libItemList.getLength(); j++){
                    Node childNode = libItemList.item(j);
                    if(childNode.getNodeType()==Node.ELEMENT_NODE){
                        Element metadata = (Element) childNode;
                        switch(xmlStrings.valueOf(metadata.getTagName())){
                            case Artist: artist = metadata.getTextContent();
                                break;
                            case Author: artist = metadata.getTextContent();
                                break;
                            case Volume: volume = metadata.getTextContent();
                                break;
                            case Name: itemName = metadata.getTextContent();
                                break;
                            case item_isCheckedOut: isCheckedOut = Boolean.parseBoolean(metadata.getTextContent());
                                break;
                            case item_dueDate: itemDueDate = metadata.getTextContent();
                                if(metadata.getTextContent().equals("null")){
                                    itemDueDate = null;
                                }
                                break;
                            case item_checkoutDate: itemCheckOutDate = metadata.getTextContent();
                                if(metadata.getTextContent().equals("null")){
                                    itemCheckOutDate = null;
                                }
                                break;
                            case item_checkedOutTo: checkedOutTo = metadata.getTextContent();
                                if(metadata.getTextContent().equals("null")){
                                    checkedOutTo = null;
                                }
                                break;
                            case item_status: itemStatus = metadata.getTextContent();
                                break;
                        }
                    }
                }


                if (itemStatus == null|| itemList.equals(""))itemStatus = "Available";
                libItem = new InventoryItem(itemID, itemName,itemType,artist,itemDueDate,itemCheckOutDate,checkedOutTo,itemStatus);
                libItem.setVolume(volume);

                if(libItem != null){
                    library.add(libItem);
                }
            }
        }
        library.sort();
        return library;
    }

    /**
     * Method name : writeXMLData()
     *
     * Saves Inventory Items to the previously loaded XML library file
     * Loops through Library list, adds each item to the JXML file
     * Throws ParserConfigurationException,TransformerException that are caught by the UIControlloer save() method
     *
     * @param lib
     * @throws ParserConfigurationException
     * @throws TransformerException
     */

    public void writeXMLData(Library lib) throws ParserConfigurationException,TransformerException{

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // root elements
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("LibData");
            doc.appendChild(rootElement);

            // Loop through library list, writing each Inventory item to the XML file
            for(InventoryItem inventoryItem : lib) {

                // Item elements
                Element itemElement = doc.createElement(ITEM);
                rootElement.appendChild(itemElement);
                // set attributes
                itemElement.setAttribute(TYPE, inventoryItem.getType());
                itemElement.setAttribute(ID, inventoryItem.getID());

                // set child elements
                Element itemName = doc.createElement(NAME);
                itemName.appendChild(doc.createTextNode(inventoryItem.getName()));
                itemElement.appendChild(itemName);

                Element isCheckedOut = doc.createElement(ITEM_ISCHECKEDOUT);
                isCheckedOut.appendChild(doc.createTextNode(String.valueOf(inventoryItem.isCheckedOut())));
                itemElement.appendChild(isCheckedOut);

                Element itemDueDate = doc.createElement(ITEM_DUEDATE);
                itemDueDate.appendChild(doc.createTextNode(String.valueOf(inventoryItem.getDueDate())));
                itemElement.appendChild(itemDueDate);

                Element itemCheckOutDate = doc.createElement(ITEM_CHECKOUTDATE);
                itemCheckOutDate.appendChild(doc.createTextNode(String.valueOf(inventoryItem.getCheckoutDate())));
                itemElement.appendChild(itemCheckOutDate);

                Element itemCheckedOutTo = doc.createElement(ITEM_CHECKEDOUTTO);
                itemCheckedOutTo.appendChild(doc.createTextNode(String.valueOf(inventoryItem.getCheckedOutToUserCardNumber())));
                itemElement.appendChild(itemCheckedOutTo);


                if (inventoryItem.getType().equals(XML_BOOK)) {

                    Element itemAuthor = doc.createElement(AUTHOR);
                    itemAuthor.appendChild(doc.createTextNode(inventoryItem.getAuthor()));
                    itemElement.appendChild(itemAuthor);
                } else if (inventoryItem.getType().equals(XML_CD)) {
                    Element itemArtist = doc.createElement(ARTIST);
                    itemArtist.appendChild(doc.createTextNode(inventoryItem.getAuthor()));
                    itemElement.appendChild(itemArtist);
                } else if (inventoryItem.getType().equals(XML_MAGAZINE)) {
                    Element itemVolume = doc.createElement(VOLUME);
                    itemVolume.appendChild(doc.createTextNode(inventoryItem.getVolume()));
                    itemElement.appendChild(itemVolume);
                }

                // write the content into xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);

                StreamResult result = new StreamResult(file);
                transformer.transform(source, result);
            }
    }

    /**
     *  Method name: processXMLMemberList()
     *
     *  Reads XML data from a file
     *  Throws ParserConfigurationException, SAXException, IOException that is caught by the UIControlloer save() method
     */

    public MemberList processXMLMemberList()throws ParserConfigurationException, SAXException, IOException{
        return processXMLMemberList(new FileInputStream(file));
    }

    public MemberList processXMLMemberList(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(inputStream);

        NodeList members = doc.getElementsByTagName("Member");
        //loop through each parent element
        for(int i = 0; i < members.getLength(); i++) {
            Node node = members.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element libItemElement = (Element) node;
                memberID = libItemElement.getAttribute("id");
                memberName = libItemElement.getAttribute("name");
                memberPassword = libItemElement.getAttribute("password");
                memberPrivilege = libItemElement.getAttribute("privilege");
            }
            member = new Member(memberName, memberID, memberPassword, memberPrivilege);
            memberList.add(member);
        }


        return memberList;
    }


}
