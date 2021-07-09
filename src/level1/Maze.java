package level1;

import level0.Direction;
import level0.Tile;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Maze extends JPanel{
    private final int difficulty;
    private final Tile[][] tiles;

    private int[][] getFileData(File file, int rows, int cols) throws FileNotFoundException {
        int[][] rawData = new int[rows][cols];
        Scanner fileScanner = new Scanner(file);

        int column = 0, row = 0;
        while(fileScanner.hasNextInt()){
            rawData[row][column] = fileScanner.nextInt();
            //System.out.print(rawData[row][column] + " ");
            column = (column + 1) % cols;
            if(column == 0) {
                row ++;
                //System.out.print("\n");
            }
        }
        //System.out.print("\n");

        if(rows != row && column != 0) throw new RuntimeException("Row/Column Mismatch!");
        return rawData;
    }

    private void processRawMazeData(int rows, int cols) throws IOException {
        int[][] structureData = getFileData(new File(getClass().getResource("").getPath() +
                        "MazeDesigns/" + difficulty + "/structure.txt"), rows, cols);
        int[][] pointsData = getFileData(new File(getClass().getResource("").getPath() +
                "MazeDesigns/" + difficulty + "/points.txt"), rows, cols);

        int[][] processedData = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(structureData[i][j] == 0) processedData[i][j] = -1;
                else {
                    int temp = 0;
                    if(i>0 && structureData[i-1][j] == 0) temp += 1;
                    if(i<(rows-1) && structureData[i+1][j] == 0) temp += 2;
                    if(j>0 && structureData[i][j-1] == 0) temp += 4;
                    if(j<(cols-1) && structureData[i][j+1] == 0) temp += 8;
                    if(pointsData[i][j] == 1) temp += 16;
                    processedData[i][j] = temp;
                }
            }
        }

        File processedFile = new File(getClass().getResource("").getPath() + "MazeDesigns/" + difficulty + ".txt");
        processedFile.createNewFile();

        FileWriter fileWriter = new FileWriter(processedFile);
        for(int i = 0; i<rows; i++) {
            for(int j = 0; j<cols; j++) {
                fileWriter.append(" ").append(String.valueOf(processedData[i][j])).append(" ");
            }
            fileWriter.append("\n");
        }
        fileWriter.flush();
    }

    public Maze(int rows, int cols, int difficulty){
        this.difficulty = difficulty;

        tiles = new Tile[rows][cols];

        setLayout(new GridLayout(rows, cols));

        if(getClass().getResource("MazeDesigns/" + difficulty + ".txt") == null) {
            try {
                processRawMazeData(rows, cols);
            } catch (IOException e) {
                System.out.println("Could not find the maze design data!");
                e.printStackTrace();
            }
        }
        try {
            Scanner fileScanner = new Scanner(new File(getClass().getResource("MazeDesigns/" + difficulty + ".txt").getPath()));

            int column = 0, row = 0;
            while(fileScanner.hasNextInt()) {
                Tile newTile = new Tile(row, column, fileScanner.nextInt());
                tiles[row][column] = newTile; add(newTile);
                column = (column + 1) % cols;
                if (column == 0) row++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        setOpaque(false);
    }

    public int getCurrentTileHeight() { return tiles[0][0].getHeight(); }
    public int getCurrentTileWidth() { return tiles[0][0].getWidth(); }

    public boolean canMove(int x, int y, Direction direction, Direction prevDirection) {
        boolean canMove;
        int tileHeight = getCurrentTileHeight(), tileWidth = getCurrentTileWidth();
        if (direction == Direction.UP) y = y + tileHeight-1;
        if (direction == Direction.LEFT) x = x + tileWidth-1;

        if (direction == Direction.UP && prevDirection == Direction.RIGHT) x = x + tileWidth-1;
        if (direction == Direction.DOWN && prevDirection == Direction.RIGHT) x = x + tileWidth-1;
        if (direction == Direction.LEFT && prevDirection == Direction.DOWN) y = y + tileHeight-1;
        if (direction == Direction.RIGHT && prevDirection == Direction.DOWN) y = y + tileHeight-1;

        Tile pacmanTile = tiles[y/tileHeight][x/tileWidth];

        if(prevDirection == direction) pacmanTile.consume();
        switch (direction) {
            case UP -> canMove = !pacmanTile.topBorder;
            case DOWN -> canMove = !pacmanTile.bottomBorder;
            case LEFT -> canMove = !pacmanTile.leftBorder;
            case RIGHT -> canMove = !pacmanTile.rightBorder;
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        }

        return canMove;
    }
}
