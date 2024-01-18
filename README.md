# Nom du Projet : Projet Java - Star Wars

## Description
Le projet tourne autour de l'univers Star Wars. Nous débutons en tant que Padawan et selon nos choix, nous devenons soit un Jedi soit un Sith.


## Remarque
Nous avons créé une interface graphique grâce à la librairie Swing, ce qui implique une modification de quelques méthodes imposées.
La classe FrmGame est l'équivalent de la classe Game demandée.
La Javadoc a été écrite sur toutes les méthodes du fichier src y compris sur les méthodes private. En revanche, elle n’a pas été générée que sur les méthodes public. 
Les tests unitaires ont été créés dans un autre fichier source reprenant pour modèle la structure du fichier src. Le nom des classes de tests reprend le nom des classes initiales en ajoutant Test à la fin.
Nous avons respecté une convention dans le nommage des fichiers audio et des images. Chaque fichier audio se termine par Sound et son extension est .wav. Chaque image se termine par Image et son extension est .png. Enfin, chaque icone se termine par Icone et son extension est .png. De même, les noms des constantes dans la classe Configuration commence par chemin s’il s’agit d’un chemin et se termine par Sound, Image ou Icone.


## Composition du projet

8 packages :
    - configuration : Les classes de configuration et utilitaire du jeu.
    - frm : Toutes les form.
    - icons : Toutes les icones pour l'interface graphique.
    - images : Toutes les images du jeu (combat, ImagNode, tutoriel).
    - main : La form principale.
    - representation : Toutes les nodes ainsi que le décorateur.
    - sounds : Tous les sons et bruitages.
    - univers : Différentes classes associées aux personnages.

6 forms :
    - FrmMain : C'est la première form que nous visualiserons lors de l'ouverture du programme. Nous pouvons créer une nouvelle partie ou continuer une partie déjà commencée. La date de la dernière connexion est affichée (en fonction de la dernière sauvegarde).
    - FrmFight : Interface graphique gérant les combats contre un ennemi.
    - FrmGame : C'est la form où l'histoire se déroulera, c'est l'équivalent de la classe Game demandée. Cette form est constituée de boutons permettant de choisir les prochaines nodes, le nombre de bouton s'adapte en fonction du nombre de choix possible. Nous avons également une visualisation des statistiques de notre personnage. De plus, nous pouvons couper le son, sauvegarder ou quitter la partie.
    - FrmInfos : Cette interface graphique permet de connaître quelques informations sur le jeu, quelles musiques ont été utilisé.
    - FrmSave : Accessible uniquement si au moins une sauvegarde existe, cette interface permet à l'utilisateur d'avoir un détail de la sauvegarde avant la sélectionner pour reprendre la partie.
    - FrmTutoriel : Permet de connaître comment fonctionne le jeu, entre-autres le système de combat.


## Compiler

Pour compiler le programme, il vous suffit de vous placer dans le répertoire racine et exécuter cette commande : javac -d bin src/**/*.java
Cette dernière va compiler le programme et va placer tous les fichiers .class dans le dossier bin.


## Exécuter

Pour exécuter le programme, il vous suffit de vous placer dans le répertoire racine et exécuter cette commande : java -cp bin main.FrmMain


## Créer le fichier .jar et l'exécuter

Pour créer le fichier .jar (executable), il faut d'abord compiler le programme puis vous placer dans le répertoire racine et ensuite exécuter cette commande : jar cvfe StarWarsGame.jar main.FrmMain -C bin/ .
Pour exécuter le fichier .jar, soit vous double-cliquer sur le fichier qui vient de se créer, soit vous exécutez cette commande : java -jar StarWarsGame.jar
Ce fichier est utile pour avoir une version finale que l'on peut donner à l'utilisateur.


## Utilisation

Une fois le jeu démarré, nous pouvons démarrer une nouvelle partie en cliquant sur « Nouvelle partie ». Nous sommes alors dirigé vers une nouvelle fenêtre de jeu où nous devons faire des choix qui influenceront l'histoire. Durant l'histoire, nous aurons à faire à quelques combats contre certains ennemis. En haut à gauche, nous pouvons couper le son, faire une sauvegarde et quitter la partie. En cliquant sur « Pseudo », nous pouvons changer de pseudo.
Durant les phases de combats, nous avons le choix entre 4 attaques qui ont chacun un taux de réussite spécifique : Attaque simple (100 %), Force (90 %), Sabre (80 %) et Attaque spéciale (65 %). L'attaque spéciale dépend du type de personnage. Si c'est un Padawan, il peut recevoir une aide de son maitre. L'attaque du maitre dépend du maitre (Yoda : 15, Obi-Wan : 10, Luke : 12 et Anakin : 17). Si c'est un Jedi, il peut lancer son sabre. Si c'est un Sith, il peut lancer des éclairs.
Les sabres ont également leur propre puissance : Bleu : 9, Vert : 10, Rouge : 15, Violet : 15, Entrainement : 7, Blanc : 17. Il faut réaliser une action spéciale pour avoir le sabre blanc...
Toutes les statistiques de chaque combattant sont visible dans l'interface de combat. Après chaque combat, notre vie revient à 100.