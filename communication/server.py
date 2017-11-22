import socket
import sys
import time
import os
import RPi.GPIO as GPIO

sys.path.insert(0, '../Moteur')

from RobotController import RobotController

def wait_action(connexion_avec_client):
	print "start"
	if connexion_avec_client == None:
		print "connect"
		connexion_avec_client, infos_connexion = connexion_principale.accept()
	print "after connect"
	msg_recu = connexion_avec_client.recv(1024)
	print "after message"
	if "forward" in msg_recu:
		print "forward"
		connexion_avec_client.send(b"forward\n")
		robotController.setDirection("forward")
	elif "backward" in msg_recu:
		print "backward"
		connexion_avec_client.send(b"backward\n")
		robotController.setDirection("backward")
	elif "right" in msg_recu:
		print "right"
		connexion_avec_client.send(b"right\n")
		robotController.setDirection("right")
	elif "left" in msg_recu:
		print "left"
		connexion_avec_client.send(b"left\n")
		robotController.setDirection("left")
	elif "stop" in msg_recu:
		print "stop"
		connexion_avec_client.send(b"stop\n")
		robotController.setDirection("stop")
	else:
		print("Fermeture de la connexion")
		connexion_avec_client.send(b"close")
		connexion_avec_client.close()
		connexion_avec_client = None

	return connexion_avec_client


moteurAvDroit = [22, 26, 24]
moteurAvGauche = [11, 13, 15]
moteurArDroit = [36, 38, 40]
moteurArGauche = [19, 23, 21]
tAv = [37,35,10]
tAr = [31,33,10]

GPIO.setmode(GPIO.BOARD)
robotController = RobotController(moteurAvDroit, moteurAvGauche, moteurArDroit, moteurArGauche, tAv, tAr)

robotController.start()

hote = ''
port = 12800

connexion_principale = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
connexion_principale.bind((hote, port))
connexion_principale.listen(5)
print("Le serveur écoute à présent sur le port {}".format(port))

connexion_avec_client = None



while(1):
	connexion_avec_client = wait_action(connexion_avec_client)

connexion_principale.close()
