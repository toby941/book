package com.danbook.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;
import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.danbook.R;

public class EpubBookInfo extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.epub);
        AssetManager assetManager = getAssets();
        try {
            // find InputStream for book
            InputStream epubInputStream = assetManager.open("1.epub");

            // Load Book from inputStream
            Book book = (new EpubReader()).readEpub(epubInputStream);

            // Log the book's authors
            Log.i("epublib", "author(s): " + book.getMetadata().getAuthors());

            // Log the book's title
            Log.i("epublib", "title: " + book.getTitle());

            // Log the book's coverimage property
            Bitmap coverImage = BitmapFactory.decodeStream(book.getCoverImage().getInputStream());
            Log.i("epublib", "Coverimage is " + coverImage.getWidth() + " by " + coverImage.getHeight() + " pixels");

            // Log the tale of contents
            logTableOfContents(book.getTableOfContents().getTocReferences(), 0);
        }
        catch (IOException e) {
            Log.e("epublib", e.getMessage());
        }
    }

    /**
     * Recursively Log the Table of Contents
     * 
     * @param tocReferences
     * @param depth
     */
    private void logTableOfContents(List<TOCReference> tocReferences, int depth) {
        if (tocReferences == null) {
            return;
        }
        for (TOCReference tocReference : tocReferences) {
            StringBuilder tocString = new StringBuilder();
            for (int i = 0; i < depth; i++) {
                tocString.append("\t");
            }
            tocString.append(tocReference.getTitle());
            Log.i("epublib", tocString.toString());

            logTableOfContents(tocReference.getChildren(), depth + 1);
        }
    }
}
