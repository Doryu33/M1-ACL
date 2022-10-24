#!/usr/bin/env bash
echo Creation de l\'archive...
./gradlew desktop:dist
echo Archive crée avec succes.
mv desktop/build/libs/DungeonCrypt-1.0.jar ../M1-ACL
echo Utilisez \"java -jar DungeonCrypt-1.0.jar\" pour lancer l\'application.