package com.example.twitter.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectMapperUtils {
    private static final ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    private ObjectMapperUtils() {
    }


    public static <D, T> D map(final T inClass, Class<D> outClass) {
        return modelMapper.map(inClass, outClass);
    }

    public static <D, T> List<D> mapAll(final Collection<T> inList, Class<D> outClass) {
        return inList.stream().map(entry -> map(entry, outClass)).collect(Collectors.toList());
    }

    public static <S, D> D map(final S source, D destination) {
        modelMapper.map(source, destination);
        return destination;
    }
}
