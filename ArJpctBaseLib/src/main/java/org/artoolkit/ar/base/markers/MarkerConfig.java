package org.artoolkit.ar.base.markers;

/**
 * Represents an ARToolkit marker configuration string.
 *
 * @see https://artoolkit.org/documentation/doku.php?id=4_Android:android_developing#loading_markers
 *
 * @author github.com/michaelboyles
 */
public interface MarkerConfig
{
    /**
     * Get the ARToolkit native marker string.
     *
     * @return the ARToolkit native marker string.
     */
    String get();
}