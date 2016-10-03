import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Vue du programme RPN
 * Classe générée avec WindowBuilder de Eclipse
 * @author Vincent Lacasse
 *
 */

public class RPNView implements MonObservateur {

	private JFrame frame;
	private JPanel contentPane;
	private JTextField textField;
	private JPanel panel;
	private RPN rpn;

	/**
	 * Create the frame.
	 */
	public RPNView(RPN rpn) {
		this.rpn = rpn;
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new
			ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		rpn.enter();
		    	}
			});
		contentPane.add(btnEnter, BorderLayout.WEST);
		
		textField = new JTextField();
		textField.setColumns(10);
		textField.setEditable(false);
    	textField.setText(rpn.getAffiche());
		contentPane.add(textField, BorderLayout.NORTH);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 4, 2, 2));
		
		JButton button;
		button = new JButton("7");
		button.addActionListener(getDigitActionListener("7"));
		panel.add(button);
		
		button = new JButton("8");
		button.addActionListener(getDigitActionListener("8"));
		panel.add(button);

		button = new JButton("9");
		button.addActionListener(getDigitActionListener("9"));
		panel.add(button);

		button = new JButton("/");
		button.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		rpn.compute(RPN.OPERATION.DIV);
		    	}
			});
		panel.add(button);

		button = new JButton("4");
		button.addActionListener(getDigitActionListener("4"));
		panel.add(button);
		
		button = new JButton("5");
		button.addActionListener(getDigitActionListener("5"));
		panel.add(button);
		
		button = new JButton("6");
		button.addActionListener(getDigitActionListener("6"));
		panel.add(button);
		
		button = new JButton("x");
		button.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		rpn.compute(RPN.OPERATION.MUL);
		    	}
			});
		panel.add(button);
		
		button = new JButton("1");
		button.addActionListener(getDigitActionListener("1"));
		panel.add(button);
		
		button = new JButton("2");
		button.addActionListener(getDigitActionListener("2"));
		panel.add(button);
		
		button = new JButton("3");
		button.addActionListener(getDigitActionListener("3"));
		panel.add(button);
		
		button = new JButton("-");
		button.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		rpn.compute(RPN.OPERATION.SUB);
		    	}
			});
		panel.add(button);
		
		button = new JButton("0");
		button.addActionListener(getDigitActionListener("0"));
		panel.add(button);
		
		button = new JButton(".");
		button.addActionListener(getDigitActionListener("."));
		panel.add(button);
		
		button = new JButton("C");
		button.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		rpn.clear();
		    	}
			});
		panel.add(button);
		
		button = new JButton("+");
		button.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		rpn.compute(RPN.OPERATION.ADD);
		    	}
			});
		panel.add(button);
		
		frame = new JFrame("Calculateur RPN");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		frame.setContentPane(contentPane);
		frame.setVisible(true);
	}

	/**
	 * Créer un objet (classe anonyme) de type ActionListener pour les chiffres du calculateur
	 * @param digit caractere à afficher et retourner à RPN
	 * @return nouvelle object de type ActionListener
	 */
	public ActionListener getDigitActionListener(String digit) {
		return new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	rpn.addDigit(digit.charAt(0));
		    }
		};
	}
	
	/*
	 * La méthode suivante est requise pour implémenter MonObservateur
	 */
	
	/**
	 * Action à faire lorsque le sujet est modifié
	 */
	public void update(MonSujet obs) {
		if (obs instanceof RPN)
			textField.setText(((RPN)obs).getAffiche());		
	}
}
