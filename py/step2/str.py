# -*-coding:Utf-8 -*

minuscules = "une chaine en minuscules"
minuscules.upper()  # Mettre en majuscules

minuscules.capitalize()  # La première lettre en majuscule

espaces = "   une  chaine avec  des espaces   "
espaces.strip()  # On retire les espaces au début et à la fin de la chaîne

titre = "introduction"
titre.upper().center(20)

prenom = "Paul"
nom = "Dupont"
age = 21
print("Je m'appelle {0} {1} et j'ai {2} ans.".format(prenom, nom, age))


# formatage d'une adresse
adresse = """
{no_rue}, {nom_rue}
 {code_postal} {nom_ville} ({pays})
""".format(no_rue=5, nom_rue="rue des Postes", code_postal=75003, nom_ville="Paris", pays="France")
print(adresse)

age = 21
message = "J'ai " + str(age) + " ans."
print(message)

mot = "lac"
mot = "b" + mot[1:]
print(mot)
