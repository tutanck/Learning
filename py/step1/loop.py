# -*-coding:Utf-8 -*

nb = 7  # On garde la variable contenant le nombre dont on veut la table de multiplication

i = 0  # C'est notre variable compteur que nous allons incrémenter dans la boucle
while i < 10:  # Tant que i est strictement inférieure à 10
    print(i + 1, "*", nb, "=", (i + 1) * nb)
    i += 1  # On incrémente i de 1 à chaque tour de boucle

chaine = "Bonjour les ZER0S"
for lettre in chaine:
    if lettre in "AEIOUYaeiouy":  # lettre est une voyelle
        print(lettre)
    else:  # lettre est une consonne... ou plus exactement, lettre n'est pas une voyelle
        print("*")


i = 1
while i < 20:  # Tant que i est inférieure à 20
    if i % 3 == 0:
        i += 4  # On ajoute 4 à i
        print("On incrémente i de 4. i est maintenant égale à", i)
        continue  # On retourne au while sans exécuter les autres lignes
    print("La variable i =", i)
    i += 1  # Dans le cas classique on ajoute juste 1 à i


while 1:  # 1 est toujours vrai -> boucle infinie
    lettre = input("Tapez 'Q' pour quitter : ")
    if lettre == "Q":
        print("Fin de la boucle")
        break
