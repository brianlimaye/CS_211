/*
 *Brian Limaye, G01260841
 *CS 262, Lab Section 212
 *Project 3
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

/*
 *Struct that will essentially hold all movies in both the watchlist and library respectively.
 */
typedef struct _movienode
{   
	char title[35];
	char genre[35];
	double duration;
	struct _movienode *next;
}  movieNode;

/*
 *Function prototypes that include both mandatory and helper functions used throughout the program.
 */
int validate_arguments(int argc);
movieNode * scan_movie_file(char * file_name);
movieNode * createmovieNode(char *title, char *genre, double duration);
movieNode * createEmptyList();
void printList(movieNode *head);
void insert_movie_sort(movieNode * head, movieNode * movie);
void printmovieInfo(movieNode *movie);
void appendmovie(movieNode *head, movieNode *newNode);
int insertmovie(movieNode ** head, movieNode *newNode, int position);
double computeDuration(movieNode *head);
int print_watchlist_menu(char input_buffer[50]);
int print_library_menu(char input_buffer[50]);
int print_add_menu(char input_buffer[50]);
movieNode * searchByTitle(movieNode *head, char *search);
movieNode * prompt_movie_title(movieNode * head, char buffer[35]);
char * str_to_upper(char * str);
movieNode * removemovie(movieNode ** head, movieNode * remNode);
int deletemovie(movieNode ** head, movieNode *delNode);
int getNodePosition(movieNode *head, char *search);
int perform_corresponding_operation(movieNode * head, movieNode * head1, int return_code);
int perform_watchlist_operation(movieNode * head, movieNode * head1, int num);
int perform_library_operation(movieNode * head, int num);
int perform_add_operation(movieNode * head, movieNode * head1, int num);
int get_list_len(movieNode * head);
int prompt_position(char buffer[50]);
int savewatchlist(movieNode *head);
movieNode * loadwatchlist();
void clean_up(movieNode ** head);


int validate_arguments(int argc) {

	/*
	 *Ensures that a file input is included, which includes the movie library to be scanned.
	 */
	if(argc != 2) {

		return -1;
	}
	return 0;
}

movieNode * scan_movie_file(char * file_name) {

	FILE * f;

    char input_buffer[35];
	char title[35];
	char genre[35];
	double duration;

	movieNode * head;

	int i = 0;

	/*
	 *Attempts to open the command-line file for scanning.
	 */
	f = fopen(file_name, "r");

	if(f == NULL) {

		printf("Cannot open file for reading...");
		exit(-1);
	}

	head = createEmptyList();
	head->next = NULL;

	/*
	 *Reads the input file, passed in as a command argument, creating a linked list among the movies.
	 */
	while(fgets(input_buffer, 35, f) != NULL) {

		input_buffer[strcspn(input_buffer, "\n")] = '\0';

		/*
		 *Scans the title of a movie.
		 */
		if(i % 3 == 0) {

			snprintf(title, 35, "%s", input_buffer);
			i++;
			continue;
		}

		/*
		 *Scans the genre of the movie.
		 */
		else if(i % 3 == 1) {

			snprintf(genre, 35, "%s", input_buffer);
			i++;
			continue;
		}
		/*
		 *Scans the duration of the movie.
		 */
		else {

			duration = atof(input_buffer);
			insert_movie_sort(head, createmovieNode(title, genre, duration));
			i++;
		}
	}

	fclose(f);
	return head;
}

void insert_movie_sort(movieNode * head, movieNode * movie) {

	movieNode * prev = NULL;
	movieNode * curr = head;
	int flag = 0;

	/*
	 *In the case where a movie needs to be inserted with just the dummy node being in the list.
	 */
	if(head->next == NULL) {

		head->next = movie;
		movie->next = NULL;
		return;
	}

	while(curr != NULL) {

		/*
		 *Ensures that the current node is not the dummy node.
		 */
		if(curr != head) {

			/*
			 *Checks if the movie to be inserted is lexigraphically less than the current movie, as it will be inserted before.
			 */

			if(strcmp(movie->title, curr->title) < 0) {

				flag = 1;
				break;
			}
		}

		prev = curr;
		curr = curr->next;
	}

	/*
	 *Branch is true in the case where a movie is to be inserted in between two nodes or movies.
	 */
	if((prev != NULL) && (flag == 1)) {

		prev->next = movie;
		movie->next = curr;
		return;
	}

	/*
	 *In the case where the movie is lexigraphically greater than all movies in the current list.
	 */
	prev->next = movie;
	movie->next = NULL;
}

/*
 *Function is solely responsible for initializing the dummy node/head of a linked list.
 */
movieNode * createEmptyList() {

	movieNode * mn = (movieNode *) calloc(1, sizeof(movieNode));

	if(mn == NULL) {

		printf("Unable to allocate memory...");
		exit(-1);
	}

	mn->duration = 0.0;

	return mn;
}

/*
 *Function is responsible for allocating memory for a movie node.
 */
movieNode * createmovieNode(char *title, char *genre, double duration) {

	movieNode * mn = (movieNode *) calloc(1, sizeof(movieNode));

	if(mn == NULL) {

		printf("Unable to allocate memory...");
		exit(-1);
	}

	snprintf(mn->title, 35, "%s", title);
	snprintf(mn->genre, 35, "%s", genre);
	mn->duration = duration;

	return mn;
}

/*
 *Function is responsible for printing the information for a single movie node.
 */
void printmovieInfo(movieNode *movie) {

	printf("\nMovie Title: %s\n", movie->title);
	printf("Genre: %s\n", movie->genre);
	printf("Duration: %f\n\n", movie->duration);
}

/*
 *Prints out all info for each movie node in a given collection of movies.
 */
void printList(movieNode *head) {

	movieNode * curr = head;

	/*
	 *Skips the dummy node, when printing.
	 */
	while(curr->next != NULL) {

		curr = curr->next;
		printmovieInfo(curr);
	}
}

void appendmovie(movieNode *head, movieNode *newNode) {

	movieNode * curr = head;

	/*
	 *Finds the last node in the list, which points to null.
	 */
	while(curr->next != NULL) {

		curr = curr->next;
	}

	/*
	 *Sets the next pointer to the added movie node.
	 */ 
	curr->next = newNode;
	newNode->next = NULL;
}

/*
 *Inserts a movie node in the linked list, whether it be at the beginning, middle, or end.
 */
int insertmovie(movieNode ** head, movieNode * newNode, int position) {

	int i = 0;
	movieNode * curr = *head;

	/*
	 *Validates that position is valid.
	 */
	if(position < 0) {

		printf("Invalid position to insert...\n");
		return 0;
	}

	/*
	 *Iterates through the linked list, until the corresponding position is reached to insert.
	 */ 
	while(curr->next != NULL) {

		/*
		 *Once the position is reached, iteration stops, and the curr node's next is set to the inserted element.
		 *In addition, the inserted element's next is set to curr's next.
		 */
		if(i == position) {

			newNode->next = curr->next;
			curr->next = newNode;
			return 1;
		}

		/*
		 *Next element of the linked list is set, as well as the counter being incremented.
		 */
		curr = curr->next;
		i++;
	}

	/*
	 *In the case where the position exceeds the length of the list, or the movie is to be appended.
	 */
	curr->next = newNode;
	newNode->next = NULL;
	return 1;
}

double computeDuration(movieNode *head) {

	double total_duration = 0.0;
	movieNode * curr = head;

	/*
	 *Iterates through the list, adding all durations of each movie node respectively.
	 */
	while(curr != NULL) {

		total_duration += curr->duration;
		curr = curr->next;
	}

	return total_duration;
}

/*
 *Prints out the watchlist menu options to the user, ensuring sanitized input.
 */
int print_watchlist_menu(char input_buffer[50]) {

	int input;
	do {
		printf("\n*******************");
		printf("\nWatchlist Menu:\n");
		printf("1. Print watchlist\n");
		printf("2. Show duration\n");
		printf("3. Search by title\n");
		printf("4. Move a movie up\n");
		printf("5. Move a movie down\n");
		printf("6. Remove a movie\n");
		printf("7. Save watchlist\n");
		printf("8. Load watchlist\n");
		printf("9. Go to movie Library\n");
		printf("10.Quit\n");
		printf("*********************\n");

		fgets(input_buffer, 50, stdin);
		input = atoi(input_buffer);
	}
	/*
	 *Ensures that input is between 1-10, nothing more.
	 */
	while((input / 11 > 0) || (input % 11 == 0));

	return input;
}

/*
 *Prints out the library menu, ensuring sanitized input.
 */
int print_library_menu(char input_buffer[50]) {

	int input;
	do {
		printf("\n*******************");
		printf("\nLibrary Menu:\n");
		printf("1. View all movies\n");
		printf("2. Search by title\n");
		printf("3. Add a movie to watchlist\n");
		printf("4. Back to watchlist\n");
		printf("*******************\n");

		fgets(input_buffer, 50, stdin);
		input = atoi(input_buffer);
	}
	/*
	 *Ensures that input is between 1-4, nothing more.
	 */
	while((input / 5 > 0) || (input % 5 == 0));

	return input;
}

/*
 *Prints out the add menu, ensuring sanitized input.
 */
int print_add_menu(char input_buffer[50]) {

	int input;
	do {
		printf("\n*******************");
		printf("\nLibrary Menu:\n");
		printf("1. Add movie to the end\n");
		printf("2. Add movie at the beginning\n");
		printf("3. Insert movie at a specific position\n");
		printf("*******************\n");

		fgets(input_buffer, 50, stdin);
		input = atoi(input_buffer);
	}
	/*
	 *Ensures that input is between 1-3, nothing more.
	 */
	while((input / 4 > 0) || (input % 4 == 0));

	return input;
}

movieNode * prompt_movie_title(movieNode * head, char buffer[35]) {

	movieNode * mn = NULL;

	/*
	 *Scans the movie title from the user, for either lookup, insert, etc.
	 */
	printf("\nPlease enter in the movie title for lookup.\n");
	fgets(buffer, 35, stdin);
	buffer[strcspn(buffer, "\n")] = '\0';
	mn = searchByTitle(head, buffer);
	
	/*
	 *Ensures that the movie is found.
	 */
	if(mn == NULL) {

		printf("\nMovie was not found! Please try again.\n\n");
		return NULL;
	}

	printf("\nThe Movie: %s was found!\n", buffer);
	return mn;
}

/*
 *Calculates the length of the linked list of movies.
 */
int get_list_len(movieNode * head) {

	int node_count = 0;
	movieNode * curr = head;

	while(curr->next != NULL) {

		curr = curr->next;
		++node_count;
	}

	return node_count;
}

/*
 *Acts as a supermenu for each of the three submenus that are navigated by the user. Returns the return value to decide the next menu displayed.
 */
int perform_corresponding_operation(movieNode * head, movieNode * head1, int return_code) {

	int input;
	int ret_val;
	char buffer[50];

	switch(return_code) {

		case 0:
			input = print_watchlist_menu(buffer);
			ret_val = perform_watchlist_operation(head, head1, input);
			break;
		case 1:
			input = print_library_menu(buffer);
			ret_val = perform_library_operation(head, input);
			break;
		case 2:
			input = print_add_menu(buffer);
			ret_val = perform_add_operation(head, head1, input);
			break;
		default:
			ret_val = -1;
			break;
	}

	return ret_val;
}

/*
 *Based on num, the corresponding number on the menu is performed.
 *Returns -1 in the case where the program should terminate.
 *Returns 0 if the watchlist operation should be displayed after the operation is completed.
 *Returns 1 in the case where the library menu should be displayed.
 *Returns 8 in the special case where a watchlist should be loaded, setting a new movie list preset.
 */

int perform_watchlist_operation(movieNode * head, movieNode * head1, int num) {

	int node_pos;
	movieNode * mn = NULL; movieNode * rm = NULL;
	char movie_title[35];

	switch(num) {

		case 1: printList(head); break;
		case 2: printf("\nTotal Duration: %f\n", computeDuration(head)); break;
		case 3: prompt_movie_title(head, movie_title); break;
		case 4:
			mn = prompt_movie_title(head, movie_title);
			/*
			 *Ensures that a movie is found, if so, the movie is removed and inserted a position up, in the case where it is a a valid position.
			 */
			if(mn == NULL) { break; }
			node_pos = getNodePosition(head, mn->title);

			if(node_pos - 1 < 0) {

				printf("Cannot move up movie, as it's at the front currently...\n");
				break;
			}
			rm = removemovie(&head, mn);  insertmovie(&head, rm, --node_pos);
			break;
		case 5:
			mn = prompt_movie_title(head, movie_title);
			/*
			 *Ensures that a movie is found, if so, the movie is removed and inserted a position down, in the case where that movie is not in the back.
			 */
			if(mn == NULL) { break; }
			node_pos = getNodePosition(head, mn->title);

			if(node_pos + 1 >= get_list_len(head)) {

				printf("Cannot move down the movie, as it's in the back currently...");
				break;
			}
			rm = removemovie(&head, mn);   insertmovie(&head, rm, ++node_pos);
			break;
		case 6:
			mn = prompt_movie_title(head, movie_title);

			if(mn == NULL) { break; }
			rm = removemovie(&head, mn);   insert_movie_sort(head1, rm);
			break;
		case 7: savewatchlist(head); break;
		case 8: return 8;
		case 9: return 1;
		case 10: return -1;
	}

	return 0;
}

/*
 *Controls the flow of the library menu based on sanitized user input.
 *Returns 0 when the watchlist menu should be displayed next.
 *Returns 1 in the case where the library menu should be printed out next.
 *Returns 2 in the case where an insertion must be made, where the add menu should be printed out next.
 */
int perform_library_operation(movieNode * head, int num) {

	char movie_title[35];

	switch(num) {

		case 1:
			printList(head);
			break;
		case 2:
			prompt_movie_title(head, movie_title);
			break;
		case 3:
			return 2;
		case 4:
			return 0;
	}

	return 1;
}

/*
 *Prompts a position to be inserted into the movie list, ensures that a valid non-negative index is inputted.
 */
int prompt_position(char buffer[50]) {

	char * endptr = NULL;
	int len;
	int position = 0;

	/*
	 *Continuously prompts the user for an index from the user, until valid input is entered.
	 */
	do {
		endptr = NULL;
		printf("\nPlease enter in a non-negative position to insert.\n");
		fgets(buffer, 50, stdin);
		position = strtol(buffer, &endptr, 10);
		len = strlen(endptr);

		/*
		 *In the case where the user simply inputs 0, which is a valid index.
		 */
		if((position == 0) && (len == 1)) {

			break;
		}
	}
	while((len != 1) || (position < 0));

	return position;
}

int perform_add_operation(movieNode * head, movieNode * head1, int num) {

	int position;
	char title[35];
	char buffer[50];
	movieNode * new_node;
	movieNode * added_node;

	/*
	 *Scans in the movie that should be added to the watchlist, ensuring that this movie exists in the library.
	 */
	printf("\nPlease enter in the movie title to be added to the watchlist.\n");
	fgets(title, 35, stdin);
	title[strcspn(title, "\n")] = '\0';

	if((new_node = searchByTitle(head1, title)) == NULL) {

		printf("Movie cannot be found! Please try again...");
		return 1;
	}

	added_node = removemovie(&head1, new_node);

	/*
	 *Adds the movie into the watchlist, in a variety of ways, depending on which option was picked by the user.
	 */
	switch(num) {

		case 1:
			appendmovie(head, added_node);
			break;
		case 2:
			insertmovie(&head, added_node, 0);
			break;
		case 3:
			position = prompt_position(buffer);
			insertmovie(&head, added_node, position);
			break;
	}

	return 1;
}

/*
 *Function is responsible for converting a string to uppercase, returning the uppercase literal.
 */
char * str_to_upper(char * str) {

	int i;
	int len = strlen(str);

	for(i = 0; i < len; i++) {

		*(str + i) = toupper(*(str + i));
	}

	return str;
}

movieNode * searchByTitle(movieNode *head, char *search) {

	movieNode * curr = head;
	search = str_to_upper(search);

	/*
	 *Iterates through the list, looking for the correct movie that matches the search title.
	 */
	while(curr != NULL) {

		if(curr != head) {

			if(strcmp(str_to_upper(curr->title), search) == 0) {

				return curr;
			}
		}

		curr = curr->next;
	}

	return NULL;
}

movieNode * removemovie(movieNode ** head, movieNode * remNode) {

	movieNode * prev = NULL;
	movieNode * curr = *head;
	movieNode * removed = NULL;

	while(curr != NULL) {

		/*
		 *Checks if the current node is not the head node.
		 */
		if((curr != (*head)) && (strcmp(curr->title, remNode->title) == 0)) {

			removed = curr;
			curr = curr->next;
			break;
		}

		prev = curr;
		curr = curr->next;
	}

	/*
	 *If the removed node was found in the list, it will be removed by modifying previous->next and removed->next.
	 */
	if((prev != NULL) && (removed != NULL)) {

		prev->next = curr;
		removed->next = NULL;
	}
	
	return removed;
}

int deletemovie(movieNode ** head, movieNode *delNode) {

	movieNode * removed;

	/*
	 *In the case where the deleted node is head, it's freed directly without needing another function call.
	 */
	if(delNode == *head) {

		printf("head");
		free(delNode);
		return 0;
	}

	removed = removemovie(head, delNode);

	if(removed != NULL) {

		free(removed);
		return 0;
	}

	return -1;
}

int getNodePosition(movieNode *head, char *search) {

	int i = -1;
	movieNode * curr = head;
	search = str_to_upper(search);

	/*
	 *Searches the linked list for a given position of a title.
	 */
	while(curr != NULL) {

		if(curr != head) {

			/*
			 *Compares titles of the target node and the search node.
			 */ 
			if(strcmp(curr->title, search) == 0) {

				return i;
			}
		}

		curr = curr->next;
		i++;
	}

	return -1;
}

int savewatchlist(movieNode *head) {

	FILE * f;
	char file_name[50];
	movieNode * curr = head;

	/*
	 *Prompts a file to save the current watchlist to, assuming the file can be opened.
	 */
	printf("\nPlease enter a file to save your watchlist to.\n");
	fgets(file_name, 50, stdin);
	file_name[strcspn(file_name, "\n")] = '\0';

	f = fopen(file_name, "w");

	if(f == NULL) {

		printf("Cannot open the file for saving...");
		return -1;
	}

	/*
	 *Writes to the file with the current movie nodes in the watchlist, for future loading.
	 */
	while(curr->next != NULL) {

		curr = curr->next;

		fprintf(f, "%s\n", curr->title);
		fprintf(f, "%s\n", curr->genre);
		fprintf(f, "%f\n\n", curr->duration);
	}

	fclose(f);

	return 0;
}

movieNode * loadwatchlist() {

	FILE * f;
	char title[35], genre[35], file_name[50], tmp_buffer[35];
	double duration = 0.0;
	movieNode * head;   movieNode * curr;
	int pos = 1;

	/*
	 *Attempts to open a file, containing a preset for a watchlist.
	 */
	printf("\nPlease enter a file that contains a watchlist preset.\n");
	fgets(file_name, 50, stdin);
	file_name[strcspn(file_name, "\n")] = '\0';

	f = fopen(file_name, "r");

	if(f == NULL) { printf("Cannot the file for loading..."); return NULL; }

	/*
	 *Initializes the head of the new list.
	 */
	head = createEmptyList();     curr = head;
	
	/*
	 *Reads the content of the file, setting the attributes of the movie nodes and/or creating the node itself.
	 */
	while(fgets(tmp_buffer, 35, f) != NULL) {

		switch(pos) {

			/*
			 *Sets the title of the current movie node.
			 */
			case 1:
				snprintf(title, 35, "%s", tmp_buffer);
				title[strcspn(title, "\n")] = '\0';
				break;
			/*
			 *Sets the genre of the current movie node.
			 */
			case 2:
				snprintf(genre, 35, "%s", tmp_buffer);
				genre[strcspn(genre, "\n")] = '\0';
				break;
			/*
			 *Sets the duration of the current movie node.
			 */
			case 3:  duration = atof(tmp_buffer);     break;
			/*
			 *Creates the movie node, allocating memory for it, and adding it to the new loaded list.
			 */ 
			case 4:
				curr->next = createmovieNode(title, genre, duration);
				curr = curr->next;
				pos = 0;
				break;				
		}

		pos++;
	}

	fclose(f);

	return head;
}

/*
 *Frees up all memory allocated throughout the lifespan of the program, including all allocations to each of the linked lists.
 */
void clean_up(movieNode ** head) {

	movieNode * curr = (*head)->next;
	movieNode * next = NULL;
	int i = 0;

	/*
	 *Frees the other movie nodes first, excluding the head.
	 */
	while(curr != NULL) {

		next = curr->next;
		deletemovie(head, curr);
		curr = next;
		++i;
	}

	/*
	 *Frees the head last.
	 */
	free(*head);
}

int main(int argc, char ** argv) {

	int return_code = 0;
	char * file_name;
	movieNode * curr_head;
	movieNode * loaded_head;
	movieNode * library_head;
	movieNode * watch_head;

	if(validate_arguments(argc) == -1) {

		printf("Invalid number of arguments...");
		exit(-1);
	}

	file_name = argv[1];

	/*
	 *Initial scan of the inputted movie library file.
	 */
	library_head = scan_movie_file(file_name);
	watch_head = createmovieNode("Dummy", "Dummy", 0.0);

	while(return_code != -1) {

		/*
		 *Sets the current head, depending on which menu will be displayed next.
		 */
		curr_head = (return_code == 1) ? library_head : watch_head;

		return_code = perform_corresponding_operation(curr_head, library_head, return_code);

		/*
		 *In the case where a movie is added, the original watchlist is deleted and replaced with the loaded watchlist.
		 */
		if(return_code == 8) {

			loaded_head = loadwatchlist();

			if(loaded_head != NULL) {

				clean_up(&watch_head);
				watch_head = loaded_head;
				return_code = 0;
			}
		}
	}

	/*
	 *Cleans up all memory when the user exits the program.
	 */
	clean_up(&library_head);
	clean_up(&watch_head);
	return 0;
}
