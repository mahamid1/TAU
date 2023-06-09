# Declare the assembly flavor to use the intel syntax.
.intel_syntax noprefix

# Define a symbol to be exported from this file.
.global my_function

# Declare symbol type to be a function.
.type my_function, @function

# Code follows below.

my_function:
    # <<<< PUT YOUR CODE HERE >>>>
    # TODO:
    # 1. Read the input to the function from the stack.
    # 2. Save the result in the register EAX (and then return!).
	
	CMP DWORD PTR[ESP+4],0
	JLE base1
	CMP DWORD PTR[ESP+4],1
	JE base2
	MOV ECX,1 #loop counter
	MOV EBX,0 #register to use for calculations
	MOV EDX,1 # register to use for calculations
loop:
	CMP ECX,DWORD PTR[ESP+4] #loop condition : i<=n
	JE end
	MOV DWORD PTR[ESP+8],EDX #saving the edx in the stack 
	IMUL EDX,EDX
	IMUL EBX,EBX
	MOV EAX,EBX
	ADD EAX,EDX
	MOV EBX,DWORD PTR[ESP+8] #getting the edx from the stack 
	MOV EDX,EAX
	INC ECX
	JMP loop
base1:
	MOV EAX,0
	JMP end
base2:
	MOV EAX,1
end:
	RET
