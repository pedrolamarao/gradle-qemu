package com.github.pedrolamarao.gradle.qemu;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

public class PluginTest
{
    @Test
    void apply__type ()
    {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply(QemuPlugin.class);
        Object extension = project.getExtensions().findByName("qemu");
        assertNotNull(extension);
        assertTrue(extension instanceof QemuExtension);
    }

    @Test
    void apply__name ()
    {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply("pl.gradle-qemu");
        Object extension = project.getExtensions().findByName("qemu");
        assertNotNull(extension);
        assertTrue(extension instanceof QemuExtension);
    }
}
