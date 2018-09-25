package Core.BD;

import java.io.Serializable;
import java.util.HashMap;

public class Predicat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String Name;
	public Object value;
	public HashMap<String,Object> mapUpdate = new HashMap<String,Object>();
	public HashMap<String,Object> mapSelect = new HashMap<String,Object>();
	
	public Predicat(String name, Object value) {
		this.Name = name;
		this.value = value;
	}
	public Predicat( HashMap<String, Object> mapSelect,HashMap<String, Object> mapUpdate) {
		
		this.mapSelect = mapSelect;
		this.mapUpdate = mapUpdate;
	}
	

}
