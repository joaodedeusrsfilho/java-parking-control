package com.api.parkingcontrol.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.format.DateTimeFormatter;

@Configuration /*essa classe vai ser uma classe de configuração do Spring
dessa forma colocando o anotation @Configuration o spring vai levar em conta essas
configurações customizadas dessa classe*/
public class DateConfig {
    /*colando a data no padrão ISO 8601 UTC*/
    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";/*COM A LETRA Z mostramos o UTC que no Brasil
    é -3 ou seja a hora é 3 vezes adiantada com relação ao Brasil*/
    public static LocalDateTimeSerializer LOCAL_DATETIME_SERIALIZER = new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIME_FORMAT));

    @Bean//como esta classe DateConfig é uma class externa ou seja está em outro pacote, precisamos do anotation @Bean
    @Primary//por questão de prioridade, caso seja criado outros @beans para o ObjectMapper
    public ObjectMapper objectMapper(){
        /*Aqui estamos passando o formato Serializado da hora para o JavaTimeModule
        * atravez do ObjectMapper, pq sempre q é feita serialização o spring se utilizada do ObjectMapper*/
        JavaTimeModule module = new JavaTimeModule();
        module.addSerializer(LOCAL_DATETIME_SERIALIZER);
        return new ObjectMapper().registerModule(module);
    }


}
