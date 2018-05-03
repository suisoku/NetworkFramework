package Services.Profile;
import java.util.HashMap;

public class Predicat {
	protected  String Name ;
	protected Object value ;
	protected HashMap<String,Object> map = new HashMap<String,Object>();
	 public Predicat(String name, Object value) {
			this.Name = name;
			this.value = value;
		}
	 
	public Predicat(String name, Object value,HashMap<String, Object> map) {
		this(name,value); 
		this.map = map;
	}
	
	

}
