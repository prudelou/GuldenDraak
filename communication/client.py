import socket

hote = "localhost"
port = 12800

connexion_avec_serveur = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
connexion_avec_serveur.connect((hote, port))
print("Connexion établie avec le serveur sur le port {}".format(port))

while 1:
	connexion_avec_serveur.send(input())
print("Fermeture de la connexion")
connexion_avec_serveur.close()
