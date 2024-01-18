package univers;

import java.io.Serializable;

/**
 * L'énumération Sabre représente les différentes couleurs de sabres laser disponibles dans le jeu.
 * Chaque couleur de sabre est associée à une valeur spécifique, la puissance.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public enum Sabre implements Serializable {
    BLEU(9),
    BLANC(17),
    ROUGE(15),
    VERT(10),
    VIOLET(15),
    ENTRAINEMENT(7);

    private final int puissance;

    /**
     * Crée une nouvelle instance de l'énumération Sabre avec la puissance spécifiée.
     *
     * @param unePuissance La puissance du sabre.
     */
    Sabre(int unePuissance) {
        this.puissance = unePuissance;
    }

    /**
     * Obtient la puissance associée à la couleur du sabre.
     *
     * @return La puissance de la couleur du sabre.
     */
    public int getPuissance() {
        return this.puissance;
    }
}
