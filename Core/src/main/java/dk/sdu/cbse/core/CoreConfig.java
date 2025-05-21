package dk.sdu.cbse.core;
import dk.sdu.cbse.commonscore.ScoreSPI;
import dk.sdu.cbse.services.IEntityPostProcessing;
import dk.sdu.cbse.services.IEntityProcessing;
import dk.sdu.cbse.services.IPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

@Configuration
public class CoreConfig {

    @Bean
    public Game game() {
        return new Game(plugins(), entityProcessings(), entityPostProcessings(), scoreService());
    }

    @Bean
    public List<IPlugin> plugins() {
        return ServiceLoader.load(IPlugin.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

    @Bean
    public List<IEntityProcessing> entityProcessings() {
        return ServiceLoader.load(IEntityProcessing.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

    @Bean
    public List<IEntityPostProcessing> entityPostProcessings() {
        return ServiceLoader.load(IEntityPostProcessing.class).stream().map(ServiceLoader.Provider::get).collect(Collectors.toList());
    }

    @Bean
    public ScoreSPI scoreService() {
        return ServiceLoader.load(ScoreSPI.class).stream().map(ServiceLoader.Provider::get).findFirst().orElse(null);
    }
}
