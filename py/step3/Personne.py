# -*-coding:Utf-8 -*


class Personne:
    """Classe définissant une personne caractérisée par :
    - son nom
    - son prénom
    - son âge
    - son lieu de résidence"""

    def __init__(self, nom, prenom):
        """Constructeur de notre classe"""
        self.nom = nom
        self.prenom = prenom
        self.age = 33
        self.lieu_residence = "Paris"


bernard = Personne("Micado", "Bernard")
print(bernard.nom, bernard.prenom, bernard.age)


class Compteur:
    """Cette classe possède un attribut de classe qui s'incrémente à chaque
    fois que l'on crée un objet de ce type"""

    objets_crees = 0  # Le compteur vaut 0 au départ

    def __init__(self):
        """À chaque fois qu'on crée un objet, on incrémente le compteur"""
        Compteur.objets_crees += 1

    def combien(cls):
        """Méthode de classe affichant combien d'objets ont été créés"""
        print("Jusqu'à présent, {} objets ont été créés.".format(
            cls.objets_crees))
    combien = classmethod(combien)


print(Compteur.objets_crees)

a = Compteur()  # On crée un premier objet
print(Compteur.objets_crees)

b = Compteur()
print(Compteur.objets_crees)

Compteur.combien()

a = Compteur()
Compteur.combien()

b = Compteur()
Compteur.combien()


class TableauNoir:
    """Classe définissant une surface sur laquelle on peut écrire,
    que l'on peut lire et effacer, par jeu de méthodes. L'attribut modifié
    est 'surface'"""

    def __init__(self):
        """Par défaut, notre surface est vide"""
        self.surface = ""

    def ecrire(self, message_a_ecrire):
        """Méthode permettant d'écrire sur la surface du tableau.
        Si la surface n'est pas vide, on saute une ligne avant de rajouter
        le message à écrire"""

        if self.surface != "":
            self.surface += "\n"
        self.surface += message_a_ecrire

    def lire(self):
        """Cette méthode se charge d'afficher, grâce à print,
        la surface du tableau"""

        print(self.surface)

    def effacer(self):
        """Cette méthode permet d'effacer la surface du tableau"""
        self.surface = ""


tab = TableauNoir()
print(tab.surface)
tab.ecrire("Coooool ! Ce sont les vacances !")
print(tab.surface)
tab.ecrire("Joyeux Noël !")
print(tab.surface)


print(tab.ecrire)
print(TableauNoir.ecrire)
print(help(TableauNoir.ecrire))
TableauNoir.ecrire(tab, "essai")
print(tab.surface)


tab = TableauNoir()
tab.lire()
tab.ecrire("Salut tout le monde.")
tab.ecrire("La forme ?")
tab.lire()
tab.effacer()
tab.lire()


class Test:
    """Une classe de test tout simplement"""
    def afficher():
        """Fonction chargée d'afficher quelque chose"""
        print("On affiche la même chose.")
        print("peu importe les données de l'objet ou de la classe.")
    afficher = staticmethod(afficher)

    def __init__(self):
        """On définit dans le constructeur un unique attribut"""
        self.mon_attribut = "ok"

    def afficher_attribut(self):
        """Méthode affichant l'attribut 'mon_attribut'"""
        print("Mon attribut est {0}.".format(self.mon_attribut))

# Créons un objet de la classe Test


un_test = Test()
un_test.afficher_attribut()
print(dir(un_test))

un_test = Test()
print(un_test.__dict__)


un_test.__dict__["mon_attribut"] = "ko"
un_test.afficher_attribut()
