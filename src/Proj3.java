/************************************************************************
 * @file: Proj3.java
 * @description: Reads a dataset of steam games (path defined in args) and tests runtimes for different sorting operations with ArrayLists.
 * @author: Will S
 * @date: November 10, 2025
 ************************************************************************/

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class    Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {
        if (left < right) {
            int mid = left + (right-left)/2;
            mergeSort(a, left, mid);
            mergeSort(a, mid+1, right);
            merge(a, left, mid, right);
        }
    }

    public static <T extends Comparable> void merge(ArrayList<T> a, int left, int mid, int right) {
        ArrayList<T> l = new ArrayList<>(a.subList(left, mid));
        ArrayList<T> r = new ArrayList<>(a.subList(mid+1, right));
        int i = 0;
        int j = 0;
        while (i < l.size() && j < r.size()) {
            if (l.get(i).compareTo(r.get(j)) <= 0) {
                a.set(left, l.get(i));
                i++;
            }
            else {
                a.set(left, r.get(j));
                j++;
            }
            left++;
        }
        while (i < l.size()) {
            a.set(left, l.get(i));
            i++;
            left++;
        }
        while (j < r.size()) {
            a.set(left, r.get(j));
            j++;
            left++;
        }
    }

    // Quick Sort
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {
        // Finish Me
    }

    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        // Finish Me
        return -1;
    }

    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable> void heapSort(ArrayList<T> a, int left, int right) {
        ArrayList<T> temp = a;
        heapify(temp, left, right);
        for (int i=right; i>=left; i--) {
            deleteMax(a, left, i);
        }
    }

    public static <T extends Comparable> void heapify (ArrayList<T> a, int left, int right) {
        int middle = left-1 + (right-left)/2;
        for (int i=middle; i>=left; i--) {
            percolate(a, i, right);
        }
        System.out.println(a);
    }

    public static <T extends Comparable> void deleteMax(ArrayList<T> a, int left, int right) {
        swap(a, left, right);
        percolate(a, left, right-1);
    }

    //swaps the root (index "left") with the smallest children
    public static <T extends Comparable> void percolate(ArrayList<T> a, int left, int right) {
        int indexToSwap = left;
        while (true) {
            int leftC = left * 2;
            int rightC = left * 2 + 1;

            if (leftC <= right && a.get(leftC).compareTo(a.get(rightC)) > 0 && a.get(leftC).compareTo(a.get(left)) > 0) {
                indexToSwap = leftC;
            }
            else if (rightC <= right && a.get(rightC).compareTo(a.get(left)) > 0) {
                indexToSwap = rightC;
            }

            if (indexToSwap > left && indexToSwap <= right) {
                swap(a, left, indexToSwap);
                left = indexToSwap;
            }
            else {
                return;
            }
        }
    }

    // Bubble Sort
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
        boolean isSorted = false;
        int temp = 0;
        for (int i = 0; i<size-1 && !isSorted; i++) {
            isSorted = true;
            for (int j = 0; j<size-i-1; j++) {
                if (a.get(j).compareTo(a.get(j + 1)) > 0) {
                    swap(a, j, j + 1);
                    temp++;
                    isSorted = false;
                }
            }
        }
        return temp;
    }

    // Odd-Even Transposition Sort
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {
        boolean isSorted = false;
        int temp = 0;
        for (int i = 0; i < size - 1 && !isSorted; i += 1) {
            isSorted = true;
            for (int j = 0; j <= size - 2; j += 2) {
                if (a.get(j).compareTo(a.get(j + 1)) > 0) {
                    swap(a, j, j + 1);
                    isSorted = false;
                }
            }
            for (int j = 1; j <= size - 2; j += 2) {
                if (a.get(j).compareTo(a.get(j + 1)) > 0) {
                    swap(a, j, j + 1);
                    isSorted = false;
                }
            }

            if (!isSorted)
                temp++; // One or more swaps happened, which all would've happened simultaneously in a parallel algorithm
        }
        return temp;
    }

    public static void main(String [] args)  throws IOException {
        FileOutputStream output;
        PrintWriter writer;

        if (args.length != 3) {
            System.err.println("Usage: java Proj3 <input file> <sorting algorithm type> <number of lines>");
            System.exit(1);
        }
        String inputFileName = args[0].strip().toLowerCase();
        String sortingAlgorithm = args[1].strip().toLowerCase();
        int numLines = Integer.parseInt(args[2].strip());
        GenerateList listGenerator = new GenerateList();

        ArrayList<Game> steam_games = listGenerator.generateList(inputFileName, numLines);

        Collections.sort(steam_games);

        output = new FileOutputStream("outputDesired.txt");
        writer = new PrintWriter(output);
        for (Game game : steam_games) {
            writer.println(game);
        }
        writer.flush();
        writer.close();

        long start;
        int N;
        long end;

        //Sorted (best case)
        start = System.nanoTime();
        N = sort(steam_games, numLines, sortingAlgorithm);
        end = System.nanoTime();

        System.out.println("Sorted Runtime: " + (end-start)/1000000000.0 + " seconds");
        if (N != -1) System.out.println("Number of iterations in parallel: " + N);

        output = new FileOutputStream("outputSorted.txt");
        writer = new PrintWriter(output);
        for (Game game : steam_games) {
            writer.println(game);
        }
        writer.flush();
        writer.close();

        //Shuffled (average case)
        Collections.shuffle(steam_games);

        start = System.nanoTime();
        N = sort(steam_games, numLines, sortingAlgorithm);
        end = System.nanoTime();

        System.out.println("Shuffled Runtime: " + (end-start)/1000000000.0 + " seconds");
        if (N != -1) System.out.println("Number of iterations in parallel: " + N);

        output = new FileOutputStream("outputShuffled.txt");
        writer = new PrintWriter(output);
        for (Game game : steam_games) {
            writer.println(game);
        }
        writer.flush();
        writer.close();

        //Reversed (worst case)
        Collections.sort(steam_games, Collections.reverseOrder());

        start = System.nanoTime();
        N = sort(steam_games, numLines, sortingAlgorithm);
        end = System.nanoTime();

        System.out.println("Reversed Runtime: " + (end-start)/1000000000.0 + " seconds");
        if (N != -1) System.out.println("Number of iterations in parallel: " + N);

        output = new FileOutputStream("outputReversed.txt");
        writer = new PrintWriter(output);
        for (Game game : steam_games) {
            writer.println(game);
        }
        writer.flush();
        writer.close();
    }

    public static int sort(ArrayList<Game> steam_games, int numLines, String sortingAlgorithm) {
        switch (sortingAlgorithm) {
            case "bubble":
                return bubbleSort(steam_games, numLines);
            case "transposition":
                return transpositionSort(steam_games, numLines);
            case "merge":
                mergeSort(steam_games, 0, numLines-1);
                break;
            case "quick":
                quickSort(steam_games, 0, numLines-1);
                break;
            case "heap":
                heapSort(steam_games, 0, numLines-1);
                break;
            default:
                System.err.println("Invalid sorting algorithm. Options: [bubble, transposition, merge, quick, heap]");
                System.exit(1);
        }
        return -1;
    }
}
