import RPi.GPIO as GPIO
from MoteurController import MoteurController
from time import sleep

moteurAvDroit = [22, 26, 24]
moteurAvGauche = [11, 13, 15]
moteurArDroit = [36, 38, 40]
moteurArGauche = [19, 23, 21]
GPIO.setmode(GPIO.BOARD)
moteurController = MoteurController(moteurAvDroit, moteurAvGauche, moteurArDroit, moteurArGauche)

sleep(2)

moteurController.setPuissance(20)
moteurController.forward()

sleep(2)

moteurController.setPuissance(80)

sleep(2)

moteurController.stop()

sleep(2)

moteurController.setPuissance(20)
moteurController.backward()

sleep(2)

moteurController.setPuissance(80)

sleep(2)

moteurController.stop()

sleep(2)

moteurController.setPuissance(20)
moteurController.right()

sleep(2)

moteurController.setPuissance(80)

sleep(2)

moteurController.stop()

sleep(2)

moteurController.setPuissance(20)
moteurController.left()

sleep(2)

moteurController.setPuissance(80)

sleep(2)

moteurController.stop()


GPIO.cleanup()