import java.util.Random;
import java.util.Scanner;

public class MineSweeper {

    public void startGame(int size)
    {
        int[][] grid = new int[size][size];
        int[][] gridShow = new int[size][size];
        int[] magicRabbit = new int[2];
        boolean[][] revealed = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                grid[i][j]= 0;
                gridShow[i][j]=0;
                revealed[i][j] = false;
            }
        }
        int mines = 10;
        Scanner scanner = new Scanner(System.in);
        magicRabbit = setGrid(grid,mines,size);
        boolean isGameRunning=true;
        int row =-1;
        int col =-1;
        String userInput ="";
        System.out.println("Hello and welcome to ...Treasure finder.\nTry to dig up the surrounding area without damaging the treasure.\nIf you hit a treasure, you brake it and lose.");
        System.out.println("You can place flag over where you think there is treasure to help you remember.\nGood luck and happy digging.\n\n");
        drawGrid(size,gridShow,revealed,row,col,false);
        while (isGameRunning)
        {
            while(!(userInput.equals("dig")||userInput.equals("flag")))
            {
                System.out.println("Would you like to [dig] or [flag] a spot on the map.");
                userInput = scanner.nextLine().toLowerCase();
            }
            if(userInput.equals("dig"))
            {
                do {


                    System.out.println("Which row would you like to dig.");
                    userInput = scanner.nextLine();
                    try {
                        row = Integer.parseInt(userInput);
                        row--;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }while (row ==-1);
                do {


                    System.out.println("Which column would you like to dig.");
                    userInput = scanner.nextLine();
                    try {
                        col = Integer.parseInt(userInput);
                        col--;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }while (col ==-1);
                if(row==magicRabbit[0]&&col==magicRabbit[1])
                {
                    System.out.println("OMG you found a magic rabbit. it reveals the surrounding area.\nYOU WIN!");
                    drawGrid(size, grid, revealed, row, col, false);
                    isGameRunning=false;
                }else
                if(selectSquare(row,col,grid,gridShow,revealed,size-1,isGameRunning))
                {
                    isGameRunning=false;
                    drawGrid(size, gridShow, revealed, row, col, true);
                }
                else {
                    drawGrid(size, gridShow, revealed, row, col, false);
                }
                row=-1;
                col=-1;
            }else
            {
                do {


                    System.out.println("Which row would you like to place a flag.");
                    userInput = scanner.nextLine();
                    try {
                        row = Integer.parseInt(userInput);
                        row--;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }while (row ==-1);
                do {


                    System.out.println("Which column would you like to place a flag.");
                    userInput = scanner.nextLine();
                    try {
                        col = Integer.parseInt(userInput);
                        col--;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }while (col ==-1);
                placeFlag(row,col,gridShow,revealed,mines);
                drawGrid(size,gridShow,revealed,row,col,false);
                row=-1;
                col=-1;
            }
            if(winGame(revealed,size))
            {
                System.out.println("You found all the treaure and won the game.");
                isGameRunning =false;
            }
        }
    }


    private boolean winGame(boolean[][] reveal, int size)
    {
        int count =0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(reveal[i][j]==false)
                {
                    count++;
                }
            }
        }
        if (count==10)
        {
            return true;
        }
        return false;
    }

    private int placeFlag(int row, int col, int[][] fake,boolean[][] reveal,int mines)
    {
        if(reveal[row][col] == false) {
            if (fake[row][col] == 9) {
                fake[row][col] = 0;
                mines++;
            } else {
                fake[row][col] = 9;
                mines--;
            }
        }
        return mines;
    }

    private boolean selectSquare(int row, int col,int[][] real, int[][] fake,boolean[][] reveal,int size,boolean hasLost)
    {
        reveal[row][col]= true;
        fake[row][col]=real[row][col];
        if(real[row][col]==0)
        {
            if(row>0) {
                if (col > 0) {
                    if(reveal[row-1][col-1]==false) {
                        selectSquare(row - 1, col - 1, real, fake, reveal, size,hasLost);
                    }
                }
                if (col < size) {
                    if(reveal[row-1][col+1]==false) {
                        selectSquare(row - 1, col + 1, real, fake, reveal, size,hasLost);
                    }
                }
                if(reveal[row-1][col]==false) {
                    selectSquare(row - 1, col, real, fake, reveal, size,hasLost);
                }
            }
            if(row<size){
                if (col > 0) {
                    if(reveal[row+1][col-1]==false) {
                        selectSquare(row + 1, col - 1, real, fake, reveal, size,hasLost);
                    }
                }
                if (col < size) {
                    if(reveal[row+1][col+1]==false) {
                        selectSquare(row + 1, col + 1, real, fake, reveal, size,hasLost);
                    }
                }
                if(reveal[row+1][col]==false) {
                    selectSquare(row + 1, col, real, fake, reveal, size,hasLost);
                }
            }
            if(col>0) {
                if(reveal[row][col-1]==false) {
                    selectSquare(row, col - 1, real, fake, reveal, size,hasLost);
                }
            }
            if(col<size) {
                if(reveal[row][col+1]==false) {
                    selectSquare(row, col + 1, real, fake, reveal, size,hasLost);
                }
            }
        }
        // if bomb selected
        if (real[row][col] == 9){
            System.out.println("GAME OVER");
            for (int i = 0; i < real.length; i++) {
                for (int j = 0; j < real.length; j++) {
                    if (real[i][j] == 9){
                        fake[i][j] = 9;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private int[] setGrid(int[][] grid,int mines,int size)
    {
        int row=0;
        int col=0;
        int[] mr= new int[2];
        Random rnd = new Random();
        while (mines+1>0) {
            row = rnd.nextInt(size);
            col = rnd.nextInt(size);
            if(grid[row][col]==0) {
                if (mines!=1) {
                    grid[row][col] = 9;
                    mines--;
                }else
                {
                    mr[0]=row;
                    mr[1]=col;
                    mines--;
                }
            }

        }

        for (int i = 0; i <size ; i++) {
            for (int j = 0; j < size; j++) {
                int count = 0;

                if(grid[i][j]==9)
                {
                    continue;
                }

                //<editor-fold desc="Check upper">
                if (i>0)
                {
                    //check upper left square
                    if(j>0)
                    {
                        if(grid[i-1][j-1]==9)
                        {
                            count++;
                        }
                    }
                    //check upper right square
                    if(j<size-1)
                    {
                        if(grid[i-1][j+1]==9)
                        {
                            count++;
                        }
                    }
                    //check upper square
                    if(grid[i-1][j]==9)
                    {
                        count++;
                    }
                }
                //</editor-fold>

                //<editor-fold desc="Check lower">
                if(i<size-1)
                {
                    //check lower left square
                    if(j>0)
                    {
                        if(grid[i+1][j-1]==9)
                        {
                            count++;
                        }
                    }
                    //check lower right square
                    if(j<size-1)
                    {
                        if(grid[i+1][j+1]==9)
                        {
                            count++;
                        }
                    }
                    //check lower square
                    if(grid[i+1][j]==9)
                    {
                        count++;
                    }
                }
                //</editor-fold>

                //<editor-fold desc="Check sides">
                if(j>0)
                {
                    if(grid[i][j-1]==9)
                    {
                        count++;
                    }
                }
                if(j<size-1)
                {
                    if(grid[i][j+1]==9)
                    {
                        count++;
                    }
                }
                //</editor-fold>

                if(count!=0) {
                    grid[i][j] = count;
                }

            }
        }
        return mr;
    }

    private void drawGrid(int size, int[][] grid,boolean[][] reveal,int row, int col,boolean hasLost)
    {
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
        final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
        final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
        for (int i = 0; i <size ; i++) {
            System.out.println("");
            for (int j = 0; j < size; j++) {
                if(hasLost&&grid[i][j]==9)
                {
                    if(grid[i][j]==9&&hasLost)
                    {
                        System.out.print(" ");
                        System.out.print(ANSI_RED+ ANSI_BLACK_BACKGROUND+ grid[i][j] + ANSI_RESET);
                    }
                }else
                if(hasLost==false&&grid[i][j]==9)
                {
                    System.out.print(" ");
                    System.out.print(ANSI_GREEN_BACKGROUND+ grid[i][j] + ANSI_RESET);
                }
                else if(reveal[i][j]==false) {
                    System.out.print(ANSI_BLUE + " " + grid[i][j] + ANSI_RESET);
                }else {
                    if(i == row && j == col)
                    {
                        System.out.print(" ");
                        System.out.print(ANSI_RED+ANSI_CYAN_BACKGROUND + grid[i][j] + ANSI_RESET);
                    }
                    else {
                        System.out.print(ANSI_RED + " " + grid[i][j] + ANSI_RESET);
                    }
                }
            }
        }

        System.out.println("\n\n\n");

    }
}
