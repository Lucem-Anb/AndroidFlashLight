package com.anb.torchcompanion;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lucemanb.TorchLight.Torch;

public class Main extends AppCompatActivity {

    View screenLight;
    SeekBar level;
    ImageButton torch, screen;
    TextView notify;

    private AlertDialog.Builder ad = null;
    private static String mode = "";
    private static boolean hasFlash = false;
    Torch mTorch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screenLight = findViewById(R.id.screenLight);
        level = findViewById(R.id.level);
        torch = findViewById(R.id.torch);
        screen = findViewById(R.id.screen);
        notify = findViewById(R.id.notify);

        init();

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==10){
            if (checkPermissions(Main.this))init();
            else
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, 10);
        }
    }

    void init(){

        if (checkPermissions(this)){
            //Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
        }else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CAMERA}, 10);
            return;
        }

        mTorch = new Torch(this);
        hasFlash = getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!mTorch.isPermissionGranted()){
            mTorch.requestPermission(this);
            return;
        }

        level.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b){
                    if (mode.matches("torch")){
                        if (ad!=null)return;
                        rootVersionAlert();
                        notify.setText("");
                    }else if (mode.matches("screen")){
                        double value = (double)i/20.0;
                        screenLight.setAlpha((float)(1.0-value));
                        notify.setText("");
                    }else{
                        notify.setText("Choose mode");
                    }
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        torch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasFlash){
                    cantSupportNoFlash();
                    return;
                }
                if (mode.matches("torch")){
                    //turn off
                    mTorch.turnOff();
                    mode = "";
                }else{
                    //turn on
                    mTorch.turnOn();
                    mode = "torch";
                }

            }
        });

        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode.matches("screen")){
                    level.setProgress(0);
                    screenLight.setAlpha(1);
                    mode = "";
                }else{
                    level.setProgress(20);
                    screenLight.setAlpha(0);
                    mode = "screen";
                }
            }
        });
    }

    void rootVersionAlert(){
        ad = new AlertDialog.Builder(this)
                .setTitle("Root Required")
                .setMessage("changing the flash light brightness requires root permissions. Download the root version.")
                .setPositiveButton("Download", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String appPackageName = "com.anb.TorchCompanionRoot";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        ad = null;
                    }
                }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ad = null;
            }
        });
        ad.show();
    }
    void cantSupportNoFlash(){
        new AlertDialog.Builder(this)
                .setTitle("Unsupported")
                .setMessage("Sorry, your device doesn't support flash light!")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
    Boolean checkPermissions(Activity activity) {
        PackageManager mPackageManager = activity.getPackageManager();
        int hasPermStorage = mPackageManager.checkPermission(android.Manifest.permission.CAMERA, activity.getPackageName());
        return hasPermStorage == PackageManager.PERMISSION_GRANTED;
    }
}
