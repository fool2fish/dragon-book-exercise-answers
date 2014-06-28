%{
/ * def init ions of manifest constant s
LT , LE , EQ , NE , GT , GE ,
IF , THEN , ELSE , ID , NUMBER , RELOP */
%}
/ * regular def init ions */
del im
[ \t \n]
ws
{delim}+
letter
[A-Za-z]
digit
[0-9]
id
{letter} ({letter} l {digit } ) *
number
{digit}+ (\ . {digit}+ ) ? (E [+-] ?{digit }+ ) ?
%%
{ws}
if
then
else
{ id}
{number}
"<"
" <= "
"="
" <> "
">"
" >= "
{/* no act ion and no return */}
{return ( IF) ; }
{return (THEN ) ; }
{return (ELSE) ; }
{yylval = ( int ) installID ( ) ; rettirn ( ID ) ; }
{yylval = ( int ) installNum ( ) ; return (NUMBER) ; }
{yylval = LT ; return (RELOP ) ; }
{yylval = LE ; return (RELOP ) ; }
{yylval = EQ ; return (RELOP ) j }
{yylval = NE ; return (RELOP ) ; }
{yylval = GT ; return (RELOP ) ; }
{yylval = GE ; return (RELOP ) ; }
%%
int installID ( ) {/* funct ion to install the lexeme , whose
f irst character is pointed to by yytext ,
arid whose length is yyleng , into the
symbol table and return a pointer
thereto */
}
int installNum ( ) {/* s imilar to installID , but puts numer­
i cal constants into a separate table * /
}
