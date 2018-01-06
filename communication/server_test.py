import socket
import sys
import time
import os
import signal
import RPi.GPIO as GPIO

def handler(signum, frame):
  print "catch ctrl-z"

def normalize_recognition():
  file = open("answers.txt", "r")
  answer = file.read()
  file.close()

  answer = answer.split("\n")
  first_answer = answer[0]
  possibility, score = first_answer.split("(score = ")
  first_possibility = possibility.split(", ")[0]
  score.replace(')','')
  return first_possibility

signal.signal(signal.SIGTSTP, handler)

print(normalize_recognition())