

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;




public class AddDay {


	public static void main(String[] args) {

		// TODO

		ArrayList<String>StrArray =new ArrayList<String>();
		HashMap<Integer, String> dayMap = new HashMap<Integer, String>();
		HashMap<Integer, String> topicMap = new HashMap<Integer, String>();
		Map<Integer, String> sortMap = new TreeMap<Integer, String>();
		int K = 101;
		String STR = "\"";
		String PATH1 = "K100_Document.csv"; //training
		//String PATH1 = "K100_TestFeature.csv"; //test
		String PATH2 = "9984.tsv";

		System.out.println("START PROGRAM.	" + (new Date()));

			//Document-Topic input
		try (Stream<String> stream = Files.lines(Paths.get(PATH1), StandardCharsets.UTF_8)) {
		    stream.forEach(line -> {
		        // do something
		    	try{
		    	    int a = Integer.parseInt(line.split(",")[0].split("_")[0]);
	    		    topicMap.put(a, line);
		    	}catch(NumberFormatException e){
	    		}
		    });
		}catch(IOException e){
			e.printStackTrace();
		}

		//OriginalData input

		try (Stream<String> stream = Files.lines(Paths.get(PATH2), StandardCharsets.UTF_8)) {
		    stream.forEach(line -> {
		        // do something
		    	try{
		    		String[] split = line.split("\t",-1);
		        	String day = split[5].split(" ")[0].replaceAll(STR, "");
		        	String ids = split[0].split("_")[0];
		        	int b = Integer.parseInt(ids);
			        //id + day
			        dayMap.put(b,ids + "," + day);
		    	}catch(NumberFormatException e){
	    		}
		    });
		}catch(IOException e){
			e.printStackTrace();
		}

			//"day" put in output
			for (Integer key : dayMap.keySet()) {
				if(topicMap.containsKey(key)){
					String line = dayMap.get(key).split(",")[1] + "," + topicMap.get(key);
					StrArray.add(line);
				}
			}

			//sort
			for (String word : StrArray) {
				String s = word.split(",")[1].split("_")[0];//id
				int c = Integer.parseInt(s);
				String str = s + "," + word.split(",")[0];//id+day
				for(int i = 2;i <= K;i++){
					str += ("," + word.split(",")[i]);
				}
				sortMap.put(c, str);
			}

			//data export
			try{
		        PrintWriter out = new PrintWriter("K100_added_day.csv", "Shift_JIS");
		        //PrintWriter out = new PrintWriter("K100_added_day_testdata.csv", "Shift_JIS");//TestDataver
		        sortMap.forEach((key, value) -> out.println(value));
		        System.out.println("finish output");
		        out.close();
			}catch(IOException ex){
				ex.printStackTrace();
			}
			System.out.println("END PROGRAM.	" + (new Date()));

		}

}
