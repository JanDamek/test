package com.nordea.service;

import com.nordea.configuration.OutputFormat;
import com.nordea.domain.Sentence;
import com.nordea.domain.Word;
import com.nordea.repository.SentenceRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.Writer;

@Service
public class ExportService {

    @Autowired
    private SentenceRepository repository;

    @SneakyThrows
    public void export(OutputFormat f, String outputFileName) {
        final int[] cnt = {1};
        Writer out = new FileWriter(outputFileName + "." + f.name());
        if (f==OutputFormat.XML){
            out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        } else if (f==OutputFormat.CSV){
            for (int i = 1; i < repository.getMaxColumns(); i++) {
                out.write(", Word "+i);
            }
            out.write("\n");
        }
        repository.iterator().forEachRemaining(s -> write(s, f, out, cnt[0]++));
        out.close();
    }

    @SneakyThrows
    private void write(Sentence sentence, OutputFormat f, Writer out, int count) {
        switch (f){
            case CSV:
                csvWrite(sentence,out,count);
                break;
            case XML:
                xmlWrite(sentence,out);
                break;
        }
        out.flush();
    }

    @SneakyThrows
    private void xmlWrite(Sentence sentence, Writer out) {
        out.write("<sentence>\n");
        for (Word word : sentence.getWords()) {
            out.write("<word>"+word.getValue()+"</word>\n");
        }
        out.write("</sentence>\n");
    }

    @SneakyThrows
    private void csvWrite(Sentence sentence, Writer out, int count) {
        out.write("Sentence "+count);
        for (Word word : sentence.getWords()) {
            out.write(", "+word.getValue());
        }
        out.write("\n");
    }
}
