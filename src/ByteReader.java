package fileStuffObjects;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileNotFoundException;

public class ByteReader {

	public static void old_main(String[] args) {
		
		FileInputStream in = null;
		FileOutputStream out = null;
		
		try {

			in = new FileInputStream ("data/strings.txt");
			out = new FileOutputStream ("data/output.txt");
			int c;
			
			while ((c = in.read()) != -1) {
				out.write(c);
			} 
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
			} catch (IOException ioe) {
				System.out.println(ioe.getMessage());
			}

			
		}

	}

}
