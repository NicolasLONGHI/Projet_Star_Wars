package univers;

/**
 * La classe Padawan représente un personnage de type Padawan dans le jeu. Padawan hérite de la classe Personnage. Un Padawan est un apprenti Jedi, et il est associé à un maître Jedi.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public class Padawan extends Personnage {

    private static final long serialVersionUID = 1L;
    private Maitre maitre;

    /**
     * Constructeur par défaut de la classe Padawan. Initialise un Padawan avec des valeurs par défaut.
     */
    public Padawan() {
        super();
        this.maitre = null;
    }

    /**
     * Constructeur paramétré de la classe Padawan. Initialise un Padawan avec des paramètres spécifiques.
     *
     * @param unPseudo        Le pseudo du Padawan.
     * @param unCheminImage   Le chemin de l'image représentant le Padawan.
     * @param uneVie          Le nombre de points de vie du Padawan.
     * @param uneAttaque      Le niveau d'attaque du Padawan.
     * @param uneForce        Le niveau de Force du Padawan.
     * @param unSabre         Le sabre laser du Padawan.
     * @param unMaitre        Le maître Jedi associé au Padawan.
     */
    public Padawan(String unPseudo, String unCheminImage, int uneVie, int uneAttaque, int uneForce, Sabre unSabre, Maitre unMaitre) {
        super(unPseudo, unCheminImage, uneVie, uneAttaque, uneForce, unSabre);
        this.maitre = unMaitre;
    }

    /**
     * Obtient le maître Jedi associé au Padawan.
     *
     * @return Le maître Jedi du Padawan.
     */
    public Maitre getMaitre() {
        return maitre;
    }

    /**
     * Définit le maître Jedi associé au Padawan.
     *
     * @param unMaitre Le nouveau maître Jedi du Padawan.
     */
    public void setMaitre(Maitre unMaitre) {
        this.maitre = unMaitre;
    }

    /**
     * Remplace la méthode equals de la classe parente Personnage pour comparer deux Padawans.
     *
     * @param unPadawan L'objet à comparer avec le Padawan actuel.
     * @return true si les Padawans sont égaux, sinon false.
     */
    @Override
    public boolean equals(Object unPadawan) {
        boolean retour = false;
        if (unPadawan instanceof Padawan) {
            Padawan lePadawan = (Padawan) unPadawan;
            if (super.equals(lePadawan) && (this.maitre == lePadawan.getMaitre())) {
                retour = true;
            }
        }
        return retour;
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du Padawan.
     *
     * @return Une chaîne de caractères représentant le Padawan.
     */
    @Override
    public String toString() {
        return "Padawan [getPseudo()=" + getPseudo() + ", getCheminImage()=" + getCheminImage() + ", getVie()=" + getVie()
                + ", getAttaque()=" + getAttaque() + ", getForce()=" + getForce() + ", getSabre()=" + getSabre() + ", maitre=" + this.maitre + "]";
    }
}
