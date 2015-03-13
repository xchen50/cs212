import java.nio.file.FileSystems;


public class Driver {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration(FileSystems.getDefault().getPath("config.json"));
		try {
			conf.init();
		} catch (InitializationException e) {
			System.err.println(e.getMessage());
			return;
		}
		
		InvertedIndexBuilder invertedIndexBuilder = new InvertedIndexBuilder(
				conf.getInputPath(), conf.useDigitDelimiter());
		
		invertedIndexBuilder.init();
	
		if (conf.getOutputPath() != null)
			invertedIndexBuilder.printToFile(conf.getOutputPath());
	}
	
}
