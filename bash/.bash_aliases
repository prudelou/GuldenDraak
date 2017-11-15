#!/bin/bash

# Function use to launch TensorFlow Image recognition
function recognizeImage {
if [ $# -eq 0 ]
then
	python ~/ImageRecognition/models/tutorials/image/imagenet/classify_image.py;
else
	python ~/ImageRecognition/models/tutorials/image/imagenet/classify_image.py --image_file="$1";
fi
}
