from random import randrange
import donnees
import pickle

f_name = donnees.f_name


def choisir_mot():
    mots = donnees.mots
    r = randrange(len(mots))
    return mots[r]


def saisir_lettre():
    lettre = ""
    alphabet = donnees.alphabet
    while lettre not in alphabet:
        lettre = input('Entrez une lettre de l\'alphabet:\n')
        lettre = str(lettre).lower()
    return lettre


def clarify(mot, lettres_trouvees):
    clare = list(mot)
    for i, l in enumerate(mot):
        if l in lettres_trouvees:
            clare[i] = mot[i]
        else:
            clare[i] = "*"
    clare = "".join(clare)
    return clare


def lire_f_score():
    with open(f_name, 'rb') as f_score_rb:
        mon_depickler = pickle.Unpickler(f_score_rb)
        return mon_depickler.load()


def ecrire_f_score(scores):
    with open(f_name, 'wb') as f_score_wb:
        mon_pickler = pickle.Pickler(f_score_wb)
        mon_pickler.dump(scores)