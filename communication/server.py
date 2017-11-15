import socket

hote = ''
port = 12800

connexion_principale = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
connexion_principale.bind((hote, port))
connexion_principale.listen(5)
print("Le serveur écoute à présent sur le port {}".format(port))

connexion_avec_client, infos_connexion = connexion_principale.accept()

connexion_avec_client.send(b"Connexion accepte")

print("Fermeture de la connexion")
connexion_avec_client.close()
connexion_principale.close()
