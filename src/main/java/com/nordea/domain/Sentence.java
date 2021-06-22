package com.nordea.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Sentence implements Serializable {

    private static final long serialVersionUID = -296353305982258436L;

    private List<Word> words;
}
