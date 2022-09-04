package com.example.twitter.utils;

import com.example.twitter.controller.dto.users.UserGetDto;
import com.example.twitter.model.User;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.context.annotation.Bean;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectMapperUtils {
    private static final ModelMapper modelMapper;

//    @Bean
//    @ConfigurationPropertiesBinding
//    public Converter<User, Long> userToUserId() {
//        return new Converter<User, Long>() {
//            @Override
//            public Long convert(MappingContext<User, Long> context) {
//                return context.getSource().getId();
//            }
//        };
//    }
//
    static {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);

//        Converter<User, Long> userToUserId = new Converter<>() {
//            @Override
//            public Long convert(MappingContext<User, Long> context) {
//                return context.getSource().getId();
//            }
//        };
//
//        modelMapper.addConverter(userToUserId);

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
