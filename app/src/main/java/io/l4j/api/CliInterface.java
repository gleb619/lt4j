package io.l4j.api;

import io.l4j.configurator.BaseConfigurator;
import io.l4j.configurator.ConfiguratorModule;
import io.l4j.core.*;
import io.l4j.settings.BaseReader;
import lombok.extern.slf4j.Slf4j;
import picocli.CommandLine;

@Slf4j
@CommandLine.Command(name = "lt4j")
public class CliInterface implements ApiInterface {

    @CommandLine.Option(names = {"-c", "--config"}, description = "Path to configuration file")
    private String configFileLocation;

    @CommandLine.Option(names = {"-h", "--help"}, description = "Print information about program")
    private boolean showHelp;

    public static void main(String[] args) {
        CommandLine.run(new CliInterface(), System.err, args);
    }

    @Override
    public void run() {
        if (showHelp) {
            CommandLine.usage(this, System.out);
            System.exit(0);
        }

        log.info("Create context");
        try (Context context = createContext(configFileLocation)) {
            Reader reader = new BaseReader();

            context.addComponents(reader);
            log.info("Starting");
            context.fire(Events.CREATE);

            //TODO: Make your business logic here

            log.info("Stopping");
            context.fire(Events.STOP);
        } catch (Exception e) {
            log.error("Detect exception: ", e);
        } catch (Throwable e) {
            log.error("Found error: ", e);
        } finally {
            log.info("Job was done");
        }
    }

    private Context createContext(String configFileLocation) {
        return Context.newOne()
                .withModule(new ConfiguratorModule())
                .withState(State.builder()
                        .configFileLocation(configFileLocation)
                        .build())
                .withConfigurator(new BaseConfigurator());
    }

}
