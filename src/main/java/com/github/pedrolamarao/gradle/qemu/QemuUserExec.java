package com.github.pedrolamarao.gradle.qemu;

import java.io.IOException;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.TaskAction;

public class QemuUserExec extends DefaultTask
{
    private static final Logger logger = Logging.getLogger(QemuSystemExec.class);
    
    private final QemuUserSpec spec;
    
    // life
    
    public QemuUserExec ()
    {
        this.spec = getProject().getObjects().newInstance(QemuUserSpec.class);
        this.spec.getTemporaryDir().set(getTemporaryDir());
    }
    
    // properties
    
    @Input
    public Property<String> getArchitecture () { return spec.getArchitecture(); }
    
    @InputFile
    public RegularFileProperty getProgram () { return spec.getProgram(); }
    
    // task
    
    @TaskAction
    public void action () throws IOException, InterruptedException
    {
        final Process process = Qemu.run(spec);
        final int status = process.waitFor();
        logger.info("QEMU: status: {}", status);
        if (status != 0) throw new RuntimeException("QEMU process failed with status " + status);
    }
}
