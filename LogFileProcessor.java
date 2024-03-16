
/**
* This class takes a file full of Ip address and Usernames and puts them into respective hashmaps. It then prints the hashmaps based on the users input.
* If the user inputs a 0, then it prints the default of how many lines were parsed, if they input a 1 it prints all of the IP addresses plus how many times they appear
* if the user in puts a 2 it prints all of the user names and how many times they appear.
* @author <Matthew Parsley>
* @version 1.0
* Assignment 4
* CS322 - Compiler Construction
* Spring 2024
*/
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;
import java.io.*;


public class LogFileProcessor {

    private static int lines_parsed = 0;
    private static BufferedReader logReader;

    /*
     * default constructor
     */
    public LogFileProcessor()
    {

    }

    /*
     * searches the file for unique IP addresses and counts and stores them in a hashmap
     * @param logFile- the file that we want to search through
     * @param regexFile- the regular expressions we want to use to search 
     * @return hashmap- a hashampa with the IP addresses from the file 
     */
    public static HashMap <String, Integer> processIP(String logFile, String regexFile)
    {
        
        String line = "";
        String regex = regexFile;
        HashMap <String, Integer> ipAdresses = new HashMap <String,Integer>();
        
        
        try
        {
            logReader = new BufferedReader(new FileReader(logFile));

            /*
             * searches a file for unique IP addresses that will then get stored into a hashmap we can then print
             */
            while((line = logReader.readLine()) != null)
            {
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher= pattern.matcher(line);
                lines_parsed++;

                /*
                 * this is where our program gets searched through and looks for the ip addresses contained within it 
                 * It then stores them into a hashmap for future use. It knows what to look for based on a regex input that it 
                 * recieves later              
                 * 
                 */
                while(matcher.find())
                {
                    String match = matcher.group();
                    if(!ipAdresses.containsKey(match))
                    {

                        ipAdresses.put(match, 0);
                    }
                    ipAdresses.put(match, ipAdresses.get(match)+1);
                  

                }
                
            }
           
        }

        catch (IOException e){
            System.out.println("error no file by that name");
        }

        return ipAdresses;
    }

    /*
     * searches the file for unique UserNames and counts and stores them in a hashmap
     * @param log- the file that we want to search through
     * @param regexFile- the regular expressions we want to use to search 
     * @return hashmap- a hashampa with the IP addresses from the file and how many times they appear
     */

    public static HashMap <String, Integer> processUsername(String log, String regex)
    {
        
        String line = "";
        String regular = regex;
        HashMap <String, Integer> usernames = new HashMap <String,Integer>();
        
        
        try
        {
            logReader = new BufferedReader(new FileReader(log));
            
             /*
             * searches a file for unique UserNames that will then get stored into a hashmap we can then print
             */
            while((line = logReader.readLine()) != null)
            {
                Pattern pat = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
                Matcher matcher= pat.matcher(line);

                /*
                 * allows us to gather the information on usernames and how often they appear.
                 * Puts every unique username into our Hashmap and makes the value to the key the amount of times that it appears in  our file.
                 */

                while(matcher.find())
                {
                    String[] match = matcher.group().split(" ");
                    String username = match[1];
                    if(!usernames.containsKey(username))
                    {

                        usernames.put(username, 0);
                    }
                    usernames.put(username, usernames.get(username)+1);
                  

                }
            }
           
        }

        catch (IOException e){
            System.out.println("No file by that name");
        }

        return usernames;
    }

    /* returns the size of our HashMap
     * @param the hasmap that we want to get the size of
     * @return the size of the hashmap
    */
    public static int size(HashMap<String,Integer> map)
    {
        return map.size();
    }
  
    /*
     * prints the key and value pairs of our hashmap
     * @parm the HashMap that we want to print
     * @return all of the key value pairs of our hsahmap
     */
    public static void printHashMap(HashMap<String,Integer> map){
        for(HashMap.Entry<String,Integer> entry: map.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    /*
     * prints our default statemen that states how many lines were parsed. It also tells us the size of both of our HashMaps to tell us
     * how many usernames were in the file and how many IP addresses there were
     * @param ipMap- map of our IP addresses so we can print off the ip address key value pairs
     * @param userMap- map of our user names so we can print off the user name key value pairs 
     */
    public static void printDefault(HashMap<String,Integer> ipMap, HashMap<String,Integer> userMap)
    {
        System.out.println(lines_parsed + " lines in the log file were parsed.\n" + 
                "There are " + ipMap.size() +" unique IP addresses in the log.\n" + //
                "There are " + userMap.size() + " unique user names in the log.");
    }
    
    
    /*
     * this is where we ask our user to input the file that they want to have the regex search through
     * We then also have the regex values that we search through the entire file for. One for the username 
     * and one for the IP addresses.
     */
    public static void main(String[]args)
    {
        

        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the name of the file: ");
        String logFile = scan.nextLine();

        String ipPattern = "\\b(?:[0-9]{1,3}\\.){3}[0-9]{1,3}\\b";
        String userPattern = "user\\s[a-z]+";
        

        
        String answer = "";
        HashMap <String, Integer> ipMap = processIP(logFile, ipPattern);
        HashMap <String, Integer> usernamesMap = processUsername(logFile, userPattern);
        

        /*
         * this is where we give the user the options of what they can input and the outputs that they should expect 
         * include a way to manually end the program by entering e for end. 
         */
        do
        {
            System.out.println("\nPlease enter the value of what you would like to print: "
            +"\n0: Print the default output"
            +"\n1: Print the IP adresses and the default output"  
            +"\n2: Print the Usernames and default output"
            +"\ne: end the program");
            answer = scan.nextLine().toLowerCase();
            

            switch(answer.charAt(0))
            {
                case '0':
                    printDefault(ipMap, usernamesMap);
                    continue;
                case '1':
                    printHashMap(ipMap);
                    printDefault(ipMap, usernamesMap);
                    continue;
                case '2':
                    printHashMap(usernamesMap);
                    printDefault(ipMap, usernamesMap);
                    continue;
                case ('e'): 
                    System.out.println("Bye Bye!");
                    break;
                default:    
                    System.out.println("Please enter one of the above choices");

            }
        }while(answer.charAt(0) != 'e');
        
        
        scan.close();

    }

}