import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress firstGameProgress = new GameProgress(91, 11, 3, 113.27);
        GameProgress secondGameProgress = new GameProgress(80, 4, 5, 333.33);
        GameProgress thirdGameProgress = new GameProgress(100, 20, 1, 2.3);

        String firstSavePath = "/Users/kirillmakarov/Games/savegames/save1.dat";
        String secondSavePath = "/Users/kirillmakarov/Games/savegames/save2.dat";
        String thirdSavePath = "/Users/kirillmakarov/Games/savegames/save3.dat";

        saveGame(firstSavePath, firstGameProgress);
        saveGame(secondSavePath, secondGameProgress);
        saveGame(thirdSavePath, thirdGameProgress);
        System.out.println();

        String zipPath = "/Users/kirillmakarov/Games/savegames/zip.zip";
        List<String> saveFiles = new ArrayList<>();
        saveFiles.add(firstSavePath);
        saveFiles.add(secondSavePath);
        saveFiles.add(thirdSavePath);

        zipFiles(zipPath, saveFiles);
    }

    public static void saveGame(String path, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(path); ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
            System.out.println("Игра сохранена: " + path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void zipFiles(String path, List<String> filesToZip) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(path))) {
            for (String filePath : filesToZip) {
                try (FileInputStream fis = new FileInputStream(filePath)) {
                    File fileToZip = new File(filePath);
                    ZipEntry entry = new ZipEntry(fileToZip.getName());
                    zout.putNextEntry(entry);
                    byte[] buffer = new byte[fis.available()];
                    fis.read(buffer);
                    zout.write(buffer);
                    zout.closeEntry();
                    System.out.println("Файл " + filePath + " архивирован!");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("Zip архив создан!");
            System.out.println();

            for (String filePath : filesToZip) {
                File file = new File(filePath);
                if (file.delete()) {
                    System.out.println("Файл " + filePath + ",находящийся вне архива, удален!");
                } else {
                    System.out.println("Ошибка удаления файла: " + filePath);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}