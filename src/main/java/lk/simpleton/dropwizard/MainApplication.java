package lk.simpleton.dropwizard;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import lk.simpleton.dropwizard.health.TemplateHealthCheck;
import lk.simpleton.dropwizard.resources.HelloWorldResource;

/**
 * Hello world!
 */
public class MainApplication extends Application<AppConfig> {

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            new MainApplication().run(new String[]{"server", "config.yml"});
        } else {
            new MainApplication().run(args);
        }
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<AppConfig> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(AppConfig configuration,
                    Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);

        environment.jersey().register(resource);
    }
}
