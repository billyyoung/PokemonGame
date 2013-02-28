import java.util.*;
import java.io.*;

public class test1{
	public static void main(String[] args)throws IOException{
		String[] lines = new String[27];
		
		BufferedReader in = new BufferedReader(new FileReader("pokemon info.txt"));
		for (int i=0;i<27;i++){
			lines[i] = in.readLine();
		}
		in.close();
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("pokemon info.txt")));
		Arrays.sort(lines);
		for (int i=0;i<27;i++){
			out.println(lines[i]);
		}
		out.close();
	}
}