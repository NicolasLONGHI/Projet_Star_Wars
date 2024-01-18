package representation;

/**
 * Classe abstraite EventDecorator qui implémente l'interface Event.
 * Cette classe sert de décorateur pour les objets de type Event, permettant
 * d'étendre leur fonctionnalité de manière dynamique.
 *
 * @author Maxime DUBOSCQ, Nicolas LONGHI
 * @version 1.0
 */
public abstract class EventDecorator implements Event {

    private static final long serialVersionUID = 1L;
	private int id;
	private Event decoratedNode;
	
	/**
     * Constructeur par défaut pour EventDecorator. Initialise une EventDecorator avec des valeurs par défaut.
     */
	public EventDecorator() {
		this.id = 0;
		this.decoratedNode = null;
	}
	
	/**
     * Constructeur pour initialiser un EventDecorator avec un identifiant et un évènement décoré.
     *
     * @param unId L'identifiant de l'EventDecorator.
     * @param unedecoratedNode L'Event à décorer.
     */
	public EventDecorator(int unId, Event unedecoratedNode) {
		if (unId < 0) {
			throw new IllegalArgumentException("L'ID ne peut pas être plus petit que 0 !");
		}
		this.id = unId;
		if (unedecoratedNode == null) {
			throw new IllegalArgumentException("La node à décorer ne peut pas être égale à null !");
		}
		this.decoratedNode = unedecoratedNode;
	}

	/**
     * Affiche les détails de l'événement décoré. Cette méthode délègue l'appel de la méthode display() à l'évènement décoré.
     *
     * @return Le retour du display() de l'évènement décoré.
     */
	@Override
    public String display() {
        return this.decoratedNode.display();
    }

	/**
     * Permet de choisir le prochain événement. Cette méthode délègue la décision à l'évènement décoré en appelant sa méthode chooseNext().
     *
     * @param unIDNode L'identifiant de la prochaine node.
     * @return Le retour du chooseNext() de l'évènement décoré.
     */
    @Override
    public Event chooseNext(int unIDNode) {
        return this.decoratedNode.chooseNext(unIDNode);
    }

    /**
     * Obtient l'identifiant de l'EventDecorator.
     *
     * @return L'identifiant de l'EventDecorator.
     */
	public int getId() {
		return id;
	}

    /**
     * Définit l'identifiant de l'EventDecorator.
     *
     * @param unId L'identifiant à définir.
     */
	public void setId(int unId) {
		this.id = unId;
	}

    /**
     * Obtient l'évènement décoré.
     *
     * @return L'évènement décoré.
     */
	public Event getDecoratedNode() {
		return decoratedNode;
	}

    /**
     * Définit l'évènement décoré de la node.
     *
     * @param uneDecoratedNode L'évènement à décoré.
     */
	public void setDecoratedNode(Event uneDecoratedNode) {
		this.decoratedNode = uneDecoratedNode;
	}

	/**
     * Compare ce EventDecorator avec un autre objet pour vérifier l'égalité.
     *
     * @param unDecoratedNode L'objet avec lequel comparer.
     * @return true si les EventDecorator sont égaaux, false sinon.
     */
	@Override
    public boolean equals(Object unDecoratedNode) {
        boolean retour = false;
        if (unDecoratedNode instanceof EventDecorator) {
        	EventDecorator leDecoratedNode = (EventDecorator) unDecoratedNode;
            if (this.decoratedNode.equals(leDecoratedNode.getDecoratedNode()) && (this.id == leDecoratedNode.getId())) {
                retour = true;
            }
        }
        return retour;
    }	
	
	/**
     * Fournit une représentation sous forme de chaîne de caractères du EventDecorator.
     *
     * @return Une chaîne de caractères représentant un EventDecorator.
     */
	@Override
	public String toString() {
		return "EventDecorator [decoratedNode=" + decoratedNode + "]";
	}
    
}
