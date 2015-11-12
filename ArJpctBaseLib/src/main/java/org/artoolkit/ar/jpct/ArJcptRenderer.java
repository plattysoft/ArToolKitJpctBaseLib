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
     * Configuration of the AR Scene
     * Initializes jPCT and gets the trackable objects list
     * Registers the markers and adds the objects to the world
     */
    @Override
    public boolean configureARScene() {
        // Initialize the game world and the camera
        mWorld = new World();
        mCamera = mWorld.getCamera();

        // Set the FOV based on the camera parameters
        // This FOW is correct when using
        android.hardware.Camera.Parameters params = mActivity.getCameraPreview().getCameraParameters();
        // Setting the FOV based on the camera params, this seems to work fine with 640x480
        float fov = params.getHorizontalViewAngle();
        float yfov = params.getVerticalViewAngle();
        mCamera.setFOV(mCamera.convertDEGAngleIntoFOV(fov));
        mCamera.setYFOV(mCamera.convertDEGAngleIntoFOV(yfov));

        // Another way of calculating the FOV
//        float focalLength = params.getFocalLength();
//        float fovRadians = (float) (2 * Math.atan2(0.5f * params.getPreviewSize().width, focalLength));
//        mCamera.setFovAngle(fovRadians);
////        float fovyRadians = (float) (2 * Math.atan2(0.5f * params.getPreviewSize().height, focalLength));
//        float fovyRadians = (float) (2 * Math.atan(mCamera.getFOV() / 2 * params.getPreviewSize().height / params.getPreviewSize().width));
//        mCamera.setYFovAngle(fovyRadians);

        mActivity.configureWorld(mWorld);

        // Get the activity list of trackable objects
        mTrackableObjects= mActivity.getTrackableObject3DList();

        // Load all the markers and add the objects to the world
        for (int i=0; i<mTrackableObjects.size(); i++) {
            TrackableObject3d trackableObject = mTrackableObjects.get(i);
            // Load the marker
            if (! trackableObject.registerMarker() ) {
                // If there was a problem, return false
                return false;
            }
            // Add the object to the world, note that mWorld.addObject is not recursive
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
     * Override the draw function from ARRenderer
     * This one sets the correct position and orientation from the camera
     * For each trackable object, checks if the marker is visible and if so, updates the object
     */
    @Override
    public final void draw(GL10 gl) {
        mBuffer.clear();

        float[] projection = ARToolKit.getInstance().getProjectionMatrix();
        projMatrix.setDump(projection);
        SimpleVector translation = projMatrix.getTranslation();

        // Calculate FOV
        float vFov = (float) Math.atan2(1, projection[5])*2;
        mCamera.setYFovAngle(vFov);
        float aspect = projection[5] / projection[0];
        // hFOV = 2 * Math.atan( Math.tan( vFOV / 2 ) * aspect );
        float fov = (float) (2 * Math.atan2(mCamera.getYFOV(), 2 ) * aspect);

        mCamera.setFovAngle(fov);

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
