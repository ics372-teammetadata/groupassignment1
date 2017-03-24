package com.metadata.LibraryDomain;

/**
 * Created by chris on 1/20/2017.
 **/



import java.io.*;
import java.time.format.DateTimeParseException;

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

import team.metadata.LibraryDomain.*;

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
    private static final String ITEM_ARTIST = "item_artist";
    private static final String ITEM_AUTHOR = "item_author";
    private static final String LIBRARY_ITEMS = "library_items";
    private static final String ITEM_NAME = "item_name";
    private static final String ITEM_ID = "item_id";
    private static final String ITEM_TYPE = "item_type";
    private static final String CD = "com.metadata.LibraryDomain.CD";
    private static final String DVD = "com.metadata.LibraryDomain.DVD";
    private static final String BOOK = "com.metadata.LibraryDomain.Book";
    private static final String MAGAZINE = "com.metadata.LibraryDomain.Magazine";

    //static variables used by XML methods
    private static final String ITEM = "Item";
    private static final String ID = "id";
    private static final String TYPE = "type";
    private static final String XML_CD = "com.metadata.LibraryDomain.CD";
    private static final String XML_DVD = "com.metadata.LibraryDomain.DVD";
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


    String memberID;
    String memberName;
    String memberCardNumber;

    /**
     *      Constructor
     *      Called by the UIController class
     */

    public FileProcessor(File f){
        file = f;
    }
    
    /**
     *      Method name : processJSONData
     *      Retrieves JSON importedJSONData from cardRepo file (which is received through the constructor)
     *      This is called by the FileProcesspor (this) processJSONData method
     *      Processes JSON file importedJSONData and generates library items from JSON object info and returns cardRepo com.metadata.LibraryDomain.Library list
     *      throws cardRepo ParseException and DateTimeParseException that can be caught within the UIController's loadFile method,
     *      which displays cardRepo meaningful error message to the user
     **/

    public Library processJSONData() throws ParseException, DateTimeParseException, FileNotFoundException, IOException {
        //instantiate JSON parser - throws FileNotFOundException and IOException

        JSONParser parser = new JSONParser();
        FileReader fileReader = new FileReader(this.file.getPath());
        jsonObject = (JSONObject) parser.parse(fileReader);

        //collection info from JSON file
        if (file.exists()) {
            System.out.println(file.getPath());
            //loop through JSON array, creating 'com.metadata.LibraryDomain.Library Item" objects, adding them to an inventory list to be returned
            if (jsonObject.get(LIBRARY_ITEMS) != null) {
                for (Object jArrayItem : (JSONArray) jsonObject.get(LIBRARY_ITEMS)) {

                    JSONObject arrayItem = (JSONObject) jArrayItem;

                    //variables to be used when instantiating com.metadata.LibraryDomain.InventoryItem objects
                    itemName = (String) arrayItem.get(ITEM_NAME);
                    itemID = (String) arrayItem.get(ITEM_ID);
                    itemType = (String) arrayItem.get(ITEM_TYPE);
                    itemDueDate = null;
                    itemCheckOutDate = null;
                    isCheckedOut = false;

                    //generate library items from JSON object info and return an com.metadata.LibraryDomain.InventoryItem
                    if (arrayItem.containsKey(ITEM_ISCHECKEDOUT)) {
                        // IF arrayItem.containsKey(ITEM_ISCHECKEDOUT), then we are using JSON file file than has been processed at least once by the application
                        // Variables will be populates with values retrieved from the JSON file

                        itemDueDate = (String) arrayItem.get(ITEM_DUEDATE);
                        itemCheckOutDate = (String) arrayItem.get(ITEM_CHECKOUTDATE);
                        isCheckedOut = (boolean) arrayItem.get(ITEM_ISCHECKEDOUT);
                        checkedOutTo = (String) arrayItem.get(ITEM_CHECKEDOUTTO);
                    }

                    if (arrayItem.get(ITEM_TYPE).equals(CD)) {
                        artist = (String) arrayItem.get(ITEM_ARTIST);
                        libItem = new CD(itemID, itemName, itemType, artist, isCheckedOut, itemDueDate, itemCheckOutDate, checkedOutTo);
                    } else if (arrayItem.get(ITEM_TYPE).equals(BOOK)) {
                        author = (String) arrayItem.get(ITEM_AUTHOR);
                        libItem = new Book(itemID, itemName, itemType, author, isCheckedOut, itemDueDate, itemCheckOutDate, checkedOutTo);
                    } else if (arrayItem.get(ITEM_TYPE).equals(DVD)) {
                        libItem = new DVD(itemID, itemName, itemType, isCheckedOut, itemDueDate, itemCheckOutDate, checkedOutTo);
                    } else if (arrayItem.get(ITEM_TYPE).equals(MAGAZINE)) {
                        libItem = new Magazine(itemID, itemName, itemType, isCheckedOut, itemDueDate, itemCheckOutDate, checkedOutTo);
                    }

                    //Add inventory item to the com.metadata.LibraryDomain.Library list
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
     *      Loops through com.metadata.LibraryDomain.Library list, adds each item to the JSON array
     *      Uses FileWriter to write the com.metadata.LibraryDomain.InventoryItem data to the previously loaded JSON file
     *      Throws IOException that is caught by the UIController save() method
     */

    public void writeJSONData(Library lib) throws IOException {
        JSONObject parentOutputJObject = new JSONObject();
        JSONArray outputJArray = new JSONArray();

        for (InventoryItem i : lib) {
            JSONObject outputChildObject = new JSONObject();
            if (i.getType().equals(CD)) {
                CD cd = (CD) i;
                outputChildObject.put(ITEM_ARTIST, cd.getArtist());
            } else if (i.getType().equals(BOOK)) {
                Book b = (Book) i;
                outputChildObject.put(ITEM_AUTHOR, b.getAuthor());
            }
            outputChildObject.put(ITEM_NAME, i.getName());
            outputChildObject.put(ITEM_TYPE, i.getType());
            outputChildObject.put(ITEM_ID, i.getID());
            outputChildObject.put(ITEM_ISCHECKEDOUT, i.isCheckedOut());
            outputChildObject.put(ITEM_DUEDATE, i.getDueDate());
            outputChildObject.put(ITEM_CHECKOUTDATE, i.getCheckoutDate());
            outputChildObject.put(ITEM_CHECKEDOUTTO, i.getCheckedOutToUserCardNumber());
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

    public Library processXMLData() throws ParserConfigurationException, SAXException, IOException, DateTimeParseException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);

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
                        switch(metadata.getTagName().toCharArray()){
                            case ARTIST : artist = metadata.getTextContent();
                                break;
                            case AUTHOR : author = metadata.getTextContent();
                                break;
                            case VOLUME : volume = metadata.getTextContent();
                                break;
                            case NAME : itemName = metadata.getTextContent();
                                break;
                            case ITEM_ISCHECKEDOUT : isCheckedOut = Boolean.parseBoolean(metadata.getTextContent());
                                break;
                            case ITEM_DUEDATE : itemDueDate = metadata.getTextContent();
                                System.out.println(itemDueDate);
                                if(metadata.getTextContent().equals("null")){
                                    itemDueDate = null;
                                }
                                break;
                            case ITEM_CHECKOUTDATE : itemCheckOutDate = metadata.getTextContent();
                                if(metadata.getTextContent().equals("null")){
                                    itemCheckOutDate = null;
                                }
                                break;
                            case ITEM_CHECKEDOUTTO : checkedOutTo = metadata.getTextContent();
                                if(metadata.getTextContent().equals("null")){
                                    checkedOutTo = null;
                                }
                                break;
                        }
                    }
                }

                if(itemType.equals(XML_CD)) {
                    libItem = new CD(itemID, itemName, itemType, artist, isCheckedOut, itemDueDate, itemCheckOutDate, checkedOutTo);
                }
                if(itemType.equals(XML_DVD)){
                    libItem = new DVD(itemID, itemName, itemType, isCheckedOut, itemDueDate, itemCheckOutDate, checkedOutTo);
                }
                if(itemType.equals(XML_BOOK)) {
                    libItem = new Book(itemID, itemName, itemType, author, isCheckedOut, itemDueDate, itemCheckOutDate, checkedOutTo);
                }
                if(itemType.equals(XML_MAGAZINE)) {
                    libItem = new Magazine(itemID, itemName, itemType, volume, isCheckedOut, itemDueDate, itemCheckOutDate, checkedOutTo);
                }

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
     * Loops through com.metadata.LibraryDomain.Library list, adds each item to the JXML file
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
                    Book book = (Book) inventoryItem;
                    Element itemAuthor = doc.createElement(AUTHOR);
                    itemAuthor.appendChild(doc.createTextNode(book.getAuthor()));
                    itemElement.appendChild(itemAuthor);
                } else if (inventoryItem.getType().equals(XML_CD)) {
                    CD cd = (CD) inventoryItem;
                    Element itemArtist = doc.createElement(ARTIST);
                    itemArtist.appendChild(doc.createTextNode(cd.getArtist()));
                    itemElement.appendChild(itemArtist);
                } else if (inventoryItem.getType().equals(XML_MAGAZINE)) {
                    Magazine magazine = (Magazine) inventoryItem;
                    Element itemVolume = doc.createElement(VOLUME);
                    itemVolume.appendChild(doc.createTextNode(magazine.getVolume()));
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
     *  Method name: processXMLData()
     *
     *  Reads XML data from a file
     *  Throws ParserConfigurationException, SAXException, IOException that is caught by the UIControlloer save() method
     */

    public MemberList processXMLMemberList() throws ParserConfigurationException, SAXException, IOException, DateTimeParseException{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);

        NodeList members = doc.getElementsByTagName("com.metadata.LibraryDomain.Member");
        //loop through each parent element
        for(int i = 0; i < members.getLength(); i++) {
            Node node = members.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element libItemElement = (Element) node;
                memberID = libItemElement.getAttribute("id");
                memberName = libItemElement.getAttribute("name");
                memberCardNumber = libItemElement.getAttribute("cardNumber");
            }
            member = new Member(memberID, memberName, memberCardNumber);
            memberList.add(member);
        }


        return memberList;
    }
}
