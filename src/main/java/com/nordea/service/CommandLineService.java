package com.nordea.service;

import com.nordea.configuration.Config;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class CommandLineService implements CommandLineRunner {

    @Autowired
    private SentenceProcessor processor;
    @Autowired
    private ArgumentParserService parserService;

    @Override
    public void run(String... args) throws Exception {
        Config config = parserService.parseInputArguments(args);
        if (config == null) {
            log.info("use -i'input file' -f'output format\"xml,csv\"' -o'output file name'-if not set use name from input file");
            return;
        }

        log.info("Startup sentence processor with arguments: '{}'", config);
        processor.process(config);
        log.info("Application terminated.");
    }

}
