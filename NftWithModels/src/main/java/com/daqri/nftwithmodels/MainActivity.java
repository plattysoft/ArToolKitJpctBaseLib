package com.daqri.nftwithmodels;

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

import org.artoolkit.ar.base.markers.NFTMarkerConfig;
import org.artoolkit.ar.jpct.ArJpctActivity;
import org.artoolkit.ar.jpct.TrackableLight;
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
        // Note: The NASA logo is really bad for tracking
        TrackableObject3d tckobj = new TrackableObject3d(new NFTMarkerConfig("Data/NASA", 80));

        Texture texture = new Texture(getResources().getDrawable(R.drawable.astnt1_1));
        TextureManager.getInstance().addTexture("astnt1_1", texture);
        texture = new Texture(getResources().getDrawable(R.drawable.moon_ground));
        TextureManager.getInstance().addTexture("moon_ground", texture);

        try {
            Object3D [] astronaut = Loader.load3DS(getAssets().open("astronaut1.3ds"), 5);
            astronaut[0].setOrigin(new SimpleVector(280, 200, 270));
            astronaut[0].setTexture("astnt1_1");
            tckobj.addChild(astronaut[0]);

            // Put a plane to see where it cuts
            Object3D object3D = Primitives.getPlane(2, 200);
            // Planes are rotated 180 degrees, so we need to flip them
            object3D.rotateX((float) Math.PI);
            object3D.setOrigin(new SimpleVector(125, 125, 0));
            object3D.setTexture("moon_ground");
            tckobj.addChild(object3D);

        } catch (IOException e) {
            e.printStackTrace();
        }

        list.add(tckobj);
    }
}