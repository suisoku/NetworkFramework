package Services.Groups;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import JBeeExceptions.JbeeException;
import Services.DataUtilities.DataMessage;
import Services.DataUtilities.DataStorage;

public class ChatGroup extends AbstractGroup implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DataStorage messageStorage;
	
	public ChatGroup(String groupName)  {
		super(groupName);
	}

	public void initializeStorage(ArrayList<DataMessage> bdDataMessages) {
		this.messageStorage = new DataStorage(this.groupName, bdDataMessages);
	}
	
	public ArrayList<DataMessage> getMessageStack() throws RemoteException{
		if(this.messageStorage == null ) throw new JbeeException("Reloading Data failed or empty");
		else return (ArrayList<DataMessage>)this.messageStorage;
	}

}










