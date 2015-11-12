# ArToolKitJpctBaseLib

This project provices an easy way to create AR applications for Android using ARToolKit and jPCT-AE together.

* ARToolKit provides marker detection and tracking, but the interface is raw OpenGL
* jPCT-AE is a 3D engine that provides high level concepts such as Camera, Object3D, Textures, ObjectLoaders, etc.

By putting them together we can create rich AR application in a simpler way. This library is a wrapper of ARToolKit that connects it with jPCT-AE

ARToolKit is provided under LGPL, and so is this wrapper.

## Notes about jPCT-AE

* Note that textures need to be of a size that is multiple of 2