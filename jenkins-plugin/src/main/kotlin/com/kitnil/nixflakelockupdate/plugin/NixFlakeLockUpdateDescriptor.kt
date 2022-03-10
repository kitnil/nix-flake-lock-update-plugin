package com.kitnil.nixflakelockupdate.plugin

import hudson.Extension
import hudson.model.AbstractProject
import hudson.tasks.BuildStepDescriptor
import hudson.tasks.Builder
import net.sf.json.JSONObject
import org.jenkinsci.Symbol
import org.kohsuke.stapler.StaplerRequest

@Extension
@Symbol("gitAutomerger")
class NixFlakeLockUpdateDescriptor : BuildStepDescriptor<Builder>(NixFlakeLockUpdateStep::class.java) {
    override fun isApplicable(jobType: Class<out AbstractProject<*, *>>): Boolean {
        return true
    }

    override fun newInstance(req: StaplerRequest?, formData: JSONObject): Builder {
        if (req == null) throw AssertionError("req should be always not-null")

        return req.bindJSON(NixFlakeLockUpdateStep::class.java, formData)
    }

    override fun getDisplayName(): String {
        return "Update flake.lock"
    }

    override fun configure(req: StaplerRequest, json: JSONObject): Boolean {
        save()
        return true
    }
}
