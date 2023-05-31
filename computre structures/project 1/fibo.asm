.data

inMsg: .asciiz "Enter a number: "
msg: .asciiz "Calculating F(n) for n = "
fibNum: .asciiz "\nF(n) is: "
.text

main:

	li $v0, 4
	la $a0, inMsg
	syscall

	# take input from user
	li $v0, 5
	syscall
	addi $a0, $v0, 0
	
	jal print_and_run
	
	# exit
	li $v0, 10
	syscall

print_and_run:
	addi $sp, $sp, -4
	sw $ra, ($sp)
	
	add $t0, $a0, $0 

	# print message
	li $v0, 4
	la $a0, msg
	syscall

	# take input and print to screen
	add $a0, $t0, $0
	li $v0, 1
	syscall

	jal fib

	addi $a1, $v0, 0
	li $v0, 4
	la $a0, fibNum
	syscall

	li $v0, 1
	addi $a0, $a1, 0
	syscall
	
	lw $ra, ($sp)
	addi $sp, $sp, 4
	jr $ra

#################################################
#         DO NOT MODIFY ABOVE THIS LINE         #
#################################################	
	
fib: 
	bgt $a0 1 rec
	move $v0 $a0
	jr $ra
rec:
	sub $sp $sp 12
	sw $ra 0($sp)   # we save the ra 
	sw $a0 4($sp)  # save n 
	add $a0 $a0 -1 #go to n-1
	jal fib # recurse on n-1
	lw $a0 4($sp)	#load n
	sw $v0 8($sp) # save the value of fib(n-1)
	add $a0 $a0 -2 # n-2
	jal fib #do fib on n-2
	lw $t0 8($sp)	#fib(n-1)
	add $v0 $t0 $v0	# fib(n-1) + fib(n-2)
	lw $ra 0($sp)	
	add $sp $sp 12
	jr $ra 
