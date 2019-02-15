package nl.nn.adapterframework.pipes;

import nl.nn.adapterframework.configuration.ConfigurationException;
import nl.nn.adapterframework.core.*;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class FileLineIteratorPipeTest extends PipeTestBase<FileLineIteratorPipe> {

    IPipeLineSession session = new PipeLineSessionBase();

    @Override
    public FileLineIteratorPipe createPipe() {
        return new FileLineIteratorPipe();
    }

    @Test
    public void getterSetterMove2dirAfterTransform() {
        String dummyDirectory = "src/main/blabla";
        pipe.setMove2dirAfterTransform(dummyDirectory);
        assertEquals(dummyDirectory, pipe.getMove2dirAfterTransform());
    }

    @Test
    public void getterSetterMove2dirAfterError() {
        String dummyDirectory = "src/main/blabla";
        pipe.setMove2dirAfterError(dummyDirectory);
        assertEquals(dummyDirectory, pipe.getMove2dirAfterError());
    }

    @Test
    public void testRightFile() throws SenderException {
        Object dummyObject = new File("src/test/resources/documents/doc001.pdf");
        String correlationID = "123";
        Map threadContext = new HashMap();
        Reader reader = pipe.getReader(dummyObject, session, correlationID, threadContext);
        assertTrue(reader instanceof FileReader);
    }

    @Test (expected = SenderException.class)
    public void testNonExistingFile() throws SenderException {
        Object dummyObject = new File("src/test/resources/documents/noFile.pdf");
        String correlationID = "123";
        Map threadContext = new HashMap();
        pipe.getReader(dummyObject, session, correlationID, threadContext);
    }

    @Test (expected = SenderException.class)
    public void testNotAFile() throws SenderException {
        Object dummyObject = "notAFileButAString";
        String correlationID = "123";
        Map threadContext = new HashMap();
        pipe.getReader(dummyObject, session, correlationID, threadContext);
    }

    @Test (expected = SenderException.class)
    public void testNoInputFileReader() throws SenderException {
        String correlationID = "123";
        Map threadContext = new HashMap();
        pipe.getReader(null, session, correlationID, threadContext);
    }

    @Test (expected = PipeRunException.class)
    public void testNoInputFilePipe() throws PipeRunException {
        pipe.doPipe(null, session);
    }

    @Test (expected = PipeRunException.class)
    public void testNotInstanceString() throws PipeRunException {
        Object dummyObject = new File("src/test/resources/documents/noFile.pdf");
        pipe.doPipe(dummyObject, session);
    }

    @Test
    public void testSuccessfulTransform() throws PipeRunException, ConfigurationException {
        pipe.setMove2dirAfterTransform("src/test/resources/documents");
        Object dummyObject = "src/test/resources/documents/doc001.pdf";
        pipe.setSender(Mockito.mock(ISender.class));
        pipe.configure();
        PipeRunResult result = pipe.doPipe(dummyObject, session);

        assertNotNull(result.getResult());
        assertEquals(result.getPipeForward().getName(), "success");
    }

    @Test (expected = PipeRunException.class)
    public void testSuccessfulError() throws PipeRunException {
        pipe.setMove2dirAfterError("src/test/resources/documents");
        Object dummyObject = "src/test/resources/documents/doc001.pdf";
        pipe.doPipe(dummyObject, session);
    }
}