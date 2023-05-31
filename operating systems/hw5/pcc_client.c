#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>
#include <arpa/inet.h>



#define MAXBUFFER 1000000
int sockId;

int SockWrite(char *buffer,size_t totalBytes);
int SocKSendFile( int filedescriptor);
int SockRecieve(char *buffer, size_t NumBytes);
uint32_t filesize(char *fp);

int main(int argc, char *argv[]) {

    char *serverIP,*path_file_send;
    int serverPort,filedescriptor;
    struct sockaddr_in server_addr;
    uint32_t fileSizeNBO,printableCharCount;

    if(argc != 4){
        fprintf(stderr, "Argument Count:%s", "Invalid Number of arguments passed!\n");
        exit(1);
    }


    serverIP = argv[1];
    serverPort = atoi(argv[2]);
    path_file_send = argv[3];


    filedescriptor = open(path_file_send, O_RDONLY);
    if (filedescriptor < 0) {
        fprintf(stderr, "Open File:%s\n", strerror(errno));
        exit(1);
    }

    fileSizeNBO=filesize(path_file_send);



    if ((sockId = socket(AF_INET, SOCK_STREAM, 0))<= 0) {
        fprintf(stderr, "Open Socket:%s\n", strerror(errno));
        exit(1);
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(serverPort);

    if (inet_pton(AF_INET, serverIP, &server_addr.sin_addr)< 0) {
        fprintf(stderr, "Inet_pton:%s\n", strerror(errno));
        exit(1);
    }

    if (connect(sockId, (struct sockaddr*)&server_addr, sizeof(server_addr)) < 0) {
        fprintf(stderr, "Connect:%s\n", strerror(errno));
        exit(1);
    }


    if (SockWrite((char *)(&fileSizeNBO), sizeof(fileSizeNBO)) < 0) {
        exit(1);
    }


    if (SocKSendFile( filedescriptor) < 0) {
        exit(1);
    }

    if (SockRecieve((char *)&printableCharCount, sizeof(printableCharCount)) < 0) {
        exit(1);
    }
    uint32_t printableCharHost = be32toh(printableCharCount);

    printf("# of printable characters: %lu\n", printableCharHost);


    close(filedescriptor);
    close(sockId);

}

uint32_t filesize(char *fp){
    struct stat st;
    uint32_t sz,size;
    stat(fp, &st);
    sz = (uint32_t)st.st_size;
    size = htobe32(sz);
    return size;
}

int SocKSendFile(int filedescriptor)
{

    char buffer[MAXBUFFER];
    int res;

    while ((res = read(filedescriptor, (char *)&buffer, MAXBUFFER)) != 0) {
        if (res < 0) {
            fprintf(stderr, "Read ERROR:%s\n", strerror(errno));
            return -1;
        }

        if (SockWrite((char *)&buffer, res) < 0) {
            return -1;
        }
    }
    return 0;
}

int SockWrite(char *buffer,size_t totalBytes) {
    int totalByteSent=0;
    int bytesWritten=0;

    while (totalByteSent < totalBytes) {
        bytesWritten = send(sockId, &buffer[totalByteSent], totalBytes - totalByteSent, 0);
        if (bytesWritten >= 0) {	/* Send successful */
            totalByteSent += bytesWritten;
            if(totalByteSent==totalBytes)
            {
                return totalByteSent;
            }
        }
        else {

            fprintf(stderr, "SEND ERROR:%s\n", strerror(errno));
            return -1;
        }
    }
    return 0;
}

int SockRecieve(char *buffer, size_t NumBytes) {
    int totalReceived=0;
    int res=0;
    while (totalReceived < NumBytes) {
        res= recv(sockId, &buffer[totalReceived], NumBytes- totalReceived, 0);
        if (res > 0) {
            totalReceived += res;
            if(totalReceived == NumBytes){
                return totalReceived;
            }
        }
        else {
            fprintf(stderr, "SockRecieve:%s\n", strerror(errno));
            return -1;
        }
    }
    return totalReceived;
}
