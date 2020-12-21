package com.dk.geektime.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author Kay
 */
@Data
@Builder
public class Foo {
    private Long id;
    private String bar;
}
