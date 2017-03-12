import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * Created by Christopher on 3/8/2017.
 */
public class testMAIN
{
    //static CardRepo cardRepo = new CardRepo();
//    public static void main(String[] args)
//    {
//        cardRepo.addCard("123");
//        cardRepo.addCard("456");
//
//        appSrc.testMAIN test = new appSrc.testMAIN();
//
//        MemberData abc = test.validateUser("456");
//
//        if (abc == null)
//        {
//            System.out.println("invalid user");
//        }
//        else
//            for (int i = 0; i < abc.getData().length; i++)
//            {
//                //System.out.println(abc.getData()[i]);
//            }




//    public MemberData validateUser(String ID)
//    {
//        return cardRepo.getMemberData(ID);
//    }
//}

//    private static final String ITEM = "Item";
//    private static final String ID = "id";
//    private static final String TYPE = "type";
//    private static final String CD = "CD";
//    private static final String DVD = "DVD";
//    private static final String MAGAZINE = "MAGAZINE";
//    private static final String BOOK = "BOOK";
//    private static final String NAME = "Name";
//    private static final String ARTIST = "Artist";
//    private static final String AUTHOR = "Author";
//    private static final String VOLUME = "Volume";
//
//    public static void main(String[] args) {
//
//            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//            String id = "";
//            String artist = "";
//            String type = "";
//            String name = "";
//            String author = "";
//            String volume = "";
//
//            try {
//                //Select XML file
//                DocumentBuilder builder = factory.newDocumentBuilder();
//                Document doc = builder.parse("C:/TEMP/testLib.xml");
//
//                //Select parent elements by tag name
//                NodeList itemList = doc.getElementsByTagName(ITEM);
//
//                //loop through each parent element
//                for(int i = 0; i < itemList.getLength(); i++){
//                    Node node = itemList.item(i);
//                    if(node.getNodeType()==Node.ELEMENT_NODE){
//                        Element libItem = (Element) node;
//
//                        // Define id variable for each parent element
//                        id = libItem .getAttribute(ID);
//
//                        // Define type variable for each parent element
//                        type = libItem .getAttribute(TYPE);
//
//                        //get all child nodes
//                        NodeList libItemList = libItem .getChildNodes();
//
//                        //loop through all child nodes, defining variable where possible
//                        for(int j = 0; j< libItemList.getLength(); j++){
//                            Node childNode = libItemList.item(j);
//                            if(childNode.getNodeType()==Node.ELEMENT_NODE){
//                                Element metadata = (Element) childNode;
//
//                                switch(metadata.getTagName()){
//                                    case ARTIST : artist = metadata.getTextContent();
//                                        break;
//                                    case AUTHOR : author = metadata.getTextContent();
//                                        break;
//                                    case VOLUME : volume = metadata.getTextContent();
//                                        break;
//                                    case NAME : name = metadata.getTextContent();
//                                        break;
//                                }
//                                //System.out.println("Library Item " + id + ":" + metadata.getTagName() + "=" + metadata.getTextContent());
//                            }
//                        }
//
//                        System.out.println(type);
//                        System.out.println(id);
//                        System.out.println(name);
//                        if(type.equals(CD)) {
//                            System.out.println(artist);
//                        }
//                        if(type.equals(DVD)){
//                        }
//                        if(type.equals(BOOK)) {
//                            System.out.println(author);
//                        }
//                        if(type.equals(MAGAZINE)) {
//                            System.out.println(volume);
//                        }
//                        System.out.println("");
//                    }
//                }
//            }catch(ParserConfigurationException e){
//                e.printStackTrace();
//            }catch(SAXException e){
//                System.out.println("GOOOOTTTTT ITTTTT");
//                e.printStackTrace();
//            }catch(IOException e){
//                e.printStackTrace();
//            }
//        }

        public static void main(String[] args) {
//            File file = new File("C:/TEMP/testLib.xml");
//            FileProcessor fileProcessor = new FileProcessor(file);
//            fileProcessor.processXMLData();

        }

    }