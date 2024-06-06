package com.foodsafety.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

@Converter
public class BeneficiaryListConverter implements AttributeConverter<List<Beneficiary>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Beneficiary> beneficiaries) {
        try {
            return objectMapper.writeValueAsString(beneficiaries);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting beneficiaries to JSON", e);
        }
    }

    @Override
    public List<Beneficiary> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<Beneficiary>>() {});
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to beneficiaries", e);
        }
    }

    public static List<Beneficiary> mapToBeneficiaries(String beneficiariesJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(beneficiariesJson, new TypeReference<List<Beneficiary>>() {});
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON to beneficiaries", e);
        }
    }
}
