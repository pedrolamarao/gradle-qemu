package com.github.pedrolamarao.gradle.qemu;

import static java.util.Arrays.asList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.gradle.api.Action;
import org.gradle.api.file.RegularFile;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.provider.Provider;

public final class Qemu 
{
	private static final Logger logger = Logging.getLogger(Qemu.class);
	
	public static Process run (QemuSystemSpec spec) throws IOException
	{
		final ArrayList<String> command = new ArrayList<>();
		if (spec.getTool().isPresent())
			command.add(spec.getTool().get());
		else
			command.add(spec.getArchitecture().map(it -> "qemu-system-" + it).get());
		ifPresent(spec.getBios(), it -> addAll(command, "-bios", it.getAsFile().toString()));
		ifPresent(spec.getCdrom(), it -> addAll(command, "-cdrom", it.getAsFile().toString()));
		ifPresent(spec.getProcessor(), it -> addAll(command, "-cpu", it));
		ifPresent(spec.getGdb(), it -> addAll(command, "-gdb", it));
		ifPresent(spec.getDisplay(), it -> addAll(command, "-display", it));
		ifPresent(spec.getKernel(), it -> addAll(command, "-kernel", it.getAsFile().toString()));
		ifPresent(spec.getMachine(), it -> addAll(command, "-machine", it));
		addAll(command, "-net", "none");
		ifPresent(spec.getRtc(), it -> addAll(command, "-rtc", "base=" + it));
		if (! spec.getStart().get()) { command.add("-S"); }
		
		logger.info("QEMU: command: {}", String.join(" ", command));
		
		final Provider<RegularFile> err = spec.getTemporaryDir().file("err.txt");
		final Provider<RegularFile> out = spec.getTemporaryDir().file("out.txt");
		
		final ProcessBuilder builder = new ProcessBuilder(command);
		final Map<String, String> environment = builder.environment();
		spec.getEnvironment().get().forEach((key, value) -> environment.put(key, value));
		builder.redirectError(err.get().getAsFile());
		builder.redirectOutput(out.get().getAsFile());
		
		return builder.start();
	}
	
	public static Process run (QemuUserSpec spec) throws IOException
    {
        final ArrayList<String> command = new ArrayList<>();
        command.add(spec.getArchitecture().map(it -> "qemu-" + it).get());
        command.add(spec.getProgram().get().getAsFile().toString());
        
        logger.info("QEMU: command: {}", String.join(" ", command));
        
        final Provider<RegularFile> err = spec.getTemporaryDir().file("err.txt");
        final Provider<RegularFile> out = spec.getTemporaryDir().file("out.txt");
        
        final ProcessBuilder builder = new ProcessBuilder(command);
        final Map<String, String> environment = builder.environment();
        spec.getEnvironment().get().forEach((key, value) -> environment.put(key, value));
        builder.redirectError(err.get().getAsFile());
        builder.redirectOutput(out.get().getAsFile());
        
        return builder.start();
    }
	
	public static void addAll (List<String> list, String... values)
	{
		list.addAll(asList(values));
	}
	
	public static <T> void ifPresent (Provider<T> provider, Action<? super T> action)
	{
		if (provider.isPresent()) action.execute(provider.get());
	}
}
