/*
 *Brian Limaye, G01260841
 *CS 262, Lab Section 212
 *Project 2
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

/*
 *Struct to keep track of the array of words, along with the amount of words
 */
struct Words {

	char ** words;
	int word_count;
};

/*
 *Method Prototypes
 */
int display_menu(char buffer[50]);
int generate_rand();
int prompt_cipher(char * cipher, struct Words ** words);
char * read_cipher(char buffer[50]);
char * read_encoded_text(char buffer[50]);
char * prompt_secret();
void encode_message(char * message, struct Words ** words, int word_len, int rand);
char * decode_message(struct Words * words, int word_len);
int read_contents(struct Words ** words, char ** file_contents);
void write_message(char * encoded, struct Words ** words, int size);
int determine_word_count(char * cipher_contents);
int find_first_index(char * haystack, char needle);
int find_last_index(char * str, char needle);
int find_nth_index(char * str, char needle, int n, int * curr_index);
char * find_nth_word(char * cipher, int n);
int find_nth_position(char * str, int n, int * reached_space);
void init_words(char ** word_arr, int word_count);
void fill_words(char ** word_arr, char * word, int n);
char * word_to_lower(char * word);
void clean_up(struct Words * words, int word_count);

/*
 *indexOf function to find a character in a given string.
 */
int find_first_index(char * haystack, char needle) {

	int index = 0;

	/*
	 *Iterates through the pointer until a desired character is found
	 */
	while((*haystack) != '\0') {

		if((*haystack) == needle) {

			return index;
		}
		haystack++;
		index++;
	}

	/*
	 *Returns -1 when a match isn't found
	 */
	return -1;
}


int display_menu(char buffer[50]) {

	
	int numeric_input;

	/*
	 *Displays the main menu, and recieves an input from the user to continue execution
	 */
	do {
		printf("Welcome to the cipher encoder/decoder program!\n");
		printf("(1) for scanning an existing cipher file.\n");
		printf("(2) for encoding a message in a new cipher file.\n");
		printf("(3) for decoding an existing cipher file.\n");
		printf("(4) for exiting the program.\n");

		fgets(buffer, 50, stdin);
		numeric_input = atoi(&buffer[0]);
	}
	while((((numeric_input != 1) && (numeric_input != 2) && (numeric_input != 3) && (numeric_input != 4))) || (buffer[1] != '\n'));

	/*
	 *Ensures that an acceptable input is recieved from the user, being 1, 2, 3, or 4.
	 */

	return numeric_input;
}

int generate_rand() {

	/*
	 *Generates a random number from 0 to 9, inclusive.
	 */
	return (rand() % 10);
}

char * read_encoded_text(char buffer[50]) {

	int len;

	/*
	 *Reads the name of an encoded text file from the user.
	 */
	do {
		printf("Please enter the name of the encoded text file.\n");
		fgets(buffer, 50, stdin);
		/*
		 *Removes the newline added in fgets().
		 */
		buffer[strcspn(buffer, "\n")] = '\0';
		len = strlen(buffer);
	}
	while(len == 1);

	return buffer;
}

char * read_cipher(char buffer[50]) {

	int len;

	/*
	 *Reads the name of a plain text file from the user, to use as a cipher
	 */
	do {
		printf("Please enter the name of the cipher file to scan.\n");
		fgets(buffer, 50, stdin);
		/*
		 *Removes the newline added in fgets().
		 */
		buffer[strcspn(buffer, "\n")] = '\0';
		len = strlen(buffer);
	}
	while(len == 1);

	return buffer;
}

int prompt_cipher(char * cipher, struct Words ** words) {

	FILE * file;
	int i;
	char * cipher_contents;
	char * word;
	int file_size = 0, word_count = 0;

	
	/*
	 *Opens the file inputted from the user, to be read into cipher_contents.
	 */
	file = fopen(cipher, "r");

	if(file == NULL) {

		printf("COULD NOT READ FILE.");
		clean_up(*words, (*words)->word_count);
		exit(-1);
	}

	/*
	 *Gets the size of the file, the number of characters present.
	 */
	fseek(file, 0, SEEK_END);
	file_size = ftell(file);
	fseek(file, 0, SEEK_SET);

	/*
	 *Puts the characters from the file into cipher_contents
	 */

	cipher_contents = (char *) malloc(file_size * sizeof(char));

	if(cipher_contents == NULL) {

		printf("COULD NOT ALLOCATE MEMORY");
		fclose(file);
		clean_up(*words, (*words)->word_count);
		exit(-1);
	}

	fread(cipher_contents, file_size, 1, file);

	cipher_contents[file_size] = '\0';

	word_count = determine_word_count(cipher_contents);

	(*words)->word_count = word_count;
	
	(*words)->words = (char **) malloc(word_count * sizeof(char *));

	if(word_count > 5000) {

		word_count = 5000;
	}

	/*
	 *Sets each word in the array to the found words in the file.
	 */
	for(i = 0; i < word_count; i++) {

		word = find_nth_word(cipher_contents, i + 1);
		fill_words((*words)->words, word, i);
	}
	
	fclose(file);

	free(cipher_contents);
	return word_count;
}

char * prompt_secret() {

	int len;
	char secret_message[1500];

	/*
	 *Reads in a secret message from the user, removing the newline added from fgets.
	 */
	do {
		printf("Please enter the secret message.\n");
		fgets(secret_message, 1500, stdin);
		secret_message[strcspn(secret_message, "\n")] = '\0';
		len = strlen(secret_message);
	}
	while(len == 0);

	/*
	 *Returns a copy of secret_message, allocating memory, under the covers.
	 */
	return strdup(secret_message);
}

void encode_message(char * message, struct Words ** words, int word_len, int rand) {

	int i, index_in_word, found_index, pos, index, j = 0, size = 0;
	char curr_char;
	char ** word_list = (*words)->words;
	char * curr_word;
	int len = strlen(message);

	/*
	 *Buffer to store the encrypted message.
	 */
	char * encrypted_message = (char *) malloc(10 * len * sizeof(char));

	for(i = 0; i < len; i++) {

		pos = 0, j = 0, index = 0;
		found_index = -1, index_in_word = -1;
		curr_char = tolower(*(message + i));

		/*
		 *If a space is encountered in the message, a space is added to the encrpyted_message buffer at that given position.
		 */
		if(isspace(curr_char)) { 
			size += snprintf(encrypted_message + size, 10 * len, " ");
			continue;
		}

		while(j < word_len) {

			curr_word = *(word_list + j);

			/*
			 *Finds the correct index of the nth instance of a certain character in the cipher file, based on the random number.
			 */
			if((found_index = find_nth_index(curr_word, curr_char, rand, &index)) != -1) {

				/*
				 *Finds the index of the current character in the current word.
				 */
				index_in_word = find_first_index(curr_word, curr_char);
				break;
			}
			else {

				pos++;
			}

			/*
			 *Occurs when the file needs to be re-looped in the case where the nth occurrence of a character hasn't been reached yet, even though it exists.
			 */
			if((j == word_len - 1) && (index != 0)) {

				j = -1;
			}

			j++;
		}

		/*
		 *Occurs when a given character is not found in the text, a # is used.
		 */
		if((found_index == -1) || (index_in_word == -1)) {

			size += snprintf(encrypted_message + size, 10 * len, "%c", '#');
		}
		else {

			size += snprintf(encrypted_message + size, 10 * len, "%d,%d", pos, index_in_word);
		}

		/*
		 *Appends a comma to separate ordered pairs, if not at the end.
		 */
		if(i != len - 1) {

			size += snprintf(encrypted_message + size, 10 * len, "%c", ',');
		}
	}

	write_message(encrypted_message, words, size);
	free(encrypted_message);
}

int read_contents(struct Words ** words, char ** file_contents) {

	/*
	 *This function reads the contents of an encrypted file and sets it to file_contents.
	 */

	FILE * f;
	int len, file_size;
	char buffer[50];

	/*
	 *Reads in the file name of the encoded file.
	 */
	do {

		printf("Please enter in a file name to be decoded.\n");
		fgets(buffer, 50, stdin);
		buffer[strcspn(buffer, "\n")] = '\0';
		len = strlen(buffer);
	}
	while(len == 0);

	f = fopen(buffer, "r");

	if(f == NULL) {

		printf("COULD NOT OPEN FILE FOR READING");
		free(*file_contents);
		clean_up(*words, (*words)->word_count);
		exit(-1);
	}

	/*
	 *Gets the length of the encoded file.
	 */
	fseek(f, 0, SEEK_END);
	file_size = ftell(f);
	fseek(f, 0, SEEK_SET);

	*file_contents = (char *) malloc(file_size * sizeof(char) + 1);

	if((*file_contents) == NULL) {

		printf("CANNOT ALLOCATE MEMORY...");
		fclose(f);
		clean_up(*words, (*words)->word_count);
		exit(-1);
	}

	fread(*file_contents, file_size, 1, f);

	(*file_contents)[file_size] = '\0';
	fclose(f);

	return file_size;
}

int get_num_indices(char * encoded_buffer) {

	/*
	 *This function gets the number of indices that are delimitted by commas in a given encrypted file.
	 */

	char * buffer = strdup(encoded_buffer);
	int count = 0;

	char * str = strtok(buffer, ",");

	while(str != NULL) {

		str = strtok(NULL, ",");
		++count;
	}

	free(buffer);
	return count;
}

char * decode_message(struct Words * words, int word_len) {

	int i, len, reached_space, position, index = 0, offset = 0;
	char curr_char;
	char ** word_list = words->words;

	char * curr_word = NULL;
	char * encoded_buffer;
	char * cpy;
	char * buffer;

	read_contents(&words, &encoded_buffer);
	len = get_num_indices(encoded_buffer);

	/*
	 *Allocates a buffer that will hold the decoded message to print
	 */
	buffer = (char *) malloc((len / 2) * sizeof(char) + 1);

	for(i = 0; i < len; i++) {

		reached_space = 0;
		/*
		 *Creates a duplicate of the encoded buffer, to be used in decoding.
		 */
		cpy = strdup(encoded_buffer);
		position = find_nth_position(cpy, i, &reached_space);
		free(cpy);

		/*
		 *If a space is reached, the decoded message will contain a space.
		 */
		if(reached_space) { buffer[index++] = ' '; }

		/*
		 *If position is equal to -1, this indicates that there are no more indices to convert to characters.
		 */
		if(position == -1) { break; }

		/*
		 *Occurs in the case where a '#' is reached, indicating NO match for a character in a given cipher file.
		 */
		if(position == -2) {

			buffer[index++] = '#';
			/*
			 *Offset is used in the below logic, to determine which index is used for the position of a character in a word or the word number in a text.
			 */
			offset++;
			continue;
		}

		/*
		 *Gets the word number of a given character based on a random number, handling the case where the text has been looped around.
		 */
		position %= word_len;

		if((i - offset) % 2 == 0) {

			curr_word = *(word_list + position);
		}
		
		else {
			curr_char = *(curr_word + position);
			buffer[index++] = curr_char;
		}
	}

	buffer[index] = '\0';

	free(encoded_buffer);
	return buffer;
}

void write_message(char * encrpyted_message, struct Words ** words, int size) {

	/*
	 *Writes a message that has been encrypted to a file!
	 */

	FILE * f;
	int len;
	char buffer[50];

	do {
		printf("Please enter in a file name to save the encrpyted message to!\n");
		fgets(buffer, 50, stdin);
		buffer[strcspn(buffer, "\n")] = '\0';
		len = strlen(buffer);
	}
	while(len == 0);

	f = fopen(buffer, "w");

	if(f == NULL) {

		printf("COULD NOT OPEN FILE FOR WRITING.");
		clean_up(*words, (*words)->word_count);
		free(encrpyted_message);
		exit(-1);
	}

	fwrite(encrpyted_message, sizeof(char), size, f);

	fclose(f);
}

int determine_word_count(char * cipher_contents) {

	/*
	 *Determines the word count of a given cipher that has been read in by the user.
	 */

	int i, word_count = 0, reached_space = 0;
	int len = strlen(cipher_contents);

	for(i = 0; i< len; i++) {

		if((reached_space) && (isspace(cipher_contents[i]))) {

			continue;
		}

		/*
		 *In the case where a space is reached, the word count is increased, as spaces act as delimeters.
		 */
		if(isspace(cipher_contents[i])) {

			reached_space = 1;
			word_count++;
		}
		else {

			reached_space = 0;
			if(i == len - 1) {

				word_count++;
			}
		}
	}

	return word_count;
}

int find_last_index(char * str, char needle) {

	/*
	 *Finds the last occurrence of an index in a string.
	 */

	int len = strlen(str);
	int pos = len;
	str += len;

	while((*(str--)) != needle) {

		pos--;
	}

	return pos;
}

int find_nth_index(char * str, char needle, int n, int * current_index) {

	/*
	 *Finds the nth index of an index in a given string, mainly used in the encryption algorithm.
	 */

	int i;
	char curr_char;
	int len = strlen(str);

	for(i = 0; i < len; i++) {

		curr_char = tolower(*(str + i));

		if(curr_char == needle) {

			*current_index = *current_index + 1;
		}

		if((*current_index) == n + 1) {

			return i;
		}
	}

	return -1;
}

char * find_nth_word(char * cipher, int n) {

	char buffer[16];
	int index = 0;
	int i;
	int count = 1;
	int len = strlen(cipher);

	for(i = 0; i < len; i++) {

		/*
		 *Increments the count when a delimeter is found.
		 */
		if(isspace(cipher[i])) {

			++count;
			continue;
		}

		/*
		 *Ensures that a word over 15 characters isn't used.
		 */
		if(index > 14) {

			break;
		}

		if(count == n) {

			buffer[index++] = cipher[i];
		}
	}

	buffer[index] = '\0';

	if(strlen(buffer) == 0) {

		return NULL;
	}

	return strdup(buffer);
}

int find_nth_position(char * str, int n, int * reached_space) {

	int count = 0;

	char * positions = strtok(str, ",");

	/*
	 *Iterates through the indices of the encoded message.
	 */
	while(positions != NULL) {

		if(n != count) {

			count++;
			positions = strtok(NULL, ",");
			continue;
		}

		/*
		 *When a '#' is encountered. -2 is returned.
		 */
		if(strncmp(positions, "#", 1) == 0) {

			return -2;
		}

		/*
		 *When a ' ' is encountered. A flag is set, to ensure it's accounted for in the decoded message.
		 */
		if(isspace(*positions)) {

			*reached_space = 1;
		}

		return atoi(positions);
	}

	return -1;
}

void fill_words(char ** word_arr, char * word, int n) {

	if(word != NULL) {

		*(word_arr + n) = word_to_lower(word);
	}
}

char * word_to_lower(char * word) {

	/*
	 *Converts a word to purely lowercase, if not already.
	 */
	int i;
	int len = strlen(word);

	for(i = 0; i< len; i++) {

		*(word + i) = tolower(*(word + i));
	}

	return word;
}

void clean_up(struct Words * words, int word_count) {

	/*
	 *Cleans up any allocated memory in any/all functions that used dynamic memory.
	 */

	int i;

	if(words != NULL) {
		
		for(i = 0; i< word_count; i++) {

			free(words->words[i]);
		}

		if(word_count > 0) {
			
			free(words->words);
		}
		free(words);
	}
}

int main(void) {

	struct Words * words =  NULL;

	char input_buffer[50];
	char * secret_message;
	char * decoded_message;
	char * file_name;
	char input;
	int rand;
	int word_len = 0, has_file = 0;


	/*
	 *Seeds the RNG to the last four digits.
	 */
	srand(841);
	rand = generate_rand();

	while(input != '4') {

		input = display_menu(input_buffer);

		/*
		 *Choice 1
		 */
		if(input == 1) {

			/*
			 *Ensures that any memory allocated in previous choices is freed before continuing.
			 */
			if(words != NULL) {

				clean_up(words, word_len);
				words = NULL;
			}
			
			
			words = (struct Words *) malloc(1 * sizeof(struct Words));
			
			/*
			 *Indicates that a file has been read by the program.
			 */
			has_file = 1;
			file_name = read_cipher(input_buffer);
			word_len = prompt_cipher(file_name, &words);
		}

		/*
		 *Choice 2
		 */
		if(input == 2) {

			/*
			 *Handles the case where choice 1 is skipped, as an input option is given to the user.
			 */
			if(!has_file) {
				
				words = (struct Words *) malloc(1 * sizeof(struct Words));
				file_name = read_cipher(input_buffer);
				word_len = prompt_cipher(input_buffer, &words);
				has_file = 1;
			}

			secret_message = prompt_secret();
			encode_message(secret_message, &words, word_len, rand);
			free(secret_message);
		}

		/*
		 *Choice 3
		 */
		if(input == 3) {

			/*
			 *Handles the case where choice 1 is skipped, as an input option is given to the user.
			 */
			if(!has_file) {
				
				words = (struct Words *) malloc(1 * sizeof(struct Words));
				file_name = read_cipher(input_buffer);
				word_len = prompt_cipher(input_buffer, &words);
				has_file = 1;
			}

			decoded_message = decode_message(words, word_len);
			printf("Decoded Message: %s\n", decoded_message);
			free(decoded_message);
		}

		/*
		 *Exits the program in the case where choice 4 is chosen.
		 */
		if(input == 4) {
			break;
		}
	}

	clean_up(words, word_len);
	return 0;
}