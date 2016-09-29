package repairhebsub;

import static java.awt.image.ImageObserver.WIDTH;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Zvika3
 */
public class Main {

    public static String MSG = "";

    /**
     * The main of the software
     */
    public static void main(String[] args) {
        Object[] choices = {"UTF-8", "Windows-1255", "Cancel"};
        Object defaultChoice = choices[2];

        int answer = JOptionPane.showOptionDialog(null,
                "The software only updates the file type \".TXT\" or \".SRT\", that containing in their path the word \"HEB\"." + "\n\nSelect the character encoding of files:\n",
                "RepairHebSub",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                choices,
                defaultChoice);
        switch (answer) {
            case 0:
                MyFile.CHARACTER_ENCODING = (String) choices[0];
                break;
            case 1:
                MyFile.CHARACTER_ENCODING = (String) choices[1];
                break;
            default:
                return;
        }

        fixTheAllFiles();
        JOptionPane.showMessageDialog(null, MSG, "RepairHebSub - Finished!", WIDTH);

    }

    /**
     * Get path and fix it
     */
    public static void fixOneFile(String path) {
        DefaultListModel<String> readFileToModel = MyFile.readFileToModel(path);
        for (int i = 0; i < readFileToModel.size(); i++) {
            String newLine = FixSubLine.fixSubLine(readFileToModel.elementAt(i));
            if (newLine != null) {
                readFileToModel.removeElementAt(i);
                readFileToModel.add(i, newLine);
            }
        }
        if (MyFile.writeModelToFile(path.substring(0,path.length()-4) + "_new" + path.substring(path.length()-4), readFileToModel)) {
            MSG += " : Ready.\n";
        } else {
            MSG += " : Error! The file is not editable.\n";
        }

    }

    /**
     * Fix all files that in the folder software
     */
    public static void fixTheAllFiles() {
        File[] allFiles = MyFile.getAllFiles();
        if(allFiles==null || allFiles.length==0){
             MSG+="There is no suitable files in the current folder.";
        }
           
        
        for (int i = 0; i < allFiles.length; i++) {
            MSG += allFiles[i].getName();
            fixOneFile(allFiles[i].getAbsolutePath());
        }
    }
}
