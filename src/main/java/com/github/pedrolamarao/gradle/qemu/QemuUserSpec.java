package com.github.pedrolamarao.gradle.qemu;

import org.gradle.api.Action;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;

public abstract class QemuUserSpec
{
	// life
	
	public QemuUserSpec ()
	{
	}
	
	// properties

	public abstract Property<String> getArchitecture ();

    public abstract MapProperty<String, String> getEnvironment ();

	public abstract RegularFileProperty getProgram ();
	
	public abstract DirectoryProperty getTemporaryDir ();
	
	// utilities
	
	public void configure (Action<? super QemuUserSpec> action)
	{
		action.execute(this);
	}
}
