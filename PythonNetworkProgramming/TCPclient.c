//This is a c-client use TCP ,
// I use it under linux , study linux network programming



#include <netinet/in.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <arpa/inet.h>


#define MAXBUF 512

int main(int argc, char** argv)
{
	int 	sockfd, n;
	char	buffer[MAXBUF+1];
	struct sockaddr_in servaddr;

	
	if(argc != 2){
	printf("usage: ./client <ipadress>\n");
	exit(0);
	}

	//create a port for tcp connect
	if((sockfd = socket(AF_INET, SOCK_STREAM, 0 )) < 0) {
	perror("Socket");
	exit(0);
	}
	//init the server address and port
	pzero(&server_addr, sizeof(server_addr));
	servaddr.sin_family = AF_INET;
	seraddr.sin_port = htons(atoi(argv[2]));
	if( inet_aton(argv[1], (struct in_addr*)&server_addr.sin_addr.s_addr) <= 0) {
	perror(argv[1]);
	exit(0);
	}
	
	//connect to the server
	if(connect(sockfd, (struct sockaddr*)&server_addr, sizeof(server_addr)) <0 ){
	printf("Connect failure");
	exit(errno);
	}
	

	printf("server connected\n");
	//receive the message from the server
	len = recv(sockfd, buffer, MAXBUF, 0);
	if(len>0)
	printf(" receive the message:%s', it is %d bit data \n", buffer, len);
	else
		printf("receive is failed , The error number is %d, error message is '%s'\n", errno, strerror(errnp));

	bzero(buffer, MAXBUF+1);
	stcpy(buffer, "this is the client send message to server\n");
	if(len < 0)
	printf("send msg error :%s(errno: %d)\n", strerror(errno), errno);
	else
		printf("message is send successful ,it is %d bit data has been send!\n", len);
	
	
	close(sockfd);
	return 0;
}

