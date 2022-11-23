package it.unibo.api;

import java.io.File;
import java.io.IOException;

public interface SimpleController {
    
    public void setCurrentFile(final String fileName);

    public File getFile();

    public String getPath();

    public void writeStringOnFile (final String input) throws IOException;

}
