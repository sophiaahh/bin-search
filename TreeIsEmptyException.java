package implementation;

/**
 * Thrown when a tree is empty. 
 * 
 */
public class TreeIsEmptyException extends Exception {
	private static final long serialVersionUID = 1L;

	public TreeIsEmptyException(String message) {
		super(message);
	}
}
