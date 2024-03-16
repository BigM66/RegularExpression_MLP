/**
 * a method that parses through a file full of IP addresses and keeps track of how many times a unique username and IP address occurs
 * it then stores this information into a hashmap with key and value pairs. we then have the user give 2 inputs 
 *@author <Matthew Parsley>
* @version 1.0
* Assignment 4
* CS322 - Compiler Construction
* Spring 2024
*/

 
import java.io.*;
import java.util.HashMap;

public class WordCounter {
    public static void main(String[] args) {
        
        
        
        String directPath = System.getProperty("user.dir");

        HashMap<String,Integer> regexCount = new HashMap<String,Integer>();

        File directory = new File(directPath);
        
        /*
         * searches through all of our ffiles in the path that end with _wc.txt
         * it goes through each file and looks for the patterns tha we want it to look for
         * Prints out the regex that we have and the number of times that it appears in total through all the files.
         */
        for(File file : directory.listFiles()){
           
            if(file.getName().endsWith("_wc.txt")){
                try{
                    BufferedReader read = new BufferedReader(new FileReader(file));
                    String line;
                    
                    while((line = read.readLine()) != null){
                        int index = line.lastIndexOf("|");

                        String pattern = line.substring(0, index-1);
                        int count = Integer.parseInt(line.substring(index+2));

                        if (!regexCount.containsKey(pattern)){
                            regexCount.put(pattern,0);
                        }
                            regexCount.put(pattern, regexCount.get(pattern)+ count);
                        
                    }
                    System.out.println(file.getName() + " done");
                    read.close();
                }
                
                catch(IOException e){
                    e.printStackTrace();;
                }
           }
        }
        
        /*
         * prints out our file with the information we want it to have 
         */
        try{
            PrintWriter outWriter = new PrintWriter(new FileWriter("total_wc.txt"));

            for (String pattern : regexCount.keySet()) {
                outWriter.println(pattern + "|" + regexCount.get(pattern));
            }

            outWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();;
        }




    }
}