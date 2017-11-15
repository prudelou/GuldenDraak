#!/bin/bash

#GPIO 17 27 22
#Moteur avant gauche
gpio mode 0 out
gpio mode 2 out
gpio mode 3 out

#GPIO 10 9 11 
#Moteur arrière gauche
gpio mode 12 out
gpio mode 13 out
gpio mode 14 out

#GPIO 16 20 21
#Moteur arrière droit
gpio mode 27 out
gpio mode 28 out
gpio mode 29 out

#GPIO 25 8 7
#Moteur avant droit
gpio mode 6 out
gpio mode 10 out
gpio mode 11 out
