import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Stream;

public class Make_AveMatrix {
	public static void main(String[] args) {

		ArrayList<String>StrArray =new ArrayList<String>();
		ArrayList<String>Output = new ArrayList<String>();
		double[] ave = new double[101];
		String a = "1999-12-29";
		String b;
		int count = 0;

		System.out.println("START PROGRAM.	" + (new Date()));

		try (Stream<String> stream = Files.lines(Paths.get("K100_added_day.csv"), StandardCharsets.UTF_8)) {
		    stream.forEach(line -> {
		        // do something
		    	StrArray.add(line);
		    });
		}catch(IOException e){
			e.printStackTrace();
		}

		//Calculation
		for (String word : StrArray){
			String[] split = word.split(",");

			b = split[1];  //Day of word
			if(0 != a.compareToIgnoreCase(b)){
				if("1999-12-29".equals(a)){//new day
					a = b;
					count = 1;
					for(int j = 0; j < 100; j++){
						ave[j] = Double.parseDouble(split[2+j]);
					}
				}else{
					String line = a;
					for(int i = 0; i < 100; i++){
						ave[i] /= count;
						line += ","+ave[i];
					}
					line += ","+count;
					Output.add(line);
					a = b;
					count = 1;
					for(int j = 0; j < 100; j++){
						ave[j] = Double.parseDouble(split[2+j]);
					}
				}

			}else{
				if("1999-12-29".equals(a)){

				}else{
					count++;
					for(int i = 0; i < 100; i++){
						ave[i] += Double.parseDouble(split[2+i]);
					}
				}

			}

		}

		String line = a;
		for(int i = 0; i < 100; i++){
			ave[i] /= count;
			line += ","+ave[i];
		}
		line += ","+count;
		Output.add(line);


		//data export
		try{
			FileWriter fw = new FileWriter("K100_matrix.csv", true);
	        PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
	        for(String str : Output){
	        	pw.println(str);
	        }
	        System.out.println("finish output");
	        pw.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}

		System.out.println("END PROGRAM.	" + (new Date()));

	}

}
