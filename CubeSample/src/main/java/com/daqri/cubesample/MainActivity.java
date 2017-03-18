package com.daqri.cubesample;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.threed.jpct.Config;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;

import org.artoolkit.ar.base.ARToolKit;
import org.artoolkit.ar.base.NativeInterface;
import org.artoolkit.ar.base.markers.MultiMarkerConfig;
import org.artoolkit.ar.jpct.ArJpctActivity;
import org.artoolkit.ar.jpct.TrackableObject3d;

import java.io.IOException;
import java.util.List;

public class MainActivity extends ArJpctActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Use the FrameLayout in this Activity's UI.
     */
    @Override
    protected FrameLayout supplyFrameLayout() {
        return (FrameLayout)this.findViewById(R.id.mainLayout);
    }

    public void configureWorld(World world) {
        Config.farPlane = 2000;
        world.setAmbientLight(255, 255, 255);
    }

    protected void populateTrackableObjects(List<TrackableObject3d> list) {
        ARToolKit.getInstance().setPatternDetectionMode(NativeInterface.AR_MATRIX_CODE_DETECTION);
        ARToolKit.getInstance().setMatrixCodeType(NativeInterface.AR_MATRIX_CODE_3x3);

        TrackableObject3d tckobj = new TrackableObject3d(new MultiMarkerConfig("Data/cubeMarkerConfig.dat"));

        Object3D object3D = Primitives.getCube(60);
        object3D.setTransparency(10);
        object3D.setTransparencyMode(Object3D.TRANSPARENCY_MODE_DEFAULT);
        object3D.rotateY((float) Math.PI / 4);
        object3D.setOrigin(new SimpleVector(0, 0, -60));

        tckobj.addChild(object3D);

        list.add(tckobj);
    }
}