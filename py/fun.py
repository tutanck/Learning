# -*-coding:Utf-8 -*


from math import fabs
# from math import *
import math


def table(nb, max=10):
    """Fonction affichant la table de multiplication par nb
    de 1*nb à max*nb

    (max >= 0)"""
    i = 0
    while i < max:
        print(i + 1, "*", nb, "=", (i + 1) * nb)
        i += 1


table(6)
# help(table)


def exemple():
    print("Un exemple d'une fonction sans paramètre")


exemple()


def exemple():  # On redéfinit la fonction exemple
    print("Un autre exemple de fonction sans paramètre")


# exemple = 4 # TypeError: 'int' object is not callable
exemple()


def carre(valeur):
    return valeur * valeur


variable = carre(5)

print(variable)

# f = lambda x, y: x + y

print(math.sqrt(16))
# help(math)
# help("math.sqrt")

print(fabs(-2))
