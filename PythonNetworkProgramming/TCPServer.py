#This is a simple connection to the Tcp server
host = ''  # receive all the connection from any port
port = 51423

#frist create a socket object
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

#second set socket
s.setsockopt(socket.SOL_SOCKET, socket.SO_RESUSEADDR, 1)

#thrid bind a port
s.bind((host,port))

#listening the connection from client
s.listen(5)	# this is means listen 5 ports from client 
