package univers;

/**
 * La classe Sith représente un personnage de type Sith dans le jeu. Sith hérite de la classe Personnage. Un Sith est un utilisateur du côté obscur de la Force, capable de déchaîner la puissance de l'éclair.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class Sith extends Personnage {

    private static final long serialVersionUID = 1L;
    private int puissanceEclair;

    /**
     * Constructeur par défaut de la classe Sith. Initialise un Sith avec des valeurs par défaut.
     */
    public Sith() {
        super();
        this.puissanceEclair = 0;
    }

    /**
     * Constructeur paramétré de la classe Sith. Initialise un Sith avec des paramètres spécifiques.
     *
     * @param unPseudo         Le pseudo du Sith.
     * @param unCheminImage    Le chemin de l'image représentant le Sith.
     * @param uneVie           Le nombre de points de vie du Sith.
     * @param uneAttaque       Le niveau d'attaque du Sith.
     * @param uneForce         Le niveau de Force du Sith.
     * @param unSabre          Le sabre laser du Sith.
     * @param unePuissanceEclair La puissance de l'éclair du Sith.
     */
    public Sith(String unPseudo, String unCheminImage, int uneVie, int uneAttaque, int uneForce, Sabre unSabre, int unePuissanceEclair) {
        super(unPseudo, unCheminImage, uneVie, uneAttaque, uneForce, unSabre);
        if (unePuissanceEclair < 0) {
        	throw new IllegalArgumentException("La puissance de l'éclair doit être initialisée à 0 ou plus !");
        }
        this.puissanceEclair = unePuissanceEclair;
    }

    /**
     * Obtient la puissance de l'éclair du Sith.
     *
     * @return La puissance de l'éclair.
     */
    public int getPuissanceEclair() {
        return puissanceEclair;
    }

    /**
     * Définit la puissance de l'éclair du Sith.
     *
     * @param unePuissanceEclair La nouvelle puissance de l'éclair.
     */
    public void setPuissanceEclair(int unePuissanceEclair) {
        this.puissanceEclair = unePuissanceEclair;
    }

    /**
     * Remplace la méthode equals de la classe parente Personnage pour comparer deux Sith.
     *
     * @param unSith L'objet à comparer avec le Sith actuel.
     * @return true si les Sith sont égaux, sinon false.
     */
    @Override
    public boolean equals(Object unSith) {
        boolean retour = false;
        if (unSith instanceof Sith) {
            Sith leSith = (Sith) unSith;
            if (super.equals(leSith) && (this.puissanceEclair == leSith.getPuissanceEclair())) {
                retour = true;
            }
        }
        return retour;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du Sith.
     *
     * @return Une chaîne de caractères représentant le Sith.
     */
    @Override
    public String toString() {
        return "Sith [getPseudo()=" + getPseudo() + ", getCheminImage()=" + getCheminImage() + ", getVie()=" + getVie()
                + ", getAttaque()=" + getAttaque() + ", getForce()=" + getForce() + ", getSabre()=" + getSabre() + ", puissanceEclair=" + puissanceEclair + "]";
    }
}
