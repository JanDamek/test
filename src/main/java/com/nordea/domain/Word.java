package com.nordea.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class Word implements Serializable {

    private static final long serialVersionUID = 7823142003677619657L;

    String value;
}
