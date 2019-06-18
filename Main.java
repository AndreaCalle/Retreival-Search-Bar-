import java.awt.Window.Type;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.security.*;
/*
HAS BEEN COMPILE !!!!
*/
public class Main {
 
    public static void main(String[] args) throws IOException {
        Invert invert  = new Invert() ; 
        int i = 0 ; 
        Scanner in = new Scanner(System.in);
		System.out.println("Welcome to CPS842 Assignment 2"+"\n"+"Stemming(On/Off): ");
        String stem= in.nextLine();

		System.out.println("Stop Word Removal(On/Off):");
        String removeWord = in.nextLine();

        File commonWord = new File("common_words"); 
        BufferedReader ch = new BufferedReader(new FileReader(commonWord));

        removeWord = removeWord.trim().toLowerCase();
        if(removeWord.equals("on")){
			invert.removeCommonWords(commonWord, ch); 
        }
        call(invert,stem,removeWord);

        while(i == 0){
            invert.getQuery(in); 
            invert.startSearch();
            System.out.println("Search for new query(YES/NO):");
             if(in.nextLine().trim().toLowerCase().equals("no")){
                i = 1; 
            } 
        }
    }
/*
Call methond will call the invert class and run the 
*/
    public static void call(Invert invert,String stem,String removeWord)throws FileNotFoundException, IOException 
    {

      invert.makeHashMaps(stem.trim().toLowerCase());
      invert.readFromFile();
    }   



}