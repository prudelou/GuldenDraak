import socket

hote = "localhost"
port = 12800

connexion_avec_serveur = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
connexion_avec_serveur.connect((hote, port))
print("Connexion établie avec le serveur sur le port {}".format(port))

while 1:
  inty = input()
  print inty
  connexion_avec_serveur.send(inty)
  print "wait"
  msg_recu = connexion_avec_serveur.recv(1024)
  print msg_recu
print("Fermeture de la connexion")
connexion_avec_serveur.close()
