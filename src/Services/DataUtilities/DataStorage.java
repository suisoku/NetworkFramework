package Services.DataUtilities;

import java.util.ArrayList;

import javax.swing.event.EventListenerList;

import Services.DataUtilities.events.ArrayListener;
import Services.DataUtilities.events.EventMessage;

/**
 * 
 * DataStorage : an array list with the capability of firing events when 
 * the inner state change (for now : adding)
 */
public class DataStorage extends ArrayList<Data_message>{

	
	private static final long serialVersionUID = 1L;
	private final String id_client;
	private final EventListenerList listeners = new EventListenerList();
	
	
	public DataStorage(String id_client, ArrayList<Data_message> payload) {
		super(payload);
		this.id_client = id_client;
	}
	
	public DataStorage(String id_client) {
		super();
		this.id_client = id_client;
	}
	
	
	
	/**
	 * fire event e for all listeners
	 * @param e
	 */
	public void fireEvent(EventMessage e) {
        for(ArrayListener listener : getArrayListeners()) {
            listener.message_added(e);
        }
	}
	
	
	/**
	 * Add and emit event
	 */
	@Override
	public boolean add(Data_message message) {
		
		fireEvent(new EventMessage(message));
		return super.add(message);
	}
	
	
	
	/**
	 * Add / remove / return listeners 
	 * @param listener
	 */
    public ArrayListener[] getArrayListeners() {
        return listeners.getListeners(ArrayListener.class);
    }
    public void addListener(ArrayListener listener) {
        listeners.add(ArrayListener.class, listener);
    }
    public void removeListener(ArrayListener listener) {
        listeners.remove(ArrayListener.class, listener);
    }


	public String getId_client() {
		return id_client;
	}
}
