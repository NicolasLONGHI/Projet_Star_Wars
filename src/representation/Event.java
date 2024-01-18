package representation;

import java.io.Serializable;

/**
 * Interface Event représentant un événement dans le jeu.
 * Elle est utilisée pour définir les actions et les interactions
 * dans le jeu. C'est l'origine du Design Pattern : Decorator.
 * Cette interface est Serializable afin de faire des sauvegardes.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public interface Event extends Serializable {

    /**
     * Affiche les détails de l'événement.
     *
     * @return Une chaîne de caractères décrivant l'événement.
     */
    public String display();

    /**
     * Choisis le prochain événement basé sur un identifiant de node.
     *
     * @param unIDNode L'identifiant de la node utilisée pour choisir le prochain événement.
     * @return L'événement suivant.
     */
    public Event chooseNext(int unIDNode);

    /**
     * Obtient l'identifiant de cet événement.
     *
     * @return L'identifiant de l'événement.
     */
    public int getId();

}
