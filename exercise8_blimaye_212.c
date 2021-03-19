#include <stdio.h>
#include <stdlib.h>

void clean_up(char ** names, int * ages, int n);

void clean_up(char ** names, int * ages, int n) {

	int i;

	for(i = 0; i < n; i++) {

		free(*names);
		*names = NULL;
		names++;
	}

	free(ages);
	ages = NULL;
}

int main() {

	//Problem 1
	int i;
	int n;
	printf("Please enter in the number of students to be recorded. ");
	scanf("%d", &n);

	if(n < 0) {

		printf("Invalid input...");
		exit(0);
	}

	char ** names = (char **) malloc(n * sizeof(char *));

	if(names == NULL) {

		printf("malloc failed...");
		exit(0);
	}
	int * ages = (int *) malloc(n * sizeof(int));

	if(names == NULL) {

		printf("malloc failed...");
	}

	for(i = 0; i < n; i++) {

		names[i] = (char *) malloc(15 * sizeof(char));
		printf("Please enter in student #%d's name. ", i + 1);
		scanf("%s", names[i]);

		printf("Please enter in student #%d's age. ", i + 1);
		scanf("%d", &(*(ages + i)));
	}

	for(i = 0; i < n; i++) {

		printf("Student %d's name is: %s, age: %d\n", i + 1, names[i], *(ages + i));
	}

	//Problem 2

	int m;
	printf("Please enter in any additional number of students that still need to be recorded. ");
	scanf("%d", &m);

	if(m < 0) {

		printf("Invalid input...");
		exit(0);
	}

	names = (char **) realloc(names, (n + m) * sizeof(char *));

	if(names == NULL) {

		printf("realloc failed...");
		exit(0);
	}
	ages = (int *) realloc(ages, (n + m) * sizeof(int));

	if(ages == NULL) {

		printf("realloc failed...");
		exit(0);
	}

	for(i = n; i < n + m; i++) {

		names[i] = (char *) malloc(15 * sizeof(char));
		printf("Please enter in student #%d's name. ", i + 1);
		scanf("%s", names[i]);

		printf("Please enter in student #%d's age. ", i + 1);
		scanf("%d", &(*(ages + i)));
	}

	for(i = 0; i < (m + n); i++) {

		printf("Student %d's name is: %s, age: %d\n", i + 1, names[i], *(ages + i));
	}

	clean_up(names, ages, n + m);
}