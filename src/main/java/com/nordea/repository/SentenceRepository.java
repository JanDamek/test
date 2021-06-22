package com.nordea.repository;

import com.nordea.domain.Sentence;
import com.nordea.domain.Word;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class SentenceRepository {

    private OutputStream tmp;
    private ObjectOutputStream out;

    public SentenceRepository() throws IOException {
        tmp = new FileOutputStream(File.createTempFile("sentence", "persist"));
        out = new ObjectOutputStream(tmp);
    }

    public void saveNew(Sentence sentence) throws IOException {
        sentence.getWords().sort(this::sort);

        persistToFile(sentence);
    }

    public void close() throws IOException {
        out.close();
    }

    private void persistToFile(Sentence sentence) throws IOException {
        out.writeObject(sentence);
    }

    private int sort(Word word, Word word1) {
        return word.getValue().compareToIgnoreCase(word1.getValue());
    }
}
