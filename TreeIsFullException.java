package implementation;

/**
 * Thrown when a tree is full. 
 * 
 */
public class TreeIsFullException extends Exception {
	private static final long serialVersionUID = 1L;

	public TreeIsFullException(String message) {
		super(message);
	}
}
