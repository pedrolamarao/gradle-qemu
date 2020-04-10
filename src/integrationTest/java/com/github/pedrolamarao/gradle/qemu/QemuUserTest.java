package com.github.pedrolamarao.gradle.qemu;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

/**
 * Assumes qemu-i386 and qemu-x86_64 are available in the execution path.
 */

public class QemuUserTest
{
    @Test
    void smoke__386 () throws Exception
    {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply(QemuPlugin.class);
        QemuExtension qemu = project.getExtensions().findByType(QemuExtension.class);
        Process process = qemu.runUser(spec -> 
        {
            spec.getArchitecture().set("i386");
            spec.getProgram().set(new File("java.exe"));
        });
        assertNotNull(process);
        boolean complete = process.waitFor(1000, TimeUnit.MILLISECONDS);
        if (! complete) {
            process.destroy();
            fail("waitFor timed out");
        }
    }

    @Test
    void smoke__x86_64 () throws Exception
    {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply(QemuPlugin.class);
        QemuExtension qemu = project.getExtensions().findByType(QemuExtension.class);
        Process process = qemu.runUser(spec -> 
        {
            spec.getArchitecture().set("x86_64");
            spec.getProgram().set(new File("java.exe"));
        });
        assertNotNull(process);
        boolean complete = process.waitFor(1000, TimeUnit.MILLISECONDS);
        if (! complete) {
            process.destroy();
            fail("waitFor timed out");
        }
    }
}
