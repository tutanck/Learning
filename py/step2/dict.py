# -*-coding:Utf-8 -*


mon_dictionnaire = {}
mon_dictionnaire["pseudo"] = "Prolixe"
mon_dictionnaire["mot de passe"] = "*"
print(mon_dictionnaire)

print()

placard = {"chemise": 3, "pantalon": 6, "tee shirt": 7}
del placard["chemise"]
print(placard)

placard = {"chemise": 3, "pantalon": 6, "tee shirt": 7}
placard.pop("chemise")
print(placard)

print()


def fete():
    print("C'est la fête.")


def oiseau():
    print("Fais comme l'oiseau")


fonctions = {}
fonctions["fete"] = fete  # on ne met pas les parenthèses
fonctions["oiseau"] = oiseau
fonctions["oiseau"]()  # on essaye de l'appeler

fruits = {"pommes": 21, "melons": 3, "poires": 31}
for cle in fruits:
    print(cle)

print()

fruits = {"pommes": 21, "melons": 3, "poires": 31}
for cle in fruits.keys():
    print(cle)

print()

for valeur in fruits.values():
    print(valeur)


if 21 in fruits.values():
    print("Un des fruits se trouve dans la quantité 21.")

fruits = {"pommes": 21, "melons": 3, "poires": 31}
for cle, valeur in fruits.items():
    print("La clé {} contient la valeur {}.".format(cle, valeur))


def fonction_inconnue(**parametres_nommes):
    """Fonction permettant de voir comment récupérer les paramètres nommés
    dans un dictionnaire"""
    print("J'ai reçu en paramètres nommés : {}.".format(parametres_nommes))


fonction_inconnue()  # Aucun paramètre

fonction_inconnue(p=4, j=8)


def fonction_inconnue2(*en_liste, **en_dictionnaire):
    """Tous les paramètres non nommés se retrouveront dans la variable en_listeet 
    les paramètres nommés dans la variable en_dictionnaire.
    """
    print("J'ai reçu en paramètres nommés : {}. {}.".format(
        en_liste, en_dictionnaire))


fonction_inconnue2(3, blo=3)

parametres = {"sep": " >> ", "end": " -\n"}
print("Voici", "un", "exemple", "d'appel", **parametres)
