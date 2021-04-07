#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

typedef struct Words {

	char ** words;
	int num_of_words;
} Words;

int display_menu(char buffer[50]);
int generate_rand();
int prompt_cipher(char * cipher, struct Words ** words);
char * read_cipher(char buffer[50]);
char * read_encoded_text(char buffer[50]);
char * prompt_secret();
void encode_message(char * message, struct Words ** words, int word_len, int rand);
char * decode_message(struct Words * words, int word_len);
int read_contents(char ** file_contents);
void write_message(char * encoded, int size);
int determine_word_count(char * cipher_contents);
int find_last_index(char * str, char needle);
int find_nth_index(char * str, char needle, int n, int * curr_index);
char * find_nth_word(char * cipher, int n);
int find_nth_position(char * str, int n, int * reached_space);
void init_words(char ** word_arr, int word_count);
void fill_words(char ** word_arr, char * word, int n);
char * word_to_lower(char * word);
void clean_up(struct Words ** words, int word_count);

int display_menu(char buffer[50]) {

	int numeric_input;

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

	return numeric_input;
}

int generate_rand() {

	return (rand() % 2) + 1;
}

char * read_encoded_text(char buffer[50]) {

	int len;

	do {
		printf("Please enter the name of the encoded text file.\n");
		fgets(buffer, 50, stdin);
		buffer[strcspn(buffer, "\n")] = '\0';
		len = strlen(buffer);
	}
	while(len == 1);

	return buffer;
}

char * read_cipher(char buffer[50]) {

	int len;

	do {
		printf("Please enter the name of the cipher file to scan.\n");
		fgets(buffer, 50, stdin);
		buffer[strcspn(buffer, "\n")] = '\0';
		len = strlen(buffer);
	}
	while(len == 1);

	return buffer;
}

int prompt_cipher(char * cipher, struct Words ** words) {

	FILE * file;
	int i;
	char cipher_contents[10000];
	char * word;
	int file_size = 0, word_count = 0;

	
	file = fopen(cipher, "r");

	if(file == NULL) {

		printf("COULD NOT READ FILE.");

		exit(-1);
	}

	fseek(file, 0, SEEK_END);
	file_size = ftell(file);
	fseek(file, 0, SEEK_SET);

	fread(cipher_contents, file_size, 1, file);

	cipher_contents[file_size] = '\0';


	word_count = determine_word_count(cipher_contents);
	
	(*words)->words = (char **) malloc(word_count * sizeof(char *));
	init_words((*words)->words, word_count);

	for(i = 0; i < word_count; i++) {

		word = find_nth_word(cipher_contents, i + 1);
		fill_words((*words)->words, word, i);
	}
	
	fclose(file);

	return word_count;
}

char * prompt_secret() {

	int len;
	char secret_message[1500];

	do {
		printf("Please enter the secret message.\n");
		fgets(secret_message, 1500, stdin);
		secret_message[strcspn(secret_message, "\n")] = '\0';
		len = strlen(secret_message);
	}
	while(len == 0);

	return strdup(secret_message);
}

void encode_message(char * message, struct Words ** words, int word_len, int rand) {

	int i, index_in_word, found_index, pos, index, j = 0, size = 0;
	char curr_char;
	char ** word_list = (*words)->words;
	char * curr_word;
	int len = strlen(message);

	char * encrpyted_message = (char *) malloc(10 * len * sizeof(char));

	for(i = 0; i < len; i++) {

		pos = 0, j = 0, index = 0;
		found_index = -1, index_in_word = -1;
		curr_char = tolower(*(message + i));

		if(isspace(curr_char)) { 
			size += snprintf(encrpyted_message + size, 10 * len, " ");
			continue;
		}

		while(j < word_len) {

			curr_word = *(word_list + j);

			if((found_index = find_nth_index(curr_word, curr_char, rand, &index)) != -1) {

				index_in_word = strcspn(curr_word, &curr_char);
				break;
			}
			else {

				pos++;
			}

			if((j == word_len - 1) && (index != 0)) {

				j = -1;
			}

			j++;
		}

		if((found_index == -1) || (index_in_word == -1)) {

			size += snprintf(encrpyted_message + size, 10 * len, "%c", '#');
		}
		else {

			size += snprintf(encrpyted_message + size, 10 * len, "%d,%d", pos, index_in_word);
		}

		if(i != len - 1) {

			size += snprintf(encrpyted_message + size, 10 * len, "%c", ',');
		}
	}

	write_message(encrpyted_message, size);
	free(encrpyted_message);
}

int read_contents(char ** file_contents) {

	FILE * f;
	int len, file_size;
	char buffer[50];

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
		exit(-1);
	}

	fseek(f, 0, SEEK_END);
	file_size = ftell(f);
	fseek(f, 0, SEEK_SET);

	*file_contents = (char *) malloc(file_size * sizeof(char) + 1);

	if((*file_contents) == NULL) {

		printf("CANNOT ALLOCATE MEMORY...");
		exit(-1);
	}

	fread(*file_contents, file_size, 1, f);

	(*file_contents)[file_size] = '\0';
	fclose(f);

	return file_size;
}

int get_num_indices(char * encoded_buffer) {

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

	read_contents(&encoded_buffer);
	len = get_num_indices(encoded_buffer);

	buffer = (char *) malloc((len / 2) * sizeof(char) + 1);

	for(i = 0; i < len; i++) {

		reached_space = 0;
		cpy = strdup(encoded_buffer);
		position = find_nth_position(cpy, i, &reached_space);
		free(cpy);

		if(reached_space) { buffer[index++] = ' '; }

		if(position == -1) { break; }

		if(position == -2) {

			buffer[index++] = '#';
			offset++;
			continue;
		}

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

	printf("decoded message: %s\n", buffer);
	free(encoded_buffer);
	return buffer;
}

void write_message(char * encrpyted_message, int size) {

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
		exit(-1);
	}

	fwrite(encrpyted_message, sizeof(char), size, f);

	fclose(f);
}

int determine_word_count(char * cipher_contents) {

	int i, word_count = 0, reached_space = 0;
	int len = strlen(cipher_contents);

	for(i = 0; i< len; i++) {

		if((reached_space) && (isspace(cipher_contents[i]))) {

			continue;
		}

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

	int len = strlen(str);
	int pos = len;
	str += len;

	while((*(str--)) != needle) {

		pos--;
	}

	return pos;
}

int find_nth_index(char * str, char needle, int n, int * current_index) {

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

		if(isspace(cipher[i])) {

			++count;
			continue;
		}

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

	printf("first token is: %s\n", positions);

	while(positions != NULL) {

		if(n != count) {

			count++;
			positions = strtok(NULL, ",");
			printf("positions: %s\n", positions);
			continue;
		}

		printf("positions now is: %s\n", positions);

		if(strncmp(positions, "#", 1) == 0) {

			return -2;
		}

		if(isspace(*positions)) {

			*reached_space = 1;
		}

		return atoi(positions);
	}

	return -1;
}

void init_words(char ** word_arr, int word_count) {

	int i;

	for(i = 0; i< word_count; i++) {

		word_arr[i] = (char *) malloc(16 * sizeof(char));
	}
}

void fill_words(char ** word_arr, char * word, int n) {

	if(word != NULL) {

		*(word_arr + n) = word_to_lower(word);
	}
}

char * word_to_lower(char * word) {

	int i;
	int len = strlen(word);

	for(i = 0; i< len; i++) {

		*(word + i) = tolower(*(word + i));
	}

	return word;
}

void clean_up(struct Words ** words, int word_count) {

	int i;
	char * str;

	if((*words) != NULL) {
		
		for(i = 0; i< word_count; i++) {

			free((*words)->words[i]);
		}
		free((*(words))->words);
		free(*words);
	}
}

int main(void) {

	struct Words * words = (struct Words *) malloc(1 * sizeof(struct Words));

	char input_buffer[50];
	char * secret_message;
	char * decoded_message;
	char * file_name;
	char input;
	int rand;
	int word_len = 0;
	int has_file = 0;

	srand(841);
	rand = generate_rand();

	printf("rand is: %d\n", rand);

	while(input != '4') {

		input = display_menu(input_buffer);

		if(input == 1) {

			has_file = 1;
			file_name = read_cipher(input_buffer);
			word_len = prompt_cipher(file_name, &words);
			printf("word len is: %d\n", word_len);
		}

		if(input == 2) {

			if(!has_file) {
				file_name = read_cipher(input_buffer);
				word_len = prompt_cipher(input_buffer, &words);
				has_file = 1;
			}

			secret_message = prompt_secret();
			encode_message(secret_message, &words, word_len, rand);
			free(secret_message);
		}

		if(input == 3) {

			if(!has_file) {
				file_name = read_cipher(input_buffer);
				word_len = prompt_cipher(input_buffer, &words);
				has_file = 1;
			}

			decoded_message = decode_message(words, word_len);
			free(decoded_message);
		}

		if(input == 4) {
			break;
		}
	}

	clean_up(&words, word_len);

	return 0;
}