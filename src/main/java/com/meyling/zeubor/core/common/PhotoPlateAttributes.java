package com.meyling.zeubor.core.common;

public final class PhotoPlateAttributes {

    private double sensitivity;
    private double zoom;
    private int snapshot;

    public PhotoPlateAttributes(final double sensitivity,
            final double zoom, final int snapshot) {
        setSensitivity(sensitivity);
        setZoom(zoom);
        setSnapshot(snapshot);
    }

    public PhotoPlateAttributes() {
        setSensitivity(13);
        setZoom(297);
        setSnapshot(0);
    }

    public final double getSensitivity() {
        return sensitivity;
    }

    public final void setSensitivity(double sensitivity) {
        this.sensitivity = sensitivity;
    }

    public final double getZoom() {
        return zoom;
    }

    public final void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public int getSnapshot() {
        return snapshot;
    }

    public final void setSnapshot(int snapshot) {
        this.snapshot = snapshot;
    }

}

