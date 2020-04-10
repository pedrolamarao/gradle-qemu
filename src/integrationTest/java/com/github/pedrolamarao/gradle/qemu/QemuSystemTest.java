package com.github.pedrolamarao.gradle.qemu;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

/**
 * Assumes qemu-system-i386 and qemu-system-x86_64 are available in the execution path.
 */

public class QemuSystemTest
{
    @Test
    void smoke__386 () throws Exception
    {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply(QemuPlugin.class);
        QemuExtension qemu = project.getExtensions().findByType(QemuExtension.class);
        Process process = qemu.runSystem(spec -> 
        {
            spec.getArchitecture().set("i386");
        });
        assertNotNull(process);
        process.destroy();
    }

    @Test
    void smoke__x86_64 () throws Exception
    {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply(QemuPlugin.class);
        QemuExtension qemu = project.getExtensions().findByType(QemuExtension.class);
        Process process = qemu.runSystem(spec -> 
        {
            spec.getArchitecture().set("x86_64");
        });
        assertNotNull(process);
        process.destroy();
    }
}
