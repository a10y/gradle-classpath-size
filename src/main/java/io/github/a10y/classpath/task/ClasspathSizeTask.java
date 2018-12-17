package io.github.a10y.classpath.task;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ResolvedConfiguration;
import org.gradle.api.artifacts.ResolvedDependency;
import org.gradle.api.tasks.TaskAction;

public class ClasspathSizeTask extends DefaultTask {
    public static final String TASK_NAME = "classpathSize";
    private static final String CONFIGURATION = "default";

    @TaskAction
    public void showClasspathSize() {
        Configuration config = getProject().getConfigurations().getByName(CONFIGURATION);
        ResolvedConfiguration resolvedConf = config.getResolvedConfiguration();

        Map<String, Long> transitiveSizeInBytes = resolvedConf.getFirstLevelModuleDependencies().stream()
                .collect(Collectors.toMap(this::getDependencyName, this::getTransitiveSizeBytes));

        transitiveSizeInBytes.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry<String, Long>::getValue).reversed())
                .forEach(ent -> {
                    String moduleName = ent.getKey();
                    String displaySize = getDisplaySize(ent.getValue());
                    System.out.println(String.format("\\___ %s: %s", moduleName, displaySize));
                });
    }

    private String getDependencyName(ResolvedDependency resolvedDep) {
        return resolvedDep.getModuleGroup() + ":" + resolvedDep.getModuleName() + ":" + resolvedDep.getModuleVersion();
    }

    private long getTransitiveSizeBytes(ResolvedDependency resolvedDep) {
        long transitiveBytes = resolvedDep.getAllModuleArtifacts().stream()
                .mapToLong(resolvedArtifact -> resolvedArtifact.getFile().length())
                .sum();
        return transitiveBytes;
    }

    private String getDisplaySize(long bytes) {
        if (bytes < 1_000L) {
            return String.format("%d B", bytes);
        } else if (bytes < 1_000_000L) {
            return String.format("%d KB", bytes / 1_000);
        } else if (bytes < 1_000_000_000L){
            return String.format("%d MB", bytes / 1_000_000);
        } else {
            return String.format("%d GB", bytes / 1_000_000_000);
        }
    }
}
