package com.github.pedrolamarao.gradle.qemu;

import java.io.IOException;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;

public abstract class QemuSystemExec extends DefaultTask
{
    private static final Logger logger = Logging.getLogger(QemuSystemExec.class);
    
	private final QemuSystemSpec spec;
	
	// life
	
	public QemuSystemExec ()
	{
		this.spec = getProject().getObjects().newInstance(QemuSystemSpec.class);
		this.spec.getTemporaryDir().set(getTemporaryDir());
	}
	
	// properties
	
	@Input
	public Property<String> getArchitecture () { return spec.getArchitecture(); }
	
	@Optional
	@InputFile
	public RegularFileProperty getBios () { return spec.getBios(); }
	
	@Optional
	@InputFile
	public RegularFileProperty getCdrom () { return spec.getCdrom(); }

	@Input
	public MapProperty<String, String> getEnvironment () { return spec.getEnvironment(); }
	
	@Optional
	@Input
	public Property<String> getGdb () { return spec.getGdb(); }
	
	@Optional
	@Input
	public Property<String> getDisplay () { return spec.getDisplay(); }
	
	@Optional
	@InputFile
	public RegularFileProperty getKernel () { return spec.getKernel(); }
	
	@Optional
	@Input
	public Property<String> getMachine () { return spec.getMachine(); }
	
	@Optional
	@Input
	public Property<String> getProcessor () { return spec.getProcessor(); }
	
	@Input
	@Optional
	public Property<String> getRtc () { return spec.getRtc(); }
	
	@Input
	public Property<Boolean> getStart () { return spec.getStart(); }
	
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
