/**
 * Message class. In this implementation is just a simple boolean (True). This designs permit potential adds on complexity, with more inforamtive messages.
 * @author Andres Monteoliva
 * @since 19-03-2018
 *
 */

public class Message {
	
	private boolean state;
	
	/**
	 * Constructor for the message class. Set state to True.
	 */
	public Message() {
		state = true;
	}
	
	/**
	 * Getter method for the Message.
	 * @return
	 */
	public boolean getMessage() {
		
		return state;
	}

}
