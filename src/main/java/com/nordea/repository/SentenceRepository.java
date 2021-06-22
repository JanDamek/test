package com.nordea.repository;

import com.nordea.domain.Sentence;
import com.nordea.domain.Word;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Iterator;

@Service
public class SentenceRepository {

    private File tmp;
    private ObjectOutputStream out;
    @Getter
    private int maxColumns;

    public SentenceRepository() throws IOException {
        tmp = File.createTempFile("sentence", "persist");
        out = new ObjectOutputStream(new FileOutputStream(tmp));
        out.useProtocolVersion(ObjectStreamConstants.PROTOCOL_VERSION_2);
    }

    public void saveNew(Sentence sentence) throws IOException {
        if (sentence.getWords().isEmpty()) return;
        sentence.getWords().sort(this::sort);
        maxColumns = Math.max(maxColumns, sentence.getWords().size());
        persistToFile(sentence);
    }

    public void close() throws IOException {
        out.close();
    }

    private void persistToFile(Sentence sentence) throws IOException {
        out.writeObject(sentence);
        out.flush();
    }

    private int sort(Word word, Word word1) {
        return word.getValue().compareToIgnoreCase(word1.getValue());
    }

    @SneakyThrows
    public Iterator<Sentence> iterator() {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(tmp));
        return new RepositoryIterator(in);
    }

    private class RepositoryIterator implements Iterator<Sentence> {
        private ObjectInputStream in;
        private Sentence next;

        public RepositoryIterator(ObjectInputStream in) {
            this.in = in;
        }

        @SneakyThrows
        @Override
        public boolean hasNext() {
            try {
                next = (Sentence) in.readObject();
                return next != null;
            } catch (Exception ignore) {
                next = null;
                return false;
            }
        }

        @SneakyThrows
        @Override
        public Sentence next() {
            return next;
        }
    }
}
