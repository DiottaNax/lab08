package it.unibo.mvc;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 
 *
 */
public final class SimpleController implements Controller {

    List<String> history = new LinkedList<>();
    String nextString;

    @Override
    public void print() {
        if (this.nextString == null)
            throw new IllegalStateException("No string to print set!");

        PrintStream out = new PrintStream(System.out);
        out.println(this.nextString);
        history.add(this.nextString);
    }
    
    public void print(String sentence) {
        this.setNextStringToPrint(sentence);
        this.print();
    }

    @Override
    public void setNextStringToPrint(String sentence) {
        this.nextString = Objects.requireNonNull(sentence, "Please write something int the text field");;
    }

    @Override
    public String getNexStringToPrint() {
        return this.nextString;
    }


    @Override
    public List<String> getAllPrintedStrings() {
        return this.history;
    }

}
