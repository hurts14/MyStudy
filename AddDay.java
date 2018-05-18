
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;




public class AddDay {


	public static void main(String[] args) {

		// TODO 自動生成されたメソッド・スタブ

		ArrayList<String>StrArray =new ArrayList<String>();
		ArrayList<String>added = new ArrayList<String>();
		HashMap<Integer, String> abc = new HashMap<Integer, String>();
		Map<Integer, String> sortMap = new TreeMap<Integer, String>();
		BufferedReader br = null;
		int roop1 = 0;
		int K = 101;
		String STR = "\"";

		System.out.println("START PROGRAM.	" + (new Date()));

			//Document-Topic input
			try {
			    	File file = new File("K100_Document.csv");
			    	br = new BufferedReader(new FileReader(file));
			        String line;

			    	while ((line = br.readLine()) != null) {
			    		try{
			    			if(0 != roop1){
				    			int a = Integer.parseInt(line.split(",")[0].split("_")[0]);
				    			abc.put(a, line);
				    		}
				    		roop1++;
			    		}catch(NumberFormatException e){

			    		}

			    	}

			}catch(IOException e){
				e.printStackTrace();
			}

			//OriginalData input
			try {
			    	// Addition add day and id
			    	File file = new File("9984.tsv");
			        br = new BufferedReader(new FileReader(file));
			        String line;

			        while ((line = br.readLine()) != null) {
			        	try{
			        		String[] split = line.split("\t",-1);
				        	String day = split[4].split(" ")[0].replaceAll(STR, "");
				        	String ids = split[0].split("_")[0];
					        //id + day
					        StrArray.add(ids + "," + day);
			        	}catch(ArrayIndexOutOfBoundsException e){

			        	}

			         }

			}catch(IOException e){
				e.printStackTrace();
			}finally {
			    try {
			        br.close();
			    } catch (IOException e) {
			    }
			}

			//"day" put in output
			for (String word : StrArray) {
				int b = Integer.parseInt(word.split(",")[0]);
				if(abc.containsKey(b)){
					String line = word.split(",")[1] + "," + abc.get(b);
					added.add(line);
				}
			}

			//sort
			for (String word : added) {
				String s = word.split(",")[1].split("_")[0];
				int c = Integer.parseInt(s);
				String str = s + "," + word.split(",")[0];
				for(int i = 2;i <= K;i++){
					str += ("," + word.split(",")[i]);
				}
				sortMap.put(c, str);
			}

			//data export
			try{
				FileWriter fw = new FileWriter("K100_added_day.csv", true);
		        PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
		        for (Integer nKey : sortMap.keySet()){
		        	pw.println(sortMap.get(nKey));
		        }
		        System.out.println("finish output");
		        pw.close();
			}catch(IOException ex){
				ex.printStackTrace();
			}
			System.out.println("END PROGRAM.	" + (new Date()));

		}

}
