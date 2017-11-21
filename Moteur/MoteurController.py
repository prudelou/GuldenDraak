from Moteur import Moteur
class MoteurController:

	def __init__(self, moteurAvD, moteurAvG, moteurArD, moteurArG):
		self.moteurAvD = Moteur(moteurAvD[0], moteurAvD[1], moteurAvD[2], 0)
		self.moteurAvG = Moteur(moteurAvG[0], moteurAvG[1], moteurAvG[2], 0)
		self.moteurArD = Moteur(moteurArD[0], moteurArD[1], moteurArD[2], 0)
		self.moteurArG = Moteur(moteurArG[0], moteurArG[1], moteurArG[2], 0)


	def setPuissance(self, puissance):
		self.moteurAvD.setPuissance(puissance)
		self.moteurAvG.setPuissance(puissance)
		self.moteurArD.setPuissance(puissance)
		self.moteurArG.setPuissance(puissance)

	def forward(self):
		self.moteurAvD.forward()
		self.moteurAvG.forward()
		self.moteurArD.forward()
		self.moteurArG.forward()

	def backward(self):
		self.moteurAvD.backward()
		self.moteurAvG.backward()
		self.moteurArD.backward()
		self.moteurArG.backward()

	def right(self):
		self.moteurAvD.stop()
		self.moteurAvG.forward()
		self.moteurArD.stop()
		self.moteurArG.forward()

	def left(self):
		self.moteurAvD.forward()
		self.moteurAvG.stop()
		self.moteurArD.forward()
		self.moteurArG.stop()

	def stop(self):
		self.moteurAvD.stop()
		self.moteurAvG.stop()
		self.moteurArD.stop()
		self.moteurArG.stop()