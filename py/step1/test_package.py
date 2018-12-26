# -*-coding:Utf-8 -*

import package.fonctions
from package.fonctions import table
table(5)  # Appel de la fonction table

# Ou ...
package.fonctions.table(5)  # Appel de la fonction table

"""
https://openclassrooms.com/forum/sujet/importer-un-package
-----------------------------------------------------
Ya pas une histoire de fichier __init__.py à créer et à placer dans le répertoire package?
Si apparemment c'est ça. 
Dans le cours il est indiqué que ce fichier devient optionnel pour des versions de python supérieure ou égale à la 3.3. 
Donc comme tu utilises la version 2.7, il faut créer ce fichier (pas besoin d'écrire quoi que ce soit dedans. 
Il suffit juste d'enregistrer un fichier avec le nom __init__.py dans le répertoire package pour que ça fonctionne).
-----------------------------------------------------
Oui en effet, il faut placer un fichier nommé __init__.py dans le dossier contenant ton package. 
Il faut aussi bien entendu que Python puisse trouver ton package. 
Python regarde à divers endroits. 
Mais l'endroit le plus naturel pour mettre ton package (et le plus simple pour débuter) et là où se trouve ton script qui appelle ton package. 
Mettons que ton script soit : C:/MesScriptsPython/test_import.py, alors ton package se trouvera dans C:/MesScriptsPython/package. 
L'arborescence sera donc : 

C:/MesScriptsPython

|-- test_import.py
|-- package
    |-- __init__.py
    |-- fonctions.py

Et là tu pourras faire dans test_import.py
	
from package.fonctions import table
"""
