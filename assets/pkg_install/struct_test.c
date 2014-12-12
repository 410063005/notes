#include <stdio.h>

int do_hello(char **arg, char reply[100]) {
	printf("%s arg0=%s arg1=%s\n", "do_hello", arg[0], arg[1]);
	
	return 0;
}

int do_haha(char **arg, char reply[100]) {
	printf("%s arg0=%s arg1=%s\n", "do_haha", arg[0], arg[1]);
	return 0;
}

struct cmdinfo {
	const char *name;
	unsigned numargs;
	int (*func)(char **arg, char reply[100]);
};

struct cmdinfo cmds[] = {
	{"hello", 0, do_hello},
	{"haha", 0, do_haha},
};

int main(int argc, const char *argv[]) {
	if (argc <= 1) {
		fprintf(stderr, "Too few arguments\n");
		exit(1);
	}
	int i;
	char reply[100];
	for (i = 0; i < sizeof(cmds) / sizeof(cmds[0]); i++) {
		if (!strcmp(cmds[i].name, argv[1])) {
			int ret = cmds[i].func(argv + 1, reply);
		}
	}
	return 0;
}
