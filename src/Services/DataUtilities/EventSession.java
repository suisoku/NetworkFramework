package Services.DataUtilities;

import java.util.Arrays;
import java.util.List;

public class EventSession {

    private String state;
    private List<String> places = Arrays.asList("posted", "disconnected");
    
    EventSession(String state) {
    	boolean checkState = false;
    	
    	for (String e : places) {
			if(e.equals(state))checkState = true;
    	}
    	
    	if(checkState == false)System.out.println("erreur etat inconnu");
    }
 
    public String getState() {
    	return state;
    }
}




