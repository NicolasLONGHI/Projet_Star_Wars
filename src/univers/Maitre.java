package univers;

import java.io.Serializable;

import configuration.Configuration;

/**
 * L'énumération Maitre représente les différents maîtres disponibles dans le jeu pour un Padawan.
 * Chaque maître est associé à une valeur spécifique, la puissance.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public enum Maitre implements Serializable {
    YODA(15, "Maitre Yoda", Configuration.cheminYodaMaitreImage, Configuration.cheminYodaSound),
    OBIWAN(10, "Obi-Wan", Configuration.cheminObiWanMaitreImage, Configuration.cheminObiWanSound),
    LUKE(12, "Luke", Configuration.cheminLukeMaitreImage, Configuration.cheminLukeSound),
    ANAKIN(17, "Anakin", Configuration.cheminAnakinMaitreImage, Configuration.cheminAnakinSound);

    private final int puissance;
    private final String nomAffiche;
    private final String cheminImage;
    private final String cheminSon;

    /**
     * Crée une nouvelle instance de l'énumération Maitre avec la puissance spécifiée et le chemin de l'image associé.
     *
     * @param unePuissance   La puissance du maître.
     * @param unCheminImage  Le chemin de l'image associé au maître.
     */
    Maitre(int unePuissance, String unNomAffiche, String unCheminImage, String unCheminSon) {
        this.puissance = unePuissance;
        this.nomAffiche = unNomAffiche;
        this.cheminImage = unCheminImage;
        this.cheminSon = unCheminSon;
    }

    /**
     * Obtient la puissance associée au maître.
     *
     * @return La puissance du maître.
     */
    public int getPuissance() {
        return this.puissance;
    }

    /**
     * Obtient le nom à afficher associé au maître.
     *
     * @return Le nom à afficher du maître.
     */
    public String getNomAffiche() {
        return this.nomAffiche;
    }

    /**
     * Obtient le chemin de l'image associé au maître.
     *
     * @return Le chemin de l'image du maître.
     */
    public String getCheminImage() {
        return this.cheminImage;
    }

    /**
     * Obtient le chemin du son associé au maître.
     *
     * @return Le chemin du son du maître.
     */
    public String getCheminSon() {
        return this.cheminSon;
    }
}
