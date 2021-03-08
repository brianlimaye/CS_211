/*
 *Brian Limaye, G01260841
 *CS 262, Lab Section 212
 *Project 1
*/

#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>

#define NUM_GOOSE_SPACES 3
#define NUM_BRIDGE_SPACES 1
#define NUM_MAZE_SPACES 2
#define NUM_SKULL_SPACES 1
const int gooseSpaces[NUM_GOOSE_SPACES] = {7, 11, 15};
const int bridge_spaces[NUM_BRIDGE_SPACES] = {6};
const int maze_spaces[NUM_MAZE_SPACES] = {13, 20};
const int skull_spaces[NUM_SKULL_SPACES] = {23};


/*
 *Function Protoypes that are used throughout the program.
 */
int roll_Dice(void);
void print_Board(int playerPos1, int playerPos2);
int is_goose_space(int space);
int is_bridge_space(int space);
int is_maze_space(int space);
int is_skull_space(int space);
int interpret_position(int old_space, int roll);
int perform_turn(int old_space, char * name);
int update_position(int old_space, int roll);
int fill_main_board(char * board, int player_pos_1, int player_pos_2, int pos, int i);
int fill_last_slot(char * board, int player_pos_1, int player_pos_2, int pos, int i);
int read_seed(char * buffer);
int read_char(char * buffer);
int roll_first(char * buffer, char input);
int determine_first(int r1, int r2);
void start_game(int user_position, int comp_position, int user_starts);
void prompt_replay(char * buffer);

int is_goose_space(int space) {

	int i;

	for(i = 0; i< NUM_GOOSE_SPACES; i++) {
		/*
		 *Checks if the given space is a "Goose" space, or in the corresponding global array.
		 */
		if(gooseSpaces[i] == space) {

			return 1;
		}
	}

	return 0;
}

int is_bridge_space(int space) {

	int i;

	for(i = 0; i< NUM_BRIDGE_SPACES; i++) {
		/*
		 *Checks if the given space is a "Bridge" space, or in the corresponding global array.
		 */
		if(bridge_spaces[i] == space) {

			return 1;
		}
	}

	return 0;
}

int is_maze_space(int space) {

	int i;

	for(i = 0; i< NUM_MAZE_SPACES; i++) {
		/*
		 *Checks if the given space is a "Maze" space, or in the corresponding global array.
		 */
		if(maze_spaces[i] == space) {

			return 1;
		}
	}

	return 0;
}

int is_skull_space(int space) {

	int i;

	for(i = 0; i< NUM_SKULL_SPACES; i++) {
		/*
		 *Checks if the given space is a "Skull" space, or in the corresponding global array.
		 */
		if(skull_spaces[i] == space) {

			return 1;
		}
	}

	return 0;
}

int update_position(int old_space, int roll) {
	
	int is_clear;
	int current_space = old_space;

	/*
	 *Loop to update position based on whether a goose, bridge, maze, or skull is landed on.
	 */
	do {
		is_clear = 1;

		if(is_goose_space(current_space) == 1) {
			current_space += roll;
			printf("-go to space %d ", current_space);
			/*
			 *Sets the is_clear flag to repeat the loop in the case where another "special" space is landed on.
			 */
			is_clear = 0; 
		}
		else if(is_bridge_space(current_space) == 1) {
			current_space = 12;
			printf("-go to space %d ", current_space);
			/*
			 *Sets the is_clear flag to repeat the loop in the case where another "special" space is landed on.
			 */
			is_clear = 0;
		}
		else if(is_maze_space(current_space) == 1) {
			current_space--;
			printf("-go to space %d ", current_space);
			/*
			 *Sets the is_clear flag to repeat the loop in the case where another "special" space is landed on.
			 */
			is_clear = 0;
		}

		else if(is_skull_space(current_space) == 1) {
			current_space = 1;
			printf("-go to space %d ", current_space);
			/*
			 *Sets the is_clear flag to repeat the loop in the case where another "special" space is landed on.
			 */
			is_clear = 0;
		}
	}
	while(is_clear == 0);

	return current_space;
}

int interpret_position(int old_space, int roll) {

    int current_space = old_space;

    current_space += roll;

    if(current_space > 24) {

    	/*
    	 *Adjusts the position of a given user in the case where they over-roll above 24 (or the max space).
    	 */
    	current_space = 24 - (current_space - 24);
    }

	printf("-go to space %d ", current_space);

	/*
	 *Function to adjust the position in the case where a special space is landed on...
	 */
	current_space = update_position(current_space, roll);

	return current_space;
}

int perform_turn(int old_space, char * player) {

	char buffer[10];
	int roll;
	int position;

	do {
		/*
		 *Reads in input from the user while handling input that does not include the newline/enter/carriage return character.
		 */
		printf("%s PLAYER'S TURN, [%d]... Press <enter> to roll the dice ", player, old_space);
		fgets(buffer, 10, stdin);
	}
	while(buffer[0] != '\n');

	/*
	 *Rolls the dice for a given user...
	 */
	roll = roll_Dice();
	position = interpret_position(old_space, roll);

	return position;
}

int roll_Dice(void) {

	/*
	 *Generates two random integers between 1-6 for a given user.
	 */
	int roll_1 = (rand() % 6) + 1;
	int roll_2 = (rand() % 6) + 1;

	printf("%d and %d for %d\n", roll_1, roll_2, roll_1 + roll_2);

	return roll_1 + roll_2;
}

/*
 *This function is responsible for filling the board with the correct characters and symbols for positions 1-23 (inclusive), based on the current positions.
 */
int fill_main_board(char * board, int player_pos_1, int player_pos_2, int pos, int i) {

		/*
		 *Increments the pointer to the next empty position to fill :)
		 */
		board += pos;
		/*
		 *at_current represents a flag that indicates whether a numerical value or the user icons should be enclosed by brackets.
		 *0 indicates that a numerical value should be enclosed, 1 being that the character icon(s) should be enclosed instead.
		 */
		int at_current = 0;
		*board = '[';
		/*
		 *Increments the next position of the pointer to set, in addition to the pointer itself.
		 */
		pos++; board++;

		/*
		 *Checks to see if the current position is equal to i, where that user icon's should be displayed and enclosed respectively.
		 */
		if(player_pos_1 == i) {

			*board = '$';
			board++; pos++;
			at_current = 1;
		}

		if(player_pos_2 == i) {

			*board = '%';
			pos++; board++;
			at_current = 1;
		}

		if(i < 10) {
			
			if(at_current == 0) {
			
				/*
				 *Encloses the position, i, in brackets in the case where the user's icon is not at that current position, and this position is less than/equal to 10.
				 *The next character is set to the ASCII equivalent character, being the ASCII value of that given numeric position.
				*/
				*board = (char) (i + 48);
				pos++; board++;
			}
		}
		else {

			if(at_current == 0) {

				/*
				 *Handles the case, where the user's icon is not at this current position, where this position is greater than or equal to 10.
				 *Both digits have to be handled, where the tens place is enclosed in brackets or angle brackets first, then the ones place shortly after.
				 *The tens place is obtained by truncating the ones, and getting the ASCII equivalent character.
				 *The ones place is obtained by modding the value by 10, and getting the ASCII equivalent character.
				 */
				*board = (char) ((i / 10) + 48);
				pos++; board++;
				*board = (char) ((i % 10) + 48);
				pos++; board++;
			}
		}
		/*
		 *Fills the closing bracket and space
		 */
		*board = ']';
		pos++; board++;
		*board = ' ';
		pos++; board++;

		/*
		 *Returns the next pos to resume filling.
		 */
		return pos;
}

int fill_last_slot(char * board, int player_pos_1, int player_pos_2, int pos, int i) {

	
    /*
     *Increments the pointer to the next empty position to fill :)
     */
	board += pos;
    /*
	 *at_current represents a flag that indicates whether a numerical value or the user icons should be enclosed by brackets.
	 *0 indicates that a numerical value should be enclosed, 1 being that the character icon(s) should be enclosed instead.
	 */	
	int at_current = 0;
	*board = '<';
	board++; pos++;

	/*
     *Checks to see if the current position is equal to i, where that user icon's should be displayed and enclosed respectively, instead of the numeric position.
	 */
	if(player_pos_1 == i) {

		*board = '$';
		board++; pos++;
		at_current = 1;
	}

	if(player_pos_2 == i) {

		*board = '%';
		board++; pos++;
		at_current = 1;
	}

	if(at_current == 0) {

		/*
		 *Handles the case, where the user's icon is not at this current position, where this position is equal to 24.
		 *Both digits have to be handled, where the tens place is enclosed in brackets or angle brackets first, then the ones place shortly after.
		 *The tens place is obtained by truncating the ones, and getting the ASCII equivalent character.
		 *The ones place is obtained by modding the value by 10, and getting the ASCII equivalent character.
		 */
		*board = (char) ((i / 10) + 48);
		board++; pos++;
		*board = (char) ((i % 10) + 48);
		board++; pos++;
	}
	/*
	 *Fills remaining part of the final position
	 */
	*board = '>';
	board++; pos++;

	/*
     *Returns the next pos to resume filling.
	 */
	return pos;
}


void print_Board(int playerPos1, int playerPos2) {

	int i = 1;
	int pos = 0;
	/*
	 *Initializes an empty array to keep track of the game board.
	 */
	char board[200] = { 0 };

	while(i < 24) {

		/*
		 *Checks to see if the current space, i, is a "special space", where a special character is needed before the enclosed numerical position/user character.
		 */
		if(is_skull_space(i) == 1) { board[pos++] = '!'; }

		else if(is_maze_space(i) == 1) { board[pos++] = '-'; }

		else if(is_bridge_space(i) == 1) { board[pos++] = '*'; }

		else if(is_goose_space(i) == 1) { board[pos++] = '+'; }

		/*
		 *Call to fill the game board for the current position, i.
		 */
		pos = fill_main_board(board, playerPos1, playerPos2, pos, i);

		i++;
	}

	/*
	 *Call to fill the last position of the board, position 24.
	 *As well as null-terminating the game board.
	 */
	pos = fill_last_slot(board, playerPos1, playerPos2, pos, i);
	board[pos] = '\0';

	/*
	 *Prints out the game board after every turn :).
	 */
	printf("\n\n%s\n", board);
}

int read_seed(char * buffer) {

	int seed;

	do {
		/*
		 *Reads the seed into a buffer, while attempting to convert the inputted literal into an int.
		 *Checks to see if the input is convertable, or is a valid positive integer.
		 */
		printf("Enter a seed for the random number generator: ");
		fgets(buffer, 10, stdin);
		seed = atoi(buffer);
	}
	while(seed <= 0);

	/*
	 *Seeds the RNG for pseudorandom results when random generating.
	 */
	srand(seed);

	return 0;
}

int read_char(char * buffer) {

	do {
		/*
		 *Prints the main menu and reads in character input into a buffer.
		 *Program continues only when valid input is entered, 'p', 'P', 'Q', or 'q'.
		 */
		printf("Welcome to the game of goose, please select an option:\n");
		printf("Press 'P' or 'p' to play\n");
		printf("Press 'Q' or 'q' to quit\n");
		fgets(buffer, 10, stdin);
	}
	while((*buffer != 'p') && (*buffer != 'P') && (*buffer != 'q') && (*buffer != 'Q'));

	/*
	 *Returns the valid inputted character to be handled in the next function call.
	 */
	return *buffer;
}

int roll_first(char * buffer, char input) {

	int first = 0;
	int user_roll, comp_roll;

	/*
	 *Exits the program in the case where the user enters in 'Q' or 'q' to quit.
	 */
	if((input == 'q') || (input == 'Q')) { exit(1); }

	if((input == 'p') || (input == 'P')) {

		do {

			/*
			 *Simulates the initial roll by the Human and Computer players, where the outer do-while ensures there is a player who goes first.
			 */
			
			do {
				/*
				 *Rolls the dice for the Human player ONLY when the enter key is pressed.
				 */
				printf("HUMAN PLAYER, Press <enter> to roll the dice ");
				fgets(buffer, 10, stdin);
			}
			while(*buffer != '\n');

			user_roll = roll_Dice();

			do {
				/*
				 *Rolls the dice for the Computer player ONLY when the enter key is pressed.
				 */
				printf("COMPUTER PLAYER, Press <enter> to roll the dice");
				fgets(buffer, 10, stdin);
			}
			while(*buffer != '\n');

			comp_roll = roll_Dice();

			/*
			 *Determines who will go first during the Game of the Goose.
			 */
			first = determine_first(user_roll, comp_roll);
		}
		while(first == -1);
	}

	return first;
}

/*
 *This function determines which user will go first.
 *Function returns 1 in the case where the human will go first. Returns 0 in the case where the computer will go first.
 *Will return -1 in the case of a tie!
 */
int determine_first(int r1, int r2) {

	if(r1 > r2) {

		printf("HUMAN PLAYER goes first\n");
		return 1;
	}
	else if(r2 > r1) {

		printf("COMPUTER PLAYER goes first\n");
		return 0;
	}

	return -1;
}

/*
 *This function starts the game after the dice are rolled to determine who goes first.
 *The Human and Computer users will perform their turns respectively, moving to various spaces, attempting to win the game at 24.
 *Once 24 is reached, a printout is shown, terminating that current game.
 */
void start_game(int user_position, int comp_position, int user_starts) {

	while((user_position < 24) && (comp_position < 24)) {

		/*
		 *Sets up the order of turns in the case when the human goes first.
		 */
		if(user_starts == 1) {

			/*
			 *Performs BOTH turns of the human and computer players, prompting the current user to press the ENTER key to roll and continue the game.
			 */
			user_position = perform_turn(user_position, "HUMAN");
			/*
			 *Prints the current status of the board after the human performs their turn.
			 */
			print_Board(user_position, comp_position);

			/*
		     *Terminates the game in the case where the user wins.
		     */
			if(user_position == 24) {
				break;
			}

			comp_position = perform_turn(comp_position, "COMPUTER");
			/*
			 *Prints the current status of the board after the computer performs their turn.
			 */
			print_Board(user_position, comp_position);

			/*
		     *Terminates the game in the case where the computer wins.
		     */
			if(comp_position == 24) {
				break;
			}
		}
		/*
		 *Sets up the order of turns in the case when the computer goes first.
		 */
		else {

			/*
			 *Performs BOTH turns of the human and computer players, prompting the current user to press the ENTER key to roll and continue the game.
			 */
			comp_position = perform_turn(comp_position, "COMPUTER");
			/*
			 *Prints the current status of the board after the human performs their turn.
			 */
			print_Board(user_position, comp_position);

		    /*
		     *Terminates the game in the case where the computer wins.
		     */
			if(comp_position == 24) {
				break;
			}
			user_position = perform_turn(user_position, "HUMAN");
			/*
			 *Prints the current status of the board after the computer performs their turn.
			 */
			print_Board(user_position, comp_position);

			/*
		     *Terminates the game in the case where the human wins.
		     */
			if(user_position == 24) {
				break;
			}
		}
	}

	/*
	 *Various end-game printouts
	 */
	if(user_position == 24) { printf("Congratulations! You Won!\n"); }

	else { printf("Game Over! The Computer Won!\n"); }
}

void prompt_replay(char * buffer) {

	    do {
			/*
			 *Prompts the user to return to the main menu, where the game can be replayed or exited when the user enters in the ENTER key.
			 */
			printf("Press <enter> to return to the main menu\n");
			fgets(buffer, 10, stdin);
	    }
		while(*buffer != '\n');
}

int main() {

	char input_buffer[10];
	char input;
	int user_starts = 1;
	int user_position = 1;
	int comp_position = 1;

	/*
	 *One-time seed read-in from the user.
	 */
	read_seed(input_buffer);

	/*
	 *Loop to play the game as long as the user consents, entering in 'p' or 'P' to continue.
	 */
	do {

		/*
		 *All steps to set-up and perform the game, reading in initial input, rolling the initial roll, and playing the game itself, updating and printing out the current board.
		 */
		input = read_char(input_buffer);

		user_starts = roll_first(input_buffer, input);

		print_Board(user_position, comp_position);

		start_game(user_position, comp_position, user_starts);

		prompt_replay(input_buffer);
	}
	while((input != 'q') && (input != 'Q'));

	return 0;
}