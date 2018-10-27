package com.lucemanb.TorchLight;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.FrameLayout;

public class Torch {
    private static String TAG = "### Torch ###";

    private Camera camera;
    private Camera.Parameters params;
    private boolean hasFlash, isFlashOn;
    private Context context;

    private boolean hasFlashAvailable(){
        return context.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }
    private void getCamera() {
        if (camera == null) {
            try {
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e(TAG, e.toString());
            }

        }else Log.e(TAG, "camera permissions missing");

    }

    public void turnOn() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
        }
    }
    public void turnOff() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
        }
    }

    public boolean isTorchOn(){return isFlashOn;}
    public boolean isPermissionGranted(){
        PackageManager mPackageManager = context.getPackageManager();
        int hasPermStorage = mPackageManager.checkPermission(android.Manifest.permission.CAMERA, context.getPackageName());
        return hasPermStorage == PackageManager.PERMISSION_GRANTED;
    }
    public void requestPermission(Activity activity){
        ActivityCompat.requestPermissions(activity, new String[]{android.Manifest.permission.CAMERA}, 10);
    }


    ///// #### USAGE ### /////
    public Torch(Context context){
        this.context = context;
        if (hasFlashAvailable()){
            if (isPermissionGranted())getCamera();
            else Log.e(TAG, "permission not granted");
        }else Log.e(TAG, "No flash available");
    }

}
