package Services.DataUtilities;


public class EventMessage {
	
    private Data_message last_element;
 
    EventMessage(Data_message last_message) {
        this.last_element = last_message;
    }
 
    public Data_message getLastMessage() {
        return last_element;
    }
 
}

