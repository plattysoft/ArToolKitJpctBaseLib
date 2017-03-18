package org.artoolkit.ar.base.markers;

/**
 * An NFT (natural feature tracker) marker configuration string.
 *
 * @see https://artoolkit.org/documentation/doku.php?id=3_Marker_Training:marker_nft_training
 *
 * @author github.com/michaelboyles
 */
public final class NFTMarkerConfig implements MarkerConfig
{
    private final static String FORMAT = "nft;%s;%d";

    private final String pathToPatternFile;
    private final int    patternWidth;

    /**
     * @param pathToPatternFile The path to the pattern file.
     * @param patternWidth      The physical (real world) width of the pattern in millimeters.
     */
    public NFTMarkerConfig(final String pathToPatternFile, final int patternWidth)
    {
        this.pathToPatternFile = pathToPatternFile;
        this.patternWidth      = patternWidth;
    }

    @Override
    public String get()
    {
        return String.format(FORMAT, pathToPatternFile, patternWidth);
    }
}
