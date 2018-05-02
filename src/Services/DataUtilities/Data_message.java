package Services.DataUtilities;


import java.io.Serializable;
import java.util.Date;

public class Data_message implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id_sender;
	private Date send_date;
	private Object data;
	
	
	public Data_message(String id , Date send_date, Object data) {
		this.id_sender = id ;
		this.send_date = send_date ;
		this.data = data ;
	}


	public String getId_sender()   {
		return id_sender;
	}


	public Date getDate()  {
		return send_date;
	}


	public Object getData()  {
		return data;
	}
	
	public void showTime()   {
		System.out.println(send_date);
	}
	
	public void showStringMess()  {
		if(data instanceof String) {
			System.out.println(data);
		}
		else System.out.println("L'objet data n'est pas une instance de String");
		
	}
	
	
	
}
