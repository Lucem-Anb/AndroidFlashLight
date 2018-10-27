# AndroidFlashLight
This Project is built on the latest SDK and is meant to build a flash light example in android

Download the app Here:


#### Implementation

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```
	dependencies {
		implementation 'com.github.Lucem-Anb:AndroidFlashLight:1.0.0'
	}
```
That's it!

### Usage
##### Permissions
```
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature android:name="android.hardware.camera" />
```
##### Initialize
```
    mTorch = new Torch(this);
```

Check for permissions
```
        if (!mTorch.isPermissionGranted()){
            mTorch.requestPermission(this);
            return;
        }
```

Toggle the torch on or off
```
mTorch.turnOn();
mTorch.turnOff();
```

