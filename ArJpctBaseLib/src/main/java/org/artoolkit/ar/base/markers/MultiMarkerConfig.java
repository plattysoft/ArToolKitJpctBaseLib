package org.artoolkit.ar.base.markers;

/**
 * A multi marker configuration string.
 */
public final class MultiMarkerConfig implements MarkerConfig
{
    private final static String PATTERN = "multi;%s";

    private final String pathToMultiConfigFile;

    /**
     * @param pathToMultiConfigFile The path to the multi-marker configuration file.
     */
    public MultiMarkerConfig(final String pathToMultiConfigFile)
    {
        this.pathToMultiConfigFile = pathToMultiConfigFile;
    }

    @Override
    public String get()
    {
        return String.format(PATTERN, pathToMultiConfigFile);
    }
}

