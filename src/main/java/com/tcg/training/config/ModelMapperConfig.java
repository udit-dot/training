package com.tcg.training.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        Converter<String, LocalDate> toLocalDate = new Converter<String, LocalDate>() {
            public LocalDate convert(MappingContext<String, LocalDate> context) {
                return context.getSource() == null ? null : LocalDate.parse(context.getSource());
            }
        };
        modelMapper.addConverter(toLocalDate);
        return modelMapper;
    }
}
