/*
 *  ARSimple.java
 *  ARToolKit5
 *
 *  Disclaimer: IMPORTANT:  This Daqri software is supplied to you by Daqri
 *  LLC ("Daqri") in consideration of your agreement to the following
 *  terms, and your use, installation, modification or redistribution of
 *  this Daqri software constitutes acceptance of these terms.  If you do
 *  not agree with these terms, please do not use, install, modify or
 *  redistribute this Daqri software.
 *
 *  In consideration of your agreement to abide by the following terms, and
 *  subject to these terms, Daqri grants you a personal, non-exclusive
 *  license, under Daqri's copyrights in this original Daqri software (the
 *  "Daqri Software"), to use, reproduce, modify and redistribute the Daqri
 *  Software, with or without modifications, in source and/or binary forms;
 *  provided that if you redistribute the Daqri Software in its entirety and
 *  without modifications, you must retain this notice and the following
 *  text and disclaimers in all such redistributions of the Daqri Software.
 *  Neither the name, trademarks, service marks or logos of Daqri LLC may
 *  be used to endorse or promote products derived from the Daqri Software
 *  without specific prior written permission from Daqri.  Except as
 *  expressly stated in this notice, no other rights or licenses, express or
 *  implied, are granted by Daqri herein, including but not limited to any
 *  patent rights that may be infringed by your derivative works or by other
 *  works in which the Daqri Software may be incorporated.
 *
 *  The Daqri Software is provided by Daqri on an "AS IS" basis.  DAQRI
 *  MAKES NO WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 *  THE IMPLIED WARRANTIES OF NON-INFRINGEMENT, MERCHANTABILITY AND FITNESS
 *  FOR A PARTICULAR PURPOSE, REGARDING THE DAQRI SOFTWARE OR ITS USE AND
 *  OPERATION ALONE OR IN COMBINATION WITH YOUR PRODUCTS.
 *
 *  IN NO EVENT SHALL DAQRI BE LIABLE FOR ANY SPECIAL, INDIRECT, INCIDENTAL
 *  OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *  SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 *  INTERRUPTION) ARISING IN ANY WAY OUT OF THE USE, REPRODUCTION,
 *  MODIFICATION AND/OR DISTRIBUTION OF THE DAQRI SOFTWARE, HOWEVER CAUSED
 *  AND WHETHER UNDER THEORY OF CONTRACT, TORT (INCLUDING NEGLIGENCE),
 *  STRICT LIABILITY OR OTHERWISE, EVEN IF DAQRI HAS BEEN ADVISED OF THE
 *  POSSIBILITY OF SUCH DAMAGE.
 *
 *  Copyright 2015 Daqri, LLC.
 *  Copyright 2011-2015 ARToolworks, Inc.
 *
 *  Author(s): Julian Looser, Philip Lamb
 *
 */

package org.artoolkit.ar.samples.ARSimple;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.threed.jpct.Light;
import com.threed.jpct.Loader;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;

import org.artoolkit.ar.base.rendering.ARRenderer;
import org.artoolkit.ar.jpct.ArJpctActivity;
import org.artoolkit.ar.jpct.TrackableLight;
import org.artoolkit.ar.jpct.TrackableObject3d;

import java.io.IOException;
import java.util.List;

/**
 * A very simple example of extending ARActivity to create a new AR application.
 */
public class ARSimple extends ArJpctActivity {

    private TrackableLight mLight;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);      
		setContentView(R.layout.main); 
	}

	/**
	 * Use the FrameLayout in this Activity's UI.
	 */
	@Override
	protected FrameLayout supplyFrameLayout() {
		return (FrameLayout)this.findViewById(R.id.mainLayout);    	
	}

    public void configureWorld(World world) {
        world.setAmbientLight(150, 150, 150);
//        Light light = new Light(world);
//        light.setIntensity(100, 255, 255);
    }

    protected void populateTrackableObjects(List<TrackableObject3d> list) {
        TrackableObject3d tckobj = new TrackableObject3d("single;Data/patt.hiro;80", getCube());

        TrackableLight light = new TrackableLight();
        light.setIntensity(0, 0, 255);
        light.setPosition(new SimpleVector(0, 0, 100));
        tckobj.addLight(light);

        list.add(tckobj);

        TrackableObject3d trackablePlane = new TrackableObject3d("single;Data/patt.kanji;80", getPlane());

        light = new TrackableLight();
        light.setIntensity(255, 0, 0);

        trackablePlane.addLight(light);

        list.add(trackablePlane);
    }

    private Object3D getCube() {
        int scale = 40;
        Object3D object3D = Primitives.getCube(scale);
        // Cubes in jpct are rotated by 45 degrees when created.
        object3D.rotateY((float) Math.PI / 4);
        object3D.setOrigin(new SimpleVector(0, 0, scale));
        return object3D;
    }

    private Object3D getPlane() {
        Object3D object3D = Primitives.getPlane(2, 60);
        // Planes are rotated 180 degrees, so we need to flip them
        object3D.rotateX((float) Math.PI);
        // Load the AR Toolkit texture on top of the plane
        Texture texture = new Texture(getResources().getDrawable(R.drawable.artoolkit_logo));
        TextureManager.getInstance().addTexture("artoolkit", texture);

        object3D.setTexture("artoolkit");
        return object3D;
    }
}