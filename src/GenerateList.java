/************************************************************************
 * @file: GenerateList.java (forked from Proj2.java)
 * @description: Reads a dataset of steam games (path defined in args) and creates an ArrayList of type Game.
 * @author: Will S
 * @date: November 10, 2025
 ************************************************************************/

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GenerateList {
    public ArrayList<Game> generateList(String inputFileName, int numLines) throws IOException {
        ArrayList<String> stringList = createStringList(inputFileName, numLines-1);
        ArrayList<Game> gameList = stringToGame(stringList);
        return gameList;
    }

    //Creates a list of games based on the input file
    //Arguments: File name, number of lines to read
    private ArrayList<String> createStringList(String filename, int numLines) throws FileNotFoundException {
        Scanner csvReader = new Scanner(new File(filename));
        csvReader.nextLine(); //skip the header line
        ArrayList<String> lines = new ArrayList<>();
        while (csvReader.hasNextLine() && numLines >= 0) {
            String line = csvReader.nextLine();
            if (!line.isEmpty()) {
                lines.add(line);
                numLines--;
            }
        }
        return lines;
    }

    //takes the array of Strings, processes them as games, and inserts into the ArrayList
    private ArrayList<Game> stringToGame(ArrayList<String> stringList) {
        ArrayList<Game> gameList = new ArrayList<>();
        for (String line : stringList) {
            Game toAdd = processLine(line);
            if (toAdd != null) gameList.add(toAdd);
        }
        System.out.println("\nArrayList populated with " + gameList.size() + " games.");
        return gameList;
    }

    private Game processLine(String line) {
        String[] data = line.split(",");
        Game processed = null;
        try {
            processed = (new Game(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), data[3], Integer.parseInt(data[4]),
                    Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]), Integer.parseInt(data[8]), Double.parseDouble(data[9])));
        } catch (NumberFormatException e) {
            System.out.println("Error with parsing line: " + line);
        }
        return processed;
    }
}
