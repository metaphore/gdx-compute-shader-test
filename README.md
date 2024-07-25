### OpenGL compute shader experiments using libGDX.
The main issue is libGDX officially only supports OpenGL ES up to version 3.0. Whereas, compute shaders require GLES 3.1 and up.
This project implements the missing functionality and provides a minimal example of a compute shader program.

## Backends
### LWJGL3
There are no issues here. LWJGL3 is fully compatible with the latest OpenGL 4.6.

### Android
There are no issues here too. Android supports GLES 3.1 since API 21.
It doesn't mean all devices may provide GLES 3.1 context though.
So extra feature requirements should be declared in the `AndroidManifest.xml`:
```xml
<uses-feature android:glEsVersion="0x00030001" android:required="true"/>
```

### iOS
**TODO**
But I expect little to no issues since the MetalANGLE backend is here.

### TeaVM + GWT
Not supported.
Due to WebGL2 powers the web backends, we are limited to only GLES 3.0 functionality here.
WebGL standard development is halted in favor of WebGPU API.
Which is very unlikely to be supported by any of the web backends anytime soon.
