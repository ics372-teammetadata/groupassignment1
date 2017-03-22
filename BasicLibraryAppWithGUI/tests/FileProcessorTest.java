import junit.framework.TestCase;
import org.json.simple.JSONObject;

import java.io.File;

/**
 * Created by chris on 3/16/2017.
 */
public class FileProcessorTest extends TestCase {
    private Library library  = new Library();
    private InventoryItem item;
    private FileProcessor loadedJsonFile = null;
    private FileProcessor loadedXMLFile = null;
    private boolean fileLoaded = false;
    private File file = null;
    private boolean reload = false;
    private Member loggedOnUser;
    private MemberList memberList;


    public void testProcessJSONData() throws Exception {
        //verify that an empty library list is created when the processJSONData method is called on a null file Object and that a null pointer exception is caught

        loadedJsonFile = new FileProcessor(file);
        try {
            library = loadedJsonFile.processJSONData();
        }catch(NullPointerException e){
            assertEquals("java.lang.NullPointerException", e.toString());
            System.out.println(e.toString());
        }
        assertNotNull(library);
        assertTrue(library.isEmpty());
    }

    public void testWriteJSONData() throws Exception {

    }

    public void testProcessXMLData() throws Exception {

    }

    public void testWriteXMLData() throws Exception {

    }

    public void testProcessXMLMemberList() throws Exception {

    }

}