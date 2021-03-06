/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2015, 2016 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 *
 * Contributor(s):
 */
// Portions Copyright [2017] [Payara Foundation and/or its affiliates]

package org.netbeans.modules.payara.tooling.admin;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.netbeans.modules.payara.tooling.PayaraIdeException;
import org.netbeans.modules.payara.tooling.data.PayaraServer;

/**
 * Command that retrieves list of components defined on server.
 * <p/>
 * @author Tomas Kraus, Peter Benedikovic
 */
@RunnerHttpClass(runner=RunnerHttpListComponents.class)
@RunnerRestClass(runner=RunnerRestListApplications.class, command="list-applications")
public class CommandListComponents extends CommandTarget {

    ////////////////////////////////////////////////////////////////////////////
    // Class attributes                                                       //
    ////////////////////////////////////////////////////////////////////////////

    /** Command string for list components command. */
    private static final String COMMAND = "list-components";

    /** Error message for administration command execution exception .*/
    private static final String ERROR_MESSAGE = "List components failed.";

    ////////////////////////////////////////////////////////////////////////////
    // Static methods                                                         //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * List components.
     * <p/>
     * @param server Payara server entity.
     * @param target Target server instance or cluster.
     * @return List components task response.
     * @throws PayaraIdeException When error occurred during administration
     *         command execution.
     */
    public static ResultMap<String, List<String>> listComponents(
            final PayaraServer server, final String target)
            throws PayaraIdeException {
        Command command = new CommandListComponents(target);
        Future<ResultMap<String, List<String>>> future = ServerAdmin
                .<ResultMap<String, List<String>>>exec(server, command);
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException
                | CancellationException ie) {
            throw new PayaraIdeException(ERROR_MESSAGE, ie);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // Constructors                                                           //
    ////////////////////////////////////////////////////////////////////////////

    /**
     * Constructs an instance of Payara server list components
     * command entity.
     * <p/>
     * @param target  Target Payara instance or cluster.
     */
    public CommandListComponents(final String target) {
        super(COMMAND, target);
    }

}
