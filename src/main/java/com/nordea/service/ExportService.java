package com.nordea.service;

import com.nordea.configuration.OutputFormat;
import com.nordea.repository.SentenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExportService {

    @Autowired
    private SentenceRepository repository;

    public void export(OutputFormat f, String outputFileName) {

    }
}
