from Moteur import Moteur
class MoteurController:

	def __init__(self, moteurAvD, moteurAvG, moteurArD, moteurArG):
		self.moteurAvD = Moteur(moteurAvD[0], moteurAvD[1], moteurAvD[2])
		self.moteurAvG = Moteur(moteurAvG[0], moteurAvG[1], moteurAvG[2])
		self.moteurArD = Moteur(moteurArD[0], moteurArD[1], moteurArD[2])
		self.moteurArG = Moteur(moteurArG[0], moteurArG[1], moteurArG[2])
		self.puissance = 0
		self.max_puissance = 0
		self.direction = "stop"


	def setMaxPuissance(self, puissance):
		if puissance > 100:
			puissance = 100
		self.max_puissance = puissance

	def setPwd(self):
		if self.direction == "stop":
			self.puissance = 0
		else:
			if self.puissance < 20:
				self.puissance = 20
			if self.puissance > self.max_puissance:
				self.puissance-= 5
				if self.puissance < self.max_puissance:
					self.puissance = self.max_puissance
			elif self.puissance < self.max_puissance:
				self.puissance+= 5
				if self.puissance > self.max_puissance:
					self.puissance = self.max_puissance
		print self.puissance
		self.moteurAvD.pwd.start(self.puissance)
		self.moteurAvG.pwd.start(self.puissance)
		self.moteurArD.pwd.start(self.puissance)
		self.moteurArG.pwd.start(self.puissance)

	def move(self, direction):
		#print("MoteurController.move direction : " + str(direction))
		if "forward" in direction:
			self.forward()
		elif "backward" in direction:
			self.backward()
		elif "right" in direction:
			self.right()
		elif "left" in direction:
			self.left()
		else:
			self.stop()

		self.setPwd()

	def forward(self):
		self.direction = "forward"
		self.moteurAvD.forward()
		self.moteurAvG.forward()
		self.moteurArD.forward()
		self.moteurArG.forward()

	def backward(self):
		self.direction = "backward"
		self.moteurAvD.backward()
		self.moteurAvG.backward()
		self.moteurArD.backward()
		self.moteurArG.backward()

	def right(self):
		self.direction = "right"
		self.moteurAvD.stop()
		self.moteurAvG.forward()
		self.moteurArD.stop()
		self.moteurArG.forward()

	def left(self):
		self.direction = "left"
		self.moteurAvD.forward()
		self.moteurAvG.stop()
		self.moteurArD.forward()
		self.moteurArG.stop()

	def stop(self):
		self.direction = "stop"
		self.moteurAvD.stop()
		self.moteurAvG.stop()
		self.moteurArD.stop()
		self.moteurArG.stop()