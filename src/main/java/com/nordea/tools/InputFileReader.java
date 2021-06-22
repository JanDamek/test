package com.nordea.tools;

import java.io.File;
import java.io.IOException;
import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;

public class InputFileReader implements Iterator<String> {

    private BreakIterator sentenceInstance;
    private int next = 0;
    private CharacterIterator ci;
    private int proxim;

    public InputFileReader(final File inputFile) throws IOException {
        sentenceInstance = BreakIterator.getSentenceInstance(Locale.US);
        ci = new StreamCharacterIterator(inputFile);
        sentenceInstance.setText(ci);
        next = sentenceInstance.first();
        proxim = sentenceInstance.next();
    }

    @Override
    public boolean hasNext() {
        return proxim > next;
    }

    @Override
    public String next() {
        if (next == proxim) {
            throw new NoSuchElementException();
        }
        var substring = new StringBuilder();
        ci.setIndex(next);
        while (next <= proxim) {
            substring.append(ci.next());
            next++;
        }
        next = proxim;
        proxim = sentenceInstance.next();
        return substring.toString();
    }
}
