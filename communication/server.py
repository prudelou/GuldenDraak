import socket
import sys
import time
import os
import RPi.GPIO as GPIO

sys.path.insert(0, '../Moteur')

from MoteurController import MoteurController


def wait_action():
	connexion_avec_client, infos_connexion = connexion_principale.accept()
	msg_recu = connexion_avec_client.recv(1024)
	if "forward" in msg_recu:
		connexion_avec_client.send(b"forward")
		moteurController.forward()
	elif "backward" in msg_recu:
		connexion_avec_client.send(b"backward")
		moteurController.backward()
	elif "right" in msg_recu:
		connexion_avec_client.send(b"right")
		moteurController.right()
	elif "left" in msg_recu:
		connexion_avec_client.send(b"left")
		moteurController.left()
	elif "stop" in msg_recu:
		connexion_avec_client.send(b"stop")
		moteurController.stop()
	else:
		print("Fermeture de la connexion")
		connexion_avec_client.send(b"close")
	connexion_avec_client.close()


moteurAvDroit = [22, 26, 24]
moteurAvGauche = [11, 13, 15]
moteurArDroit = [36, 38, 40]
moteurArGauche = [19, 23, 21]

GPIO.setmode(GPIO.BOARD)
moteurController = MoteurController(moteurAvDroit, moteurAvGauche, moteurArDroit, moteurArGauche)
moteurController.setPuissance(80)

hote = ''
port = 12800

connexion_principale = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
connexion_principale.bind((hote, port))
connexion_principale.listen(5)
print("Le serveur écoute à présent sur le port {}".format(port))


while(1):
	wait_action()

connexion_principale.close()
