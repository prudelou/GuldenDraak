pour réaliser un stream basic avec pc linux il faut:

	-instaler mplayer sur le pc linux

	-lancer la commande suivante sur le pc linux
		nc -l -p 5001 | /usr/bin/mplayer -fps 60 -cache 1024 -

	-lancer la commande suivante sur la pi
		raspivid -t 0 -w 1280 -h 720 -o - | nc 192.168.43.156 5001


Ne sera probablement pas utilisé avec android


Nouvelle solution 

capture 1 image tout les 300ms (-tl), -bm (burstmode), -q (qualité /100)
raspistill -bm --nopreview -w 640 -h 480 -q 5 -o /tmp/stream/pic.jpg -tl 300 -t 9999999
