package it.unibo.mvc;

import java.io.*;
import java.nio.charset.StandardCharsets;

import javax.imageio.IIOException;
import javax.print.event.PrintEvent;

import it.unibo.api.SimpleController;

/**
 * Application controller. Performs the I/O.
 */
public class Controller implements SimpleController {

    private static String HOME = System.getProperty("user.home");
    private static String DEFAULT_FILE = "output.txt";
    private File currentFile = new File(HOME + File.separator + DEFAULT_FILE);

    @Override
    public File getFile() {
        return this.currentFile;
    }

    @Override
    public String getPath() {
        return this.currentFile.getPath();
    }

    @Override
    public void writeStringOnFile(String input) throws IOException {
        try{
            PrintWriter write = new PrintWriter(this.currentFile, StandardCharsets.UTF_8);
            write.println(input);
            write.close();
        } catch (IIOException e) {
            System.out.println("Errore nella scrittura: ");
        }
    }

    @Override
    public void setCurrentFile(String fileName) {
        this.setCurrentFile(new File(fileName));
    }

    public void setCurrentFile(File file) {
        if (file.getParentFile().exists()) {
            this.currentFile = file;
        } else {
            throw new IllegalArgumentException(
                    "There's no location such as " + file.getParentFile());
        }
    }

}
