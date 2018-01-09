#!/bin/bash

raspistill -bm --nopreview -w 640 -h 480 -q 5 -o /tmp/pic.jpg -tl 300 -t 9999999 &

LD_LIBRARY_PATH=/usr/local/lib mjpg_streamer -i "input_file.so -f /tmp -n pic.jpg" -o "output_http.so -w ./www" &


