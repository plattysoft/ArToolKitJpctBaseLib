package org.artoolkit.ar.base.markers;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/*
 * Unit tests for multi marker configuration string convenience class.
 *
 * @author github.com/michaelboyles
 */
public class MultiMarkerConfigTest
{
    @Test
    public void basicTest()
    {
        final MarkerConfig conf = new MultiMarkerConfig("test/test");
        assertEquals(conf.get(), "multi;test/test");
    }
}
