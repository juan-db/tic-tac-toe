NAME = tictactoe.jar
# Can't use prefix function cus Digital Mars (I assume)
SRCS = src/Main.java src/tictactoe/TicTacToe.java src/tictactoe/Vec2.java

all: $(NAME)

$(NAME):
	javac $(SRCS)
	jar cvfe $(NAME) Main -C src "." "tictactoe"

clean:
# Can't use wildcard function cus Digital Mars
	rm -f src/*.class src/tictactoe/*.class

fclean: clean
	rm -f $(NAME)

re: fclean all

.PHONY: all clean fclean re
