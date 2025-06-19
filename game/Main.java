package game;

/* What do I want to achieve here
- Display player board
- Display enemy board
- Each board shows the ships placed -- OMIT
- Each board shows the hits and misses -- DONE

Control:
- Turn base control for the game
- Prompt user to place ships on the board
- Prompt user to input the location to fire
- Prompt user to end the placing ships phase
*/

// A board will be a 10x10 grid with a hit is represented as O and miss is X
/*
 * Player Board:
    1 2 3 4 5 6 7 8 9 10
  +---------------------+
A | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
B | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
C | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
D | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
E | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
F | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
G | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
H | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
I | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
J | ~ ~ ~ ~ ~ ~ ~ ~ ~ ~ |
  +---------------------+
 * 
 * 
 * 
 */

public class Main {
    public static void main(String[] args) {

        // Start the game
        int[][] board = new int[10][10];

        String[] ships = {"A1","A2","A3"};
        String[] ships2 = {"A1", "B1", "C1"};
        printBoard(board);
        setShip(ships,board,3);
        action("A2",board);
        setShip(ships2,board,3);
        action("B1",board);
        action("A1",board);
        //action("A11",board);
        printBoard(board);
    }

    /*
     * Board is dynamically print based on pre-determined size
     * 
     * In the board
     * print 'X' if slot = -1
     * print 'O' if slot = 1
     * else print '~'
     */
    public static void printBoard(int[][] board) {
        int ROW_START = 65;
        char slotState = '~';
        /*------------------------------------------------------------------- */
        // Print out first line of numbers
        System.out.print("    ");
        for (int column = 0; column < board[0].length; column++) {
            System.out.print(column + 1);
            System.out.print(" ");
        }
        System.out.println();
        /*------------------------------------------------------------------- */
        // Print out the top edge of the board
        System.out.print("  +-");
        for (int a = 0; a < board.length; a++) {
            System.out.print("--");
        }
        System.out.println("+");
        /*------------------------------------------------------------------- */
        // Print out the board

        for (int row = 0; row < board.length; row++) {
            System.out.print(Character.toString((char) ROW_START + row) + " |");
            for (int column = 0; column < board[0].length; column++) {
                switch (board[row][column]) {
                    case 1:
                        slotState = 'O';
                        break; // Hit
                    case -1:
                        slotState = 'X';
                        break; // Miss
                    default:
                        slotState = '~'; // Available
                }
                System.out.print(" " + slotState);
            }
            System.out.println(" |");
        }
        /*------------------------------------------------------------------- */
        // Print the bottom edge
        System.out.print("  +-");
        for (int a = 0; a < board.length; a++) {
            System.out.print("--");
        }
        System.out.println("+");
        System.out.println("");
        System.out.println("*-------------------------------------------------------*");
        System.out.println("");
    }

    /*
     * Coordinate is the combination of row-column. Example format: A6, B10, J1, F5,
     * etc.
     * 
     * the slot will change based on the following scenarios:
     * > 1 if hit (meaning slot has number from 2-5)
     * > -1 if miss
     * 
     * Parameter
     * - coordinate: as mentioned above
     * - board: the board that needs to take action
     * 
     * Return
     * true: action success
     * false: action fail, retake action
     */
    public static boolean action(String coordinate, int[][] board) {
        int ROW_START = 65; // Represent the first row of the board, aka row 0

        char rowChar = coordinate.charAt(0);
        int row = ((int) rowChar - ROW_START);
        int column = 0;
        try {
            column = Integer.parseInt(coordinate.substring(1)) -1;
        } catch (NumberFormatException e) {
            System.out.println(
                    coordinate + "is an invalid coordinate, please follow this format: A5, B6, G10, F9, etc. ");
                    return false;
        }
        // Case that column exceeds the board size
        if (column > board[0].length-1 || row > board[0].length-1 || row < 0 || column < 0) {
            System.out.println("bruh, are you trying to sink ships on Mars? That’s not even on the map 💀📈");
            return false;
        }

        // Logic check for action
        if (board[row][column] == 0) // a miss
        {
            board[row][column] = -1;
            return true;
        } else if (board[row][column] >= 2) // a hit
        {
            board[row][column] = 1;
            return true;
        } else // case slot is already taken action or invalid slot (-1, 1, or else)
        {
            System.out.println("Fr? You already tried that spot. Giving déjà vu energy rn 👀✨ Reshoot");
            return false;
        }
    }

    /*
     * Set ship
     * 
     * Parameter:
     * - coordinate: list of coordinates to set ship
     * - board: the player board
     * - numsOfShips: maybe delete later
     * 
     * Return:
     * - false: not able to set ship, coordinate already used or sth else, make no changes
     * - true: set ship successfully
     */

    public static boolean setShip(String[] coordinate, int[][] board, int numOfShips) {
        int[][] originalBoard = board;
        int ROW_START = 65; // Represent the first row of the board, aka row 0
        char rowChar;
        int row;
        int column = 0;

        // When we set ships, we give it a bunch of coordinates representing the ships,
        // therefore we need a for loop to set them
        for (int ship = 0; ship < numOfShips; ship++) {
            rowChar = coordinate[ship].charAt(0);
            row = ((int) rowChar - ROW_START);

            try {
                column = Integer.parseInt(coordinate[ship].substring(1)) -1;
            } catch (NumberFormatException e) {
                System.out.println(
                        coordinate + "is an invalid coordinate, please follow this format: A5, B6, G10, F9, etc. ");
                    return false;
            }

            // Case that column exceeds the board size
            if (column > board[0].length-1 || row > board[0].length-1 || row < 0 || column < 0) {
                System.out.println("You tryna park your ship in the void? That’s not even on the board, captain 💀🌊");
                return false;
            }

            switch (originalBoard[row][column]) {
                case 0:
                    originalBoard[row][column] = numOfShips;
                    break;
                default: {
                    System.out.println("This coordinate " + coordinate[ship] + " has conflict, aborting change...");
                    return false;
                }
            }
        }

        // only change if all the ships location can be set
        board = originalBoard;
        return true;
    }



}
