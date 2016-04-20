#This is a UDP client in python under the linux
#I write it for test
import socket

client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
print("UDP client is start!")

while True:
	data = input("Please input some content:")
	client.sendto(bytes(data, "utf-8"), ("127.0.0.1", 17800))
	if data == "quit":
		break

print("UDP client is finish!")
client.close()

