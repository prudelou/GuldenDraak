import RPi.GPIO as GPIO
import time
class Telemetre:

	def __init__(self, pin_trigger, pin_echo, min_distance):
		self.pin_trigger = pin_trigger
		self.pin_echo = pin_echo
		self.min_distance = min_distance

		# Set pins as output and input
		GPIO.setup(self.pin_trigger,GPIO.OUT)  # Trigger
		GPIO.setup(self.pin_echo,GPIO.IN)      # Echo

		# Set trigger to False (Low)
		GPIO.output(self.pin_trigger, False)


	def getDistance(self):
		# Send 10us pulse to trigger
		GPIO.output(self.pin_trigger, True)
		time.sleep(0.00001)
		GPIO.output(self.pin_trigger, False)
		start = time.time()
		while GPIO.input(self.pin_echo)==0:
		  start = time.time()

		while GPIO.input(self.pin_echo)==1:
		  stop = time.time()

		# Calculate pulse length
		elapsed = stop-start

		# Distance pulse travelled in that time is time
		# multiplied by the speed of sound (cm/s)
		distance = elapsed * 34000

		# That was the distance there and back so halve the value
		distance = distance / 2

		return distance

	def needStop(self):
		stop = False
		if self.getDistance() < self.min_distance:
			stop = True

		return stop

	def setMinDistance(self, min_distance):
		self.min_distance = min_distance