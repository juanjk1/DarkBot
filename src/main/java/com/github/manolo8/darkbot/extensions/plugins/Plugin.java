package com.github.manolo8.darkbot.extensions.plugins;

import com.github.manolo8.darkbot.config.ConfigEntity;
import com.github.manolo8.darkbot.config.PluginConfig;
import eu.darkbot.api.extensions.PluginInfo;
import eu.darkbot.api.utils.Version;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.Set;

public class Plugin implements PluginInfo {

    private final File file;
    private final URL jar;

    private PluginDefinition definition;
    private PluginDefinition updateDefinition;
    private PluginConfig info;
    private final IssueHandler issues = new IssueHandler();
    private final IssueHandler updateIssues = new IssueHandler();

    private UpdateStatus updateStatus = UpdateStatus.UNCHECKED;
    public enum UpdateStatus {
        UNCHECKED, UP_TO_DATE, AVAILABLE, INCOMPATIBLE, FAILED, UNKNOWN
    }

    public Plugin(File file, URL jar) {
        this.file = file;
        this.jar = jar;
    }

    public File getFile() {
        return file;
    }

    public URL getJar() {
        return jar;
    }

    public void setDefinition(PluginDefinition definition) {
        this.definition = definition;
        info = ConfigEntity.INSTANCE.getPluginInfo(definition);
    }

    public void setUpdateDefinition(PluginDefinition definition) {
        this.updateDefinition = definition;
    }

    public void setUpdateStatus(UpdateStatus status) {
        updateStatus = status;
    }

    public PluginDefinition getDefinition() {
        return definition;
    }

    public PluginDefinition getUpdateDefinition() {
        return updateDefinition;
    }

    public PluginConfig getInfo() {
        return info;
    }

    public IssueHandler getIssues() {
        return issues;
    }

    @Override
    public @NotNull Set<? extends Issue> getIssueSet() {
        return issues.getIssues();
    }

    @Override
    public void addIssue(@NotNull String message, @NotNull String description, @NotNull Level level) {
        issues.add(message, description, PluginIssue.Level.values()[level.ordinal()]);
    }

    public IssueHandler getUpdateIssues() {
        return updateIssues;
    }

    public UpdateStatus getUpdateStatus() {
        return updateStatus;
    }

    @Override
    public String getBasePackage() {
        return definition.basePackage;
    }

    @Override
    public String getName() {
        return definition != null ? definition.name : new File(jar.getFile()).getName();
    }

    @Override
    public String getAuthor() {
        return definition.author;
    }

    @Override
    public Version getVersion() {
        return definition.version;
    }

    @Override
    public Version getMinimumVersion() {
        return definition.minVersion;
    }

    @Override
    public Version getSupportedVersion() {
        return definition.supportedVersion;
    }

    @Override
    public URL getUpdateURL() {
        return definition.update;
    }

    @Override
    public URL getDonationURL() {
        return definition.donation;
    }

    @Override
    public URL getDownloadURL() {
        return definition.download;
    }

    @Override
    public String[] getFeatureIds() {
        return definition.features;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plugin plugin = (Plugin) o;
        return getName().equals(plugin.getName()) &&
                definition.version.equals(plugin.definition.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), definition.version);
    }

}
