/* 
   Compile with:
     gcc -Wall -o parser 2.4.1.1.c
   Run: ./parser

   The code will quitely parse well-formed expressions. For bad expressions that don't
   follow the grammar, it will complain with "Syntax error."

   Some example input:
   > ++++aaaaa
   > +++--+-+-+-+-+++-a-aaaaaaaaaaaaaaaaaa
   > 

   Note that you canot use spaces. The grammar doesn't allow for that.
   We could change gettoken() to ignore spaces, but we'd be introducing
   a problem. You see, when reading the input, the last lookahead symbol
   left is a newline. If we ignored whitespace, we would block on the call
   to gettoken() in the last production, because gettoken() would ignore
   whitespaces! This is why a lot of languages require statements to be
   properly ended, for example, with a semi-colon.
*/

#include <stdio.h>

/* The lookahead symbol */
int token;

int gettoken(void) {
  return token = getchar();
}

void s(void) {
  if (token == 'a')
    gettoken();
  else if (token == '+' || token == '-') {
    gettoken();
    s();
    s();
  }
  else {
    fprintf(stderr, "Syntax error.\n");
  }
}

int main(void) {
  printf("Please enter expressions according to the following grammar:\n");
  printf("S -> '+' S S | '-' S S | 'a'\n");
  while (1) {
    printf("> ");
    fflush(stdout);
    gettoken();
    s();
  }
  return 0;
}

