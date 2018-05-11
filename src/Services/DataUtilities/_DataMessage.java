package Services.DataUtilities;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Date;

public interface _DataMessage extends Serializable{

	public String getId_sender() throws RemoteException;
	public Date getDate() throws RemoteException;
	public Object getData() throws RemoteException; 
	public void showTime() throws RemoteException;
	public void showStringMess() throws RemoteException; 
}
