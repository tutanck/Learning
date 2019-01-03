b = 7
var = 4
a = 5


def print_a():
    """Fonction chargée d'afficher la variable a.
    Cette variable a n'est pas passée en paramètre de la fonction.
    On suppose qu'elle a été créée en dehors de la fonction, on veut voir
    si elle est accessible depuis le corps de la fonction"""

    print("La variable a = {0}.".format(a))


print_a()

a = 8
print_a()

print("--------------------------------")

print(a)
print(b)
print(var)


def set_var(nouvelle_valeur):
    """Fonction nous permettant de tester la portée des variables
    définies dans notre corps de fonction"""
    nouvelle_valeur = "keke"

    print("La variable a = {0}.".format(a))

    # On essaye d'afficher la variable si elle existe

    try:
        print("Avant l'affectation, notre variable a vaut {0}.".format(a))
    except NameError:
        print("La variable a n'existe pas encore.")

    try:
        print("Avant l'affectation, notre variable b vaut {0}.".format(b))
    except NameError:
        print("La variable b n'existe pas encore.")

    try:
        print("Avant l'affectation, notre variable var vaut {0}.".format(var))
    except NameError:
        print("La variable var n'existe pas encore.")

    """ la declaration de a masque la a global qui devient inexistant
    même plus haut à l'intérieur de la fonction """
    # a = nouvelle_valeur
    var = nouvelle_valeur
    b = nouvelle_valeur
    print("Après l'affectation, notre variable b vaut {0}.".format(b))
    print("Après l'affectation, notre variable var vaut {0}.".format(var))


set_var(1)

print(a)
print(b)
print(var)

ma_liste1 = [1, 2, 3]
# Cela revient à copier le contenu de ma_liste1
ma_liste2 = list(ma_liste1)
ma_liste2.append(4)
print(ma_liste2)
print(ma_liste1)


ma_liste1 = [1, 2]
ma_liste2 = [1, 2]
print(ma_liste1 == ma_liste2)  # On compare le contenu des listes
print(ma_liste1 is ma_liste2)  # On compare leur référence

i = 4  # Une variable, nommée i, contenant un entier


def inc_i():
    """Fonction chargée d'incrémenter i de 1"""
    global i  # Python recherche i en dehors de l'espace local de la fonction
    i += 1


inc_i()

print("i=", i)


""" - Les variables locales définies avant 
    l'appel d'une fonction seront accessibles,
    depuis le corps de la fonction, en lecture seule.

    - Une variable locale définie dans une fonction 
    sera supprimée après l'exécution de cette fonction.

    - On peut cependant appeler les attributs et méthodes 
    d'un objet pour le modifier durablement.

    - Les variables globales se définissent à l'aide du 
    mot-cléglobal suivi du nom de la variable préalablement créée.

    - Les variables globales peuvent être modifiées depuis 
    le corps d'une fonction (à utiliser avec prudence). """
