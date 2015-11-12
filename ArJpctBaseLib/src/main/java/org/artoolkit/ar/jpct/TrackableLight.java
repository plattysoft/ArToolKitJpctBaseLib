package org.artoolkit.ar.jpct;

import com.threed.jpct.Light;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

/**
 * Created by portales on 12/11/15.
 */
public class TrackableLight {

    private Light mLight;
    private SimpleVector mPosition = new SimpleVector();
    private SimpleVector mCurrentLocation = new SimpleVector();
    private int mR, mG, mB;
    private boolean mVisible;

    public TrackableLight() {
    }

    public void setIntensity(int r, int g, int b) {
        mR = r;
        mG = g;
        mB = b;
        if (mLight != null) {
            mLight.setIntensity(mR, mG, mB);
        }
    }

    public void setPosition(SimpleVector newPosition) {
        mPosition.set(newPosition);
        if (mLight != null) {
            mLight.setPosition(mPosition);
        }
    }

    public void setVisibility(boolean visible) {
        mVisible = visible;
        if (mLight != null) {
            if (visible) {
                mLight.enable();
            }
            else {
                mLight.disable();
            }
        }
    }

    public void addToWorld(World world) {
        mLight = new Light(world);
        setPosition(mPosition);
        setIntensity(mR, mG, mB);
        setVisibility(mVisible);
    }

    public void update(SimpleVector translation) {
        if (mLight != null) {
            mCurrentLocation.set(mPosition);
            mCurrentLocation.add(translation);
            mLight.setPosition(mCurrentLocation);
        }
    }
}
