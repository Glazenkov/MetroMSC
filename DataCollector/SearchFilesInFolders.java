package DataCollector;

import java.io.File;

public class SearchFilesInFolders {
    public SearchFilesInFolders() {
    }

    public void bypassFolders(String path) {
        File folder = new File(path);
        String extension = getFileExtension(folder);
        if (folder.isFile() &&
                (extension.equals("csv") || extension.equals("json"))) {
            System.out.println(folder.getPath());
        }
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            assert files != null;
            for (File f : files) {
                bypassFolders(f.getPath());
            }
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") > 0)
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        else return "";
    }
}
