# ArToolKitJpctBaseLib

This project provices an easy way to create AR applications for Android using ARToolKit and jPCT-AE together.

* ARToolKit provides marker detection and tracking, but the interface is raw OpenGL
* jPCT-AE is a 3D engine that provides high level concepts such as Camera, Object3D, Textures, ObjectLoaders, etc.

By putting them together we can create rich AR application in a simpler way. This library is a wrapper of ARToolKit that connects it with jPCT-AE.

This project is an extension to the ARBaseLib project of ARToolkit.

ARToolKit is provided under LGPL, and so is this wrapper.

## Notes about jPCT-AE

* Note that textures need to be of a size that is multiple of 2
* The images should be placed under drawable-nodpi or the system will mess up the dimensions

## Other notes

* The NTF project uses free available model from NASA website
* FOV is not read correctly on same cases, but seems to be working fine for 640x480 on most phones
