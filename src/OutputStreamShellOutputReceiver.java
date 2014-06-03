import java.io.IOException;
import java.io.OutputStream;

import com.android.ddmlib.IShellOutputReceiver;

/**
 * 传递命令
 * @author Yuedong Li
 *
 */
public class OutputStreamShellOutputReceiver implements IShellOutputReceiver {
	OutputStream os; 

	public OutputStreamShellOutputReceiver(OutputStream os) { 
		this.os = os; 
	} 

	public boolean isCancelled() { 
		return false; 
	} 

	public void flush() { 
	} 

	public void addOutput(byte[] buf, int off, int len) { 
		try { 
			os.write(buf,off,len); 
			os.flush();
		} catch(IOException ex) { 
			throw new RuntimeException(ex); 
		} 
	} 
} 