package com.ufbra.sistemaescolar;

import com.ufbra.sistemaescolar.config.AsyncSyncConfiguration;
import com.ufbra.sistemaescolar.config.EmbeddedSQL;
import com.ufbra.sistemaescolar.config.JacksonConfiguration;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = { SistemaEscolarApp.class, JacksonConfiguration.class, AsyncSyncConfiguration.class })
@EmbeddedSQL
public @interface IntegrationTest {
}
