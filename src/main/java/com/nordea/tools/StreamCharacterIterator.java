package com.nordea.tools;

import lombok.SneakyThrows;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.CharacterIterator;

public class StreamCharacterIterator implements CharacterIterator {

    private static final int MAX = 8192;
    private final Reader is;
    private final long length;
    private int pos;
    private char[] current = new char[1];

    public StreamCharacterIterator(File is) throws IOException {
        length = is.length();
        this.is = new BufferedReader(new InputStreamReader(new FileInputStream(is), StandardCharsets.UTF_8));
        this.is.mark(MAX);
        pos = 0;
        current[0]=0;
    }

    @SneakyThrows
    @Override
    public char first() {
        is.mark(MAX);
        is.reset();
        pos += is.read(current);
        return current[0];
    }

    @SneakyThrows
    @Override
    public char last() {
        is.mark(MAX);
        is.reset();
        pos += is.read(current);
        return current[0];
    }

    @Override
    public char current() {
        return current[0];
    }

    @SneakyThrows
    @Override
    public char next() {
        pos += is.read(current);
        return current[0];
    }

    @SneakyThrows
    @Override
    public char previous() {
        is.mark(MAX);
        is.reset();
        pos = (int) is.skip(pos-2);
        pos += is.read(current);
        return current[0];
    }

    @SneakyThrows
    @Override
    public char setIndex(int position) {
        pos = position;
        is.mark(MAX);
        is.reset();
        pos = (int) is.skip(pos);
        pos += is.read(current);
        return current[0];
    }

    @Override
    public int getBeginIndex() {
        return 0;
    }

    @SneakyThrows
    @Override
    public int getEndIndex() {
        return (int) length;
    }

    @Override
    public int getIndex() {
        return pos;
    }

    @Override
    public Object clone() {
        return null;
    }
}
