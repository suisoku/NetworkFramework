package Core.Serveur;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Data_message extends UnicastRemoteObject implements Serializable, DataInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id_sender;
	private Date send_date;
	private Object data;
	
	
	public Data_message(String id , Date send_date, Object data) throws RemoteException{
		this.id_sender = id ;
		this.send_date = send_date ;
		this.data = data ;
	}


	public String getId_sender() throws RemoteException {
		return id_sender;
	}


	public Date getDate()throws RemoteException {
		return send_date;
	}


	public Object getData() throws RemoteException{
		return data;
	}
	
	public void showTime() throws RemoteException {
		System.out.println(send_date);
	}
	
	public void showStringMess() throws RemoteException{
		if(data instanceof String) {
			System.out.println(data);
		}
		else System.out.println("L'objet data n'est pas une instance de String");
		
	}
	
	
	
}
