import socket

hote = "192.168.43.43"
port = 12800

connexion_avec_serveur = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
connexion_avec_serveur.connect((hote, port))
print("Connexion tablie avec le serveur sur le port {}".format(port))
msg_recu = connexion_avec_serveur.recv(1024)