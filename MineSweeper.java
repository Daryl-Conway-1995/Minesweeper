import java.util.Random;

public class MineSweeper {

    public void startGame(int size)
    {
        int[][] grid = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                grid[i][j]= 0;
            }
        }
        int mines = (int)Math.sqrt(size)*2;

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
                    if(j<size)
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
                if(i<size)
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
                    if(j<size)
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
            }
        }
    }
}
