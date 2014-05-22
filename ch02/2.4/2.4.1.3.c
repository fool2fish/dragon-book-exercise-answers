/*   
   Compile with:
     gcc -Wall -o parser 2.4.1.3.c
   Run: ./parser

  Again, the grammar given cannot be used as provided, because it is not clear which production
  to use when a '0' is read. In particular, FIRST('0' S '1') is not disjoint with FIRST('0' '1'), which
  makes it ambiguous for a predictive parser to move forward.
  One solution would be to recursively try the first production, and if unsuccessfull, try the second alternative.
  This would still make it a recursive descent parser, but there is a more intelligent approach.

  The grammar can be "factored out" by bringing together those rules where FIRST is '0'. The following grammar
  is equivalent to the original one, with the advantage that it can be implemented using a predictive parser:

  S -> '0' R
  R -> S '1' | '1'

  Basically, the rules starting with '0' were merged into a single rule, and the rest of the job delegated to R.
  Since S always starts with a '0' now, it is trivial to implement a predictive parser for this tweaked version.
*/

#include <stdio.h>

/* The lookahead symbol */
int token;

int gettoken(void) {
  return token = getchar();
}

void r(void);
void s(void) {
  if (token != '0')
    fprintf(stderr, "Syntax error.\n");
  else {
    gettoken();
    r();
  }
}

void r(void) {
  switch (token) {
  case '0':
    s();
    if (token != '1')
      fprintf(stderr, "Syntax error.\n");
    gettoken();
    break;
  case '1':
    gettoken();
    break;
  default:
    fprintf(stderr, "Syntax error.\n");
    break;
  }
}

int main(void) {
  printf("Please enter expressions according to the following grammar:\n");
  printf("S -> '0' S '1' | '0' '1'\n");
  while (1) {
    printf("> ");
    fflush(stdout);
    gettoken();
    s();
  }
  return 0;
}
