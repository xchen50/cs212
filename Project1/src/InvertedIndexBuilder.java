import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Class that builds the InvertedIndex.
 * 
 * @author srollins
 *
 */
public class InvertedIndexBuilder {

	/**
	
	It is up to you to design this class. 
	This class will need to recursively traverse an input directory and any time it finds a file
	that has the .txt extension (case insensitive!), it will process the file and add all words to 
	the InvertedIndex.
	
	
	**/
	private Path inputPath;
	private InvertedIndex invertedIndex;
	private Boolean digitDelimiter;
	
	public InvertedIndexBuilder(String inPathStr, Boolean digitDelimiter) {
		this.inputPath = FileSystems.getDefault().getPath(inPathStr);
		this.digitDelimiter = digitDelimiter;
		invertedIndex = new InvertedIndex();
	}
	
	public void init() {
		traversePath(inputPath);
	}
	
	private void traversePath(Path path) {
		// System.out.println("Traversing: " + path);
		if (Files.isDirectory(path)) {
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
				for (Path entry: stream)  {	
					// System.out.println(entry);
					if (entry.toString().toLowerCase().endsWith(".txt"))
						processTextFile(entry);
					else if (Files.isDirectory(entry))
						traversePath(entry);
				}
			} catch(IOException e) {
				System.err.println(e.getMessage());
			}	     
		}
		
	}
	
	private void processTextFile(Path path) {
		// System.out.println("Processing: " + path);
		try {
			int location = 0;
			Pattern pattern;
			if (digitDelimiter) 
				pattern = Pattern.compile("[^a-zA-Z]+");
			else 
				pattern = Pattern.compile("[^0-9a-zA-Z]+");
			Scanner scan = new Scanner(path).useDelimiter(pattern);
			while (scan.hasNext()) {
				location++;
				invertedIndex.add(scan.next().toLowerCase(), path.toString(), location);
			}
		} catch(IOException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	public String toString() {
		return invertedIndex.toString();
	}
	
	public void printToFile(String fileNameStr) {
		Path filePath = FileSystems.getDefault().getPath(fileNameStr);
		invertedIndex.printToFile(filePath);
	}
	
}
