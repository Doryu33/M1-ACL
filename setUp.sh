#!/usr/bin/env bash
#Constantes...
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color
BOLD=$(tput bold)
NORMAL=$(tput sgr0)
FILE=desktop/build/libs/DungeonCrypt-1.0.jar

#Debut du script

echo -e "${BOLD}Creation de l'archive...${NORMAL}"
./gradlew desktop:dist
if [ -f "$FILE" ]; then 
    echo -e "\n${BOLD}${GREEN}Archive crée avec succès.${NORMAL}"
    mv desktop/build/libs/DungeonCrypt-1.0.jar ../
    cd ../
    echo -e "\nUtilisez \"${BOLD}java -XstartOnFirstThread -jar DungeonCrypt-1.0.jar\"${NORMAL} pour lancer l'application.${NC}"
else 
    echo -e "\n${RED}Erreur lors de la création de l'archive."
fi

echo -e "\nFin de l'installation...\n"

while true; do
    read -p "Voulez-vous lancez le jeu maintenant? (y/n)" yn
    case $yn in
        [Yy]* )  java -XstartOnFirstThread -jar DungeonCrypt-1.0.jar; break;;
        [Nn]* ) exit;;
        * ) exit;;
    esac
done



