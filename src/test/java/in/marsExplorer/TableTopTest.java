package in.marsExplorer;

import in.marsExplorer.models.TableTop;
import org.junit.jupiter.api.*;

@DisplayName("Trivial test for TableTop class")
public class TableTopTest {
    TableTop tableTop;

    @BeforeEach
    void setUp() throws Exception {
        tableTop = new TableTop(5, 5);
    }

    @AfterEach
    void tearDown() throws Exception {

    }

    @Test
    void testLength5() {
        Assertions.assertEquals(5, tableTop.getDimentions().get("length"));
    }



}
