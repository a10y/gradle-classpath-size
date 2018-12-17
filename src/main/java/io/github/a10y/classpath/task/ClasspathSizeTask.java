package io.github.a10y.classpath.task;

import java.io.File;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskAction;

public final class ClasspathSizeTask extends DefaultTask {
    public static final String TASK_NAME = "classpathSize";

    private static final String CONFIGURATION = "runtimeClasspath";

    private final Project project;

    public ClasspathSizeTask(Project project) {
        this.project = project;
    }

    @TaskAction
    public void showClasspathSize() {
        Set<File> configurationFiles = project.getConfigurations().getByName(CONFIGURATION).resolve();
        AtomicLong totalSize = new AtomicLong();
        configurationFiles.forEach(jar -> {
            totalSize.addAndGet(jar.length());
        });
        long byteSize = totalSize.get();
        long megabyteSize = byteSize >>> 20;
        long gigabyteSize = byteSize >>> 30;
        System.err.println(CONFIGURATION + " resolved sizes:");
        System.err.println("bytes\tmegabytes\tgigabytes");
        System.out.println(byteSize + "\t" + megabyteSize + "\t" + gigabyteSize);
    }
}
