package controller.database;


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


	public IResposistory load() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
            return (IResposistory) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            return new SystemResposistory(); // to be swapped with CSV loader
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("failed to load from .dat file", e);
        }
    }


	/**
	 * 
	 * @param repo
	 */
	public void save(IResposistory repo) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
            oos.writeObject(repo);
        }catch (IOException e){
            throw new RuntimeException("failed to save to .dat file", e);
        }
	}

    public void saveUpdate(IResposistory repo) {
        save(repo);
    }

}