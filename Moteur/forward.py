import RPi.GPIO as GPIO
from time import sleep


def init():
	for pin in MoteurAvDroit:
		GPIO.setup(pin,GPIO.OUT)
		GPIO.output(pin,GPIO.LOW)

	for pin in MoteurArDroit:
		GPIO.setup(pin,GPIO.OUT)
		GPIO.output(pin,GPIO.LOW)

	for pin in MoteurAvGauche:
		GPIO.setup(pin,GPIO.OUT)
		GPIO.output(pin,GPIO.LOW)

	for pin in MoteurArGauche:
		GPIO.setup(pin,GPIO.OUT)
		GPIO.output(pin,GPIO.LOW)

def setPwd(moteur, puissance):
	pwm = GPIO.PWM(moteur[0],50)
	pwm.start(puissance)

def setOutput(moteur, indiceLow):
	for i in range(0, len(moteur)):
		if i == indiceLow:
			GPIO.output(moteur[i],GPIO.LOW)
		else:
			GPIO.output(moteur[i],GPIO.HIGH)
def forward(puissance):

	setPwd(MoteurAvDroit, puissance)
	setPwd(MoteurArDroit, puissance)
	setPwd(MoteurAvGauche, puissance)
	setPwd(MoteurArGauche, puissance)

	setOutput(MoteurAvDroit, 2)
	setOutput(MoteurArDroit, 2)
	setOutput(MoteurAvGauche, 2)
	setOutput(MoteurArGauche, 2)

GPIO.setmode(GPIO.BOARD)   ##je prefere la numerotation BOARD plutot que BCM

MoteurAvDroit = [22, 26, 24]
MoteurArDroit = [36, 38, 40]
MoteurAvGauche = [11, 13, 15]
MoteurArGauche = [19, 23, 21]

init()
forward(1)

sleep(2)



GPIO.cleanup()