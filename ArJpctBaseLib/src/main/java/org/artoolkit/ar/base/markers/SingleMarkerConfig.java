package org.artoolkit.ar.base.markers;

/**
 * A single marker configuration string.
 *
 * @author github.com/michaelboyles
 */
public final class SingleMarkerConfig implements MarkerConfig
{
    private final static String PATTERN = "single;%s;%d";

    private final String pathToPatternFile;
    private final int    patternWidth;

    /**
     * @param pathToPatternFile The path to the pattern file.
     * @param patternWidth      The physical width of the pattern in millimeters.
     */
    public SingleMarkerConfig(final String pathToPatternFile, final int patternWidth)
    {
        this.pathToPatternFile = pathToPatternFile;
        this.patternWidth      = patternWidth;
    }

    @Override
    public String get()
    {
        return String.format(PATTERN, pathToPatternFile, patternWidth);
    }
}
