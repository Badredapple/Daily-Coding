#Error Handling Example with shutdown and File like objects 
import socket, sys, time


host = sys.argv[1]
textport = sys.argv[2]
filename = sys.argv[3]

try:
	s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
except socket.error, e:
	print 'Strange error creating socket:%s' % e
	sys.exit(1)

#try parsing it as a numeric port number

try:
	port = int(textport)
except ValueError:
	#that didn't work ,look it up instread
	try:
		port = socket.getservbyname(textport, 'tcp')
	except socket.error, e:
		print 'could not find your port:%s' % e
		sys.exit(1)
try:
	s.connect((host,port))
except	socket.geterror, e:
	print 'Address-related error connecting to server: %s' % e
	sys.exit(1)

fd = s.makefile('rw', 0)

print 'sleeping....'
time.sleep(10)
print 'continuing.'

try:
	fd.write('Get %s Http/1.0\r\n\r\n' % filename)
except socket.error, e:
	print 'Error sending data :%s' %e
	sys.exit(1)

try:
	fd.flush()
except socket.error, e:
	print 'Error sending data (detected by flush): %s' %e
	sys.exit(1)

try:
	s.shutdown(1)
except socket.error, e:
	print 'Error sending data (detected by shutdown ):%s' %e
	sys.exit(1)

while 1:
	try:
		buf = fd.read(2048)
	except socket.error, e:
		print 'error receiving data: %s' % e
		sys.exit(1)
	if not len(buf):
		break
	sys.stdout.write(buf)
