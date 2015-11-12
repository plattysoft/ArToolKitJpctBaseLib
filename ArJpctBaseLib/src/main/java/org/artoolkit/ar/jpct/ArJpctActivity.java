package org.artoolkit.ar.jpct;

import android.widget.FrameLayout;

import com.threed.jpct.World;

import org.artoolkit.ar.base.ARActivity;
import org.artoolkit.ar.base.rendering.ARRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by portales on 11/11/15.
 */
public abstract class ArJpctActivity extends ARActivity {

    @Override
    protected ARRenderer supplyRenderer() {
        return new ArJcptRenderer(this);
    }

    public final List<TrackableObject3d> getTrackableObject3DList() {
        List<TrackableObject3d> list = new ArrayList<TrackableObject3d>();
        populateTrackableObjects(list);
        return list;
    }

    protected abstract void populateTrackableObjects(List<TrackableObject3d> list);

    public abstract void configureWorld(World world);


}
