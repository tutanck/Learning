# -*-coding:Utf-8 -*

"""module multipli contenant la fonction table"""


def table(nb, max=10):
    """Fonction affichant la table de multiplication par nb de
    1 * nb jusqu'à max * nb"""
    i = 0
    while i < max:
        print(i + 1, "*", nb, "=", (i + 1) * nb)
        i += 1


# test de la fonction table
""" Tout repose en fait sur la variable__name__, 
c'est une variable qui existe dès le lancement de l'interpréteur. 
Si elle vaut__main__, cela veut dire que le fichier appelé est le fichier exécuté.
Autrement dit, si__name__vaut__main__,
vous pouvez mettre un code qui sera exécuté lorsque le fichier est lancé directement comme un exécutable.
"""

if __name__ == "__main__":
    table(4)
