from MoteurController import MoteurController
import sys
from threading import Thread
sys.path.insert(0, '../telemetre')
from Telemetre import Telemetre
import time
class RobotController(Thread):

	def __init__(self, moteurAvD, moteurAvG, moteurArD, moteurArG, telemetreAv, telemetreAr):
		Thread.__init__(self)
		self.moteurController = MoteurController(moteurAvD, moteurAvG, moteurArD, moteurArG)
		self.moteurController.setPuissance(80)
		self.tAv = Telemetre(telemetreAv[0],telemetreAv[1],telemetreAv[2])
		self.tAr = Telemetre(telemetreAr[0],telemetreAr[1],telemetreAr[2])
		self.direction = "stop"


	def run(self):
		while 1:
			move = "stop"

			if "backward" in self.direction:
				if not self.tAr.needStop():
					move = "backward"

			else:
				if not self.tAv.needStop():

					move = self.direction

			self.moteurController.move(move)

			time.sleep(0.1)


	def setDirection(self, direction):
		print "pass"
		self.direction = direction