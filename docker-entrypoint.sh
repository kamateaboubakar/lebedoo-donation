#!/bin/sh

echo "En attente de la base de donnée"
#./wait-for db:5432


echo "Demarrage du serveur"
java -jar /application.jar