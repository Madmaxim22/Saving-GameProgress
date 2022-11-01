import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static final String filePath = "/home/maksim/Games/savegames/";
    private static final String zipPath = filePath + "save.zip";
    private static int fileCounter = 1;
    private static List<String> listPath = new ArrayList<>();

    public static void main(String[] args) {


        GameProgress progress1 = new GameProgress(100, 15, 2, 10);
        GameProgress progress2 = new GameProgress(50, 20, 4, 10);
        GameProgress progress3 = new GameProgress(25, 25, 3, 10);

        saveGame(filePath, progress1);
        saveGame(filePath, progress2);
        saveGame(filePath, progress3);

        zipFiles(zipPath, listPath);
        deleteFile(listPath);
    }

    public static void saveGame(String filePath, GameProgress progress) {
        try (FileOutputStream fos = new FileOutputStream(filePath + "Save" + fileCounter + ".dat"); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            listPath.add(filePath + "Save" + fileCounter + ".dat");
            fileCounter++;
            oos.writeObject(progress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<String> fileList) {
        fileCounter = 1;
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String s : fileList) {
                try (FileInputStream fis = new FileInputStream(s)) {
                    ZipEntry entry = new ZipEntry("Save" + fileCounter + ".dat");
                    fileCounter++;
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                } catch (Exception ex) {
                    System.out.println((ex.getMessage()));
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFile(List<String> fileList) {
        for (String s : fileList) {
            File file = new File(s);
            file.delete();
        }
    }
}