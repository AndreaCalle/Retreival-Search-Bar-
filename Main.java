import java.awt.Window.Type;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.security.*;
/*
HAS NOT BEEN COMPILE !!!!
*/
public class Main {
    public static void main(String[] args) throws IOException {
        Invert invert  = new Invert() ; 

        Scanner in = new Scanner(System.in);
		System.out.println("Welcome to CPS842 Assignment 2");
		System.out.println("Choose File(query.text or cacm.all) :");
		String fileName= in.nextLine();
		System.out.println("Stemming(On/Off): ");
		String stem= in.nextLine();
		makeHashMaps(fileName.trim().toLowerCase(),stem.trim().toLowerCase());
		System.out.println("Stop Word Removal(On/Off):");
		String removeWord = in.nextLine();
		File commonWord = new File("common_words"); 
        BufferedReader ch = new BufferedReader(new FileReader(commonWord));
		removeWord = removeWord.trim().toLowerCase(); 

      //  makeHashMaps();
		if(removeWord.equals("on")){
			invert.removeCommonWords(commonWord, ch, document); 
			invert.removeCommonWords(commonWord, ch, query); 
		}

		invert.readFromFile();
		invert.getQuery(in);


        if(fileName.equals("cacm.all")){  
	
			for (String term : userQuery) {
				BufferedReader dff = new BufferedReader(new FileReader("Dictionary.txt"));
				double idf = invert.getDocumentFrequency(dff, term);
				idfValues.put(term,idf);
			}
			System.out.println("IDF values are" + idfValues + "\n");
			double qWeight = invert.termFrequencyOfQuery(idfValues);

			invert.termFrequency(document, idfValues, qWeight);
		

		}
		if(fileName.equals("query.txt")){
	
			for (String term : userQuery) {
				BufferedReader dff = new BufferedReader(new FileReader("queryDictionary.txt"));
				double idf = invert.getDocumentFrequency(dff, term);
				idfValues.put(term,idf);
			}
			System.out.println("IDF values are" + idfValues + "\n");
			double qWeight = invert.termFrequencyOfQuery(idfValues);

			invert.termFrequency(query, idfValues, qWeight);
		}

    }
}