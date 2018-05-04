package Services.DataUtilities.events;

import Services.DataUtilities.Data_message;

public class EventMessage {
	
    private Data_message last_element;
 
    public EventMessage(Data_message last_message) {
        this.last_element = last_message;
    }
 
    public Data_message getLastMessage() {
        return last_element;
    }
 
}

