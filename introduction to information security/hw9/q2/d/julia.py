import socket
import math
from scapy.add import *


SRC_PORT = 65000
indices=set()
num=0
msg_int=0


def stealth(packet):
    global msg_int
    global num
    if not packet.haslayer(TCP):
        return;
    tcp=packet.getlayer(TCP)
    if tcp.sport!=SRC_PORT or tcp.flags!=18:
        return;
    num=tcp.ack
    indicies.add(tcp.seq)
    msg=tcp.reserved<<3*tcp.seq
    msg_int=msg|msg_int

def stopfilter(packet):
    global num
    return len(indices)!=0 and len(indices)==num


def receive_message(port: int) -> str:
    """Receive *hidden* messages on the given TCP port.

    As Winston sends messages encoded over the TCP metadata, re-implement this
    function so to be able to receive the messages correctly.

    Notes:
    1. Use `SRC_PORT` as part of your implementation.
    """
    # TODO: RE-IMPLEMENT THIS FUNCTION
    global msg_int
    sniff(iface="lo",prn=stealth,stop_filter=stopfilter)
    chars=[]
    for i in range(math.ceil(((num)*3)/8.0)):
        char=chr((msg_int>>(8*i))& 0xff)
        chars.append(char)
    return ''.join(chars)


def main():
    # WARNING: DO NOT MODIFY THIS FUNCTION!
    message = receive_message(1984)
    print('received: %s' % message)


if __name__ == '__main__':
    main()
