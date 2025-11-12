package controller.database;

import java.io.*;

/**
 * SystemDataManger create system repository and load data into repository
 * save the repository data as .dat file for future runs
 */
public class SystemDataManager {

	private final String filePath ;// filename to be filled
    private static final String FILE_PATH = "system.dat";

    /**
     * constructor of SystemDataManger when filepath undefined
     * @param filePath file to be saved
     */
    public SystemDataManager(String filePath) {
        this.filePath = filePath;
    }

    /**
     * constructor of SystemDataManager when filepath is defined
     */
    public SystemDataManager() {
        this(FILE_PATH);
    }


    /**
     * deserialize .dat file to extract data and return as repository
     * if .dat file not present, call CSVLoader
     * @return repository
     */
	public IRepository load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
            return (IRepository) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            IRepository repo = new SystemRepository();
            return new CsvLoader(repo).load();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("failed to load from .dat file", e);
        }
    }


	/**
	 * serialize system repository and save it in a .dat file
	 * @param repo system repository
	 */
	public void save(IRepository repo) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(repo);
        }catch (IOException e){
            throw new RuntimeException("failed to save to .dat file", e);
        }
	}


}