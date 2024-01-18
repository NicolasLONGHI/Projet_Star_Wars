package representation;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

/**
 * Classe SoundNode qui étend EventDecorator pour ajouter une fonctionnalité de son.
 * Cette classe permet de jouer un son lors de l'affichage de l'événement décoré.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class SoundNode extends EventDecorator {

    private static final long serialVersionUID = 1L;
	private String chemin;

	
	/**
     * Constructeur par défaut pour SoundNode. Initialise une SoundNode avec des valeurs par défaut.
     */
	public SoundNode() {
		super();
		this.chemin = "";
	}
	 /**
     * Constructeur de SoundNode avec des paramètres spécifiques.
     *
     * @param unId Identifiant unique du SoundNode.
     * @param uneNode L'Event à décorer avec le son.
     * @param unChemin Chemin d'accès au fichier son.
     */
	public SoundNode(int unId, Event uneNode, String unChemin) {
		super(unId, uneNode);
		if (unChemin.equals("")) {
			throw new IllegalArgumentException("Le chemin du son ne peut pas être égale à rien !");
		}
		this.chemin = unChemin;
	}
	
	/**
     * Joue le son et affiche les détails de l'événement décoré.
     *
     * @return La description de l'événement décoré.
     */
	@Override
    public String display() {
		playSound();
        return super.getDecoratedNode().display();
    }
	
	/**
     * Joue le son associé à cette node.
     */
	private void playSound() {
    	try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(getClass().getResource(this.chemin)));
            clip.start();
        }
        catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Une erreur est survenue lors du chargement du son.", "Erreur", JOptionPane.WARNING_MESSAGE);
        }
    }

	/**
    * Récupère le chemin du son associé à cette node.
    *
    * @return Le chemin de du son.
    */
	public String getChemin() {
		return chemin;
	}

    /**
     * Définit le chemin du son pour cette node.
     *
     * @param unChemin Le nouveau chemin du son.
     */
	public void setChemin(String unChemin) {
		this.chemin = unChemin;
	}

	/**
     * Compare cette SoundNode avec un autre objet pour vérifier l'égalité.
     *
     * @param unSoundNode L'objet avec lequel comparer.
     * @return true si les SoundNode sont égales, false sinon.
     */
	@Override
    public boolean equals(Object unSoundNode) {
        boolean retour = false;
        if (unSoundNode instanceof SoundNode) {
        	SoundNode leSoundNode = (SoundNode) unSoundNode;
            if (super.equals(leSoundNode) && this.chemin.equals(leSoundNode.getChemin())) {
                retour = true;
            }
        }
        return retour;
    }
	
	/**
     * Fournit une représentation sous forme de chaîne de caractères de la SoundNode.
     *
     * @return Une chaîne de caractères représentant de la SoundNode.
     */
	@Override
	public String toString() {
		return "SoundNode [chemin=" + chemin + ", getDecoratedNode()=" + getDecoratedNode() + "]";
	}
}
