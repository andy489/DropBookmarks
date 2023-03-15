package com.pros.bookmarks.resources;

import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.jetty.http.HttpStatus;import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;import static org.junit.jupiter.api.Assertions.fail;

@ExtendWith(DropwizardExtensionsSupport.class)
public class HelloResourceTest {

  private static final ResourceExtension EXT =
      ResourceExtension.builder().addResource(new HelloResource()).build();

  @Test
  @Disabled
  void testTest() {
    fail("Not yet implemented");
  }
  @Test
  void testGreeting() {
    Response response = EXT.target("/").request().get();

    int expectedOk200 = HttpStatus.OK_200;
    String expectedTextPlain = MediaType.TEXT_PLAIN;

    assertEquals(expectedOk200, response.getStatus(), "Expected 200 OK status code");
    assertEquals(expectedTextPlain, response.getMediaType().toString(), "Expected text/plain media type");

    Response responseHome = EXT.target("/home").request().get();

    assertEquals(expectedOk200, responseHome.getStatus(), "Expected 200 OK status code");
    assertEquals(expectedTextPlain, responseHome.getMediaType().toString(), "Expected text/plain media type");

    Response responseIndex = EXT.target("index").request().get();

    assertEquals(expectedOk200, responseIndex.getStatus(), "Expected 200 OK status code");
    assertEquals(expectedTextPlain, responseIndex.getMediaType().toString(), "Expected text/plain media type");
  }


}
