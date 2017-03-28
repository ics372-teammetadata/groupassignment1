import com.metadata.LibraryDomain.*;
import junit.framework.TestCase;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

/**
 * Created by chris on 3/16/2017.
 */
public class FileProcessorTest extends TestCase {
    private Library library  = new Library();
    private InventoryItem item;
    private FileProcessor loadedJsonFile = null;
    private FileProcessor loadedXMLFile = null;
    private FileProcessor memberFile = null;
    private boolean fileLoaded = false;
    private File nullJSONfile = null;
    private File JSONFile = new File("./src/JSONLib.json");
    private File membersXMFile = new File("./src/members.xml");
    private File xmlFile = new File("./src/testLib.xml");

    private boolean reload = false;
    private Member loggedOnUser;
    private MemberList memberList;
    private JSONObject jsonObject = null;
    private static final String LIBRARY_ITEMS = "library_items";

    FileProcessor f = new FileProcessor(JSONFile);

    public void testProcessJSONData() throws Exception{
        //verify that an empty library list is created when the processJSONData method is called on a null file Object and that a null pointer exception is caught

        loadedJsonFile = new FileProcessor(JSONFile);
        JSONParser parser = new JSONParser();

        try {
            library = loadedJsonFile.processJSONData();
        }catch(NullPointerException e){
            assertEquals("java.lang.NullPointerException", e.toString());
        }
        assertNotNull(library);
        assertTrue(!(library.isEmpty()));


        FileReader fileReader = new FileReader(this.JSONFile.getPath());
        jsonObject = (JSONObject) parser.parse(fileReader);
        assertNotNull(jsonObject.get(LIBRARY_ITEMS));

    }

    public void testWriteJSONData() throws Exception {
        CD cd = new CD("id123", "OK Computer", "com.metadata.LibraryDomain.CD", "Radiohead", null, null, null);
        library.add(cd);

        try {
            loadedJsonFile.writeJSONData(library);
        }catch(NullPointerException e){
            assertEquals("java.lang.NullPointerException", e.toString());
        }

        assertTrue(!(library.isEmpty()));
    }

    public void testProcessXMLData() throws Exception {
        library = null;
        loadedXMLFile = new FileProcessor(xmlFile);
        library = loadedXMLFile.processXMLData();
        assertTrue(!(library.isEmpty()));
    }

    public void testWriteXMLData() throws Exception {
        //verify that an empty library list is created when the processXMLData method is called on a null file Object and that an illegal argument exception is caught
        CD cd = new CD("id123", "OK Computer", "com.metadata.LibraryDomain.CD", "Radiohead", null, null, null);
        library.add(cd);
        loadedXMLFile = new FileProcessor(xmlFile);
        try {
            loadedXMLFile.writeXMLData(library);
        }catch(NullPointerException e){
            assertEquals("java.lang.NullPointerException", e.toString());
        }
        assertNotNull(library);
        assertTrue(!(library.isEmpty()));
    }

    public void testProcessXMLMemberList() throws Exception {

        memberFile = new FileProcessor(membersXMFile);
        memberList = memberFile.processXMLMemberList();
        assertEquals("Ricky Rubio", memberList.getMemberByCardNumber("111").getName());

    }

}