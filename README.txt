# M1-ACL
            Projet ACL M1-Info 2022-2023
Noms des contributeurs: 
BOHAN Thomas    JACQUES Louis    SERRATORE Alexandre    SUCK Hugo


Backlogs et détails des Sprints :
https://docs.google.com/document/d/15mvGTUFEJ62VbLJEnIgwamEphrG5qdatsP_4NAIlIEY/edit#heading=h.wy8h7abuvwt3

            # Compilation et Execution
Version de Java : 11
Librairies externes nécessaires : Aucune

1) Lancer le script "setUp.sh" (ne pas oublier de le rendre executable, après avoir vérifier son contenu)
2) Pour lancer l'application (une fois l'archive générée), utiliser: "java -jar DungeonCrypt.1-0.jar"

* 3) Pour tester la fonctionnalité de chargement, copier coller le fichier "DungeonCrypt-Save.txt" à côté de l'archive.

Les fichiers de sauvegardes sont crées et placés a côté du .jar. Seul les "Save" et "DebugSave" fonctionnent.
Seul la sauvegarde via la touche de debug est disponible pour le moment (Commandes plus bas).

"Nouvelle partie": Crée une nouvelle partie.
"Continure la partie": Charge une sauvegarde automatique si elle existe.
"Charger une partie": Charge la partie sauvegardée.
"Options": Permets de modifier certains paramètres.
"Quitter": Ferme l'application

            # Commandes
Pour jouer:
Z pour monter
S pour descendre
Q pour aller à gauche
D pour aller à droite
Espace pour frapper

Pour debug/test:
N pour générer une nouvelle salle
O/P augmenter/diminuer le score
J pour sauvegarder la partie (fichier DebugSave.txt)
K pour charger la partie (fichier DebugSave.txt)