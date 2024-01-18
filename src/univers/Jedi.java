package univers;

/**
 * La classe Jedi représente un personnage de type Jedi dans le jeu. Jedi hérite de la classe Personnage. Un Jedi est un personnage qui possède des compétences spécifiques liées au lancé de sabre laser.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class Jedi extends Personnage {

    private static final long serialVersionUID = 1L;
    private int puissanceLanceSabre;

    /**
     * Constructeur par défaut de la classe Jedi. Initialise un Jedi avec des valeurs par défaut.
     */
    public Jedi() {
        super();
        puissanceLanceSabre = 0;
    }

    /**
     * Constructeur paramétré de la classe Jedi. Initialise un Jedi avec des paramètres spécifiques.
     *
     * @param unPseudo           Le pseudo du Jedi.
     * @param unCheminImage      Le chemin de l'image représentant le Jedi.
     * @param uneVie             Le nombre de points de vie du Jedi.
     * @param uneAttaque         Le niveau d'attaque du Jedi.
     * @param uneForce           Le niveau de Force du Jedi.
     * @param unSabre            Le sabre laser du Jedi.
     * @param unePuissanceLanceSabre La puissance du lancé de sabre laser.
     */
    public Jedi(String unPseudo, String unCheminImage, int uneVie, int uneAttaque, int uneForce, Sabre unSabre, int unePuissanceLanceSabre) {
        super(unPseudo, unCheminImage, uneVie, uneAttaque, uneForce, unSabre);
        if (unePuissanceLanceSabre < 0) {
        	throw new IllegalArgumentException("La puissance du lancé de sabre doit être initialisée à 0 ou plus !");
        }
        this.puissanceLanceSabre = unePuissanceLanceSabre;
    }

    /**
     * Obtient la puissance du lancé de sabre laser du Jedi.
     *
     * @return La puissance du lancé de sabre laser.
     */
    public int getPuissanceLanceSabre() {
        return this.puissanceLanceSabre;
    }

    /**
     * Définit la puissance du lancé de sabre laser du Jedi.
     *
     * @param unePuissanceLanceSabre La nouvelle puissance du lancé de sabre laser.
     */
    public void setPuissanceLanceSabre(int unePuissanceLanceSabre) {
        this.puissanceLanceSabre = unePuissanceLanceSabre;
    }

    /**
     * Remplace la méthode equals de la classe parente Personnage pour comparer deux Jedi.
     *
     * @param unJedi L'objet à comparer avec le Jedi actuel.
     * @return true si les Jedi sont égaux, sinon false.
     */
    @Override
    public boolean equals(Object unJedi) {
        boolean retour = false;
        if (unJedi instanceof Jedi) {
            Jedi leJedi = (Jedi) unJedi;
            if (super.equals(leJedi) && this.puissanceLanceSabre == leJedi.getPuissanceLanceSabre()) {
                retour = true;
            }
        }
        return retour;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du Jedi.
     *
     * @return Une chaîne de caractères représentant le Jedi.
     */
    @Override
    public String toString() {
        return "Jedi [getPseudo()=" + getPseudo() + ", getCheminImage()=" + getCheminImage()
                + ", getVie()=" + getVie() + ", getAttaque()=" + getAttaque()
                + ", getForce()=" + getForce() + ", getSabre()=" + getSabre() + ", puissanceLanceSabre=" + this.puissanceLanceSabre + "]";
    }
}
