package Services.DataUtilities;

import java.util.EventListener;

public interface ArrayListener extends EventListener{
	public void message_added(EventMessage e);
}