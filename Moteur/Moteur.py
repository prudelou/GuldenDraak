import RPi.GPIO as GPIO

class Moteur:

	def __init__(self, pin_pwd, pin_forward, pin_backward, puissance):
		self.pin_pwd = pin_pwd
		self.pin_forward = pin_forward
		self.pin_backward = pin_backward
		self.puissance = puissance

		GPIO.setup(self.pin_pwd,GPIO.OUT)
		GPIO.setup(self.pin_forward,GPIO.OUT)
		GPIO.setup(self.pin_backward,GPIO.OUT)

		self.pwd = GPIO.PWM(self.pin_pwd,1000)


	def forward(self):
		GPIO.output(self.pin_pwd,GPIO.HIGH)
		GPIO.output(self.pin_forward,GPIO.HIGH)
		GPIO.output(self.pin_backward,GPIO.LOW)

		self.pwd.start(self.puissance)

	def backward(self):
		GPIO.output(self.pin_pwd,GPIO.HIGH)
		GPIO.output(self.pin_forward,GPIO.LOW)
		GPIO.output(self.pin_backward,GPIO.HIGH)

		self.pwd.start(self.puissance)

	def stop(self):
		GPIO.output(self.pin_pwd,GPIO.LOW)
		GPIO.output(self.pin_forward,GPIO.LOW)
		GPIO.output(self.pin_backward,GPIO.LOW)

		self.pwd.start(0)

	def setPuissance(self, puissance):
		self.puissance = puissance
		self.pwd.start(self.puissance)