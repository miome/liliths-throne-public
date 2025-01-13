package com.lilithsthrone.threading;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

/**
 * @since 0.4.10.7
 * @version 0.4.10.7
 * @author KeldonSlayer (DrZed)
 */
public class DocBuilders {
    /* Multiple DocBuilders for the threads to effectively get one each */
    private static final ArrayList<DocumentBuilderFactory> docFactories = new ArrayList<>();
    private static final ArrayList<DocumentBuilder> docBuilders = new ArrayList<>();

    /* This reduces failures */
    public static Document parseDoc(File xmlFile) {
        Document doc;
        do {
            doc = parseResetGet(xmlFile);
        } while (doc == null);// since we return null if the doc builder is busy, this basically says "try the next one"
        return doc;
    }

    /* Try, try again */
    private static Document parseResetGet(File xmlFile) {
        if (!xmlFile.exists()) {
            System.err.println("File doesn't exist : " + xmlFile.getPath());
            return null;
        }
        Document doc;
        try {
            DocumentBuilder db = getNextDocBuilder();
            doc = db.parse(xmlFile);
            db.reset();// resetting is only mildly important
        } catch (Exception ignored) {
            return null; // Exception Thrown is a Concurrency Issue, since JAX is not thread-safe
        }// We Catch/Try again because that just means the doc builder sent out was still being used by another thread.
        return doc;
    }

    /* Initializing with 7 was found to be the sweet spot of efficiency */
    private static void initBuilders() {
        for (int i = 0; i < 7; i++) // You can increase this number, for faster loading, however it's diminishing returns.
            docFactories.add(DocumentBuilderFactory.newInstance());
        try {
            for (DocumentBuilderFactory docFactory : docFactories)
                docBuilders.add(docFactory.newDocumentBuilder());
        } catch (Exception ignored) {}
    }

    private static int docBuilderIndex = 0;

    public static DocumentBuilder getNextDocBuilder() {
        if (docFactories.isEmpty())
            initBuilders();
        docBuilderIndex++;
        if (docBuilderIndex >= docBuilders.size())
            docBuilderIndex = 0;
        return docBuilders.get(docBuilderIndex);
    }
}