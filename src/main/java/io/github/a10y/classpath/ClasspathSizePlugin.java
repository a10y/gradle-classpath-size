package io.github.a10y.classpath;

import io.github.a10y.classpath.task.ClasspathSizeTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Finds the full size of the classpath
 */
public final class ClasspathSizePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getPlugins().withId("java", plugin -> {
            project.getTasks().create(ClasspathSizeTask.TASK_NAME, ClasspathSizeTask.class);
        });
    }
}
