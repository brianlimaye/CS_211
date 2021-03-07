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
const int bridgeSpaces[NUM_BRIDGE_SPACES] = {6};
const int mazeSpaces[NUM_MAZE_SPACES] = {13, 20};
const int skullSpaces[NUM_SKULL_SPACES] = {23};

int roll_Dice(void);
void print_Board(int playerPos1, int playerPos2);
char * construct_Board(int playerPos1, int playerPos2);
int is_goose_space(int space);
int is_bridge_space(int space);
int is_maze_space(int space);
int is_skull_space(int space);
int interpret_position(int old_space, int roll);
int perform_turn(int old_space, char * name);
int update_position(int old_space, int roll);
int fill_main_board(char * board, int playerPos1, int playerPos2, int pos, int i);
int fill_last_slot(char * board, int playerPos1, int playerPos2, int pos, int i);

int is_goose_space(int space) {

	int i;

	for(i = 0; i< NUM_GOOSE_SPACES; i++) {

		if(gooseSpaces[i] == space) {

			return 1;
		}
	}

	return 0;
}

int is_bridge_space(int space) {

	int i;

	for(i = 0; i< NUM_BRIDGE_SPACES; i++) {

		if(bridgeSpaces[i] == space) {

			return 1;
		}
	}

	return 0;
}

int is_maze_space(int space) {

	int i;

	for(i = 0; i< NUM_MAZE_SPACES; i++) {

		if(mazeSpaces[i] == space) {

			return 1;
		}
	}

	return 0;
}

int is_skull_space(int space) {

	int i;

	for(i = 0; i< NUM_SKULL_SPACES; i++) {

		if(skullSpaces[i] == space) {

			return 1;
		}
	}

	return 0;
}

int update_position(int old_space, int roll) {
	int is_clear;
	int current_space = old_space + roll;
	do {
		is_clear = 1;

		if(is_goose_space(current_space) == 1) {
			current_space += roll;
			printf("go to space %d ", current_space);
			is_clear = 0;
		}
		else if(is_bridge_space(current_space) == 1) {
			current_space = 12;
			printf("go to space %d ", current_space);
			is_clear = 0;
		}
		else if(is_maze_space(current_space) == 1) {
			current_space--;
			printf("go to space %d ", current_space);
			is_clear = 0;
		}

		else if(is_skull_space(current_space) == 1) {
			current_space = 1;
			printf("go to space %d ", current_space);
			is_clear = 0;
		}
	}
	while(is_clear == 0);

	return current_space;
}

int interpret_position(int old_space, int roll) {

    int is_clear;
    int current_space = old_space;

    current_space += roll;

    if(current_space > 24) {

    	current_space = 24 - (current_space - 24);
    }

	printf("go to space %d ", current_space);

	current_space = update_position(current_space, roll);

	return current_space;
}

int perform_turn(int old_space, char * player) {

	char buffer[10];
	char * board;
	int roll;
	int position;

	do {
		printf("%s PLAYER'S TURN, [%d]... Press <enter> to roll the dice ", player, old_space);
		fgets(buffer, 10, stdin);
	}
	while(buffer[0] != '\n');

	roll = roll_Dice();
	position = interpret_position(old_space, roll);

	printf("\n%s\n", board);

	return position;
}

int roll_Dice(void) {

	int roll_1 = (rand() % 6) + 1;
	int roll_2 = (rand() % 6) + 1;

	printf("%d and %d for %d\n", roll_1, roll_2, roll_1 + roll_2);

	return roll_1 + roll_2;
}

int fill_main_board(char * board, int playerPos1, int playerPos2, int pos, int i) {

		board += pos;
		int at_current = 0;
		*board = '[';
		pos++;
		board++;

		if(playerPos1 == i) {

			*board = '$';
			board++;
			pos++;
			at_current = 1;
		}

		if(playerPos2 == i) {

			*board = '%';
			pos++;
			board++;
			at_current = 1;
		}

		if(i < 10) {
			
			if(at_current == 0) {
			
				*board = (char) (i + 48);
				pos++;
				board++;
			}
		}
		else {

			if(at_current == 0) {

				*board = (char) ((i / 10) + 48);
				pos++;
				board++;
				*board = (char) ((i % 10) + 48);
				pos++;
				board++;
			}
		}
		*board = ']';
		pos++;
		board++;
		*board = ' ';
		pos++;
		board++;

		return pos;
}

int fill_last_slot(char * board, int playerPos1, int playerPos2, int pos, int i) {

	board += pos;
	int at_current = 0;
	*board = '<';
	board++;

	if(playerPos1 == i) {

		*board = '$';
		board++;
		at_current = 1;
	}

	if(playerPos2 == i) {

		*board = '%';
		board++;
		at_current = 1;
	}

	if(at_current == 0) {

		*board = (char) ((i / 10) + 48);
		board++;
		*board = (char) ((i % 10) + 48);
		board++;
	}
	*board = '>';
	board++;

	return pos;
}


char * construct_Board(int playerPos1, int playerPos2) {

	int i = 1;
	int at_current = 0;
	int pos = 0;
	char board[200] = { 0 };

	while(i < 24) {

		if(is_skull_space(i) == 1) { board[pos++] = '!'; }

		else if(is_maze_space(i) == 1) { board[pos++] = '-'; }

		else if(is_bridge_space(i) == 1) { board[pos++] = '*'; }

		else if(is_goose_space(i) == 1) { board[pos++] = '+'; }

		pos = fill_main_board(board, playerPos1, playerPos2, pos, i);

		i++;
	}

	pos = fill_last_slot(board, playerPos1, playerPos2, pos, i);

	return strdup(board);
}

int main() {

	char input_buffer[10];
	int seed;
	char input;
	int user_starts = 1;
	int user_roll, comp_roll;
	int user_position = 1;
	int comp_position = 1;
	char * board;

	do {
		printf("Enter a seed for the random number generator: ");
		fgets(input_buffer, 10, stdin);
		seed = atoi(input_buffer);
	}
	while(seed == 0);

	srand(seed);

	do {
		printf("Welcome to the game of goose, please select an option:\n");
		printf("Press 'P' or 'p' to play\n");
		printf("Press 'Q' or 'q' to quit\n");
		fgets(input_buffer, 10, stdin);
	}
	while((input_buffer[0] != 'p') && (input_buffer[0] != 'P') && (input_buffer[0] != 'q') && (input_buffer[0] != 'Q'));

	if((input_buffer[0] == 'p') || (input_buffer[0] == 'P')) {

		while(user_roll == comp_roll) {

			do {
				printf("HUMAN PLAYER, Press <enter> to roll the dice ");
				fgets(input_buffer, 10, stdin);
			}
			while(input_buffer[0] != '\n');

			user_roll = roll_Dice();

			do {
				printf("COMPUTER PLAYER, Press <enter> to roll the dice");
				fgets(input_buffer, 10, stdin);
			}
			while(input_buffer[0] != '\n');

			comp_roll = roll_Dice();
		}

		if(user_roll > comp_roll) {

			printf("HUMAN PLAYER goes first\n");
		}
		else {

			printf("COMPUTER PLAYER goes first\n");
			user_starts = 0;
		}

		board = construct_Board(user_position, comp_position);
		printf("\n%s\n", board);

		while((user_position < 24) && (comp_position < 24)) {

			if(user_starts == 1) {

				user_position = perform_turn(user_position, "HUMAN");
				board = construct_Board(user_position, comp_position);
				printf("\n%s\n", board);

				if(user_position == 24) {
					break;
				}

				comp_position = perform_turn(comp_position, "COMPUTER");
				board = construct_Board(user_position, comp_position);
				printf("\n%s\n", board);

				if(comp_position == 24) {
					break;
				}
			}
			else {

				comp_position = perform_turn(comp_position, "COMPUTER");
				board = construct_Board(user_position, comp_position);
				printf("\n%s\n", board);

				if(comp_position == 24) {
					break;
				}
				user_position = perform_turn(user_position, "HUMAN");
				board = construct_Board(user_position, comp_position);
				printf("\n%s\n", board);

				if(user_position == 24) {
					break;
				}
			}
		}

		if(user_position == 24) {

			printf("Congratulations! You Won!");
		}
		else {

			printf("Game Over! The Computer Won!");
		}
	}

	return 0;
}