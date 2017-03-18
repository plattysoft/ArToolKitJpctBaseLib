package org.artoolkit.ar.base.markers;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/*
 * Unit tests for single marker configuration string convenience class.
 *
 * @author github.com/michaelboyles
 */
public class SingleMarkerConfigTest
{
    @Test
    public void basicTest()
    {
        final MarkerConfig conf = new SingleMarkerConfig("test/test", 80);
        assertEquals(conf.get(), "single;test/test;80");
    }
}
