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

package org.drools.workbench.services.verifier.api.client.checks.util;

import org.drools.workbench.services.verifier.api.client.cache.util.HasUUID;
import org.drools.workbench.services.verifier.api.client.cache.util.maps.InspectorList;
import org.drools.workbench.services.verifier.api.client.index.keys.UUIDKey;
import org.uberfire.commons.validation.PortablePreconditions;

public class Conflict
        extends Relation<Conflict> {

    public static Conflict EMPTY = new Conflict();

    private final HasUUID conflictedItem;
    private final HasUUID conflictingItem;

    public Conflict( final HasUUID conflictedItem,
                     final HasUUID conflictingItem ) {
        super( null );
        this.conflictedItem = PortablePreconditions.checkNotNull( "conflictedItem", conflictedItem );
        this.conflictingItem = PortablePreconditions.checkNotNull( "conflictingItem", conflictingItem );
    }

    public Conflict( final InspectorList conflictedItem,
                     final InspectorList conflictingItem,
                     final Conflict origin ) {
        super( PortablePreconditions.checkNotNull( "origin", origin ) );
        this.conflictedItem = PortablePreconditions.checkNotNull( "conflictedItem", conflictedItem );
        this.conflictingItem = PortablePreconditions.checkNotNull( "conflictingItem", conflictingItem );
    }

    private Conflict() {
        super( null );
        this.conflictedItem = null;
        this.conflictingItem = null;
    }

    @Override
    public boolean foundIssue() {
        return conflictingItem != null;
    }

    public HasUUID getConflictedItem() {
        return conflictedItem;
    }

    public HasUUID getConflictingItem() {
        return conflictingItem;
    }

    @Override
    public UUIDKey otherUUID() {
        return conflictingItem.getUuidKey() ;
    }

    @Override
    public boolean doesRelationStillExist() {
        if ( origin != null
                && stillContainsConflictedItem( getOrigin().getConflictedItem() )
                && stillContainsConflictingItem( getOrigin().getConflictingItem() ) ) {

            return ConflictResolver.isConflicting( getOrigin().getConflictedItem(),
                                                   getOrigin().getConflictingItem() ).foundIssue();
        } else {
            return false;
        }
    }

    private boolean stillContainsConflictedItem( final HasUUID item ) {
        if ( this.conflictedItem instanceof InspectorList ) {
            return (( InspectorList ) this.conflictedItem).contains( item );
        } else {
            if ( parent != null ) {
                return parent.stillContainsConflictedItem( item );
            } else {
                return false;
            }
        }
    }

    private boolean stillContainsConflictingItem( final HasUUID item ) {
        if ( this.conflictingItem instanceof InspectorList ) {
            return (( InspectorList ) this.conflictingItem).contains( item );
        } else {
            if ( parent != null ) {
                return parent.stillContainsConflictingItem( item );
            } else {
                return false;
            }
        }
    }
}
