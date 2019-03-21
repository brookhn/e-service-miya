package com.pp.server.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class StreamOperation {

    public static void main(String args[])
    {
        List<String> testList = new ArrayList<>();
        testList.add("hello");
        testList.add("world");
        testList.add("hellot");
//        testList.forEach(k->{
//                System.out.println(k);
//        });
//        long size = testList.stream().filter(k->k.contains("hello")).count();
//        System.out.println(size);

        List<String> collected = Stream.of("a", "b", "c")
                                       .map(string->string.toUpperCase())
                                       .filter(value->!value.equals("A"))
                                       .collect(Collectors.toList());
        collected.forEach(k->{
            System.out.println(k);
        });
        /******************************************************
         * flatMap operation
         */
        List<Integer> together = Stream.of(asList(12, 34), asList(56,78))
                                        .flatMap(number->number.stream())
                                        .collect(Collectors.toList());
        together.forEach(k->{
            System.out.println(k);
        });

        /**********************************************************
         * max min operation
         */
        List<Integer> toger = Stream.of(asList(124, 125), asList(42, 545))
                                    .flatMap(number -> number.stream())
//                                    .min(Comparator.comparing(k->k.intValue()))
                                    .collect(Collectors.toList());
        Integer minValue = toger.stream().min(Comparator.comparingInt(value -> value)).get();
        System.out.println("================minValue=============="+minValue);

        /**********************************************************
         * max min operation
         */

    }
}
