package repairhebsub;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.swing.DefaultListModel;

/**
 *
 * @author Zvika3
 */
public class MyFile {

    public static String CHARACTER_ENCODING = ""; //the "class Main" updates this variable
    
    /**
     * read the file and return the file in DefaultListModel
     */
    public static DefaultListModel<String> readFileToModel(String path) {
        DefaultListModel<String> list = new DefaultListModel<>();
        String line = "";
        try {
            InputStream inputStream = new FileInputStream(path);
            Reader inputStreamReader = new InputStreamReader(inputStream, CHARACTER_ENCODING); 

            int data = inputStreamReader.read();
            while (data != -1) {
                char theChar = (char) data;
                line += theChar;

                if (theChar == '\n') { //Each line separately
                    list.addElement(line);
                    line = "";
                }

                data = inputStreamReader.read();
            }
            //for the last line
            list.addElement(line);

            inputStreamReader.close();
            inputStream.close();
        } catch (FileNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
        return list;
    }

    /**
     * save the file from the DefaultListModel in the path.
     * BOOLEAN returns to action success or failure.
     */
    public static boolean writeModelToFile(String path, DefaultListModel<String> list) {
        File file;
        OutputStream File_Writer;
        Writer OutputStreamWriter;
        BufferedWriter bufferedWriter;

        String line;
        int size = list.getSize();

        try {
            if (path == null || list == null) {
                return false;
            }
            file = new File(path);
            if (file == null) {
                return false;
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            if (!file.exists()) {
                return false;
            }

            File_Writer = new FileOutputStream(path);
            OutputStreamWriter = new OutputStreamWriter(File_Writer, CHARACTER_ENCODING);

            for (int i = 0; i < size; i++) {
                line = (String) list.getElementAt(i);
                if (line != null) {
                    OutputStreamWriter.write(line + "\n");
                }
            }

            OutputStreamWriter.close();
            File_Writer.close();

        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    /**
     * Returns an array with all the addresses of the files in folder software.
     */
    public static File[] getAllFiles() {
        Path currentRelativePath = Paths.get("");
        String path = currentRelativePath.toAbsolutePath().toString();
        Main.MSG += "Current folder: " + path + "\n\n";

        File dir = new File(path);
        File[] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String file_path = name.toLowerCase();

                return file_path.contains("heb") && (file_path.endsWith(".txt") || file_path.endsWith(".srt"));
            }
        });
        return files;
    }
}
