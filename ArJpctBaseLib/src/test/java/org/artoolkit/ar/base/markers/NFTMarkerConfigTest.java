package org.artoolkit.ar.base.markers;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/*
 * Unit tests for NFT marker configuration string convenience class.
 *
 * @author github.com/michaelboyles
 */
public class NFTMarkerConfigTest
{
    @Test
    public void basicTest()
    {
        final MarkerConfig conf = new NFTMarkerConfig("test/test", 80);
        assertEquals(conf.get(), "nft;test/test;80");
    }
}
