package controller.database;


import entity.user.Student;

import java.io.*;

public class SystemDataManager {

	private final String filePath ;// filename to be filled
    private static final String FILE_PATH = "system.dat";

    public SystemDataManager(String filePath) {
        this.filePath = filePath;
    }

    public SystemDataManager() {
        this(FILE_PATH);
    }


	public IRepository load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
            return (IRepository) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            IRepository repo = new SystemRepository();
            return new CsvLoader(repo).load(); // to be swapped with CSV loader
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("failed to load from .dat file", e);
        }
    }


	/**
	 * 
	 * @param repo
	 */
	public void save(IRepository repo) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(repo);
        }catch (IOException e){
            throw new RuntimeException("failed to save to .dat file", e);
        }
	}

    public void saveUpdate(IRepository repo) {
        save(repo);
    }

}