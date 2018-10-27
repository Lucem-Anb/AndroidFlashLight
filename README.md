# AndroidFlashLight [![Lucem](https://camo.githubusercontent.com/cfcaf3a99103d61f387761e5fc445d9ba0203b01/68747470733a2f2f7472617669732d63692e6f72672f6477796c2f657374612e7376673f6272616e63683d6d6173746572)](https://about.me/lucem-anb) [![](https://jitpack.io/v/Lucem-Anb/AndroidFlashLight.svg)](https://jitpack.io/#Lucem-Anb/AndroidFlashLight)
This Project is built on the latest SDK and is meant to build a flash light example in android

Download the app [here](https://play.google.com/store/apps/details?id=com.lucemanb.torchcompanion)

![root](https://anbinsane.files.wordpress.com/2018/10/ic_launcher.png) ![normal](https://anbinsane.files.wordpress.com/2018/10/icc.png)

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

