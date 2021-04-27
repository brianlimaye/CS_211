#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef struct _movienode
{   
	char title[35];
	char genre[35];
	double duration;
	struct _movienode *next;
}  movieNode;

int validate_arguments(int argc);
movieNode * scan_movie_file(char * file_name);
movieNode * createmovieNode(char *title, char *genre, double duration);
movieNode * createEmptyList();
void printList(movieNode *head);
void insert_movie_sort(movieNode * head, movieNode * movie);
void printmovieInfo(movieNode *movie);
void appendmovie(movieNode *head, movieNode *newNode);
int insertmovie(movieNode *head, movieNode *newNode, int position);
double computeDuration(movieNode *head);
int print_watchlist_menu(char input_buffer[50]);
int print_library_menu(char input_buffer[50]);
int print_add_menu(char input_buffer[50]);
movieNode * searchByTitle(movieNode *head, char *search);
char * str_to_upper(char * str);
movieNode * removemovie(movieNode * head, movieNode * remNode);
int deletemovie(movieNode *head, movieNode *delNode);
int getNodePosition(movieNode *head, char *search);


int validate_arguments(int argc) {

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
	int file_len;

	f = fopen(file_name, "r");

	if(f == NULL) {

		printf("Cannot open file for reading...");
		exit(-1);
	}

	head = createEmptyList();
	head->next = NULL;
	prev = head;

	while(fgets(input_buffer, 34, f) != NULL) {

		input_buffer[strcspn(input_buffer, "\n")] = '\0';
		//printf("%s\n", input_buffer);

		if(i % 3 == 0) {

			strcpy(title, input_buffer);
			i++;
			continue;
		}

		else if(i % 3 == 1) {

			strcpy(genre, input_buffer);
			i++;
			continue;
		}
		else {

			duration = atof(input_buffer);
			insert_movie_sort(head, createmovieNode(title, genre, duration));
			//printmovieInfo(prev->next);
			//prev = prev->next;
			i++;
		}
	}

	//printf("Sorted List...\n");
	//printList(head);
	//prev->next = NULL;

	return head;
}

void insert_movie_sort(movieNode * head, movieNode * movie) {

	movieNode * prev = NULL;
	movieNode * curr = head;
	int flag = 0;

	if(head->next == NULL) {

		head->next = movie;
		movie->next = NULL;
		return;
	}

	while(curr != NULL) {

		if(curr->duration != 0.0) {

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

movieNode * createEmptyList() {

	movieNode * mn = (movieNode *) malloc(sizeof(movieNode));

	if(mn == NULL) {

		printf("Unable to allocate memory...");
		exit(-1);
	}

	mn->duration = 0.0;

	return mn;
}

movieNode * createmovieNode(char *title, char *genre, double duration) {

	movieNode * mn = (movieNode *) malloc(sizeof(movieNode));

	if(mn == NULL) {

		printf("Unable to allocate memory...");
		exit(-1);
	}

	strcpy(mn->title, title);
	strcpy(mn->genre, genre);
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

	while(curr != NULL) {

		printmovieInfo(curr);
		curr = curr->next;
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

int insertmovie(movieNode *head, movieNode *newNode, int position) {

	int i = 0;
	movieNode * curr = head;

	if(position <= 0) {

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

		fgets(input_buffer, 50, stdin);
		input = atoi(input_buffer);
	}
	while((input / 11 == 0) && (input % 11 > 0));

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
	while((input / 5 == 0) && (input % 5 > 0));

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
	while((input / 4 == 0) && (input % 4 > 0));

	return input;
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

	search = str_to_upper(search);
	movieNode * curr = head;

	while(curr != NULL) {

		if(curr->duration != 0.0) {

			if(strcmp(curr->title, search) == 0) {

				return curr;
			}
		}

		curr = curr->next;
	}

	return NULL;
}

movieNode * removemovie(movieNode * head, movieNode * remNode) {

	movieNode * prev = NULL;
	movieNode * curr = head;
	movieNode * removed = NULL;

	while(curr->next != NULL) {

		if((strcmp(curr->title, remNode->title) == 0) && (strcmp(curr->genre, remNode->genre) == 0) && (curr->duration == remNode->duration)) {

			removed = curr;
		}

		prev = curr;
		curr = curr->next;
	}

	if(removed != NULL) {

		prev->next = curr;
	}
	
	return removed;
}

int deletemovie(movieNode *head, movieNode *delNode) {

	movieNode * removed = removemovie(head, delNode);

	if(removed != NULL) {

		free(removed);
		return 0;
	}

	return -1;
}

int getNodePosition(movieNode *head, char *search) {

	int i = 0;
	search = str_to_upper(search);
	movieNode * curr = head;

	while(curr->next != NULL) {

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

int main(int argc, char ** argv) {

	char * file_name;
	char input_buffer[50];
	movieNode * head;

	if(validate_arguments(argc) == -1) {

		printf("Invalid number of arguments...");
		exit(-1);
	}

	file_name = argv[1];

	head = scan_movie_file(file_name);

	//insertmovie(head, createmovieNode("Spongebob", "Cartoon", 2.0), -100);

	print_watchlist_menu(input_buffer);

	return 0;
}