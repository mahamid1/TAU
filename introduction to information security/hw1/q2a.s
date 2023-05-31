# Declare the assembly flavor to use the intel syntax.
.intel_syntax noprefix

# Define a symbol to be exported from this file.
.global my_function

# Declare symbol type to be a function.
.type my_function, @function

# Code follows below.

my_function:
	# <<<< PUT YOUR CODE HERE >>>>
	#FIRST BLOCK : SAVING THE ARGUMENT AND CHECKS ITS VALUE IF ITS 1 OR 0
	#SECOND BLOCK : PUSHING n-2 to THE STACK AND THEN CALCULATING FIB(N-1)
	#THIRD BLOCK: WE USE A LOCAL VARIABLE TO SAVE THE RESULT AND THEN CALCULATING FIB(N-1)
	#4TH BLOCK:CALCULATING SQUARES FOR THE FIB(N-1) AND FIB(N-2)
	PUSH EBP
	MOV EBP,ESP
	SUB ESP,4
	MOV EAX,DWORD PTR [EBP+8]
	CMP EAX,0
	JLE base1
	CMP EAX,1
	JE base2

	SUB EAX,2
	PUSH EAX
	CALL my_function

	
	ADD ESP ,4
	MOV DWORD PTR[ESP],EAX
	MOV EAX ,DWORD PTR[EBP+8] #GETTING N (INPUT) THAT WE STORED IN THE STACK
	SUB EAX,1
	PUSH EAX
	CALL my_function

	
	ADD ESP,4
	MOV EBX,DWORD PTR[ESP]
	IMUL EAX,EAX
	IMUL EBX,EBX
	ADD EAX,EBX
	JMP end
	
	

base1:
	MOV EAX,0
	JMP end
base2:
	MOV EAX,1

end:
	MOV ESP,EBP
	POP EBP
	RET
	
	
    # TODO:
    # 1. Read the input to the function from the stack.
    # 2. Save the result in the register EAX (and then return!).
    # 3. Make sure to include a recursive function call (the recursive function
    #    can be this function, or a helper function defined later in this file).
