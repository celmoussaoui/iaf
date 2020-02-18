package nl.nn.adapterframework.pipes;

import nl.nn.adapterframework.core.IPipeLineSession;
import nl.nn.adapterframework.core.PipeRunException;
import nl.nn.adapterframework.doc.IbisDocPipe;
import nl.nn.adapterframework.util.XmlUtils;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertTrue;


public class IbisDocPipeTest extends PipeTestBase<IbisDocPipe> {

    @Override
    public IbisDocPipe createPipe() {
        return new IbisDocPipe();
    }

    @Test
    public void wellFormedXML() throws PipeRunException {
        String xml = pipe.getSchema();
        assertTrue(XmlUtils.isWellFormed(xml));
    }
}
