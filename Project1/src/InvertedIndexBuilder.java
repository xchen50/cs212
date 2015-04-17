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
	private InvertedIndex invertedIndex;
	
	public InvertedIndexBuilder() {
		
	}
	
	public InvertedIndex getInvertedIndex(String inPathStr, Boolean digitDelimiter) {
		invertedIndex = new InvertedIndex();
		Path inputPath = FileSystems.getDefault().getPath(inPathStr);
		traversePath(inputPath,  digitDelimiter);
		return invertedIndex;
	}
	
	private void traversePath(Path path, Boolean digitDelimiter) {
		if (Files.isDirectory(path)) {
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
				for (Path entry: stream)  {	
					if (entry.toString().toLowerCase().endsWith(".txt"))
						processTextFile(entry, digitDelimiter);
					else if (Files.isDirectory(entry))
						traversePath(entry, digitDelimiter);
				}
			} catch(IOException e) {
				System.err.println(e.getMessage());
			}	     
		}
		
	}
	
	private void processTextFile(Path path, Boolean digitDelimiter) {
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
	
}
