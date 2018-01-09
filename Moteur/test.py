import RPi.GPIO as GPIO
from RobotController import RobotController
from time import sleep

moteurAvDroit = [22, 26, 24]
moteurAvGauche = [11, 13, 15]
moteurArDroit = [36, 38, 40]
moteurArGauche = [19, 23, 21]
tAv = [37,35,20]
tAr = [31,33,20]
GPIO.cleanup()
GPIO.setmode(GPIO.BOARD)
robotController = RobotController(moteurAvDroit, moteurAvGauche, moteurArDroit, moteurArGauche, tAv, tAr)
robotController.start()

while 1:
  msg_recu = input()
  print msg_recu
  if "forward" in msg_recu:
    robotController.setDirection("forward")
  elif "backward" in msg_recu:
    robotController.setDirection("backward")
  elif "right" in msg_recu:
    robotController.setDirection("right")
  elif "left" in msg_recu:
    robotController.setDirection("left")
  elif "stop" in msg_recu:
    robotController.setDirection("stop")
  elif "puissance" in msg_recu:
    puissance = int(msg_recu.split(":")[1])
    robotController.setMaxPuissance(puissance)

GPIO.cleanup()