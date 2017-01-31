package rpn;
/**
 * Interfade de l'observateur
 * @author Vincent Lacasse
 *
 */
public interface MonObservateur {
	
	/**
	 * Méthode appelée lorsque l'état de MonSujet à été modifier 
	 * @param obs
	 */
	public void update(MonSujet obs);
}
