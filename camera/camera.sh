#!/bin/bash

raspivid -t 0 -w 1280 -h 720 -o - | nc 192.168.43.156 5001