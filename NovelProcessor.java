import java.util.Scanner;

/**
* This class will ask the user to pick a book that they want to search through for certain words 
* using regular expressions.The program will also count the number of instances that the words we want to know 
* about appear in the selected books
* @author <Matthew Parsley>
* @version 1.0
* Assignment 4
* CS322 - Compiler Construction
* Spring 2024
*/

import java.io.*;
import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;
import java.util.HashMap;
public class NovelProcessor {
    private static BufferedReader Title;
    private static BufferedReader regexReader;
    private static PrintWriter outWriter;
    
    /**
     * constructor for our main class
     */
    public NovelProcessor(){
    }

    /**
     * a method that reads our book we select and matches up the regular expressions with how many times they appear in the book using a hashmap
     * @param bookTitle the title of the book they wat to search through
     * @param regexFile The list of regular expressions that will be used as the key for the words we want to search for 
     * @return a HashMap that gives us our regular expressions and how many times they occur throughout our books
     */
    public static HashMap <String, Integer> processNovel(String bookTitle, String regexFile){
        String line = "";
        HashMap <String, Integer> regexCount = new HashMap<String,Integer>();

        try{
            Title = new BufferedReader(new FileReader(bookTitle));
            regexReader = new BufferedReader(new FileReader(regexFile));
            
              
              /*
               * This is what allows us to split our book into the individual words
               */
             
            while ((line = regexReader.readLine()) != null){
                regexCount.put(line.trim(), 0);
            }
            
            /*
             * this is what allows us to connect the regex expressions to the book
             */
            while((line = Title.readLine()) != null){

                for(String regString : regexCount.keySet()){
                    Pattern pat = Pattern.compile(regString, Pattern.CASE_INSENSITIVE);
                    Matcher match= pat.matcher(line);

                    while(match.find()){
                        regexCount.put(regString, regexCount.get(regString)+1);
                    }
                }
            }
           
        }

        catch (IOException e){
            System.out.println("File not Found");
        }

        return regexCount;
    }

    /**
     * creates a text file of our regex patterns and the key values
     * @param map
     * @param FinalFile
     */
    public static void outputFile(HashMap<String,Integer> map, String FinalFile){
        try{
            
            outWriter = new PrintWriter(new FileWriter(FinalFile.replaceAll(".txt","_wc.txt")));
            
            for(HashMap.Entry<String, Integer> entry : map.entrySet()){           
                outWriter.println(entry.getKey() + " | " + entry.getValue());
            }
          
            outWriter.close();
        }

        catch(IOException e){
            e.printStackTrace();
        }     
    }
    

    /*
     * main method where we ask the user for the title of the book they want to search, and the regular expression file that 
     */
    public static void main(String[]args){

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the title of the book that you want searched through: ");
        String bookFile = scan.nextLine();
        
        System.out.println("Please enter the name of where the regular expressions you want to search for in the book are: ");
        String regFile = scan.nextLine();

        HashMap<String, Integer> wordCount = processNovel(bookFile, regFile);

        outputFile(wordCount, bookFile);

        scan.close();

    }
}


