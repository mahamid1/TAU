def check_message(path: str) -> bool:
    """
    Return True if `msgcheck` would return 0 for the file at the specified path,
    return False otherwise.
    :param path: The file path.
    :return: True or False.
    """
    with open(path, 'rb') as reader:
        # Read data from the file, do whatever magic you need
        arr=[]
        res = 175
        for line in reader:
            for ch in line:
                arr.append(ch)
        for i in range(arr[0]):
            res=arr[i+2]^res
        return res==arr[1]


def main(argv):
    if len(argv) != 2:
        print('USAGE: python {} <msg-file>'.format(argv[0]))
        return -1
    path = argv[1]
    if check_message(path):
        print('valid message')
        return 0
    else:
        print('invalid message')
        return 1


if __name__ == '__main__':
    import sys
    sys.exit(main(sys.argv))
