/*
   Compile with:
     gcc -Wall -o parser 2.4.1.2.c
   Run: ./parser

  The grammar given cannot be used as is, since it is left recursive.
  Recursive descent parsers cannot be implemented with a left recursive grammar,
  because it would loop forever!

  Instead, we apply the rule mentioned earlier in the book to convert this grammar
  to an equivalent grammar without left recursion. The general form of the rule is:
  
  A -> Aa | B

  is equivalent to:

  A -> BR
  R -> aR | epsilon

  In our case, A is S, and B is epsilon; we get:

  S -> R
  R -> '(' S ')' S
       | epsilon

  This is the grammar that the code below implements.
*/

#include <stdio.h>

/* The lookahead symbol */
int token;

int gettoken(void) {
  return token = getchar();
}

void r(void);
void s(void) {
  r();
}

void r(void) {
  if (token != '(')
    return; /* R -> epsilon */
  gettoken();
  s();
  if (token != ')') {
    fprintf(stderr, "Syntax error.\n");
    return;
  }
  gettoken();
  s();
}

int main(void) {
  printf("Please enter expressions according to the following grammar:\n");
  printf("S -> S '(' S ')' S | epsilon\n");
  while (1) {
    printf("> ");
    fflush(stdout);
    gettoken();
    s();
  }
  return 0;
}
