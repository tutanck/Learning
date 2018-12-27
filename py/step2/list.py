# -*-coding:Utf-8 -*

ma_liste = list()  # On crée une liste vide
type(ma_liste)

ma_liste = [1, 2, 3, 4, 5]  # Une liste avec cinq objets
print(ma_liste)

ma_liste = ['c', 'f', 'm']
ma_liste[0]  # On accède au premier élément de la liste
ma_liste[2]  # Troisième élément
ma_liste[1] = 'Z'  # On remplace 'f' par 'Z'
print(ma_liste)

ma_liste = [1, 2, 3]
ma_liste.append(56)  # On ajoute 56 à la fin de la liste
print(ma_liste)

ma_liste = ['a', 'b', 'd', 'e']
ma_liste.insert(2, 'c')  # On insère 'c' à l'indice 2
print(ma_liste)

ma_liste1 = [3, 4, 5]
ma_liste2 = [8, 9, 10]
ma_liste1.extend(ma_liste2)  # On insère ma_liste2 à la fin de ma_liste1
print(ma_liste1)
ma_liste1 = [3, 4, 5]
ma_liste = ma_liste1 + ma_liste2
print("ma_liste", ma_liste)
print(ma_liste1)
ma_liste1 += ma_liste2  # Identique à extend
print(ma_liste1)

ma_liste = [-5, -2, 1, 4, 7, 10]
del ma_liste[0]  # On supprime le premier élément de la liste
print(ma_liste)
[-2, 1, 4, 7, 10]
del ma_liste[2]  # On supprime le troisième élément de la liste
print(ma_liste)


ma_liste = [31, 32, 33, 34, 35]
ma_liste.remove(32)
print(ma_liste)

ma_liste = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h']
i = 0  # Notre indice pour la boucle while
while i < len(ma_liste):
    print(ma_liste[i])
    i += 1  # On incrémente i, ne pas oublier !

# Cette méthode est cependant préférable
for elt in ma_liste:  # elt va prendre les valeurs successives des éléments de ma_liste
    print(elt)


ma_liste = ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h']
for i, elt in enumerate(ma_liste):
    print("À l'indice {} se trouve {}.".format(i, elt))

for elt in enumerate(ma_liste):
    print(elt)


autre_liste = [
    [1, 'a'],
    [4, 'd'],
    [7, 'g'],
    [26, 'z'],
]  # J'ai étalé la liste sur plusieurs lignes
for nb, lettre in autre_liste:
    print("La lettre {} est la {}e de l'alphabet.".format(lettre, nb))


""" À la différence des listes, les tuples,
 une fois créés, ne peuvent être modifiés :
 on ne peut plus y ajouter d'objet ou en retirer. """
tuple_vide = ()
tuple_non_vide = (1,)  # est équivalent à ci dessous
tuple_non_vide = 1,
tuple_avec_plusieurs_valeurs = (1, 2, 5)

# Affectation multiple
a, b = 3, 4
# eq
(a, b) = (3, 4)

""" Quand Python trouve plusieurs variables ou valeurs séparées par des virgules
et sans délimiteur, il va les mettre dans des tuples.
Dans le premier exemple, les parenthèses sont sous-entendues
et Python comprend ce qu'il doit faire.
"""


def decomposer(entier, divise_par):
    """Cette fonction retourne la partie entière et le reste de
    entier / divise_par"""

    p_e = entier // divise_par
    reste = entier % divise_par
    return p_e, reste


partie_entiere, reste = decomposer(20, 3)

print(partie_entiere)
print(reste)


""" En résumé

    Une liste est une séquence mutable pouvant contenir plusieurs autres objets.

    Une liste se construit ainsi :liste = [element1, element2, elementN].

    On peut insérer des éléments dans une liste à l'aide des méthodesappend,insertetextend.

    On peut supprimer des éléments d'une liste grâce au mot-clédelou à la méthoderemove.

    Un tuple est une séquence pouvant contenir des objets. À la différence de la liste, le tuple ne peut être modifié une fois créé.

 """


print("Bonjour à tous".split())

ma_liste = ['Bonjour', 'à', 'tous']
ma_phrase = " ".join(ma_liste)
print(ma_phrase)


def afficher_flottant(flottant):
    """Fonction prenant en paramètre un flottant et renvoyant une chaîne de caractères représentant la troncature de ce nombre. La partie flottante doit avoir une longueur maximum de 3 caractères.

    De plus, on va remplacer le point décimal par la virgule"""

    if type(flottant) is not float:
        raise TypeError("Le paramètre attendu doit être un flottant")
    flottant = str(flottant)
    partie_entiere, partie_flottante = flottant.split(".")
    # La partie entière n'est pas à modifier
    # Seule la partie flottante doit être tronquée
    return ",".join([partie_entiere, partie_flottante[:3]])


"""
>>> afficher_flottant(3.99999999999998)
'3,999'
>>> afficher_flottant(1.5)
'1,5'
 """


def fonction_inconnue(*parametres):
    """Test d'une fonction pouvant être appelée avec un nombre variable de paramètres"""
    print("J'ai reçu : {}.".format(parametres))


fonction_inconnue()  # On appelle la fonction sans paramètre
# J'ai reçu: ().
fonction_inconnue(33)
# J'ai reçu: (33,).
fonction_inconnue('a', 'e', 'f')
# J'ai reçu : ('a', 'e', 'f').
var = 3.5
fonction_inconnue(var, [4], "...")
# J'ai reçu : (3.5, [4], '...').


def fonction_inconnue_2(nom, prenom, *commentaires):
    """ fonction_inconnue avec_params_obligatoires """


def afficher(*parametres, sep=' ', fin='\n'):
    """Fonction chargée de reproduire le comportement de print.

    Elle doit finir par faire appel à print pour afficher le résultat.
    Mais les paramètres devront déjà avoir été formatés. 
    On doit passer à print une unique chaîne, en lui spécifiant de ne rien mettre à la fin :

    print(chaine, end='')"""

    # Les paramètres sont sous la forme d'un tuple
    # Or on a besoin de les convertir
    # Mais on ne peut pas modifier un tuple
    # On a plusieurs possibilités, ici je choisis de convertir le tuple en liste
    parametres = list(parametres)
    # On va commencer par convertir toutes les valeurs en chaîne
    # Sinon on va avoir quelques problèmes lors du join
    for i, parametre in enumerate(parametres):
        parametres[i] = str(parametre)
    # La liste des paramètres ne contient plus que des chaînes de caractères
    # À présent on va constituer la chaîne finale
    chaine = sep.join(parametres)
    # On ajoute le paramètre fin à la fin de la chaîne
    chaine += fin
    # On affiche l'ensemble
    print(chaine, end='')


liste_des_parametres = [1, 4, 9, 16, 25, 36]
print(*liste_des_parametres)

""" On utilise une étoile * dans les deux cas. 
Si c'est dans une définition de fonction, 
cela signifie que les paramètres fournis non attendus 
lors de l'appel seront capturés dans la variable, 
sous la forme d'un tuple. 

Si c'est dans un appel de fonction, 
au contraire, cela signifie que la variable sera décomposée 
en plusieurs paramètres envoyés à la fonction. """


liste_origine = [0, 1, 2, 3, 4, 5]
l = [nb * nb for nb in liste_origine]
print(l)


liste_origine = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
l = [nb for nb in liste_origine if nb % 2 == 0]
print(l)

qtt_a_retirer = 7  # On retire chaque semaine 7 fruits de chaque sorte
fruits_stockes = [15, 3, 18, 21]  # Par exemple 15 pommes, 3 melons...
l = [nb_fruits - qtt_a_retirer for nb_fruits in fruits_stockes if nb_fruits > qtt_a_retirer]
print(l)


inventaire = [
    ("pommes", 22),
    ("melons", 4),
    ("poires", 18),
    ("fraises", 76),
    ("prunes", 51),
]

# solution
print(inventaire)
inventaire_inverse = [(q, n) for (n, q) in inventaire]
print(inventaire_inverse)
inventaire_inverse_trie = sorted(inventaire_inverse,  key=lambda fruit: fruit[0], reverse=True)
print(inventaire_inverse_trie)
inventaire_trie = [(n, q) for (q, n)in inventaire_inverse_trie]
print(inventaire_trie)

# Correction
# On change le sens de l'inventaire, la quantité avant le nom
inventaire_inverse = [(qtt, nom_fruit) for nom_fruit, qtt in inventaire]
# On n'a plus qu'à trier dans l'ordre décroissant l'inventaire inversé
# On reconstitue l'inventaire trié
inventaire = [(nom_fruit, qtt)
              for qtt, nom_fruit in sorted(inventaire_inverse,
                                           reverse=True)]
print(inventaire)