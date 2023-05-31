#define _GNU_SOURCE
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include <unistd.h>
#include <wait.h>
#include <fcntl.h>
#include <signal.h>

int register_signal_handling() {
    struct sigaction new_action;
    memset(&new_action, 0, sizeof(new_action));

    new_action.sa_handler = SIG_DFL;

    // Overwrite default behavior for ctrl+c
    return sigaction(SIGINT, &new_action, 0);
}

int register_sig_chld_handler(){
    struct sigaction new_action;
    memset(&new_action,0,sizeof(new_action));
    new_action.sa_sigaction =NULL;
    return sigaction(SIGCHLD,&new_action,0);
}
int register_sig_ign_handler(){
    struct sigaction new_action;
    memset(&new_action,0,sizeof(new_action));
    new_action.sa_sigaction = (void *) SIG_IGN;
    return sigaction(SIGINT,&new_action,0);
}

int find(char c, char** arglist){
    int i ;
    i=0;
    if(c=='|') {
        while (arglist[i]!=NULL){
            if ((arglist[i] != NULL) && (strcmp(arglist[i], "|") == 0)) {
                return i;
            }
            i++;
        }
    }
    else{
        while(arglist[i]!=NULL) {
            if ( (strcmp(arglist[i], ">") == 0)) {
                return i;
            }
            i++;
        }
    }
    return 0;
}
int process_arglist(int count, char** arglist) { /*arglist size is count+1*/

    if (strcmp(arglist[count - 1] , "&")==0) {/*executing command in the background*/
        arglist[count-1]=NULL;
        int pid ;
        pid = fork();
        if (pid == -1) {
            perror("Failed forking");
            exit(1);
        }
        else if (pid == 0) {
            if (execvp(arglist[0],arglist) < 0) {
                perror("Error executing");
                exit(1);
            }
        }
        else {
            signal(SIGCHLD,SIG_IGN);
        }
        return 1;
    }

    else if (find('>',arglist)) {

        int index,pid,fd;
        index = find('>',arglist);
        arglist[index]=NULL;
        char** righttoarrow;
        righttoarrow= arglist+index+1;

        pid = fork();
        if(pid ==-1) {
            perror("failed forking");
            exit(1);
        }
        else if (pid==0){
            if (register_signal_handling() == -1) {
                perror("Failed registering handler");
                exit(1);
            }
            fd = open(*righttoarrow,O_WRONLY | O_CREAT,0777);
            if(fd==-1) {
                perror("failed opening file ");
                exit(1);
            }
            if(dup2(fd,STDOUT_FILENO)==-1){
                perror("Failed in dup2");
                exit(1);
            }
            close(fd);

            if(execvp(arglist[0],arglist)==-1){
                perror("failed executing");
                exit(1);
            }
        }
        signal(SIGCHLD,SIG_IGN);
        return 1;
    }


    else if (find('|',arglist)) {

        int index, pid1, pid2, fd[2];
        index = find('|', arglist);
        arglist[index] = NULL;
        char **righttopipe;
        righttopipe = arglist + index + 1;

        if (pipe(fd) == -1) {
            perror("failed piping");
            exit(1);
        }

        pid1 = fork();
        if (pid1 == -1) {
            perror("failed forking");
            exit(1);
        } else if (pid1 == 0) {
            //child process 1
            if (register_signal_handling() == -1) {
                perror("error in sigint handler");
                exit(1);
            }
            while ((dup2(fd[1], STDOUT_FILENO) == -1) && (errno == EINTR)) {}
            close(fd[1]);
            close(fd[0]);
            if (execvp(*arglist, arglist) < 0) {
                perror("* ERROR: exec failed");
                exit(1);
            }
        } else {
            pid2 = fork();
            if (pid2 == -1) {
                perror("Failed forking pid2");
                exit(1);
            }
            if (pid2 == 0) {
                if (register_signal_handling() == -1) {
                    perror("Failed registering handler");
                    exit(1);
                }
                while ((dup2(fd[0], STDIN_FILENO) == -1 && (errno == EINTR))) {}
                close(fd[0]);
                close(fd[1]);
                if (execvp(*righttopipe, righttopipe) < 0) {
                    perror("* ERROR: exec failed");
                    exit(1);
                }
            }

            close(fd[1]);//closing parent's fds.
            close(fd[0]);
            waitpid(pid1, NULL, 0);
            waitpid(pid2, NULL, 0);
        }
        return 1;
    }

    else {
        int pid;
        pid = fork();
        if (pid<0){
            perror("error forking");
            exit(1);
        }
        else if (pid == 0 ){
            if(register_signal_handling()==-1) exit(1);
            if(execvp(*arglist,arglist)==-1){
                perror("failed executing");
                exit(1);
            }
        }
        else waitpid(pid,NULL,0);
        return 1;
    }
}

int prepare(void){
    if(register_sig_ign_handler()==-1) {
        perror("Error in sigint handler");
        exit(1);
    }
    if(register_sig_chld_handler()==-1) {
        perror("Error in sigchld handler");
        exit(1);
    }
    return 0;
}

int finalize(void){
    return 0;
}