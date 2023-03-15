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
  @Test
  @Disabled
  void testTest() {
    fail("Not yet implemented");
  }
}
