package hw3;

import static api.Orientation.*;
import static api.CellType.*;

import java.util.ArrayList;
import java.util.Arrays;

import api.Cell;
import api.CellType;

/**
 * Utilities for parsing string descriptions of a grid.
 * 
 * @author Aditi
 */
public class GridUtil {
	/**
	 * Constructs a 2D grid of Cell objects given a 2D array of cell descriptions.
	 * String descriptions are a single character and have the following meaning.
	 * <ul>
	 * <li>"*" represents a wall.</li>
	 * <li>"e" represents an exit.</li>
	 * <li>"." represents a floor.</li>
	 * <li>"[", "]", "^", "v", or "#" represent a part of a block. A block is not a
	 * type of cell, it is something placed on a cell floor. For these descriptions
	 * a cell is created with CellType of FLOOR. This method does not create any
	 * blocks or set blocks on cells.</li>
	 * </ul>
	 * The method only creates cells and not blocks. See the other utility method
	 * findBlocks which is used to create the blocks.
	 * 
	 * @param desc a 2D array of strings describing the grid
	 * @return a 2D array of cells the represent the grid without any blocks present
	 */
	public static Cell[][] createGrid(String[][] desc) {
		int row = desc.length;
		int col = desc[0].length;
		
		Cell [][] Grid = new Cell [row][col];//makes a new empty Grid
		
		for (int i = 0; i<Grid.length;i++) {
			for (int j = 0; j<Grid[i].length;j++) {
				if (desc[i][j].equals("*")) { 
					Grid [i][j] = new Cell(WALL, i, j);
				}
				else if(desc[i][j].equals("e")) {
					Grid [i][j] = new Cell(EXIT, i, j);
				}
				else {
					Grid [i][j] = new Cell(FLOOR, i, j);
				}	
			}
		}
		return Grid;
	}

	/**
	 * Returns a list of blocks that are constructed from a given 2D array of cell
	 * descriptions. String descriptions are a single character and have the
	 * following meanings.
	 * <ul>
	 * <li>"[" the start (left most column) of a horizontal block</li>
	 * <li>"]" the end (right most column) of a horizontal block</li>
	 * <li>"^" the start (top most row) of a vertical block</li>
	 * <li>"v" the end (bottom most column) of a vertical block</li>
	 * <li>"#" inner segments of a block, these are always placed between the start
	 * and end of the block</li>
	 * <li>"*", ".", and "e" symbols that describe cell types, meaning there is not
	 * block currently over the cell</li>
	 * </ul>
	 * 
	 * @param desc a 2D array of strings describing the grid
	 * @return a list of blocks found in the given grid description
	 */
	public static ArrayList<Block> findBlocks(String[][] desc) {
		ArrayList<Block> BlockLocation = new ArrayList<Block>();
		int size = 0; 
		int firstRow = 0; 
		int firstCol = 0;
		
		for (int i = 0; i<desc.length;i++) { //iterates through the 2d string list
			for (int j = 0; j<desc[i].length;j++) { //if [ is found, increment size until ] is found
				if (desc[i][j].equals("[")) {
					firstRow = i;
					firstCol = j; 
					size = size+1;
					while (!desc[i][j].equals("]")) {
						size = size+1;
						j = j + 1; //increases columns
					}
					BlockLocation.add(new Block(firstRow,firstCol, size,HORIZONTAL));
					size = 0; //rests size to 0
					i = firstRow;
					j = firstCol;
				}
				else if (desc[i][j].equals("^")) { //repeats for vertical blocks
					firstRow = i;
					firstCol = j;
					size = size + 1;
					while (!desc[i][j].equals("v")) {
						size = size+1;
						i = i + 1;
					}
					BlockLocation.add(new Block(firstRow,firstCol, size, VERTICAL));
					size = 0;
					i = firstRow;
					j = firstCol;
				}
			}
		}
		return BlockLocation;
	}
}
