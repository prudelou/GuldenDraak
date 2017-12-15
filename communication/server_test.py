import socket
import sys
import time
import os
import RPi.GPIO as GPIO

sys.path.insert(0, '../Moteur')

from RobotController import RobotController
import signal


connexion_avec_client = None
connexion_principale = None
robotController = None
moteurAvDroit = [22, 26, 24]
moteurAvGauche = [11, 13, 15]
moteurArDroit = [36, 38, 40]
moteurArGauche = [19, 23, 21]
tAv = [37,35,20]
tAr = [31,33,20]
hote = ''
port = 12800
def handler(signum, frame):
  print "catch ctrl-z"
  exit(0)

def exit_handler():
  print "catch ctrl-z"
  if connexion_avec_client != None:
    connexion_avec_client.close()

  if connexion_principale != None:
    connexion_principale.close()

  if robotController != None:
    robotController.stop = True
    robotController.join()


  GPIO.cleanup()
  print "exit"



def wait_action(connexion_avec_client):
  print "start"
  if connexion_avec_client == None:
    print "Server.wait_action connect"
    connexion_avec_client, infos_connexion = connexion_principale.accept()
  print "Server.wait_action after connect"
  msg_recu = connexion_avec_client.recv(1024)
  print "Server.wait_action msg: " + str(msg_recu)
  if msg_recu != "":
    connexion_avec_client.send(msg_recu)
  else:
    print("Fermeture de la connexion")
    connexion_avec_client.send(b"close")
    connexion_avec_client.close()
    connexion_avec_client = None

  return connexion_avec_client



signal.signal(signal.SIGTSTP, handler)

GPIO.setmode(GPIO.BOARD)
robotController = RobotController(moteurAvDroit, moteurAvGauche, moteurArDroit, moteurArGauche, tAv, tAr)

robotController.start()

connexion_principale = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
connexion_principale.bind((hote, port))
connexion_principale.listen(5)
print("Server : Le serveur ecoute a present sur le port {}".format(port))



try:
  while(1):
    connexion_avec_client = wait_action(connexion_avec_client)
    time.sleep(0.5)
finally:
  exit_handler()
