import java.nio.file.FileSystems;
import java.nio.file.Path;


public class Driver {

	public static void main(String[] args) throws Exception {
		
		
		Configuration conf = new Configuration(FileSystems.getDefault().getPath("config.json"));
		try {
			conf.init();
		} catch (InitializationException e) {
			System.err.println(e.getMessage());
			return;
		}
		
		InvertedIndex invertedIndex 
			= new InvertedIndexBuilder().getInvertedIndex(conf.getInputPath(), 
					conf.useDigitDelimiter());
		
	
		if (conf.getOutputPath() != null) {
			Path outPath = FileSystems.getDefault().getPath(conf.getOutputPath());
			invertedIndex.printToFile(outPath);
		}
	}
	
}
