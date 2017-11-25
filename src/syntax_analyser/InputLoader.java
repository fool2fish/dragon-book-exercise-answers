import model.support.Production;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * @author Hiki
 * @create 2017-11-16 19:28
 */
public class InputLoader {

	private final static String INPUT_END = "$";

	/**
	 * Read inputs from [txt] file.
	 * @param fileName
	 * @return a symbol sequence.
	 */
	public static Queue<String> loadInput(String fileName){
		// Load the file to a reader.
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream is =  classLoader.getResourceAsStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		// Read the stream into a list.
		Queue<String> inputs = new LinkedList<>();
		String line = null;
		try {
			while ((line = br.readLine()) != null){
				inputs.addAll(Arrays.asList(line.split(" ")));
			}
			inputs.add(INPUT_END);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputs;
	}

}
