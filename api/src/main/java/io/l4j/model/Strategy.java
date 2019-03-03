package io.l4j.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Strategy implements Serializable {

    private String type;
    private Long iterations;
    private Integer delay;

}
