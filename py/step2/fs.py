import pickle
import os

print(os.getcwd())

mon_fichier = open("test/fichier.txt", "r")
contenu = mon_fichier.read()
print(contenu)
mon_fichier.close()

mon_fichier = open("test/fichier.txt", "w")  # Argh j'ai tout écrasé !
mon_fichier.write("Premier test d'écriture dans un fichier via Python")
mon_fichier.close()

with open('test/fichier.txt', 'r') as mon_fichier:
    """ Cela ne veut pas dire que le bloc d'instructions ne lèvera aucune exception.
    Cela signifie simplement que, si une exception se produit, le fichier sera tout de même fermé à la fin du bloc.
    """
    texte = mon_fichier.read()
    print(texte)


score = {
    "joueur 1":    5,
    "joueur 2":   35,
    "joueur 3":   20,
    "joueur 4":    2,
}
with open('test/donnees', 'wb') as fichier:
    mon_pickler = pickle.Pickler(fichier)
    mon_pickler.dump(score)


with open('test/donnees', 'rb') as fichier:
    mon_depickler = pickle.Unpickler(fichier)
    score_recupere = mon_depickler.load()
    print(score_recupere)
