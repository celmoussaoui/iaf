package nl.nn.adapterframework.pipes;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nl.nn.adapterframework.core.PipeRunResult;

/** 
* RemoveFromSession Tester. 
* 
* @author <Sina Sen>
*/ 
public class RemoveFromSessionTest extends PipeTestBase<RemoveFromSession> {

	@Override
	public RemoveFromSession createPipe() {
		return new RemoveFromSession();
	}

	/** 
	* 
	* Method: configure() 
	* 
	*/ 
	@Test
	public void testEmptySessionKeyNonEmptyInput() throws Exception {
		pipe.setSessionKey(null);
		session.put("a", "123");
		PipeRunResult res = doPipe(pipe, "a", session);
		assertEquals("123", res.getResult().asString());
	}

    @Test
    public void testNonEmptySessionKeyNonEmptyInput() throws Exception {
        pipe.setSessionKey("a");
        session.put("a", "123");
        PipeRunResult res = doPipe(pipe, "a", session);
        assertEquals("123", res.getResult().asString());
    }

    @Test
    public void testNonEmptySessionKeyEmptyInput() throws Exception {
            pipe.setSessionKey("a");
            session.put("a", "123");
            PipeRunResult res = pipe.doPipe(null, session);
            assertEquals( "123", res.getResult().asString());
    }
  
    @Test
    public void testEmptySessionKeyEmptyInput() throws Exception {
        exception.expect(NullPointerException.class);
        pipe.setSessionKey("");
        session.put("a", "123");
        PipeRunResult res = doPipe(pipe, null, session);
        assertEquals( "[null]", res.getResult().asString());
    }


    @Test
    public void testFailAsKeyIsWrong() throws Exception {
        pipe.setSessionKey("ab");
        session.put("a", "123");
        PipeRunResult res = doPipe(pipe, "ab", session);
        assertEquals("[null]", res.getResult().asString());
    }

}
