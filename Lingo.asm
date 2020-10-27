# Lingo Game					J+M+J

.data

word1:		.asciiz	"SALVE"
word2:		.asciiz	"PATER"
word3:		.asciiz	"PIETA"
word4:		.asciiz	"NOMEN"
word5:		.asciiz	"SICUT"
word6:		.asciiz	"NOBIS"
word7:		.asciiz	"FILII"
word8:		.asciiz	"TERRA"
word9:		.asciiz	"MISSE"
word10:		.asciiz	"PANEM"
word_list:	.word	word1, word2, word3, word4, word5, word6, word7, word8, word9, word10
wlcm:		.asciiz	"Welcome to Lingo!"
input_str:	.space	6
already_seen:	.space	6
nw_ln:		.asciiz	"\n"
u_score:	.asciiz " _ _ _ _"
colon:		.asciiz	": "
the_word:	.asciiz	"The word to guess is: "
correct:	.asciiz	" is in the right place"
almost:		.asciiz	" is in the word, but not the right place"
guess:		.asciiz	"Enter guess number "
win:		.asciiz	"You win!"
lose:		.asciiz "You lose!"
again:		.asciiz	"Would you like to play again? "
go_again_upper:	.asciiz	"Y"
go_again_lower:	.asciiz	"y"
first_rando_Word_index:	.space	1
hold2:		.space	1
hold3:		.space	1
hold4:		.space	1

.text
# HERE BEGINS THE LOOPING PROCESS OF WHOLE GAME
GAME:
##############################################################################
# seed the random number generator
##############################################################################

# get the time
li	$v0, 30		# get time in milliseconds (as a 64-bit value)
syscall

move	$t0, $a0	# save the lower 32-bits of time

# seed the random generator (just once)
li	$a0, 1		# random generator id (will be used later)
move 	$a1, $t0	# seed from time
li	$v0, 40		# seed random number generator syscall
syscall

#############################################################################
# Choose random word and get its first letter.
#############################################################################

li	$a0, 1		# as said, this id is the same as random generator id
li	$a1, 6		# upper bound of the range
li	$v0, 42		# random int range
syscall

move 	$t2, $a0	# move random num to register t2

la 	$t3, word_list	# Load base address of word array
sll 	$t2, $t2, 2	
add 	$t1, $t2, $t3
lw 	$s1, 0($t1)	# Pull word from random index of word array and put into register s1

lb 	$t6, 0($s1)	# Pull first letter of that word and store in register t6

#############################################################################
# Print Intro
#############################################################################

la 	$a0, wlcm 
li 	$v0, 4		# Print welcome
syscall

la 	$a0, nw_ln 	# Print newline
li 	$v0, 4
syscall

la 	$a0, the_word 	
li 	$v0, 4		# Print prompt
syscall
move 	$a0, $t6 
li 	$v0, 11		# Print first letter of chosen word
syscall	
la 	$a0, u_score 
li 	$v0, 4		# Print underscores
syscall

la	$a0, nw_ln 
li 	$v0, 4		# Print newline
syscall

#############################################################################
# Main loop of game
#############################################################################

li 	$s2, 1		# Keeps count of number of guesses so far

GUESSES:

la 	$a0, nw_ln 
li 	$v0, 4		# Print newline
syscall

la 	$a0, guess 		
li 	$v0, 4		# Print prompt
syscall
move 	$a0, $s2 		
li	$v0, 1		# Print guess number
syscall
la 	$a0, colon 
li 	$v0, 4		# Print colon
syscall

li 	$v0, 8		# Read user input
la	$a0, input_str	# Loads user byte space
syscall

la 	$a0, nw_ln 
li 	$v0, 4		# Print newline
syscall

jal 	CAPS		# Use a function to capitalize user input

jal	CHECK_FOR_WIN

jal 	COMPLETS	# Use a function to COMPare the LETterS of the input and random words.

addi 	$s2, $s2, 1	# Increment guess counter
bne 	$s2, 6, GUESSES	# If guesses are not yet at 6, keep loop going

# PLAYER HAS REACHED MAX ATTEMPTS AND LOST
la 	$a0, nw_ln 
li 	$v0, 4		# Print newline
syscall
la 	$a0, lose 
li 	$v0, 4		# Print lose label
syscall

#############################################################################
# Ask the user if they want to play again, then loop or end game depending
# on that input.
#############################################################################

EXIT:
la 	$a0, nw_ln 
li 	$v0, 4		# Print newline
syscall
la 	$a0, again 
li 	$v0, 4		# Print again label
syscall
li 	$v0, 8		# Read user input
la	$a0, input_str	# Loads user byte space
syscall

la	$t0, input_str		# Prep for the comparison below
la	$t1, go_again_upper
la	$t2, go_again_lower
lb	$t0, 0($t0)
lb	$t1, 0($t1)
lb	$t2, 0($t2)

beq	$t0, $t1, GAME		# If the user enters an uppercase Y, restart game
beq	$t0, $t2, GAME		# If user enters a lowercase y, restart game

li	$v0, 10			# exit syscall
syscall


#############################################################################
# Convert all lower case letters to upper.
#############################################################################

CAPS:

la	$t1, input_str	# Current index of the Uer's word
la	$t3, input_str	# Current index of the Uer's word

CAPSLOOP:
lb	$t2, 0($t1)
beq 	$t2, 0, ENDCAPS		# If null pointer in input is reached, return
sub 	$t2, $t2, 32
sb 	$t2, 0($t1)
addi	$t1, $t1, 1
j 	CAPSLOOP

ENDCAPS:
jr 	$ra

#############################################################################
# Checks if the user guessed the correct word.
#############################################################################

CHECK_FOR_WIN:

li 	$t0, 0			# Keeps count of current index of random word
la 	$t1, input_str		# Keeps count of current index of user's input

CHECK_WIN_LOOP:
sll 	$t4, $t0, 2		# Generate offest to get current letter of the random word	
add 	$t2, $t0, $s1
lb 	$t2, 0($t2)		# Get letter in current index of random word

lb 	$t3, 0($t1)		# Get letter in current index of user's input

beq 	$t2, $zero, END_WITH_WIN # If the null character at the end of the random number is reached, return

addi	$t0, $t0, 1
addi 	$t1, $t1, 1
beq 	$t2, $t3, CHECK_WIN_LOOP	# If the two characters are equal, continue loop

jr	$ra			# If a pair of letters don't match, return

END_WITH_WIN:
la 	$a0, nw_ln 
li 	$v0, 4		# Print newline
syscall
la 	$a0, win 
li 	$v0, 4		# Print win label
syscall
j	EXIT

##############################################################################
# Compare the letter locations of the random and user input words.
#############################################################################

COMPLETS:

li 	$t0, 0			# Keeps count of current index of random word
la 	$t1, input_str		# Keeps count of current index of user's input

COMPLETSLOOP:

sll 	$t4, $t0, 2		# Generate offest to get current letter of the random word	
add 	$t2, $t0, $s1
lb 	$t2, 0($t2)		# Get letter in current index of random word

lb 	$t3, 0($t1)		# Get letter in current index of user's input

beq 	$t2, $zero, ENDCOMP	# If the null character at the end of the random number is reached, return
beq 	$t2, $t3, CORRECTPLACE	# If the two characters are equal, go to label
bne	$t2, $t3, CHECK_IF_IN	# If the two characters are NOT equal, go to label

addi 	$t0, $t0, 1		# Increment random word's index
addi	$t1, $t1, 1		# Increment user's word's index
j	COMPLETSLOOP		# Continue loop

####################################################################################.
# Prints that the two characters from the current COMLETS loop are in the same index
####################################################################################

CORRECTPLACE:
move 	$a0, $t3 
li 	$v0, 11			# Print current letter of user's input
syscall
la 	$a0, correct 
li 	$v0, 4			# Print string in "orrect" label
syscall
la 	$a0, nw_ln 
li 	$v0, 4			# Print new line character
syscall
addi 	$t0, $t0, 1		# Increment random word's index
addi	$t1, $t1, 1		# Increment user's word's index
j	COMPLETSLOOP

####################################################################################
# Prints that the current character from user's input is in the random word and not 
# the right index, or nothing at all if the character is not in the random word.
####################################################################################

CHECK_IF_IN:
li 	$t5, 0			# Begining index of the random word again

CHECK_IF_IN_LOOP:
sll 	$t7, $t5, 2		# Generate offest to get current letter of the random word	
add 	$t8, $t5, $s1
lb 	$t8, 0($t8)		# Get letter in current index of random word

beq 	$t8, $zero, END_CHECK_IF_IN # If the null character at the end of the random number is reached, end loop

beq 	$t8, $t3, PRINT_CHECK	# If equal go to label
addi	$t5, $t5, 1		# Increment index of random word

j	CHECK_IF_IN_LOOP

####################################################################################
# Actually prints that the current user's char is in the word, but not the right 
# place, and returns to COMPLETS.
####################################################################################

PRINT_CHECK:
move 	$a0, $t3 
li 	$v0, 11			# Print current letter of user's input
syscall
la 	$a0, almost 
li 	$v0, 4			# Print string in "almost" label
syscall
la 	$a0, nw_ln 
li 	$v0, 4			# Print new line character
syscall
j	END_CHECK_IF_IN

####################################################################################
# Increments the indexes of the words in COMPLETS and jumps back to COMPLETS.
####################################################################################

END_CHECK_IF_IN:
addi 	$t0, $t0, 1		# Increment random word's index
addi	$t1, $t1, 1		# Increment user's word's index
j	COMPLETSLOOP

# END OF COMPLETS FUNCTION
ENDCOMP:
jr 	$ra 



