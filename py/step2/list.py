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
