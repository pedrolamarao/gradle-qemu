Date: 2020-04-10

# Summary

`gradle-qemu` teaches Gradle how to run QEMU for a variety of purposes.

# Usage

Currently, this plugin is not published anywhere.

You may publish it from source to Maven local, or, include it in your project with `includeBuild`.

# Scenarios

See the `samples` subdirectory for usage scenarios.

# Design

This project is the result of an ongoing research on the architecture of "bare metal" projects.
The plugin was designed in the easiest way we could think of.

Integration tests are mainly exercised in a `x86_64-linux-gnu` host.

# Roadmap

Currently, the plugin enables the minimal usage scenarios originally required.

Further work we intend to do:

- compiler task for QEMU disk images
- complete model for QEMU system emulator `-drive`
- complete model for QEMU system emulator `-device`
- `QemuProcess` allows interacting with QEMU via QMP

## References

- [Gradle Native](https://docs.gradle.org/current/userguide/building_cpp_projects.html)
- [QEMU](https://wiki.qemu.org/)
