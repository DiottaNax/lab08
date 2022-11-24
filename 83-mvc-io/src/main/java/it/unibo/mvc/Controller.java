package it.unibo.mvc;

import java.util.List;

/**
 *
 */
public interface Controller {

    /**
     * 
     * @param sentence
     * 
     * Prints the current String on Standard Output
     */
    public void print();

    /**
     * 
     * @param sentence
     * 
     * A method for setting @param sentence as the next string to print. 
     * Null values are not acceptable, and an exception should be produced.
     */
    public void setNextStringToPrint(String sentence);

    /**
     * 
     * @return next String to print
     */
    public String getNexStringToPrint();


    /**
     * 
     * @return a List cotaining the history of printed Strings
     */
    public List<String> getAllPrintedStrings();


}
