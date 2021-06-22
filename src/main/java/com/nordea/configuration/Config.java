package com.nordea.configuration;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.io.File;
import java.util.Set;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Config {
    File inputFile;
    Set<OutputFormat> outputFormats;
    String outputFileName;
}
