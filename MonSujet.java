/**
 * Inteface du sujet observé
 * @author Vincent Lacasse
 */

public interface MonSujet {
	
	/**
	 * Ajouter un observeteur de type MonObserateur à la liste 
	 * des observateurs de MonSujet
	 * @param obs observateur à ajouter
	 */
	public void addObserver(MonObservateur obs);
	
	/**
	 * Retirer un observateur de la liste des observateurs de MonSujet
	 * @param obs observateur à retirer
	 */
	public void removeObserver(MonObservateur obs);
	
	/**
	 * Appeller tous les observateurs enregistrés à MonSujet.
	 * L'ordre d'execution des observateurs n'est pas prévisible. 
	 */
	public void notifyObservers();
}
