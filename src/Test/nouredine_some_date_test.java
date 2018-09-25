package Test;
import java.text.ParseException;
import java.util.HashMap;
import java.util.UUID;

public class nouredine_some_date_test {

	public static void main(String[] args) throws ParseException {

	HashMap<UUID , String> map = new HashMap<UUID , String>();
	
	
	 UUID id1 = UUID.randomUUID();
	 
	 map.put(id1, "sss");
	 
	 
	 System.out.println(map.get(id1));
	 
	 
     
    
	}

}
