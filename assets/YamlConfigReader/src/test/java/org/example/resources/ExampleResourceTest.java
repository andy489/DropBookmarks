package org.example.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.configuration.YamlConfigurationFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jersey.validation.Validators;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.validation.Validator;
import jakarta.ws.rs.core.Response;
import org.example.YamlConfigReaderConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(DropwizardExtensionsSupport.class)
public class ExampleResourceTest {

    private static YamlConfigReaderConfiguration configuration;
    private static ObjectMapper objectMapper;

    private final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new ExampleResource(configuration))
            .build();

    @BeforeAll
    static void setUp() throws ConfigurationException, IOException {
        objectMapper = Jackson.newObjectMapper();
        final Validator validator = Validators.newValidator();
        final YamlConfigurationFactory<YamlConfigReaderConfiguration> factory = new YamlConfigurationFactory<>(
                YamlConfigReaderConfiguration.class,
                validator,
                objectMapper,
                "dw"
        );

        final File yaml = new File(
                Objects.requireNonNull(
                        Objects.requireNonNull(
                                Thread.currentThread()
                                        .getContextClassLoader()
                                        .getResource("config.yml")
                        ).getPath()
                )
        );

        configuration = factory.build(yaml);
    }

    @Test
    void testGetMyArg() {
        final String expected = configuration.getMyMetrics().getMyArg();

        Response response = EXT.target("/yml/my-arg").request().get();

        String actual = response.readEntity(String.class);

        assertEquals(
                expected,
                actual,
                String.format("Content of response expected to be: %s", expected)
        );
    }

    @Test
    void testGetMyList() throws JsonProcessingException {
        final List<String> expected = configuration.getMyMetrics().getMyList();

        Response response = EXT.target("/yml/my-list").request().get();

        TypeReference<List<String>> typeReference = new TypeReference<>() {
        };

        List<String> entity = objectMapper.readValue(response.readEntity(String.class), typeReference);

        assertEquals(
                expected,
                entity,
                String.format("Content of response expected to be: %s", expected)
        );
    }

    @Test
    void testGetMyListOfLists() throws JsonProcessingException {
        final List<List<String>> expected = configuration.getMyMetrics().getMyListOfLists();

        Response response = EXT.target("/yml/my-list-of-lists").request().get();

        TypeReference<List<List<String>>> typeReference = new TypeReference<>() {
        };

        List<List<String>> entity = objectMapper.readValue(response.readEntity(String.class), typeReference);

        assertEquals(
                expected,
                entity,
                String.format("Content of response expected to be: %s", expected)
        );
    }

    @Test
    void testGetMyMap() throws JsonProcessingException {
        final Map<String, String> expected = configuration.getMyMetrics().getMyMap();

        Response response = EXT.target("/yml/my-map").request().get();

        TypeReference<Map<String, String>> typeReference = new TypeReference<>() {
        };

        Map<String, String> entity = objectMapper.readValue(response.readEntity(String.class), typeReference);

        assertEquals(
                expected,
                entity,
                String.format("Content of response expected to be: %s", expected)
        );
    }

    @Test
    void testGetMyMKapOfLists() throws JsonProcessingException {
        final Map<String, List<Integer>> expected = configuration.getMyMetrics().getMyMapOfLists();

        Response response = EXT.target("/yml/my-map-of-lists").request().get();

        TypeReference<Map<String, List<Integer>>> typeReference = new TypeReference<>() {
        };

        Map<String, List<Integer>> entity = objectMapper.readValue(response.readEntity(String.class), typeReference);

        assertEquals(
                expected,
                entity,
                String.format("Content of response expected to be: %s", expected)
        );
    }
}
