package Core.Serveur;

import java.util.EventListener;

public interface SessionListener extends EventListener{
	
	public void eventClientSession(EventSession e);
}
