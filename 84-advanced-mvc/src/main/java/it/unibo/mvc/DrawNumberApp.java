package it.unibo.mvc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import it.unibo.mvc.Configuration.Builder;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {
    private final DrawNumber model;
    private final List<DrawNumberView> views;
    private static final String SEP = File.separator;
    private final String PATH = "src" + SEP + "main" + SEP + "resources" + SEP + "config.yml";

    /**
     * @param views
     *            the views to attach
     */
    public DrawNumberApp(final DrawNumberView... views) {
        /*
         * Side-effect proof
         */
        this.views = Arrays.asList(Arrays.copyOf(views, views.length));
        for (final DrawNumberView view : views) {
            view.setObserver(this);
            view.start();
        }

        Configuration.Builder buildConfiguration = new Configuration.Builder();
        try(BufferedReader read = new BufferedReader(new FileReader(PATH))){
            for (var line = read.readLine(); line != null; line = read.readLine()) {
                StringTokenizer splitter = new StringTokenizer(line);
                String firstToken = splitter.nextToken();
                if (firstToken.contains("max")) {
                    buildConfiguration.setMax(Integer.parseInt(splitter.nextToken()));
                }else if(firstToken.contains("min")) {
                    buildConfiguration.setMin(Integer.parseInt(splitter.nextToken()));
                } else if(firstToken.contains("attempts")) {
                    buildConfiguration.setAttempts(Integer.parseInt(splitter.nextToken()));
                } else {
                    this.displayError("Error while setting the configuration, cannot read: " + firstToken);
                }
            }
        } catch(IOException e){
            this.displayError("Something went wrong while reading the configuration file: " + e);
        }

        Configuration configuration = buildConfiguration.build();
        if(configuration.isConsistent()){
            this.model = new DrawNumberImpl(configuration);
        } else{
            this.displayError("Error with the configuartion: " 
                + "min=" + configuration.getMin() 
                + " max=" + configuration.getMax()
                + " attempts=" + configuration.getAttempts()
                + "deafult configuration will be used");
            this.model = new DrawNumberImpl(new Configuration.Builder().build());
        }
            
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view: views) {
                view.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView view: views) {
                view.numberIncorrect();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        /*
         * A bit harsh. A good application should configure the graphics to exit by
         * natural termination when closing is hit. To do things more cleanly, attention
         * should be paid to alive threads, as the application would continue to persist
         * until the last thread terminates.
         */
        System.exit(0);
    }

    /**
     * @param args
     *            ignored
     * @throws FileNotFoundException 
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp(new DrawNumberViewImpl(),
                new DrawNumberViewImpl(),
                new PrintStreamView(System.out));
    }

    @Override
    public void displayError(String errMessage) {
        for (final var view : views) {
            view.displayError(errMessage);
        }
        
    }

}
