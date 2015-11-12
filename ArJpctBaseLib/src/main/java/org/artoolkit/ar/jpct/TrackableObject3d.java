package org.artoolkit.ar.jpct;

import android.content.Context;

import com.threed.jpct.Loader;
import com.threed.jpct.Matrix;
import com.threed.jpct.Object3D;
import com.threed.jpct.World;

import org.artoolkit.ar.base.ARToolKit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by portales on 12/11/15.
 */
public class TrackableObject3d extends Object3D {

    private final String mMarkerString;
    private int mMarkerId;
    private Matrix projMatrix = new Matrix();
    private List<Object3D> mChildren = new ArrayList<Object3D>();

    public TrackableObject3d(String markerString) {
        super(2); // 2 mx triangles, this object is the parent of all the trackable items
        mMarkerString = markerString;
    }

    public TrackableObject3d(String markerString, Object3D child) {
        this(markerString);
        addChild(child);
    }

    public void add3DSModel(Context c, String path, float scale) {
        try {
            Object3D [] object3D = Loader.load3DS(c.getAssets().open(path), scale);
            for (int i=0; i<object3D.length; i++) {
                addChild(object3D[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean registerMarker() {
        mMarkerId = ARToolKit.getInstance().addMarker(mMarkerString);
        return mMarkerId != -1;
    }

    public void updateMarkerTransformation() {
        // Update the position and rotation of the trackable object
        boolean markerVisible = ARToolKit.getInstance().queryMarkerVisible(mMarkerId);
        setVisibility(markerVisible);
        if (markerVisible) {
            float[] transformation = ARToolKit.getInstance().queryMarkerTransformation(mMarkerId);
            projMatrix.setDump(transformation);
            clearTranslation();
            translate(projMatrix.getTranslation());
            setRotationMatrix(projMatrix);
        }
    }

    @Override
    public void setVisibility (boolean visible) {
        super.setVisibility(visible);
        for (int i=0; i<mChildren.size(); i++) {
            mChildren.get(i).setVisibility(visible);
        }
    }

    @Override
    public void addChild(Object3D object3D) {
        super.addChild(object3D);
        // Keep it in a local list
        mChildren.add(object3D);
    }

    @Override
    public void removeChild(Object3D object3D) {
        super.removeChild(object3D);
        // remove it from the local list
        mChildren.remove(object3D);
    }

    /**
     * Add to the world, including all the children
     * @param world
     */
    public void addToWorld(World world) {
        world.addObject(this);
        for (int i=0; i<mChildren.size(); i++) {
            world.addObject(mChildren.get(i));
        }
    }

}
