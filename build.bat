javac "src\Main.java" "src\tictactoe\TicTacToe.java" "src\tictactoe\Vec2.java"
jar cvfe tictactoe.jar Main -C "src" "." ".\tictactoe"

PAUSE