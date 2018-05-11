package Services.DataUtilities.events;

import Services.DataUtilities.DataMessage;

public class EventMessage {
	
    private DataMessage last_element;
 
    public EventMessage(DataMessage last_message) {
        this.last_element = last_message;
    }
 
    public DataMessage getLastMessage() {
        return last_element;
    }
 
}

