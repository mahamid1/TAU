from scapy.all import *


def on_packet(packet):
    """Implement this to send a SYN ACK packet for every SYN.

    Notes:
    1. Use *ONLY* the `send` function from scapy to send the packet!
    """
    # TODO: IMPLEMENT THIS FUNCTION
    if not packet.haslayer(TCP):
        return;
    if not packeet.getlayer(TCP).flags==2:
        return;
    iplayer=packet.getlayer(IP)
    tcplayer=packet.getlayer(TCP)
    ip=IP(dst=iplayer.src,src=iplayer.dst)
    tcp=TCP(dport=tcplayer.sport,sport=tcplayer.dport,flags='SA',ack=tcplayer.seq+1)
    send(ip/tcp)


def main(argv):
    # WARNING: DO NOT MODIFY THIS FUNCTION!
    sniff(prn=on_packet)


if __name__ == '__main__':
    # WARNING: DO NOT MODIFY THIS FUNCTION!
    import sys
    sys.exit(main(sys.argv))
