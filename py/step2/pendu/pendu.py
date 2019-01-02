
import os
from donnees import nb_chances
from fonctions import *

continuer_partie = True
nom_joueur = input("Entrez votre nom de joueur:\n")
scores = {}

try:
    scores = lire_f_score()
except FileNotFoundError:
    ecrire_f_score(scores)

if nom_joueur not in scores.keys():
    print("Bienvenu! Commençons une nouvelle partie de pendu!")
    scores[nom_joueur] = 0
else:
    print("Bienvenu", nom_joueur,
          "! Reprenons la partie.\nVotre score sauvegardé est :", scores[nom_joueur])

while continuer_partie == True:
    mot = choisir_mot()
    lettres_trouvees = list()
    mot_en_clair = clarify(mot, lettres_trouvees)
    print(mot)
    i = 8
    while i > 0:
        lettre = saisir_lettre()
        i -= 1
        if lettre in mot:
            lettres_trouvees.append(lettre)
            mot_en_clair = clarify(mot, lettres_trouvees)

        print("Le mot est :", mot_en_clair)

        if "*" not in mot_en_clair:
            print("Vous avez trouvé!")
            scores[nom_joueur] = scores[nom_joueur]+i
            print("Votre score est maintenant de :", scores[nom_joueur])
            break

        if i > 0:
            print("Il vous reste ", i, "essais pour trouver!")
        else:
            print("Vous n'avez pas trouvé! Le mot était :", mot)

    quitter = input("Souhaitez-vous quitter le casino(o/n) ? \n")
    if quitter == "o" or quitter == "O":
        print("Vous quittez la partie avec un score de ", scores[nom_joueur])
        continuer_partie = False
        ecrire_f_score(scores)