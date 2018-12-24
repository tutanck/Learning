# -*-coding:Utf-8 -*

annee = input("year: ")
try:  # On essaye de convertir l'année en entier
    annee = int(annee)
    print(annee)
except:
    print("Erreur lors de la conversion de l'année.")


try:
    resultat = numerateur / denominateur
except NameError:
    print("La variable numerateur ou denominateur n'a pas été définie.")
except TypeError:
    print("La variable numerateur ou denominateur possède un type incompatible avec la division.")
except ZeroDivisionError:
    print("La variable denominateur est égale à 0.")
else:
    print("Le résultat obtenu est", resultat)


try:  # Bloc de test
    resultat = numerateur / denominateur
except NameError as e:
    print("Voici l'erreur :", e)
finally:
    pass


try:
    # Test d'instruction(s)
    pass  # ne rien faire
except:  # Rien ne doit se passer en cas d'erreur
    pass  # ne rien faire


annee = input("Saisissez une année supérieure à 0 :")
try:
    annee = int(annee)  # Conversion de l'année
    assert annee > 0
except ValueError:
    print("Vous n'avez pas saisi un nombre.")
except AssertionError:
    print("L'année saisie est inférieure ou égale à 0.")

""" 
<=>
"""

annee = input("Saisissez une année supérieure à 0 :")
try:
    annee = int(annee)  # On tente de convertir l'année
    if annee > 0:
        raise AssertionError("L'année saisie est inférieure ou égale à 0.")
except ValueError:
    print("Vous n'avez pas saisi un nombre.")
except AssertionError: 
    print("L'année saisie est inférieure ou égale à 0.")
