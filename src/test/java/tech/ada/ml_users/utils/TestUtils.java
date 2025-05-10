package tech.ada.ml_users.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import tech.ada.ml_users.dto.EnderecoDTO;

public class TestUtils {

    private TestUtils() {}

    public static String asJsonString(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static EnderecoDTO umEndereco() {
        return new EnderecoDTO(
                "7190120",
                "Aguas Claras",
                "Norte",
                "Brasilia"
        );
    }
}
