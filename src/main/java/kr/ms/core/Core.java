package kr.ms.core;

import kr.ms.core.repo.impl.$STArgumentRepositoryImpl;
import kr.ms.core.container.ContainerListener;
import kr.ms.core.repo.STArgumentRepository;
import kr.ms.core.util.AsyncExecutor;
import kr.ms.core.util.ItemStackNameUtil;
import kr.ms.core.util.PlayerSkullManager;
import kr.ms.core.version.VersionController;
import org.bukkit.plugin.java.JavaPlugin;

public class Core extends JavaPlugin {

    private static $STArgumentRepositoryImpl argumentRepository;
    private static Core instance;

    @Override
    @SuppressWarnings("all")
    public void onEnable() {
        instance = this;
        argumentRepository = new $STArgumentRepositoryImpl();
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

    public static STArgumentRepository getArgumentRepository() { return argumentRepository; }
    public static Core getInstance() { return instance; }

}