package nl.nn.adapterframework.pipes;

import nl.nn.adapterframework.configuration.ConfigurationException;
import nl.nn.adapterframework.core.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class FilePipeTest extends PipeTestBase<FilePipe> {

    IPipeLineSession session = new PipeLineSessionBase();

    @Override
    public FilePipe createPipe() {
        return new FilePipe();
    }

    @Test
    public void setterActions() {
        String actions = "dummyActions";
        pipe.setActions(actions);

        assertEquals(actions, pipe.fileHandler.getActions());
    }

    @Test
    public void setterCreateDirectory() {
        pipe.setCreateDirectory(true);
        assertTrue(pipe.fileHandler.isCreateDirectory());

        pipe.setCreateDirectory(false);
        assertFalse(pipe.fileHandler.isCreateDirectory());
    }

    @Test
    public void setterWriteLineSeparator() {
        pipe.setWriteLineSeparator(true);
        assertTrue(pipe.fileHandler.isWriteLineSeparator());

        pipe.setWriteLineSeparator(false);
        assertFalse(pipe.fileHandler.isWriteLineSeparator());
    }

    @Test
    public void setterTestCanWrite() {
        pipe.setTestCanWrite(true);
        assertTrue(pipe.fileHandler.isTestCanWrite());

        pipe.setTestCanWrite(false);
        assertFalse(pipe.fileHandler.isTestCanWrite());
    }

    @Test
    public void setterSkipBOM() {
        pipe.setSkipBOM(true);
        assertTrue(pipe.fileHandler.isSkipBOM());

        pipe.setSkipBOM(false);
        assertFalse(pipe.fileHandler.isSkipBOM());
    }

    @Test
    public void setterDeleteEmptyDirectory() {
        pipe.setDeleteEmptyDirectory(true);
        assertTrue(pipe.fileHandler.isDeleteEmptyDirectory());

        pipe.setDeleteEmptyDirectory(false);
        assertFalse(pipe.fileHandler.isDeleteEmptyDirectory());
    }

    @Test
    public void setterStreamResultToServlet() {
        pipe.setStreamResultToServlet(true);
        assertTrue(pipe.fileHandler.isStreamResultToServlet());

        pipe.setStreamResultToServlet(false);
        assertFalse(pipe.fileHandler.isStreamResultToServlet());
    }

    @Test
    public void setterDirectory() {
        String directory = "dummyDirectory";
        pipe.setDirectory(directory);

        assertEquals(pipe.fileHandler.getDirectory(), directory);
    }

    @Test
    public void setterWriteSuffix() {
        String writeSuffix = "dummyWriteSuffix";
        pipe.setWriteSuffix(writeSuffix);

        assertEquals(writeSuffix, pipe.fileHandler.getWriteSuffix());
    }

    @Test
    public void setterFileName() {
        String fileName = "dummyFileName";
        pipe.setFileName(fileName);

        assertEquals(fileName, pipe.fileHandler.getFileName());
    }

    @Test
    public void setterFileNamesessionKey() {
        String fileNameSessionKey = "dummyFileNameSessionKey";
        pipe.setFileNameSessionKey(fileNameSessionKey);

        assertEquals(fileNameSessionKey, pipe.fileHandler.getFileNameSessionKey());
    }

    @Test (expected = ConfigurationException.class)
    public void testEmptyConfiguration() throws ConfigurationException {
        pipe.configure();
    }

    @Test (expected = ConfigurationException.class)
    public void testConfigurationWithInvalidAction() throws ConfigurationException {
        pipe.setActions("dummyAction");
        pipe.configure();
    }

    @Test
    public void testConfigurationWithValidAction() throws ConfigurationException, PipeRunException {
        Object dummyObject = "dummyString";
        pipe.setActions("write");
        pipe.configure();
        assertNotNull(pipe.doPipe(dummyObject, session));
    }

    @Test
    public void testPipeWithExceptionForward() throws ConfigurationException, PipeRunException {
        Object dummyObject = "dummyObject";
        pipe.setActions("uhh");
        PipeForward pipeForward = new PipeForward();
        pipeForward.setName("exception");
        pipe.registerForward(pipeForward);
        pipe.doPipe(dummyObject, session);
        PipeRunResult result = new PipeRunResult(pipe.findForward("exception"), dummyObject);
        assertEquals(result.getResult(), pipe.doPipe(dummyObject, session).getResult());
    }

    @Test (expected = PipeRunException.class)
    public void testPipeWithException() throws PipeRunException {
        Object dummyObject = "dummyString";
        pipe.setActions("uhh");
        pipe.doPipe(dummyObject, session);
    }

    @Test
    public void testNormalPipeRun() throws Exception {
        Object dummyObject = "dummyString";
        pipe.setActions("delete");
        pipe.configure();
        PipeRunResult result = new PipeRunResult(pipe.getForward(), pipe.fileHandler.handle(dummyObject, session, pipe.getParameterList()));
        assertEquals(result.getResult(), pipe.doPipe(dummyObject, session).getResult());
    }

    @Test (expected = ConfigurationException.class)
    public void testSetInvalidOutputType() throws ConfigurationException {
        pipe.setOutputType("dummyOutputType");
        pipe.setActions("delete");
        pipe.configure();
    }
}