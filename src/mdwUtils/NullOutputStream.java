package mdwUtils;
/*
 * An output stream that does nothing.
 * http://stackoverflow.com/questions/2127979/platform-independent-dev-null-output-sink-for-java
 */
import java.io.OutputStream;
import java.io.IOException;

public class NullOutputStream extends OutputStream {
	@Override
    public void write(int i) throws IOException {
        //do nothing
    }
	
	public static final NullOutputStream DEFAULT_INSTANCE = new NullOutputStream();
}
