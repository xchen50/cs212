
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The Configuration class parses a JSON file containing configuration information and provides wrapper methods
 * to access the configuration data in the JSON object.
 * @author srollins
 *
 */
public class Configuration {

	/**
	 * Constants that store the keys used in the configuration file.
	 */
	public static final String INPUT_PATH = "inputPath";
	public static final String OUTPUT_PATH = "outputPath";
	public static final String DIGIT_DELIMITER = "digitDelimiter";	
	

	/**
	 * TODO: Instance variables to store shared information.
	 */
	private Path jsonFilePath = null;
	private String inputPath = null;
	private String outputPath = null;
	private Boolean digitDelimiter = false;
	
	/**
	 * Instantiates a Configuration object.
	 * @param path - the location of the file 			
	 */
	public Configuration(Path path) {
		//TODO: Complete constructor.
		this.jsonFilePath = path;
	}
	
	/**
	 * Initializes a Configuration object. Uses a JSONParser to parse the contents of the file. Hint:
	 * I used a helper method to validate the contents of the file once it was parsed. Note: you will need
	 * to implement your own exception class called InitializationException.
	 * @throws InitializationException - thrown in the following cases: (1) an IOException is generated when 
	 * 				accessing the file; (2) a ParseException is thrown when parsing the JSON contents of the file;
	 * 				(3) the file does not contain the inputPath key; (4) the file does not contain the digitDelimiter 
	 * 				key; (5) the digitDelimiter value is not a boolean.
	 */
	public void init() throws InitializationException {
		JSONObject jsonobject = null;
		JSONParser parser = new JSONParser();
		BufferedReader inFile = null;
		
		try {
			inFile = Files.newBufferedReader(jsonFilePath, Charset.forName("UTF-8"));
		} catch(IOException e) {
			throw new InitializationException("Unable to open file");
		}
		
		try {
			jsonobject = (JSONObject) parser.parse(inFile);
		} catch (IOException | ParseException e) {
			throw new InitializationException("Unable to parse file");
		}
		
		validateJsonContent(jsonobject);

	}
	
	/** 
	 * A helper method to validate the contents of the JSON file once it is parsed.
	 * @throws InitializationException - thrown in the following cases:
	 *       (1) the file does not contain the inputPath key;
	 *       (2) the file does not contain the digitDelimiter;
	 *       (3) the digitDelimiter value is not a boolean.
	 */
	private void validateJsonContent(JSONObject jsonobject) throws InitializationException {
		if (jsonobject.containsKey(INPUT_PATH)) 
			inputPath = (String) ((JSONObject) jsonobject).get(INPUT_PATH);
		else 
			throw new InitializationException("inputPath not specified");
		
		if (jsonobject.containsKey(DIGIT_DELIMITER)) {
			try {
				digitDelimiter = (Boolean)((JSONObject) jsonobject).get(DIGIT_DELIMITER);
			} catch (ClassCastException e) {
				throw new InitializationException("digitDelimiter not a boolean");
			}
		} else {
			throw new InitializationException("digitDelimiter not specified");
		}
		
		if (jsonobject.containsKey(OUTPUT_PATH))
			outputPath = (String) ((JSONObject) jsonobject).get(OUTPUT_PATH);
		
	}
	

	/**
	 * Returns the value of associated with the inputPath key in the JSON configuration file.
	 * @return - value associated with key inputPath
	 */
	public String getInputPath() {
		return inputPath;
	}
	
	/**
	 * Returns the value of associated with the outputPath key in the JSON configuration file.
	 * @return - value associated with key outputPath - null if no outputPath specified
	 */
	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * Returns the value of associated with the digitDelimiter key in the JSON configuration file.
	 * @return - value associated with key digitDelimiter
	 */
	public boolean useDigitDelimiter() {
		return digitDelimiter;
	}
	
	
	/**
	 * Simple main method used for in-progress testing of Configuration class only.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		(new Configuration(FileSystems.getDefault().getPath("config.json"))).init();
	}
	
}
