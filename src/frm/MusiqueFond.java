package frm;

import javax.sound.sampled.Clip;

/**
 * Interface qui gére la musique de fond des JFrames.
 * Elle est utilisées pour définir les getters et setters de musiqueDeFond et arreterMusiqueFond.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public interface MusiqueFond {
	
	/**
     * Obtient la musique de fond.
     *
     * @return La musique de fond.
     */
	public Clip getMusiqueDeFond();
	
	/**
     * Définit la musique de fond.
     *
     * @param uneMusiqueDeFond La musique de fond.
     */
	public void setMusiqueDeFond(Clip uneMusiqueDeFond);
	
	/**
     * Obtient si la musique de fond est arrêtée ou non.
     *
     * @return La musique de fond arrêtée ou non.
     */
	public boolean getArreterMusiqueFond();
	
	/**
     * Définit si la musique de fond est arrêtée ou non.
     *
     * @param uneArreterMusiqueFond La musique de fond arrêtée ou non.
     */
	public void setArreterMusiqueFond(boolean uneArreterMusiqueFond);
}
