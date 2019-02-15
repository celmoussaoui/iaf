package nl.nn.adapterframework.pipes;

import nl.nn.adapterframework.core.IPipeLineSession;
import nl.nn.adapterframework.core.PipeLineSessionBase;
import nl.nn.adapterframework.core.PipeRunException;
import nl.nn.adapterframework.core.PipeRunResult;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExceptionPipeTest extends PipeTestBase<ExceptionPipe> {

    IPipeLineSession session = new PipeLineSessionBase();

    @Override
    public ExceptionPipe createPipe() {
        return new ExceptionPipe();
    }

    @Test(expected = PipeRunException.class)
    public void testExceptionThrown() throws PipeRunException {
        Object dummyObject  = "NullPointerException";
        pipe.doPipe(dummyObject, session);
    }

    @Test
    public void testNoExceptionThrown() throws PipeRunException {
        Object dummyObject  = "NullPointerException";
        pipe.setThrowException(false);
        PipeRunResult result = pipe.doPipe(dummyObject, session);
        assertEquals(result.getResult(), dummyObject);
    }

    @Test
    public void testEmptyObjectString() throws PipeRunException {
        Object dummyObject = "";
        pipe.setThrowException(false);
        PipeRunResult result = pipe.doPipe(dummyObject, session);
        String resultString = "exception: " + pipe.getName();
        assertEquals(result.getResult(), resultString);
    }

    @Test
    public void getterSetterThrowException() {
        pipe.setThrowException(false);
        assertFalse(pipe.isThrowException());

        pipe.setThrowException(true);
        assertTrue(pipe.isThrowException());
    }


}