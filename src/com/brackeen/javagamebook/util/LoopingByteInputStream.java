package com.brackeen.javagamebook.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import com.brackeen.javagamebook.codereflection.*;

/**
    The LoopingByteInputStream is a ByteArrayInputStream that
    loops indefinitly. The looping stops when the close() method
    is called.
    <p>Possible ideas to extend this class:<ul>
    <li>Add an option to only loop a certain number of times.
    </ul>
*/
public class LoopingByteInputStream extends ByteArrayInputStream {

    private boolean closed;
    private Throwable e = new Throwable();

    /**
        Creates a new LoopingByteInputStream with the specified
        byte array. The array is not copied.
    */
    public LoopingByteInputStream(byte[] buffer) {
        super(buffer);
    	if(CodeReflection.isTracing()) {
        	if(CodeReflection.getAbstactionLevel()>=1)
        	{//check to make sure it's this level of abstraction
        		e.fillInStackTrace();		
        		CodeReflection.registerMethod(e.getStackTrace()[0].getClassName(),
        								e.getStackTrace()[0].getMethodName());
        	}
    	}
        closed = false;
    }


    /**
        Reads <code>length</code> bytes from the array. If the
        end of the array is reached, the reading starts over from
        the beginning of the array. Returns -1 if the array has
        been closed.
    */
    public int read(byte[] buffer, int offset, int length) {
        if (closed) {
            return -1;
        }
        int totalBytesRead = 0;

        while (totalBytesRead < length) {
            int numBytesRead = super.read(buffer,
                offset + totalBytesRead,
                length - totalBytesRead);

            if (numBytesRead > 0) {
                totalBytesRead += numBytesRead;
            }
            else {
                reset();
            }
        }
        return totalBytesRead;
    }


    /**
        Closes the stream. Future calls to the read() methods
        will return 1.
    */
    public void close() throws IOException {
        super.close();
        closed = true;
    }

}
