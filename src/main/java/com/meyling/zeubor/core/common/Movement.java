package com.meyling.zeubor.core.common;

/**
 * Movement wish.
 */
public final class Movement {

    private final int dx;
    private final int dy;
    private final int dz;

    /**
     * 
     * @param dx	Must be -1, 0 or 1.
     * @param dy	Must be -1, 0 or 1.
     * @param dz	Must be -1, 0 or 1.
     */
    public Movement(int dx, int dy, int dz) {
    	this.dx = dx;
    	this.dy = dy;
    	this.dz = dz;
    	if (Math.abs(dx) > 1 || Math.abs(dy) > 1 || Math.abs(dz) > 1) {
    		throw new IllegalArgumentException("dx=" + dx + " dy=" + dy + " dz=" + dz);
    	}
    }

    public final int getDx() {
        return dx;
    }

    public final int getDy() {
        return dy;
    }

    public final int getDz() {
        return dz;
    }

}


