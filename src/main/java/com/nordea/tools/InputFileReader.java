package com.nordea.tools;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputFileReader implements Iterator<String> {

    private final Scanner scanner;

    public InputFileReader(final File inputFile) throws IOException {
        scanner = new Scanner(inputFile, StandardCharsets.UTF_8);
        scanner.useLocale(Locale.US);
        scanner.useDelimiter(Pattern.compile("[.!?]",Pattern.CASE_INSENSITIVE|Pattern.MULTILINE));
    }

    @Override
    public boolean hasNext() {
        return scanner.hasNext();
    }

    @Override
    public String next() {
        return scanner.next();
    }
}
