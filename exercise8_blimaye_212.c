#include <stdio.h>
#include <stdlib.h>

#define MAX_SIZE 1000

void clean_up(char ** names, int * ages, int n);

void clean_up(char ** names, int * ages, int n) {
	int i;

	if (names != NULL)
	{
		for(i = 0; i < n; i++) {

			free(*names);
			*names = NULL;
			names++;
		}
	}

	if (ages != NULL)
	{
		free(ages);
		ages = NULL;
	}
}

int main() {

	//Problem 1
	int i;
	int n;
	int m = -1;
	int char_count;
	int exit_code = 1;

	char ** names = NULL;
	int * ages = NULL;

	printf("Please enter in the number of students to be recorded. ");
	
	char_count = scanf("%d", &n);
	if (char_count < 0)
	{
		goto exit;
	}


	if(n < 0) {
		printf("Invalid input...");
		goto exit;
	}

	if (n > MAX_SIZE)
	{
		printf("Cannot process more than %i students!", MAX_SIZE);
		goto exit;
	}

	names = (char **) malloc(n * sizeof(char *));

	if(names == NULL) {
		printf("malloc failed...");
		goto exit;
	}

	ages = (int *) malloc(n * sizeof(int));

	if(names == NULL) {
		printf("malloc failed...");
		goto exit;
	}

	for(i = 0; i < n; i++) {
		names[i] = (char *) malloc(15 * sizeof(char));
		printf("Please enter in student #%d's name. ", i + 1);
		char_count = scanf("%14s", names[i]);
		if (char_count < 0)
		{
			goto exit;
		}

		printf("Please enter in student #%d's age. ", i + 1);
		char_count = scanf("%d", &(*(ages + i)));
		if (char_count < 0)
		{
			goto exit;
		}
	}

	for(i = 0; i < n; i++) {
		printf("Student %d's name is: %s, age: %d\n", i + 1, names[i], *(ages + i));
	}

	//Problem 2

	printf("Please enter in any additional number of students that still need to be recorded. ");
	char_count = scanf("%d", &m);
	if (char_count < 0)
	{
		goto exit;
	}

	if(m < 0) {
		printf("Invalid input...");
		goto exit;
	}

	if (m > MAX_SIZE)
	{
		printf("Cannot process more than %i students!", MAX_SIZE);
		goto exit;
	}

	names = (char **) realloc(names, (n + m) * sizeof(char *));

	if(names == NULL) {
		printf("realloc failed...");
		goto exit;
	}
	ages = (int *) realloc(ages, (n + m) * sizeof(int));

	if(ages == NULL) {
		printf("realloc failed...");
		goto exit;
	}

	for(i = n; i < n + m; i++) {
		names[i] = (char *) malloc(15 * sizeof(char));
		printf("Please enter in student #%d's name. ", i + 1);
		char_count = scanf("%14s", names[i]);
		if (char_count < 0)
		{
			goto exit;
		}

		printf("Please enter in student #%d's age. ", i + 1);
		char_count = scanf("%d", &(*(ages + i)));
		if (char_count < 0)
		{
			goto exit;
		}
	}

	for(i = 0; i < (m + n); i++) {
		printf("Student %d's name is: %s, age: %d\n", i + 1, names[i], *(ages + i));
	}

	exit_code = 0;

	exit:
	clean_up(names, ages, n + m);
	exit(exit_code);
}