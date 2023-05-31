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
#include <stdatomic.h>
#include <signal.h>


#define MAXBUFFER 1000000
#define START_RANGE 32
#define END_RANGE 126

uint32_t pcc_total[END_RANGE-START_RANGE+1];
uint32_t temp_pcc_total[END_RANGE-START_RANGE+1];


void InitializeTempPcc();
void UpdatePCC();
void pcc_data_show();
void sigint_handler();
int SocketRecieve(char *buff, size_t count);
int SockSend(char *buffer, size_t BytestoSend);

int clientAccepted;
int serverBusy;
int conn_sock;
atomic_int sigInt_rxd;

int main(int argc, char *argv[]) {


    if(argc != 2){
        fprintf(stderr, "Argument Count:%s", "Invalid Number of arguments passed!\n");
        exit(1);
    }
    clientAccepted=0;
    serverBusy=0;
    sigInt_rxd=0;
    int mysockfd;
    int myport;
    struct sockaddr_in myaddr;


    myport = atoi(argv[1]);

    struct sigaction act;
    act.sa_handler=&sigint_handler;
    act.sa_flags=SA_RESTART;
    sigfillset(&act.sa_mask);


    if (sigaction(SIGINT, &act, NULL) == -1) {
        perror(strerror(errno));
        exit(1);
    }



    if ((mysockfd = socket(AF_INET, SOCK_STREAM, 0))<= 0) {
        fprintf(stderr, "Open Socket:%s\n", strerror(errno));
        exit(1);
    }


    int optval = 1;
    if ((setsockopt(mysockfd,SOL_SOCKET,SO_REUSEADDR,&optval,sizeof(int))) == -1)
    {
        close(mysockfd);
        fprintf(stderr, "setsockopt:%s\n", strerror(errno));
        exit(1);
    }
    /* filling address for socket binding */
    myaddr.sin_family = AF_INET;
    myaddr.sin_port = htons(myport);
    myaddr.sin_addr.s_addr = htonl(INADDR_ANY);

    if (bind(mysockfd, (struct sockaddr*)&myaddr, sizeof(myaddr)) != 0) {
        fprintf(stderr, "Server Bind Socket:%s\n", strerror(errno));
        exit(1);
    }


    if (listen(mysockfd, 10) != 0) {
        fprintf(stderr, "Server Listen Socket:%s\n", strerror(errno));
        exit(1);
    }


    while (1) {


        if(sigInt_rxd==0){
            conn_sock=0;
            conn_sock = accept(mysockfd, NULL, NULL);
            if (conn_sock < 0) {
                fprintf(stderr, "Server Accept Socket:%s\n", strerror(errno));
                exit(1);
            }
            clientAccepted=1;
            serverBusy=1;
            uint32_t fileSizeNBO,fileSizeHBO;
            if (SocketRecieve((char *)&fileSizeNBO, sizeof(fileSizeNBO)) < 0) {
                fprintf(stderr, "Socket Read Error:%s\n", strerror(errno));
            }
            fileSizeHBO = be64toh(fileSizeNBO);


            InitializeTempPcc();
            char buffer[MAXBUFFER];

            int bufferSize;
            int num_printable_char = 0;

            while (fileSizeHBO > 0) {

                if (fileSizeHBO > MAXBUFFER) {
                    bufferSize = MAXBUFFER;
                }
                else {
                    bufferSize = fileSizeHBO;
                }

                int rxdBytesCount = SocketRecieve((char *)&buffer, bufferSize);		/* Receive data */
                if (rxdBytesCount < 0) {
                    return -1;
                }

                for (int i = 0; i < rxdBytesCount; ++i) {
                    if (buffer[i] >= START_RANGE && buffer[i] <= END_RANGE) {
                        buffer[i] -= START_RANGE;

                        temp_pcc_total[(uint32_t)buffer[i]]++;
                        num_printable_char++;
                    }
                }
                fileSizeHBO = fileSizeHBO-rxdBytesCount;
            }

            if (num_printable_char == 0) {
                fprintf(stderr, "Invalid num_printable:%s\n", strerror(errno));
            }
            uint32_t num_printable = htobe64(num_printable_char);

            /* send number of printable chars */
            if (SockSend((char *)&num_printable, sizeof(num_printable)) < 0) {
                fprintf(stderr, "Write Socket Error:%s\n", strerror(errno));
            }else{

                UpdatePCC();
            }

            if(sigInt_rxd==1){
                pcc_data_show();
                exit(0);
            }
            serverBusy=0;

            close(conn_sock);
        }
    }
}

void InitializeTempPcc(){
    for (int i = 0; i < END_RANGE-START_RANGE+1; ++i) {
        temp_pcc_total[i] = 0;
    }
}
void UpdatePCC(){
    for (int i = 0; i < END_RANGE-START_RANGE+1; ++i) {
        pcc_total[i] += temp_pcc_total[i];
    }
}

void pcc_data_show() {
    int i;

    for (i = 0; i < END_RANGE-START_RANGE+1; ++i) {
        char c = (char)(i + START_RANGE);
        printf("char '%c' : %lu times\n",c,pcc_total[i]);
    }
}
int SocketRecieve(char *buffer, size_t NumBytestoRead) {
    long TotalRxdBytes=0;

    while (TotalRxdBytes < NumBytestoRead) {
        long res= recv(conn_sock, &buffer[TotalRxdBytes], NumBytestoRead - TotalRxdBytes, 0);
        if ((res > 0)||(res==0)) {
            TotalRxdBytes += res;
            if(TotalRxdBytes == NumBytestoRead){
                return TotalRxdBytes;
            }
        }
        else {
            fprintf(stderr, "Server Read Socket:%s\n", strerror(errno));
            return -1;
        }
    }
    return TotalRxdBytes;
}
int SockSend(char *buffer, size_t BytestoSend) {
    int totalSend=0;

    while (totalSend < BytestoSend) {
        int res = send(conn_sock, &buffer[totalSend], BytestoSend - totalSend, 0);
        if ((res > 0)||(res==0)) {
            totalSend += res;
            if(totalSend==BytestoSend){
                return totalSend;
            }
        }
        else {
            fprintf(stderr, "Server Write:%s\n", strerror(errno));
            return -1;
        }
    }
    return 0;
}
void sigint_handler() {

    if(clientAccepted==0){
        pcc_data_show();
    }
    else{
        sigInt_rxd=1;
    }
    exit(0);
}
