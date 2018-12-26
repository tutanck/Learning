# -*-coding:Utf-8 -*
print("Bonjour le monde !")

annee = input("Entrez une année.\n")
print(annee, type(annee))
annee = int(annee)

if not annee % 4 == 0:
    print(annee, "n'est pas bissextile")
elif annee % 400 == 0 or (annee % 4 == 0 and annee % 100 != 0):
    print(annee, "est bissextile")
else:
    print(annee, "n'est pas bissextile")
