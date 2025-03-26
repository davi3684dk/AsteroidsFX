package dk.sdu.cbse.data;

import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class PluginsLayer {
    private static ModuleLayer layer;

    public static ModuleLayer getLayer() {
        if (layer != null)
            return layer;

        ModuleFinder finder = ModuleFinder.of(Paths.get("plugins"));
        ModuleLayer parent = ModuleLayer.boot();

        List<String> plugins = finder.findAll()
                .stream()
                .map(m -> m.descriptor().name())
                .collect(Collectors.toList());

        Configuration cf = parent.configuration()
                .resolve(finder, ModuleFinder.of(), plugins);

        layer = parent.defineModulesWithOneLoader(cf, ClassLoader.getSystemClassLoader());

        return layer;
    }
}
