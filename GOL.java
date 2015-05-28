import java.awt.Color;

public class GOL{
    private int generations = 0;
    private Draw draw = new Draw();

    public GOL(int N, int T, int S) {
        boolean t = true;

        // positions of cells
        double[] px = new double[(N*2)+1];
        double[] py = new double[(N*2)+1];

        // cell alive = 1, cell dead = 0
        int[][] cell = new int[(N*2)+1][N+1];
        int[][] tempCell = new int[(N*2)+1][N+1];

        // amount of neighbouring cells
        int[][] cellCount = new int[(N*2)+1][N+1];

        // sets scale of x and y
        draw.setCanvasSize(1000, 500);
        draw.setXscale(0, N*2);
        draw.setYscale(0, N);

        // draw black background and set canvas size
        draw.clear(Color.BLACK);
        draw.show();
        
         // initial configuration of cells
        for (int i = 0; i <= N*2; i++){

            // sets all possible x and y co-ordinates
            px[i] = i + 0.5;
            py[i] = i + 0.5;
            for (int j = 0; j <= N; j++){

                // T determines percent of squares filled with living cells

                if (Math.random()*100 < T){
                    cell[i][j] = 1;
                } 
            }
        }

  /*      for (int i = 1; i < N; i++){
            cell[N][i] = tempCell[1][i];
            cell[i][N] = tempCell[i][1];
            cell[0][i] = tempCell[N-1][i];
            cell[i][0] = tempCell[i][N-1];
        }*/

        // tabulation of neighbouring cells, 
        if (T != 0){
           while(t){
            for (int i = 0; i < N*2; i++){
                for (int j = 0; j < N; j++){
                    int nx = i;
                    int ny = j;

                    if (i == 0){
                        nx = N*2;
                    }
                    if (j == 0){
                        ny = N;
                    }
                        // Checks living cells, counts live neighbours
                    if (cell[i][j] == 1){
                        if (cell[(i+1)%(N*2)][j] == 1) cellCount[i][j]++;
                        if (cell[(nx-1)][j] == 1) cellCount[i][j]++;

                        if (cell[i][(j+1)%(N)] == 1) cellCount[i][j]++;
                        if (cell[i][ny-1] == 1) cellCount[i][j]++;

                        if (cell[(i+1)%(N*2)][(j+1)%(N)] == 1) cellCount[i][j]++;
                        if (cell[(i+1)%(N*2)][ny-1] == 1) cellCount[i][j]++;
                        if (cell[nx-1][(j+1)%(N)] == 1) cellCount[i][j]++;
                        if (cell[nx-1][ny-1] == 1) cellCount[i][j]++;

                            // Kills living cells if cellCount is more than 3 or less than 2
                        if (cellCount[i][j] > 3 || cellCount[i][j] < 2){
                            tempCell[i][j] = 0;
                        }
                            // resets cellCount to zero because the values are not needed anymore
                        cellCount[i][j] = 0;

                    } 
                        // checks dead cells, checks live neighbours
                    else {
                        if (cell[(i+1)%(N*2)][j] == 1) cellCount[i][j]++;
                        if (cell[nx-1][j] == 1) cellCount[i][j]++;

                        if (cell[i][(j+1)%(N)] == 1) cellCount[i][j]++;
                        if (cell[i][ny-1] == 1) cellCount[i][j]++;

                        if (cell[(i+1)%(N*2)][(j+1)%(N)] == 1) cellCount[i][j]++;
                        if (cell[(i+1)%(N*2)][ny-1] == 1) cellCount[i][j]++;
                        if (cell[nx-1][(j+1)%(N)] == 1) cellCount[i][j]++;
                        if (cell[nx-1][ny-1] == 1) cellCount[i][j]++;

                            // Birth of living cells
                        if (cellCount[i][j] == 3){
                            tempCell[i][j] = 1;
                        }
                        cellCount[i][j] = 0;
                    }
                }
            }

            

                // clears previous cells
            draw.clear(Color.BLACK);

            for (int i = 0; i < N*2; i++){
                for (int j = 0; j < N; j++){
                    if (tempCell[i][j] == 1){
                        cell[i][j] = 1;
                        draw.setPenColor(Color.GREEN);
                        draw.filledCircle(px[i], py[j], 0.375);
                    } 
                    else{
                        cell[i][j] = 0;
                    }
                }
            }

                // adds a genereration and displays the current one in the terminal
            generations++;
            draw.textLeft(0,N+(N*0.01), "Gen: " + Integer.toString(generations));

            draw.show(S);
        } 
    }
    else{
        System.out.println("Not a high enough percentage of cells");
    }
}

    public static void main(String[] args) {
            // size of N*N grid
        int N = Integer.parseInt(args[0]);
            // number from 0-100, how much (%) of grids to have cells.
        int T = Integer.parseInt(args[1]);
            // speed of simulation
        int S = Integer.parseInt(args[2]);
        new GOL(N, T, S);
    }
}
