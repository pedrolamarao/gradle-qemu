package com.github.pedrolamarao.gradle.qemu;

import java.io.IOException;
import java.nio.file.Files;

import javax.inject.Inject;

import org.gradle.api.Action;
import org.gradle.api.file.ProjectLayout;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;

public abstract class QemuExtension implements ExtensionAware
{
    // Properties
    
	@Inject
	public abstract ProjectLayout getLayout ();
	
	@Inject
	public abstract ObjectFactory getObjects ();

	@Inject
	public abstract TaskContainer getTasks ();
	
	// Services

    public TaskProvider<QemuSystemExec> registerSystem (String name, Action<? super QemuSystemExec> configure)
    {
        return getTasks().register(name, QemuSystemExec.class, configure);
    }
    
    public TaskProvider<QemuUserExec> registerUser (String name, Action<? super QemuUserExec> configure)
    {
        return getTasks().register(name, QemuUserExec.class, configure);
    }
    
    public Process runSystem (Action<? super QemuSystemSpec> configure) throws IOException
    {
        final QemuSystemSpec spec = getObjects().newInstance(QemuSystemSpec.class);
        spec.getTemporaryDir().set(getLayout().getBuildDirectory().dir("tmp/qemu/" + spec.hashCode()));
        Files.createDirectories(spec.getTemporaryDir().get().getAsFile().toPath());
        configure.execute(spec);    
        return Qemu.run(spec);
    }
    
    public Process runUser (Action<? super QemuUserSpec> configure) throws IOException
    {
        final QemuUserSpec spec = getObjects().newInstance(QemuUserSpec.class);
        spec.getTemporaryDir().set(getLayout().getBuildDirectory().dir("tmp/qemu/" + spec.hashCode()));
        Files.createDirectories(spec.getTemporaryDir().get().getAsFile().toPath());
        configure.execute(spec);    
        return Qemu.run(spec);
    }

	public Process run (QemuSystemSpec spec) throws IOException
	{
        return Qemu.run(spec);
	}
    
    public Process run (QemuUserSpec spec) throws IOException
    {
        return Qemu.run(spec);
    }
	
	public QemuSystemSpec system (Action<? super QemuSystemSpec> configure)
	{
	    final QemuSystemSpec spec = getObjects().newInstance(QemuSystemSpec.class);
	    configure.execute(spec);
	    return spec;
	}
    
    public QemuUserSpec user (Action<? super QemuUserSpec> configure)
    {
        final QemuUserSpec spec = getObjects().newInstance(QemuUserSpec.class);
        configure.execute(spec);
        return spec;
    }
}
