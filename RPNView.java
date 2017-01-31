package rpn;
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

public class RPNView extends JFrame implements MonObservateur {

	private JPanel contentPane;
	private JTextField textField;
	private JPanel buttonPanel;
	private RPN rpn;

	/**
	 * Create the frame.
	 */
	public RPNView(RPN rpn) {
		super("Calculateur RPN");
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
		
		buttonPanel = new JPanel();
		contentPane.add(buttonPanel, BorderLayout.CENTER);
		buttonPanel.setLayout(new GridLayout(4, 4, 2, 2));
		
		JButton button;
		button = new JButton("7");
		button.addActionListener(getDigitActionListener("7"));
		buttonPanel.add(button);
		
		button = new JButton("8");
		button.addActionListener(getDigitActionListener("8"));
		buttonPanel.add(button);

		button = new JButton("9");
		button.addActionListener(getDigitActionListener("9"));
		buttonPanel.add(button);

		button = new JButton("/");
		button.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		rpn.compute(RPN.OPERATION.DIV);
		    	}
			});
		buttonPanel.add(button);

		button = new JButton("4");
		button.addActionListener(getDigitActionListener("4"));
		buttonPanel.add(button);
		
		button = new JButton("5");
		button.addActionListener(getDigitActionListener("5"));
		buttonPanel.add(button);
		
		button = new JButton("6");
		button.addActionListener(getDigitActionListener("6"));
		buttonPanel.add(button);
		
		button = new JButton("x");
		button.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		rpn.compute(RPN.OPERATION.MUL);
		    	}
			});
		buttonPanel.add(button);
		
		button = new JButton("1");
		button.addActionListener(getDigitActionListener("1"));
		buttonPanel.add(button);
		
		button = new JButton("2");
		button.addActionListener(getDigitActionListener("2"));
		buttonPanel.add(button);
		
		button = new JButton("3");
		button.addActionListener(getDigitActionListener("3"));
		buttonPanel.add(button);
		
		button = new JButton("-");
		button.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		rpn.compute(RPN.OPERATION.SUB);
		    	}
			});
		buttonPanel.add(button);
		
		button = new JButton("0");
		button.addActionListener(getDigitActionListener("0"));
		buttonPanel.add(button);
		
		button = new JButton(".");
		button.addActionListener(getDigitActionListener("."));
		buttonPanel.add(button);
		
		button = new JButton("C");
		button.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		rpn.clear();
		    	}
			});
		buttonPanel.add(button);
		
		button = new JButton("+");
		button.addActionListener(new ActionListener() {
		    	public void actionPerformed(ActionEvent e) {
		    		rpn.compute(RPN.OPERATION.ADD);
		    	}
			});
		buttonPanel.add(button);
		
		// operation du JFrame 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setContentPane(contentPane);
		setVisible(true);
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
