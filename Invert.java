import java.awt.Window.Type;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.security.*;
/*
1. chnage the parser 
2. chnage static to private , create a secure code 
	using get() method , toString method 
**SHOULD CREATE A GUI FOR IT ???**
*/

public class Invert {
	static HashMap<Integer, HashMap<String, Integer>> document = new HashMap<Integer, HashMap<String, Integer>>();
	static HashMap<Integer, HashMap<String, Integer>> query = new HashMap<Integer, HashMap<String,Integer>>();
	static int docNum = -1;
	static ArrayList<String> userQuery = new ArrayList<>();
	static ArrayList<Double> documentFrequency = new ArrayList<>();
	static HashMap<String, Integer> queryMap = new HashMap<>();
	static HashMap<String, Double> termFreq = new HashMap<>();
	static HashMap<String, Double> idfValues = new HashMap<>();
	static List<Double> cosineList = new ArrayList<>();


/*
startSearch: will 
*/
	static public void startSearch() throws IOException {

			for (String term : userQuery) {
				BufferedReader dff = new BufferedReader(new FileReader("Dictionary.txt"));
				double idf = getDocumentFrequency(dff, term);
				idfValues.put(term,idf);
			}
			System.out.println("IDF values are" + idfValues + "\n");
			double qWeight = termFrequencyOfQuery(idfValues);

			termFrequency(document, idfValues, qWeight);
	}
/*
Parsing the text, Will check on or off to version stemming 
*/
    static  void makeHashMaps(String stem) throws IOException{
		if(stem.equals("off")){
			File file = new File("cacm.all");
			BufferedReader br = new BufferedReader(new FileReader(file));
			doStuff(file, br);

			File file2 = new File("query.text");
			br = new BufferedReader(new FileReader(file2));
			parseAllQueriesEval(file2,br);
		}
		if(stem.equals("on")){
			//if(fileName.equals("cacm.all")){
			String[] s = new String[1]; 
			s[0] = "cacm.all"; 
			Stemmer.main(s) ; 
			File file = new File("stemming.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			doStuff(file, br);

		}
	}
	// test
	static void readFromFile() throws IOException {


		PrintWriter dict = new PrintWriter("Dictionary.txt", "UTF-8");
		PrintWriter port = new PrintWriter("PostList.txt", "UTF-8");
		documentFrequency(document, dict);
		printHashMap(document, port);
		dict.close();
		port.close();


	}
/*
must make chnages , parsing isn't , will be using (JSON, LEX FILE , )
*/
	static void doStuff(File file, BufferedReader br) throws FileNotFoundException, IOException {
		String line = null;
		String values = " ";
		int run = 0;
		while ((line = br.readLine()) != null) {
			String test[] = line.split(" ");
            test[0] = test[0].toLowerCase() ; 
			if (test[0].equals(".b") || test[0].equals(".a") || test[0].equals(".n") || test[0].equals(".x")) {
				run = 0;
			}
			if (run == 1){
				values = values + " " + line;
				values.toLowerCase() ; 
			    values = values.replaceAll("[^A-Za-z]"," ") ;
			}

			if (test[0].equals(".w") || test[0].equals(".t")) {

				run = 1;
              
			}

			if (test[0].equals(".i")){
					docNum = Integer.parseInt(test[1]) ; 
					values = " ";
				}

			if(test[0].equals(".x")){
				    
					countingTerms(values.toLowerCase(),0);
			}	

        }

	}
/*
def parseAllQUERIES(file, br)//buffer 
*/
	static void parseAllQueriesEval(File file, BufferedReader br)throws FileNotFoundException, IOException {
		String line = null;
		String values = "";
		int run = 0 ; 
		while ((line = br.readLine()) != null) {
			line = line.trim() ; 
			String test[] = line.split(" ");
			//System.out.println("this is line:" + test[0]);
			if(test.length > 0 ){
				test[0] = test[0].toLowerCase() ; 
			values = values + " " + line;
			values.toLowerCase() ; 
			values = values.replaceAll("[^A-Za-z]"," ") ;
			
			if(run == 1 ){
				//System.out.println(values);
				countingTerms(values.toLowerCase(),1);
				values = " ";
				run = 0 ; 
			}
			if (test[0].equals(".i")){
				docNum = Integer.parseInt(test[1]) ; 
				
			}
			if (test[0].equals(".n")){
				run = 1  ; 
			}
		}

        }
	}
	/// values contain a string of all the terms in abstract and title
	static void countingTerms(String values, int on ) throws FileNotFoundException, IOException {
        HashMap<String, Integer> terms = new HashMap<String, Integer>() ;//terms will take the word and the TF(number of time it's in the document)
	//	System.out.println(values);
		if (values != null) {
		//	System.out.println(values);
			String [] docWords = values.split(System.getProperty("line.separator"));
            docWords = values.split("\\W+"); //remove ', -, numbers etc
			String [] duplicate = new String[docWords.length];
			Arrays.fill(duplicate, "");//not sure 
			for (int i = 0; i < docWords.length; i++) {
				int check = 0;//use for dupicates...should change it 
				int counter = 0;//will count the # of time the word is in the file 

				for (int x = 1; x < docWords.length; x++) {
					if (docWords[i].equals(docWords[x])){// && temp[i].equals())
						counter++;//count the number of time the word is in the file 
                    }
                   duplicate[i] = docWords[i]; //will store word into duplicate
				}
				
                for (int y = 0; y < docWords.length; y++) {//CAN CREATE A FUNCTION 
                    if (duplicate[y].equals(docWords[i])) {//will check if the term is in the duplicate list 
                        check++;
                    }
				}

				if (check == 1) {
					terms.put(docWords[i], counter);
				}

			}
			if(on == 0 ){
			document.put(docNum, terms);
			}
			if(on == 1){
			 query.put(docNum, terms);
			}
		}

	}


	static void documentFrequency(HashMap<Integer, HashMap<String, Integer>> document2, PrintWriter terms) {
		HashMap<String, Integer> x = new HashMap<String, Integer>();
		for (Map.Entry<Integer, HashMap<String, Integer>> t : document2.entrySet()) {
			HashMap<String, Integer> inner = t.getValue();
			Integer key = t.getKey();
			int check = 0;

			for (Map.Entry<String, Integer> e : inner.entrySet()) {

				if (x.containsKey(e.getKey())) {// && check != key){
					check = key;
					String temp = e.getKey();
					int num = 1 + x.get(temp);
					x.remove(e.getKey());
					x.put(temp, num);
				} else {
					x.put(e.getKey(), 1);
				}
			}
		}
		for (Map.Entry<String, Integer> t : x.entrySet()) {
			String key = t.getKey();
			Integer value = t.getValue();
			terms.println(key + " " + value);
		}
	}

	static void removeCommonWords(File file, BufferedReader ch)//, HashMap<Integer, HashMap<String, Integer>> document2)
		throws FileNotFoundException, IOException {
		String line = null;

		while ((line = ch.readLine()) != null) {
			Iterator<Map.Entry<Integer, HashMap<String, Integer>>> t = document.entrySet().iterator();
			String comWord = line.replaceAll("\\s", "");
			while (t.hasNext()) {
				Map.Entry<Integer, HashMap<String, Integer>> outer = t.next();
				Iterator<Map.Entry<String, Integer>> e = (outer.getValue()).entrySet().iterator();
				while (e.hasNext()) {

					Map.Entry y = e.next();
					if (comWord.equals(y.getKey())) {
						e.remove();
					}
				}
			}
		}
	}

	static void printHashMap(HashMap<Integer, HashMap<String, Integer>> document, PrintWriter terms) {
		for (Map.Entry<Integer, HashMap<String, Integer>> t : document.entrySet()) {
			HashMap<String, Integer> inner = t.getValue();
			Integer key = t.getKey();
			for (Map.Entry<String, Integer> e : inner.entrySet()) {
				terms.println(key + " " + e.getKey() + " " + e.getValue());

			}

		}

	}

	static void getQuery(Scanner in) {
		String userIn;
		System.out.println("Enter a query");
		userIn = in.nextLine();
		String querySplit[] = userIn.split(" ");
		for (String terms : querySplit) {
			userQuery.add(terms);
		}

	}

	static void termFrequency(HashMap<Integer, HashMap<String, Integer>> document, HashMap<String, Double> idf, double qWeight) {
		ArrayList<Integer> keys = new ArrayList<>();
		ArrayList<Integer> dupe = new ArrayList<>();
		ArrayList<Double> dff  = new ArrayList<>();
		ArrayList<Double> tw  = new ArrayList<>();
		int frequency;
		for (Map.Entry<Integer, HashMap<String, Integer>> t : document.entrySet()) {
			HashMap<String, Integer> n = t.getValue();
			Integer key = t.getKey();
			//int i = 0 ; 
			for (Map.Entry<String, Integer> e : n.entrySet()) {
            // i++ ; 
				for (String query : userQuery) {
					if (e.getKey().equals(query)) {
						frequency = e.getValue();

						System.out.println("Word: " + query + " " + "Document number is " + key + " " + e.getKey()
								+ " frequency: " + frequency);
						keys.add(key);
						double termFrequency = (1 + Math.log10(frequency));
						System.out.println("Term frequency is " + termFrequency);
						double df = calculate(termFrequency, idf, query, key);
						dff.add(df) ;

					}
				}

			}

		}

		for(int i = 0 ;i < keys.size() ;i ++){
			double total =0; 
			for(int x = 0; x < keys.size(); x++){
				if(keys.get(i) == keys.get(x)){
					//S//ystem.out.println("tw for D "+ keys.get(i) + "is: "+ dff.get(x));
					total = total +  Math.pow(dff.get(x), 2) ;
					
				}

			}
			total = Math.sqrt(total);
			tw.add(total);
			//System.out.println("tw for D "+ keys.get(i) + "is: "+ tw.get(i));

		
		}
		//printWriter.println(key + " " + weight);
		for(int h = 0 ; h < tw.size(); h ++){
		double cosine = tw.get(h)/qWeight;
		System.out.println("cosine similarity is: " + cosine + " for doc: " + keys.get(h));
		cosineList.add(cosine);
		Collections.sort(cosineList);
		Collections.reverse(cosineList);
		}
		System.out.println("Order of similarity: " + cosineList);



	}

	static double getDocumentFrequency(BufferedReader br, String term) throws FileNotFoundException, IOException {
		double idf = 0; // the Inverted document frequency , will return this value
		double df = 0; // the document frequency
		String line = null;
		while ((line = br.readLine()) != null) {
			String words[] = line.split(" ");
			if (words[0].equals(term)) {
				df = Double.parseDouble(words[1]);
			}
		}
		idf = Math.log10(3204 / df);
		return idf;
	}

	static double calculate(double termFrequency, HashMap<String,Double> idf, String query, int key) {
	 //ArrayList<String> tw  = new ArrayList<>();
		for (Map.Entry<String, Double> x : idf.entrySet()) {
			if(query.equals(x.getKey())) {
				double idfExtracted = x.getValue();
				double weight = termFrequency * idfExtracted;
				System.out.println("weight is: " + weight + "\n");
                return weight ; 
			
			}

		}
		return 0 ; 

	}

	static double termFrequencyOfQuery(HashMap<String, Double> idf) {
		double weight = 0;
		for (String query : userQuery) {
			if (queryMap.containsKey(query)) {
				queryMap.put(query, queryMap.get(query) + 1);
			} else {
				queryMap.put(query, 1);
			}
		}

		for (Map.Entry<String, Integer> e : queryMap.entrySet()) {
			double frequency = e.getValue();
			double termFrequency = (1 + Math.log10(frequency));

			for (Map.Entry<String, Double> x : idf.entrySet()) {
				double idfExtracted = x.getValue();
				double queryWeight = termFrequency * idfExtracted;
				weight += queryWeight;
				//System.out.println(weight);

			}
		}
		return weight;
	}

	static void ranking(int [] arr ){
		Arrays.sort(arr);
		System.out.print("Ranking: " );
		for(int i = arr.length - 1; i >= 0; i--){
  				System.out.print( " " +arr[i]);
		}
	}

}