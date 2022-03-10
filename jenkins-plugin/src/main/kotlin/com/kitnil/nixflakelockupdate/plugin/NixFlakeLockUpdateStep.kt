package com.kitnil.nixflakelockupdate.plugin

import hudson.FilePath
import hudson.Launcher
import hudson.model.Descriptor
import hudson.model.Run
import hudson.model.TaskListener
import hudson.tasks.BuildStepMonitor
import hudson.tasks.BuildStepMonitor.NONE
import hudson.tasks.Builder
import jenkins.tasks.SimpleBuildStep
import org.kohsuke.stapler.DataBoundConstructor
import org.kohsuke.stapler.DataBoundSetter

class NixFlakeLockUpdateStep @DataBoundConstructor constructor() : Builder(), SimpleBuildStep {
    @set:DataBoundSetter
    var inputs: List<String>? = null

    override fun getDescriptor(): Descriptor<Builder> = DESCRIPTOR

    override fun getRequiredMonitorService(): BuildStepMonitor = NONE

    override fun perform(run: Run<*, *>, workspace: FilePath, launcher: Launcher, listener: TaskListener) {
        inputs.orEmpty().forEach {
            launcher.launch()
                .pwd(workspace)
                .cmds("nix", "flake", "lock", "--update-input", it)
                .stdout(listener)
                .join()
        }
    }

    companion object {
        @JvmStatic
        val DESCRIPTOR = NixFlakeLockUpdateDescriptor()
    }
}
