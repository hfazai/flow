/*
 * Copyright 2000-2018 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.flow.server.frontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.server.Command;

import static com.vaadin.flow.server.frontend.NodeUpdater.log;

/**
 * Run <code>npm install</code> after dependencies have been updated.
 */
public class NodeNpmInstall implements Command {

    private final NodeUpdatePackages packageUpdater;

    /**
     * Create an instance of the command.
     *
     * @param packageUpdater
     *            package-updater instance used for checking if previous
     *            execution modified the package.json file
     */
    public NodeNpmInstall(NodeUpdatePackages packageUpdater) {
        this.packageUpdater = packageUpdater;
    }

    @Override
    public void execute() {
        if (packageUpdater.modified) {
            if (log().isInfoEnabled()) {
                log().info("Running `npm install` ...");
            }
            runNpmInstall();
        } else {
            if (log().isInfoEnabled()) {
                log().info("Skipping `npm install`.");
            }
        }
    }

    /**
     * Executes `npm install` after `package.json` has been updated.
     */
    private void runNpmInstall() {
        List<String> command = new ArrayList<>();
        command.addAll(FrontendUtils.getNpmExecutable());
        command.add("install");

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.directory(packageUpdater.npmFolder);

        // For a locally installed Node on windows we need to add the node folder
        // to the path for it to work as expected with NPM
        if (FrontendUtils.isWindows() && command.get(0).contains("node.exe")) {
            String nodeExecutable = command.get(0).replace("\\node.exe", "");
            builder.environment().put("PATH",
                    System.getenv().get("PATH") + ";" + nodeExecutable);
        }

        Process process = null;
        try {
            process = builder.inheritIO().start();
            int errorCode = process.waitFor();
            if (errorCode != 0) {
                log().error(
                        ">>> Dependency ERROR. Check that all required dependencies are deployed in npm repositories.");
            } else {
                log().info("package.json updated and npm dependencies installed. ");
            }
        } catch (InterruptedException | IOException e) {
            log().error("Error when running `npm install`", e);
        } finally {
            if (process != null) {
                process.destroyForcibly();
            }
        }
    }

}