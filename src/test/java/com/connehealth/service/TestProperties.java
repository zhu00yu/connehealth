package com.connehealth.service;

import org.glassfish.jersey.test.spi.TestContainerFactory;

/**
 * Jersey test framework configuration properties.
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 */
public final class TestProperties {
    /**
     * If set to {@code true} the property enables basic logging of the request and
     * response flow on both - client and server. Note that traffic logging does not
     * dump message entities by default. Please see {@link #DUMP_ENTITY} documentation
     * for instructions how to enable entity content dumping.
     * <p />
     * The default value is {@code false}.
     * <p />
     * The name of the configuration property is <code>{@value}</code>.
     */
    public static final String LOG_TRAFFIC = "jersey.config.test.logging.enable";
    /**
     * If set to {@code true} the property instructs the test traffic logger to
     * dump message entities as part of the test traffic logging. Message entity
     * dumping is turned off by default for performance reasons. Note that the
     * value of the property will be ignored unless {@link #LOG_TRAFFIC traffic
     * logging} is enabled too.
     * <p />
     * The default value is {@code false}.
     * <p />
     * The name of the configuration property is <code>{@value}</code>.
     */
    public static final String DUMP_ENTITY = "jersey.config.test.logging.dumpEntity";
    /**
     * Specifies the {@link TestContainerFactory test container factory} implementation
     * class to be used to create a test container instance for the test. The value
     * of the property must be a {@code String} identifying a valid, fully qualified
     * name of a test container factory implementation class, otherwise it will
     * be ignored.
     * <p />
     * The default value is <code>{@value #DEFAULT_CONTAINER_FACTORY}</code>.
     * <p />
     * The name of the configuration property is <code>{@value}</code>.
     *
     * @see #CONTAINER_PORT
     */
    public static final String CONTAINER_FACTORY = "jersey.config.test.container.factory";
    /**
     * Specifies the default {@link TestContainerFactory test container factory}
     * implementation class to be used to create a test container instance for the
     * test.
     *
     * @see #CONTAINER_FACTORY
     */
    public static final String DEFAULT_CONTAINER_FACTORY =
            "org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory";

    /**
     * Specifies the network connection port to be used by an active test container
     * for test application deployment. The value of the property must be a valid
     * positive integer, otherwise it will be ignored.
     * <p />
     * The default value is <code>{@value #DEFAULT_CONTAINER_PORT}</code>.
     * <p />
     * The name of the configuration property is <code>{@value}</code>.
     *
     * @see #CONTAINER_FACTORY
     */
    public static final String CONTAINER_PORT = "jersey.config.test.container.port";
    /**
     * Specifies the default network connection port to be used by an active test
     * container for test application deployment.
     *
     * @see #CONTAINER_PORT
     */
    public static final int DEFAULT_CONTAINER_PORT = 9998;

    /**
     * If set to a numeric value then this property enables to store log records at {@link java.util.logging.Level log level}
     * value (or higher) defined by the value of this property.
     * Log records can be retrieved in tests using {@link org.glassfish.jersey.test.JerseyTest#getLoggedRecords()}.
     * <p />
     * The name of the configuration property is <code>{@value}</code>.
     */
    public static final String RECORD_LOG_LEVEL = "jersey.config.test.logging.record.level";

    private TestProperties() {
        // prevents instantiation
    }
}
