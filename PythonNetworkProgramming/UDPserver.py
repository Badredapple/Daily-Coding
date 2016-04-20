#This is UDP server in python
#I will try it under linux

import socket

server = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server.bind(("127.0.0.1", 17800))
print ("UDP server is start !")

while True:
	data.addr = server.recvfrom(1024)
	text = str(data, encoding = "utf-8")
	print("addrss  is : %s data:%s" %(addr.text))
	if text == "quit":
		break;

prinit("UDP server is finish")
server.close()
