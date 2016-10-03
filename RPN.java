import java.util.Locale;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation d'une calculatrice RPN avec une pile de 4 éléments
 * @author vincentlacasse
 *
 */
public class RPN implements MonSujet {
	
	public enum OPERATION { ADD, SUB, MUL, DIV };
	public static final int MAX_DIGIT = 10;		// grandeur maximum de l'affichage
	public static final int STACK_SIZE = 4;		// grandeur de la pile
	
	// Données du modèle
	private double[] pile;		// pile contenant les valeurs du calculateur
	private String affiche;		// nombre qui est affiché à l'écran
	private boolean edite;		// mode édition: vrai si on est en train d'éditer l'affichage
	private boolean pousse;		// faux s'il ne faut pas pousser sur la pile (après enter)
	
	// La liste d'observateur est requise pour l'implémentation de MonSujet.
	// Le sujet doit maintenir une collection d'observateurs. Plusieurs
	// observeurs peuvent s'enregistrer afin d'être aviser ("notified")
	// lorsque lorsque l'état du sujet est modifé. 
	private List<MonObservateur> observateurs;	// Collection d'observateurs
	
	
	/**
	 * Constructeur
	 */
	public RPN () {
		pile = new double[STACK_SIZE];
		observateurs = new ArrayList<MonObservateur>();
		remetteAZero();
	}
	
	/**
	 * Remettre à 0 l'état du calculateur
	 */
	private void remetteAZero() {
		edite = false;
		pousse = true;
		pile[0] = 0.0;
		deposerPile0DansAffiche();
	}
	
	/**
	 * La touche Enter est pressée.
	 * Si on est en mode édition, on convertit la valeur affichée et on la place 
	 * dans pile[0], puis on sort du mode édition.
	 * On pousse sur la pile; pile[0] et pile[1] auront la même valeur.
	 * On rafraichit l'affichage.
	 */
	public void enter() {
		if (edite) {
			deposerAfficheDansPile0();
			edite = false;
		}
		pousser();
		pousse = false;
		deposerPile0DansAffiche();
	}

	/**
	 * Gérer lorsqu'un chiffre ou le point est pressé.
	 * Si c'est le premier chiffre après une opération (!edite), il faut
	 * faire un "enter" automatique, ajouter le chiffre à l'affichage et
	 * entrer en mode édition.
	 * Sinon, on est déjà en mode édition, alors il faut ajouter le chiffre
	 * à l'affichage en prennant soin de ne pas saisir plus de MAX_DIGIT ou
	 * deux points.
	 * @param c chiffre à ajouter
	 */
	public void addDigit(char c) {
		boolean decimalPresent = affiche.contains(".");
		if (edite) {
			if (affiche.length() < MAX_DIGIT 
					&& (c != '.' || (c == '.' && !decimalPresent)))
				affiche = affiche + c;
		}
		else {
			if (pousse) pousser();
			affiche = "" + c;
			edite = true;
		}
		notifyObservers();
	}
	
	/**
	 * Effectue une opération arithmétique sur la pile 
	 * @param op opération à effectuée
	 */
	public void compute(OPERATION op) {
		double operande;
		if (edite) {
			operande = convertirAffiche();
			retirer();
		}
		else
		    operande = retirer();
		edite = false;
		switch (op) {
			case ADD: pile[0] += operande; break;
			case SUB: pile[0] -= operande; break;
			case MUL: pile[0] *= operande; break;
			case DIV: pile[0] /= operande; break;
		}
		deposerPile0DansAffiche();
		pousse = true;
	}
			
	/**
	 * La touche C (clear) est pressée.
	 * Si on est en mode édition, on détruit le dernier caractère entré, 
	 * jusqu'à ce qu'il y en ait plus.
	 * Si on n'est pas en mode édition, on met 0 dans pile[0].
	 */
	public void clear() {
		if (edite) {
			int i = affiche.length();
			if (i > 1) {
				affiche = affiche.substring(0, i-1);
				deposerAfficheDansPile0();
				notifyObservers();
			}
			else remetteAZero();
		}
		else remetteAZero();
	}
	
	/**
	 * Retirer (pop) un élément de la pile.
	 * La valeur pile[0] est écrasée. La valeur de pile[STACK_SIZE-1] est conservée 
	 * @return la valeur de pile[0] avant le retrait
	 */
	private double retirer() {
		double pile0 = pile[0];
		for (int i = 1; i < STACK_SIZE; i++) pile[i-1] = pile[i];
		return pile0;
	}
	
	/**
	 * Pousser (push) les valeur de la pile vers le haut.
	 * pile[0] conserve sa valeur.
	 */
	private void pousser() {
		for (int i = STACK_SIZE-1; i > 0; i--) pile[i] = pile[i-1];
	}
		
	/**
	 * Retourner la valeur qui doit être affichée par la vue.
	 * @return String valeur affichable
	 */
	public String getAffiche() { return affiche; }
	
	/**
	 * Convertir pile[0] en String et le mettre dans affiche.
	 * Conversion avec 4 chiffres après le point, justifié à gauche.
	 * On doit rafraichir la valeur affichée dans la vue par le mécanisme de l'observateur.
	 */
	private void deposerPile0DansAffiche() {
		affiche = String.format(Locale.US, "%-6.4f", pile[0]);
		notifyObservers();
	}
	
	/**
	 * Convertir affiche en double et le mettre dans pile[0].
	 */
	private void deposerAfficheDansPile0() {
		pile[0] = convertirAffiche();
	}
	
	/**
	 * Convertir affiche en double.
	 * Si affiche contient ".", on convertit en 0.0
	 * @return
	 */
	private double convertirAffiche() {
		if (affiche.equals(".")) 
			return 0.0;
		else 
			return Double.valueOf(affiche);
	}
	
	/*
	 * Les trois méthodes qui suivent sont requises pour implémenter MonSujet
	 */
	
	/**
	 * Ajouter un observateur
	 */
	public void addObserver(MonObservateur obs) {
		observateurs.add(obs);
	}
	
	/**
	 * Retirer un observateur
	 */
	public void removeObserver(MonObservateur obs) {
		int i = observateurs.indexOf(obs);
		if (i >= 0)
			observateurs.remove(i);
	}
	
	/**
	 * Aviser tous les observateurs enristrés que le modèle a changé
	 */
	public void notifyObservers() {
		for (MonObservateur obs : observateurs) 
			obs.update(this);
	}
}
