from infosec.core import assemble


def patch_program_data(program: bytes) -> bytes:
    """
    Implement this function to return the patched program. This program should
    execute lines starting with #!, and print all other lines as-is.

    Use the `assemble` module to translate assembly to bytes. For help, in the
    command line run:

        ipython3 -c 'from infosec.core import assemble; help(assemble)'

    :param data: The bytes of the source program.
    :return: The bytes of the patched program.
    """
    arr=bytearray(program)
    offset1=1587
    offset2=1485
    
    bytes1=assemble.assemble_file("patch1.asm")
    bytearray1=bytearray(bytes1)
    for i in range(len(bytearray1)):
        arr[offset1+i]=bytearray1[i]

    
    bytes2=assemble.assemble_file("patch2.asm")
    bytearray2=bytearray(bytes2)
    for i in range(len(bytearray2)):
        arr[offset2+i]=bytearray2[i]
    return bytes(arr)



def patch_program(path):
    with open(path, 'rb') as reader:
        data = reader.read()
    patched = patch_program_data(data)
    with open(path + '.patched', 'wb') as writer:
        writer.write(patched)


def main(argv):
    if len(argv) != 2:
        print('USAGE: python {} <readfile-program>'.format(argv[0]))
        return -1
    path = argv[1]
    patch_program(path)
    print('done')


if __name__ == '__main__':
    import sys
    sys.exit(main(sys.argv))
