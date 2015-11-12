package org.artoolkit.ar.jpct;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Matrix;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

import org.artoolkit.ar.base.ARToolKit;
import org.artoolkit.ar.base.rendering.ARRenderer;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by portales on 11/11/15.
 */
public class ArJcptRenderer extends ARRenderer {

    private final ArJpctActivity mActivity;
    private List<TrackableObject3d> mTrackableObjects;

    private World mWorld;
    private Camera mCamera;
    private FrameBuffer mBuffer;
    private Matrix projMatrix = new Matrix();

    public ArJcptRenderer(ArJpctActivity arJpctActivity) {
        mActivity = arJpctActivity;
    }

    /**
     * Markers can be configured here.
     */
    @Override
    public boolean configureARScene() {
        // Initialize the game world and the camera
        mWorld = new World();
        mCamera = mWorld.getCamera();

        float fov = mActivity.getCameraPreview().getCameraFov();
        float yfov = mActivity.getCameraPreview().getCameraYFov();

        mCamera.setFOV(mCamera.convertDEGAngleIntoFOV(fov));
        mCamera.setYFOV(mCamera.convertDEGAngleIntoFOV(yfov));
//        mCamera.setFOV(1.38f);
//        float fovy = (float) (2 * Math.atan(mCamera.getFOV() / 2 *  mBuffer.getHeight() / mBuffer.getWidth()));
//        mCamera.setYFOV(fovy);

        mActivity.configureWorld(mWorld);

        // Get the activity list of trackable objects
        mTrackableObjects= mActivity.getTrackableObject3DList();

        // Load all the markers and add the objects to the world
        for (int i=0; i<mTrackableObjects.size(); i++) {
            TrackableObject3d trackableObject = mTrackableObjects.get(i);
            // Load the marker
            boolean success = trackableObject.registerMarker();
            if (!success) {
                return false;
            }
            // Add the object to the world, note that mWorld.addObject is not recursive
            // So we do it ourselves
            trackableObject.addToWorld(mWorld);
        }

        mWorld.buildAllObjects();

        return true;
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int w, int h) {
        super.onSurfaceChanged(unused, w, h);
        mBuffer = new FrameBuffer(unused,w,h);
    }

    /**
     * Override the draw function from ARRenderer.
     */
    @Override
    public final void draw(GL10 gl) {
        mBuffer.clear();

        float[] projection = ARToolKit.getInstance().getProjectionMatrix();
        projMatrix.setDump(projection);
        SimpleVector translation = projMatrix.getTranslation();

        SimpleVector dir = projMatrix.getZAxis();
        SimpleVector up = projMatrix.getYAxis();
        mCamera.setPosition(translation);
        mCamera.setOrientation(dir, up);

        for (int i=0; i<mTrackableObjects.size(); i++) {
            TrackableObject3d trackableObject = mTrackableObjects.get(i);
            trackableObject.updateMarkerTransformation();
        }

        mWorld.renderScene(mBuffer);
        mWorld.draw(mBuffer);
        mBuffer.display();
    }
}
