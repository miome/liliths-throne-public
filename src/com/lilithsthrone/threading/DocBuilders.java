package com.lilithsthrone.threading;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class DocBuilders {
    private static final ArrayList<DocumentBuilderFactory> docFactories = new ArrayList<>();
    private static final ArrayList<DocumentBuilder> docBuilders = new ArrayList<>();

    /* This reduces failures */
    public static Document parseDoc(File xmlFile) {
        Document doc;
        do {
            doc = parseResetGet(xmlFile);
        } while (doc == null);
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
            db.reset();
        } catch (Exception ignored) {
            return null;
        }// Exception Thrown is a Concurrency Issue, since JAX is not thread-safe
        return doc;
    }

    /* Initializing with 7 was found to be the sweet spot of efficiency */
    private static void initBuilders() {
        for (int i = 0; i < 7; i++) docFactories.add(DocumentBuilderFactory.newInstance());
        try {
            for (DocumentBuilderFactory docFactory : docFactories) docBuilders.add(docFactory.newDocumentBuilder());
        } catch (Exception ignored) {}
    }

    private static int docBuilderIndex = 0;

    public static DocumentBuilder getNextDocBuilder() {
        if (docFactories.isEmpty()) initBuilders();
        docBuilderIndex++;
        if (docBuilderIndex >= docBuilders.size()) docBuilderIndex = 0;
        return docBuilders.get(docBuilderIndex);
    }
}