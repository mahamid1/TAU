import socket
import math
from scapy.all import *


SRC_PORT = 65000
MSG=b'I love you'

def send_message(ip: str, port: int):
    """Send a *hidden* message to the given ip + port.

    Julia expects the message to be hidden in the TCP metadata, so re-implement
    this function accordingly.

    Notes:
    1. Use `SRC_PORT` as part of your implementation.
    """
    # TODO: RE-IMPLEMENT THIS FUNCTION
    
    msg_int=int.from_bytes(MSG,"little")
    num=math.ceil(len(MSG)*8/3.0)
    pakcets=[]
    for i in range(num):
        send=msg_int&7
        msg_int=msg_int>>3
        tcp=TCP(dport=port,sport=SRC_PORT,seq=i,ack=num,reserved=send,flags='SA')
        packets.append(IP(dst=ip)/tcp)
    sr(packets)
        


def main():
    # WARNING: DO NOT MODIFY THIS FUNCTION!
    send_message('127.0.0.1', 1984)


if __name__ == '__main__':
    main()
