/**
 * Ce package contient les éléments qui constituent la structure du jeu et comment fonctionne celui-ci.
 * 
 * Event: Interface pour les événements dans le jeu.
 * Node: Classe de base abstraite pour les nodes dans le jeu.
 * InnerNode: Classe pour les nodes internes qui peuvent conduire à d'autres nodes.
 * ChanceNode: Node permettant de choisir aléatoirement le prochain événement.
 * DecisionNode: Node offrant plusieurs choix de node au joueur.
 * FightNode: Représente une node de combat dans le jeu.
 * TerminalNode: Représente une node terminal sans suivant.
 * EventDecorator: Classe abstraite pour décorer les événements avec des fonctionnalités supplémentaires.
 * SoundNode: Joue un son lors de l'activation de la node.
 * ImageNode: Affiche une image lors de l'activation de la node.
 * ModifyCharacterNode: Modifie les attributs d'un personnage.
 * Caracteristique: Enumération des différentes caractéristiques modifiables d'un personnage pour ModifyCharacterNode.
 * Observateur: Interface pour mettre à jour la référence de Personnage après modification du type dans les nodes.
 * 
 * @author Maxime DUBOSCQ, Nicolas LONGHI 
 * @version 1.0
 */
package representation;