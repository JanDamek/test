package com.nordea.service;

import com.nordea.configuration.Config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.io.IOException;

@SpringBootTest
class ArgumentParserServiceTest {

    @Autowired
    private ArgumentParserService parserService;

    @Test
    void parseInputArguments() throws IOException {
        String[] args = {"-i=classpath:/small.in", "-fxml,csv"};
        Config config = parserService.parseInputArguments(args);
        Assert.isTrue(config.getInputFile() != null, "Input file not set.");
        Assert.isTrue(config.getOutputFormats().size() == 2, "Not parsed input formats");
        Assert.isTrue(config.getOutputFileName().equalsIgnoreCase("small.in"), "Not set output name.");

    }
}