import socket
from Crypto.Cipher import AES

KEY=b'From$MohammadQ2!'



def receive_message(port: int) -> str:
    """Receive *encrypted* messages on the given TCP port.

    As Winston sends encrypted messages, re-implement this function so to
    be able to decrypt the messages.

    Notes:
    1. The encryption is based on AES.
    2. Julia and Winston already have a common shared key, just define it on your own.
    3. Mind the padding! AES works in blocks of 16 bytes.
    """
    # TODO: RE-IMPLEMENT THIS FUNCTION
    listener = socket.socket()
    try:
        listener.bind(('', port))
        listener.listen(1)
        connection, address = listener.accept()
        try:
            msg=connection.recv(1024)
            iv=msg[:AES.block_size]
            cipher=AES.new(KEY,AES.MODE_CBC,iv)
            plaintext=cipher.decrypt(msg[AES.block_size:])
            return (plaintext[:-plaintext[-1]).decode("latin-1")
        finally:
            connection.close()
    finally:
        listener.close()


def main():
    # WARNING: DO NOT MODIFY THIS FUNCTION!
    message = receive_message(1984)
    print('received: %s' % message)


if __name__ == '__main__':
    main()
