package org.example;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import org.example.resources.ExampleResource;

public class YamlConfigReaderApplication extends Application<YamlConfigReaderConfiguration> {

    public static void main(final String[] args) throws Exception {
        new YamlConfigReaderApplication().run(args);
    }

    @Override
    public String getName() {
        return "YamlConfigReader";
    }

    @Override
    public void initialize(final Bootstrap<YamlConfigReaderConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final YamlConfigReaderConfiguration configuration,
                    final Environment environment) {

        System.out.println(configuration.getMyMetrics().getMyArg());
        System.out.println(configuration.getMyMetrics().getMyList());
        System.out.println(configuration.getMyMetrics().getMyListOfLists());
        System.out.println(configuration.getMyMetrics().getMyMap());
        System.out.println(configuration.getMyMetrics().getMyMapOfLists());

        environment.jersey().register(new ExampleResource(configuration));
    }

}
