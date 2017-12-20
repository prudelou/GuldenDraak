import picamera
import time
import socket
import io

camera = picamera.PiCamera()

hote = '192.168.43.43'
port = 12800

connexion_principale = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
connexion_principale.bind((hote, port))
connexion_principale.listen(0)
connexion_principale.setblocking(1)

print "wait connexion"
connexion_avec_client, infos_connexion = connexion_principale.accept()

connection = connexion_avec_client.makefile('wb')
try:
  stream = io.BytesIO()
  camera.capture(stream,'jpeg')
  stream.seek(0)
  connection.write(stream.read())
  stream.seek(0)
  stream.truncate()
finally:
  connection.close()
