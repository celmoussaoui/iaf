package nl.nn.adapterframework.pipes;

import nl.nn.adapterframework.configuration.ConfigurationException;
import nl.nn.adapterframework.core.IPipeLineSession;
import nl.nn.adapterframework.core.PipeForward;
import nl.nn.adapterframework.core.PipeLineSessionBase;
import nl.nn.adapterframework.core.PipeRunException;
import nl.nn.adapterframework.parameters.Parameter;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class FixedForwardPipeTest extends PipeTestBase<FixedForwardPipe> {

    IPipeLineSession session = new PipeLineSessionBase();

    @Override
    public FixedForwardPipe createPipe() {
        return new FixedForwardPipe();
    }

    @Test
    public void getterSetterForwardName() {
        String forwardName = "forwardName";
        pipe.setForwardName(forwardName);
        assertEquals(forwardName, pipe.getForwardName());
    }

    @Test
    public void getterSetterSkipOnEmptyInput() {
        pipe.setSkipOnEmptyInput(true);
        assertTrue(pipe.isSkipOnEmptyInput());

        pipe.setSkipOnEmptyInput(false);
        assertFalse(pipe.isSkipOnEmptyInput());
    }

    @Test
    public void getterSetterIfParam() {
        String ifParam = "ifParam";
        pipe.setIfParam(ifParam);
        assertEquals(ifParam, pipe.getIfParam());
    }

    @Test
    public void getterSetterIfValue() {
        String ifValue = "ifValue";
        pipe.setIfValue(ifValue);
        assertEquals(ifValue, pipe.getIfValue());
    }

    @Test(expected = ConfigurationException.class)
    public void testUnknownForward() throws ConfigurationException {
        pipe.setForwardName("dummyForward");
        pipe.configure();
    }

    @Test
    public void testKnownForward() throws ConfigurationException {
        PipeForward forward = new PipeForward();
        forward.setName("dummyName");
        pipe.registerForward(forward);
        pipe.configure();

        //This is done to ensure an assert is passed in the test
        assertEquals(pipe.getForwardName(), "success");
    }

    @Test
    public void testSkipOnEmptyInput() throws PipeRunException {
        pipe.setSkipOnEmptyInput(true);
        Object dummyObject = "";
        assertEquals(pipe.doInitialPipe(dummyObject, session).getResult(), dummyObject);
        assertEquals(pipe.doInitialPipe(null, session).getResult(), null);

    }

    @Test
    public void testBlALFAsLFALSLADs() throws PipeRunException {
        Object dummyObject = "dummyString";
        pipe.setIfParam("dummyIfParam");
        assertNull(pipe.doInitialPipe(dummyObject, session));
    }

    @Test
    public void testodkaoskfoajspfa() throws PipeRunException {
        Object dummyObject = "dummyString";
        assertNull(pipe.doInitialPipe(dummyObject, session));
    }

    @Test
    public void teasGOSOMEDAY() throws PipeRunException, ConfigurationException {
        Object dummyObject = "dummyString";
        pipe.setIfParam("dummyParameterName");
        Parameter dummyParameter = Mockito.mock(Parameter.class);
        dummyParameter.setName("dummyParameterName");
        dummyParameter.setValue("dummyValue");
        pipe.addParameter(dummyParameter);
        pipe.doInitialPipe(dummyObject, session);
    }
}