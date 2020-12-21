package com.dk.geektime.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author DingKay
 */
@Data
@Builder
public class Foo {
    private Integer id;
    private String bar;
}
