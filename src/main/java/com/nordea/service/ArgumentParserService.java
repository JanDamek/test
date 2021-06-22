package com.nordea.service;

import com.nordea.configuration.Config;
import com.nordea.configuration.OutputFormat;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class ArgumentParserService {

    public Config parseInputArguments(String[] args) throws IOException {
        if (args != null) {
            File inputFile = null;
            Set<OutputFormat> outputFormats = new HashSet<>();
            var outputName = "";
            for (String arg : args) {
                arg = arg.replace("=", "");
                if (arg.startsWith("-i")) {
                    inputFile = parserInputFile(arg);
                } else if (arg.startsWith("-f")) {
                    outputFormats.addAll(parseOutputFormat(arg));
                } else if (arg.startsWith("-o")) {
                    outputName = parseOutputName(arg);
                }
            }

            return Config.builder()
                    .inputFile(inputFile)
                    .outputFormats(outputFormats)
                    .outputFileName(fixOutputName(outputName, inputFile))
                    .build();
        } else {
            return null;
        }
    }

    private String fixOutputName(String outputName, File inputFileName) {
        if (StringUtils.hasText(outputName)) {
            return outputName;
        }
        if (inputFileName != null) {
            return inputFileName.getName();
        } else {
            return null;
        }
    }

    private String parseOutputName(String arg) {
        return arg.substring(2);
    }

    private Set<OutputFormat> parseOutputFormat(String arg) {
        String[] formats = arg.substring(2).split(",");
        Set<OutputFormat> result = new HashSet<>();
        for (String format : formats) {
            if (format.equalsIgnoreCase("csv")) {
                result.add(OutputFormat.CSV);
            } else if (format.equalsIgnoreCase("xml")) {
                result.add(OutputFormat.XML);
            }
        }
        return result;
    }

    private File parserInputFile(String arg) throws IOException {
        File file;
        var name = arg.substring(2);
        if (name.startsWith("classpath:")) {
            file = new ClassPathResource(name.substring(10)).getFile();
        } else {
            file = new File(name);
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("Input is not file");
        }
        return file;
    }
}
