
import RPi.GPIO as GPIO
import sys
import time
sys.path.insert(0, '../Moteur')

from RobotController import RobotController
# Use BOARD GPIO references
# instead of physical pin numbers
GPIO.setmode(GPIO.BOARD)

# Define GPIO to use on Pi


moteurAvDroit = [22, 26, 24]
moteurAvGauche = [11, 13, 15]
moteurArDroit = [36, 38, 40]
moteurArGauche = [19, 23, 21]
tAv = [37,35,10]
tAr = [31,33,10]

GPIO.setmode(GPIO.BOARD)
robotController = RobotController(moteurAvDroit, moteurAvGauche, moteurArDroit, moteurArGauche, tAv, tAr)

robotController.start()

time.sleep(2)

robotController.setDirection("forward")

time.sleep(5)

