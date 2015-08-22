#include <stdio.h>

int f(int x, int *py, int **ppz) {
	**ppz += 1;
	*py += 2;
	x += 3;
	return x + *py + **ppz;

}

int main() {
	int c = 4;
	int *b = &c;
	int **a = &b;
	printf("%d\n", f(c, b, a));
}
