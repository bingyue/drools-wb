/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.drools.workbench.screens.guided.dtable.client.widget.analysis.index.builders;

import org.drools.workbench.models.guided.dtable.shared.model.BaseColumn;
import org.drools.workbench.models.guided.dtable.shared.model.GuidedDecisionTable52;
import org.drools.workbench.services.verifier.api.client.configuration.AnalyzerConfiguration;
import org.drools.workbench.services.verifier.api.client.index.Column;
import org.uberfire.commons.validation.PortablePreconditions;

public class ColumnBuilder {

    private final GuidedDecisionTable52 model;
    private final BaseColumn baseColumn;
    private final AnalyzerConfiguration configuration;

    public ColumnBuilder( final GuidedDecisionTable52 model,
                          final BaseColumn baseColumn,
                          final AnalyzerConfiguration configuration ) {
        this.model = PortablePreconditions.checkNotNull( "model",
                                                         model );
        this.baseColumn = PortablePreconditions.checkNotNull( "baseColumn",
                                                              baseColumn );
        this.configuration = PortablePreconditions.checkNotNull( "configuration",
                                                                 configuration );
    }

    public Column build() {
        return new Column( model.getExpandedColumns()
                                   .indexOf( baseColumn ),
                           configuration );
    }
}