import socket
from Crypto.Cipher import AES
from Crypto import Random
from cryptography.hazmat.primitives import padding

KEY=b'From$MohammadQ2!'


def send_message(ip: str, port: int):
    """Send an *encrypted* message to the given ip + port.

    Julia expects the message to be encrypted, so re-implement this function accordingly.

    Notes:
    1. The encryption is based on AES.
    2. Julia and Winston already have a common shared key, just define it on your own.
    3. Mind the padding! AES works in blocks of 16 bytes.
    """
    # TODO: RE-IMPLEMENT THIS FUNCTION
    connection = socket.socket()
    padder=padding.PKCS7(128).padder()
    padded=padder.update(b'I love you')
    padded+=padder.finalize()
    random=Random.new().read(AES.block_size)
    payload=AES.new(KEY,AES.MODE_CBC,iv).encrypt(padded)
    try:
        connection.connect((ip, port))
        connection.send(random+payload)
    finally:
        connection.close()


def main():
    # WARNING: DO NOT MODIFY THIS FUNCTION!
    send_message('127.0.0.1', 1984)


if __name__ == '__main__':
    main()
