package fileStuffObjects;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.PrintWriter;

public class LineReader {
	
	public static void old_main_2(String[] args) {
		
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			
			in = new BufferedReader( new FileReader("data/strings.txt"));
			out = new PrintWriter( new BufferedWriter ( new FileWriter("data/output.txt")));
			String s;
			int tcount = 0;
			
			System.out.println("Opened files");
			
			while((s = in.readLine()) != null) {
				out.println(s);
				System.out.print(".");
				++tcount;
				if(tcount > 10)
					break;
				
			}
			System.out.println(" ");
			System.out.println("Wrote file");
			
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe.getMessage());
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		} finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
				System.out.println("Closed files");
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}
		}
	}
}
