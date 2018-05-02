package Services.DataUtilities;

import java.util.EventListener;

public interface SessionListener extends EventListener{
	
	public void eventClientSession(EventSession e);
}
