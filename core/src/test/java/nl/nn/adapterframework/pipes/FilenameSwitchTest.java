package nl.nn.adapterframework.pipes;

import nl.nn.adapterframework.configuration.ConfigurationException;
import nl.nn.adapterframework.core.*;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class FilenameSwitchTest extends PipeTestBase<FilenameSwitch> {

    private IPipeLineSession session = new PipeLineSessionBase();

    @Override
    public FilenameSwitch createPipe() {
        return new FilenameSwitch();
    }

    @Test
    public void getterSetterNotFoundForwardName() {
        String dummyString = "dummyString";
        pipe.setNotFoundForwardName(dummyString);
        assertEquals(dummyString, pipe.getNotFoundForwardName());

        String anotherString = "anotherString";
        pipe.setNotFoundForwardName(anotherString);
        assertEquals(anotherString, pipe.getNotFoundForwardName());
    }

    @Test
    public void getterSetterToLowercase() {
        pipe.setToLowercase(true);
        assertTrue(pipe.isToLowercase());

        pipe.setToLowercase(false);
        assertFalse(pipe.isToLowercase());
    }

    @Test
    public void testBasicConfigure() throws ConfigurationException {
        String forwardName = "forwardName";
        pipe.setNotFoundForwardName(forwardName);
        pipe.configure();

        assertEquals(pipe.getNotFoundForwardName(), forwardName);
    }

    @Test
    public void testSuccessfulPipe() throws ConfigurationException, PipeRunException {
        Object dummyObject = "success";
        PipeForward forward = Mockito.mock(PipeForward.class);
        forward.setName("success");
        assertEquals(new PipeRunResult(forward, dummyObject).getResult(), pipe.doPipe(dummyObject, session).getResult());
    }

    @Test (expected = PipeRunException.class)
    public void testPipeWithSubstring() throws PipeRunException, ConfigurationException {
        pipe.setToLowercase(true);
        Object dummyObject = "dummy\\Ob/ject";
        pipe.doPipe(dummyObject, session);
    }

    @Test
    public void testSuccessfulPipeWithSubstring() throws PipeRunException, ConfigurationException {
        pipe.setToLowercase(true);
        Object dummyObject = "su/c\\success";
        PipeForward forward = Mockito.mock(PipeForward.class);
        forward.setName("su\\c/success");
        assertEquals(new PipeRunResult(forward, dummyObject).getResult(), pipe.doPipe(dummyObject, session).getResult());
    }
}