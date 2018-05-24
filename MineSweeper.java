import java.util.Random;
import java.util.Scanner;

public class MineSweeper {

    public void startGame(int size)
    {
        int[][] grid = new int[size][size];
        int[][] gridShow = new int[size][size];
        boolean[][] revealed = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                grid[i][j]= 0;
                gridShow[i][j]=0;
                revealed[i][j] = false;
            }
        }
        int mines = 10;
        setGrid(grid,mines,size);
        drawGrid(size,grid,revealed);
        drawGrid(size,gridShow,revealed);
        selectSquare(5,4,grid,gridShow,revealed,size-1);
        drawGrid(size,gridShow,revealed);
    }

    private void selectSquare(int row, int col,int[][] real, int[][] fake,boolean[][] reveal,int size)
    {
        reveal[row][col]= true;
        fake[row][col]=real[row][col];
        if(real[row][col]==0)
        {
            if(row>0) {
                if (col > 0) {
                    if(reveal[row-1][col-1]==false) {
                        selectSquare(row - 1, col - 1, real, fake, reveal, size);
                    }
                }
                if (col < size) {
                    if(reveal[row-1][col+1]==false) {
                        selectSquare(row - 1, col + 1, real, fake, reveal, size);
                    }
                }
                if(reveal[row-1][col]==false) {
                    selectSquare(row - 1, col, real, fake, reveal, size);
                }
            }
            if(row<size){
                if (col > 0) {
                    if(reveal[row+1][col-1]==false) {
                        selectSquare(row + 1, col - 1, real, fake, reveal, size);
                    }
                }
                if (col < size) {
                    if(reveal[row+1][col+1]==false) {
                        selectSquare(row + 1, col + 1, real, fake, reveal, size);
                    }
                }
                if(reveal[row+1][col]==false) {
                    selectSquare(row + 1, col, real, fake, reveal, size);
                }
            }
            if(col>0) {
                if(reveal[row][col-1]==false) {
                    selectSquare(row, col - 1, real, fake, reveal, size);
                }
            }
            if(col<size) {
                if(reveal[row][col+1]==false) {
                    selectSquare(row, col + 1, real, fake, reveal, size);
                }
            }
        }
    }

    private void setGrid(int[][] grid,int mines,int size)
    {
        int row=0;
        int col=0;
        Random rnd = new Random();
        while (mines>0) {
            row = rnd.nextInt(size);
            col = rnd.nextInt(size);
            if(grid[row][col]==0) {
                grid[row][col] = 9;
                mines--;
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
    }

    private void drawGrid(int size, int[][] grid,boolean[][] reveal)
    {
        final String ANSI_BLUE = "\u001B[34m";
        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        for (int i = 0; i <size ; i++) {
            System.out.println("");
            for (int j = 0; j < size; j++) {
                if(reveal[i][j]==false) {
                    System.out.print(ANSI_BLUE + " " + grid[i][j] + ANSI_RESET);
                }else {
                    System.out.print(ANSI_RED + " " + grid[i][j] + ANSI_RESET);
                }
            }
        }
        System.out.println("\n\n\n");

    }
}
