package com.nordea.service;

import com.nordea.configuration.Config;
import com.nordea.domain.Sentence;
import com.nordea.domain.Word;
import com.nordea.logging.LogTime;
import com.nordea.repository.SentenceRepository;
import com.nordea.tools.InputFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.LinkedList;

@Service
public class SentenceProcessor {

    @Autowired
    private SentenceRepository repository;
    @Autowired
    private ExportService exportService;

    BreakIterator wb = BreakIterator.getWordInstance();

    @LogTime
    public void process(Config config) throws IOException {
        InputFileReader fileReader = new InputFileReader(config.getInputFile());
        while (fileReader.hasNext()) {
            Sentence sentence = parseSentence(fileReader.next());
            repository.saveNew(sentence);
        }
        config.getOutputFormats().forEach(f -> exportService.export(f, config.getOutputFileName()));
    }

    private Sentence parseSentence(String inputSentence) {
        Sentence result = new Sentence(new LinkedList<>());
        wb.setText(inputSentence);

        int last = 0;
        int current = wb.next();
        while (current != BreakIterator.DONE) {
            processWord(result, inputSentence.substring(last, current));
            last = current;
            current = wb.next();
        }
        return result;
    }

    private void processWord(Sentence result, String substring) {
        if (!StringUtils.hasText(substring)){
            return;
        }
        if (substring.length()>1 && Character.isLetter(substring.charAt(0))) {
            result.getWords().add(Word.builder().
                    value(substring)
                    .build()
            );
        }
    }

}
