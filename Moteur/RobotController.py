from MoteurController import MoteurController
import sys
from threading import Thread
sys.path.insert(0, '/home/pi/Git/GuldenDraak/telemetre')
from Telemetre import Telemetre
import time
class RobotController(Thread):

	def __init__(self, moteurAvD, moteurAvG, moteurArD, moteurArG, telemetreAv, telemetreAr):
		Thread.__init__(self)
		self.moteurController = MoteurController(moteurAvD, moteurAvG, moteurArD, moteurArG)
		self.moteurController.setMaxPuissance(80)
		self.tAv = Telemetre(telemetreAv[0],telemetreAv[1],telemetreAv[2])
		self.tAr = Telemetre(telemetreAr[0],telemetreAr[1],telemetreAr[2])
		self.direction = "stop"
		self.stop = False


	def run(self):
		while 1:
			if self.stop:
				break

			move = "stop"

			if "backward" in self.direction:
				if not self.tAr.needStop():
					move = "backward"
				else:
					print "needStop"
			else:
				if not self.tAv.needStop():
					move = self.direction
				else:
					print "needStop"

			self.moteurController.move(move)

			time.sleep(0.3)


	def setDirection(self, direction):
		print "RobotController.setDirection"
		self.direction = direction

	def setMaxPuissance(self, puissance):
		self.moteurController.setMaxPuissance(puissance)