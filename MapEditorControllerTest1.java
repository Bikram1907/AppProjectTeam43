package basic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sample.controller.MapEditorController;
import sample.model.GameDetails;

import java.io.File;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapEditorControllerTest1 {

    private File validfile;
    private MapEditorController mecObject = new MapEditorController();

    @Before
    public void setUp() throws Exception {
        validfile = new File("E:\\IntelliJ\\AppProject\\src\\resources\\Maps\\invalid.map");
    }
    
     @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void saveContent() {
        assertFalse(mecObject.ismapValid(validfile,"EDITMAP"));
    }
}
