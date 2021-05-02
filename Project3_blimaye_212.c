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
 *Function prototypes that include mandatory and helper functions throughout the program
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
int perform_watchlist_operation(movieNode * head, int num);
int perform_library_operation(movieNode * head, int num);
int perform_add_operation(movieNode * head, movieNode * head1, int num);
int get_list_len(movieNode * head);
int prompt_position(char buffer[50]);
int savewatchlist(movieNode *head);
movieNode * loadwatchlist();
void clean_up(movieNode ** head);


int validate_arguments(int argc) {

	/*
	 *Ensures that a file input is included that includes the movie library to be scanned.
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
	movieNode * prev;

	int i = 0;

	f = fopen(file_name, "r");

	if(f == NULL) {

		printf("Cannot open file for reading...");
		exit(-1);
	}

	head = createEmptyList();
	head->next = NULL;
	prev = head;

	/*
	 *Reads the input file, passed in as a command argument, creating a linked list among the movies.
	 */
	while(fgets(input_buffer, 34, f) != NULL) {

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
		if(curr->duration != 0.0) {

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

	if((prev != NULL) && (flag == 1)) {

		prev->next = movie;
		movie->next = curr;
		return;
	}

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

void printmovieInfo(movieNode *movie) {

	printf("\nMovie Title: %s\n", movie->title);
	printf("Genre: %s\n", movie->genre);
	printf("Duration: %f\n\n", movie->duration);
}

void printList(movieNode *head) {

	movieNode * curr = head;

	while(curr->next != NULL) {

		curr = curr->next;
		printmovieInfo(curr);
	}
}

void appendmovie(movieNode *head, movieNode *newNode) {

	movieNode * curr = head;

	while(curr->next != NULL) {

		curr = curr->next;
	}

	curr->next = newNode;
	newNode->next = NULL;
}

int insertmovie(movieNode ** head, movieNode * newNode, int position) {

	int i = 0;
	movieNode * curr = *head;

	if(position < 0) {

		printf("Invalid position to insert...\n");
		return 0;
	}

	while(curr->next != NULL) {

		if(i == position) {

			newNode->next = curr->next;
			curr->next = newNode;
			return 1;
		}

		curr = curr->next;
		i++;
	}

	curr->next = newNode;
	newNode->next = NULL;
	return 1;
}

double computeDuration(movieNode *head) {

	double total_duration = 0.0;
	movieNode * curr = head;

	while(curr != NULL) {

		total_duration += curr->duration;
		curr = curr->next;
	}

	return total_duration;
}

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

int print_library_menu(char input_buffer[50]) {

	int input;
	do {
		printf("\nLibrary Menu:\n");
		printf("1. View all movies\n");
		printf("2. Search by title\n");
		printf("3. Add a movie to watchlist\n");
		printf("4. Back to watchlist\n");

		fgets(input_buffer, 50, stdin);
		input = atoi(input_buffer);
	}
	/*
	 *Ensures that input is between 1-4, nothing more.
	 */
	while((input / 5 > 0) || (input % 5 == 0));

	return input;
}

int print_add_menu(char input_buffer[50]) {

	int input;
	do {
		printf("\nLibrary Menu:\n");
		printf("1. Add movie to the end\n");
		printf("2. Add movie at the beginning\n");
		printf("3. Insert movie at a specific position\n");

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

	printf("\nPlease enter in the movie title for lookup.\n");
	fgets(buffer, 35, stdin);
	buffer[strcspn(buffer, "\n")] = '\0';
	mn = searchByTitle(head, buffer);
	
	if(mn == NULL) {

		printf("\nMovie was not found! Please try again.\n\n");
		return NULL;
	}

	printf("\nThe Movie: %s was found!\n", buffer);
	return mn;
}

int get_list_len(movieNode * head) {

	int node_count = 0;
	movieNode * curr = head;

	while(curr->next != NULL) {

		curr = curr->next;
		++node_count;
	}

	return node_count;
}

int perform_corresponding_operation(movieNode * head, movieNode * head1, int return_code) {

	int input;
	int ret_val;
	char buffer[50];

	switch(return_code) {

		case 0:
			input = print_watchlist_menu(buffer);
			ret_val = perform_watchlist_operation(head, input);
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

int perform_watchlist_operation(movieNode * head, int num) {

	int node_pos;
	movieNode * mn = NULL;
	movieNode * rm = NULL;
	char movie_title[35];

	switch(num) {

		case 1:
			printList(head);
			break;
		case 2:
			printf("\nTotal Duration: %f\n", computeDuration(head));
			break;
		case 3:
			mn = prompt_movie_title(head, movie_title);
			break;
		case 4:
			mn = prompt_movie_title(head, movie_title);
			
			if(mn == NULL) { break; }
			node_pos = getNodePosition(head, mn->title);

			if(node_pos - 1 < 0) {

				printf("Cannot move up movie, as it's at the front currently...");
				break;
			}
			rm = removemovie(&head, mn);
			insertmovie(&head, rm, --node_pos);
			break;
		case 5:
			mn = prompt_movie_title(head, movie_title);

			if(mn == NULL) { break; }
			node_pos = getNodePosition(head, mn->title);

			if(node_pos + 1 >= get_list_len(head)) {

				printf("Cannot move down the movie, as it's in the back currently...");
				break;
			}
			rm = removemovie(&head, mn);
			insertmovie(&head, rm, ++node_pos);
			break;
		case 6:
			mn = prompt_movie_title(head, movie_title);

			if(mn == NULL) { break; }
			deletemovie(&head, mn);
			break;
		case 7:
			savewatchlist(head);
			break;
		case 8:
			return 8;
		case 9:
			return 1;
			break;
		case 10:
			return -1;
	}

	return 0;
}

int perform_library_operation(movieNode * head, int num) {

	movieNode * mn = NULL;
	char movie_title[35];

	switch(num) {

		case 1:
			printList(head);
			break;
		case 2:
			mn = prompt_movie_title(head, movie_title);
			break;
		case 3:
			return 2;
		case 4:
			return 0;
	}

	return 1;
}

int prompt_position(char buffer[50]) {

	char * endptr = NULL;
	int len;
	int position = 0;

	do {
		endptr = NULL;
		printf("\nPlease enter in a non-negative position to insert.\n");
		fgets(buffer, 50, stdin);
		position = strtol(buffer, &endptr, 10);
		len = strlen(endptr);

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

	printf("\nPlease enter in the movie title to be added to the watchlist.\n");
	fgets(title, 35, stdin);
	title[strcspn(title, "\n")] = '\0';

	if((new_node = searchByTitle(head1, title)) == NULL) {

		printf("Movie cannot be found! Please try again...");
		return 1;
	}

	added_node = createmovieNode(new_node->title, new_node->genre, new_node->duration);

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

	while(curr != NULL) {

		if(curr->duration != 0.0) {

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

	while(curr != NULL) {

		if(curr->duration != 0.0) {

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

	printf("\nPlease enter a file to save your watchlist to.\n");
	fgets(file_name, 50, stdin);
	file_name[strcspn(file_name, "\n")] = '\0';

	f = fopen(file_name, "w");

	if(f == NULL) {

		printf("Cannot open the file for saving...");
		return -1;
	}

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
	char tmp_buffer[35];
	char file_name[50];
	char title[35];
	char genre[35];
	double duration = 0.0;
	movieNode * head;
	movieNode * curr;
	int pos = 1;

	printf("\nPlease enter a file that contains a watchlist preset.\n");
	fgets(file_name, 50, stdin);
	file_name[strcspn(file_name, "\n")] = '\0';

	f = fopen(file_name, "r");

	if(f == NULL) {

		printf("Cannot the file for loading...");
		return NULL;
	}

	head = createEmptyList();
	curr = head;

	while(fgets(tmp_buffer, 35, f) != NULL) {

		switch(pos) {

			case 1:
				snprintf(title, 35, "%s", tmp_buffer);
				title[strcspn(title, "\n")] = '\0';
				break;
			case 2:
				snprintf(genre, 35, "%s", tmp_buffer);
				genre[strcspn(genre, "\n")] = '\0';
				break;
			case 3:
				duration = atof(tmp_buffer);
				break;
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

void clean_up(movieNode ** head) {

	movieNode * curr = (*head)->next;
	movieNode * next = NULL;
	int i = 0;

	while(curr != NULL) {

		next = curr->next;
		deletemovie(head, curr);
		curr = next;
		++i;
	}

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

	library_head = scan_movie_file(file_name);
	watch_head = createmovieNode("Dummy", "Dummy", 0.0);

	while(return_code != -1) {

		curr_head = (return_code == 1) ? library_head : watch_head;

		return_code = perform_corresponding_operation(curr_head, library_head, return_code);

		if(return_code == 8) {

			loaded_head = loadwatchlist();

			if(loaded_head != NULL) {

				clean_up(&watch_head);
				watch_head = loaded_head;
				loaded_head = NULL;
				return_code = 0;
			}
		}
	}

	clean_up(&library_head);
	clean_up(&watch_head);
	return 0;
}
