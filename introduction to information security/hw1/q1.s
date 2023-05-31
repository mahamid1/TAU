# Declare the assembly flavor to use the intel syntax.
.intel_syntax noprefix

# Define a symbol to be exported from this file.
.global my_function

# Declare symbol type to be a function.
.type my_function, @function

# Code follows below.

my_function:
    # This code reads the first argument from the stack into EBX.
    # (If you need, feel free to edit/remove this line).
	MOV EBX, DWORD PTR [ESP + 4]
	

	# <<<< PUT YOUR CODE HERE >>>>
	MOV ECX,1  #loop counter initialize to 1
	CMP EBX,1  #input check (if input<1)	
	JL Fail
	JE one
	MOV EDX,EBX
	MOV EAX,0
	SHR EDX,1 #loop until input/2
	MOV ECX,0
	
loop:
	MOV EAX,ECX
	IMUL EAX,EAX   #(counter) squared
	CMP EAX,EBX
	JE ROOT
	ADD ECX,1
	MOV EAX,ECX
	CMP ECX,EDX
	JLE loop



Fail:
	MOV EAX,0
	JMP finish
one:
	MOV EAX,1
	JMP finish
ROOT:
	MOV EAX,ECX
	

	
    # TODO:
    # 1. Read the input to the function from EBX.
    # 2. Save the result in the register EAX.

    # This returns from the function (call this after saving the result in EAX).
    # (If you need, feel free to edit/remove this line).
finish:
	RET
