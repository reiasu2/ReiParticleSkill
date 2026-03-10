// SPDX-License-Identifier: LGPL-3.0-only
// Copyright (C) 2025 Reiasu
package com.reiasu.reiparticleskill.client;

/**
 * Beam suppression now happens directly in the End Crystal render path via mixin.
 *
 * <p>This legacy class remains only to avoid breaking source references while the
 * frame-level entity scan stays removed.</p>
 */
public final class EndCrystalBeamSuppressor {
    private EndCrystalBeamSuppressor() {
    }
}
