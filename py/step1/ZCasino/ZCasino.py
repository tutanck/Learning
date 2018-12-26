# -*-coding:Utf-8 -*

from math import ceil
import random

print("Bienvenu chez ZCasino\nCommençons une partie!")
gain = 0

while 1:

    while 1:
        try:
            mise = input("Combien voulez-vous parier ?\n")
            mise = int(mise)  # arrondit les float
            assert mise > 0
            print("Vous avez choisi de parier ", mise, "$")
            break
        except ValueError:
            print("La somme que vous pariez doit être un nombre positif!")
            continue
        except AssertionError:
            print("La somme que vous pariez doit être un nombre positif!")
            continue

    while 1:
        try:
            num = input("Sur quel numéro voulez-vous parier ?\n")
            num = int(num)  # arrondit les float
            assert num >= 0 and num <= 49 and type(num) == int
            print("Vous avez choisi de parier sur le numéro", num)
            break
        except ValueError:
            print("Le numéro choisi doit être un entier compris entre 0 et 49!")
            continue
        except AssertionError:
            print("Le numéro choisi doit être un entier compris entre 0 et 49!")
            continue

    r = random.randrange(50)
    print("Le hasard a tiré : ", r)

    if r == num:
        bonus = 3 * mise
        print("Vous avez gagné 3x la mise!")
    elif (num % 2 == 0 and r % 2 == 0) or (num % 2 != 0 and r % 2 != 0):
        bonus = mise / 2
        print("le numéro misé est de la même couleur que le numéro gagnant!")
    else:
        bonus = 0
        print("Vous perdez votre mise!")

    bonus = ceil(bonus)
    print("Votre bonus est :", bonus, "$")

    gain += bonus
    print("Votre gain est de : ", gain, "$")

    try:
        choix = input("Tapez 'C' pour Continuer : \n")
        assert choix == "C"
    except:
        print("Merci d'avoir joué. A bientôt!")
        break