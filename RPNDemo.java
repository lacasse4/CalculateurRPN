package rpn;
import java.awt.EventQueue;

/**
 * Programme Calculateur "Reverse Polish Notation" (RPN)
 * Exemple simple pour démontrer une implémentation du patron Observer
 * @author Vincent Lacasse
 *
 */
public class RPNDemo {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RPN rpn = new RPN();
					RPNView frame = new RPNView(rpn);
					rpn.addObserver(frame);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
