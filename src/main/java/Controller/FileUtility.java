package Controller;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtility {
    private static final int BUFFER_SIZE = 4096;
    private final String localPath = "/Users/prakshat/Documents/git/GitHubPlagiarismDetector/src/main/resources/Repositories/team";
    private List<String> results = new ArrayList<>();

    public void makeTeamRepositories(int size) throws InterruptedException, IOException {
        for(int index=1; index<= size; index++) {
            File f = new File((localPath+index));
            f.mkdir();
        }
    }

    public void zip(List<File> listFiles, String destZipFile) throws FileNotFoundException,
            IOException {
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(destZipFile));
        for (File file : listFiles) {
            if (file.isDirectory()) {
                zipDirectory(file, file.getName(), zos);
            }
        }
        zos.flush();
        zos.close();
    }

    private void zipDirectory(File folder, String parentFolder,
                              ZipOutputStream zos) throws FileNotFoundException, IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                zipDirectory(file, parentFolder + "/" + file.getName(), zos);
                continue;
            }
            zos.putNextEntry(new ZipEntry(parentFolder + "/" + file.getName()));
            BufferedInputStream bis = new BufferedInputStream(
                    new FileInputStream(file));
            long bytesRead = 0;
            byte[] bytesIn = new byte[BUFFER_SIZE];
            int read = 0;
            while ((read = bis.read(bytesIn)) != -1) {
                zos.write(bytesIn, 0, read);
                bytesRead += read;
            }
            zos.closeEntry();
        }
    }

    public List<String> getResults() {
        return results;
    }

}