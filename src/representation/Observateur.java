package representation;

import univers.Personnage;

/**
 * Interface Observateur utilisée pour implémenter le modèle de conception Observateur.
 * Permet à un objet d'être informé des changements de type d'un Personnage.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public interface Observateur {
	/**
     * Change la référence du personnage par la nouvelle référence.
	 * Est appelé lorsque le personnage change de type.
     *
     * @param unPersonnage Le personnage où le type a changé.
     */
	void mettreAJour(Personnage unPersonnage);
}
