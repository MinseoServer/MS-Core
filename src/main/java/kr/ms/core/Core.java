package kr.ms.core;

import kr.ms.core.repo.impl.$MSArgumentRepositoryImpl;
import kr.ms.core.container.ContainerListener;
import kr.ms.core.repo.MSArgumentRepository;
import kr.ms.core.util.AsyncExecutor;
import kr.ms.core.util.ItemStackNameUtil;
import kr.ms.core.util.PlayerSkullManager;
import kr.ms.core.version.VersionController;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    private static $MSArgumentRepositoryImpl argumentRepository;
    private static Core instance;

    @Override
    @SuppressWarnings("all")
    public void onEnable() {
        instance = this;
        getLogger().info("asdasd");
        argumentRepository = new $MSArgumentRepositoryImpl();
        //new Metrics(this, 17172);

        VersionController.$initializing(this);
        PlayerSkullManager.$initializing(VersionController.getInstance().getVersion(), getServer());
        ItemStackNameUtil.$initializingLocale(this);

        getServer().getPluginManager().registerEvents(new ContainerListener(), this);
    }

    @Override
    public void onDisable() {
        AsyncExecutor.shutdown();
    }

    public static MSArgumentRepository getArgumentRepository() { return argumentRepository; }
    public static Core getInstance() { return instance; }

}