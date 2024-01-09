import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FlashlightActivity extends Activity {
    private Camera camera;
    private Button button;
    private boolean isFlashOn = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);
        
        button = (Button) findViewById(R.id.button);
        
        // Check if the device has a camera flash
        boolean hasFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            // If the device doesn't have a camera flash, display an error message
            button.setEnabled(false);
            Toast.makeText(FlashlightActivity.this, "No flash available on your device", Toast.LENGTH_SHORT).show();
        }
        
        // Get an instance of the Camera object
        camera = Camera.open();
        
        // Set the button click listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFlashOn) {
                    // Turn off the flash
                    turnOffFlash();
                } else {
                    // Turn on the flash
                    turnOnFlash();
                }
            }
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release the Camera object when the activity is destroyed
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
    
    private void turnOnFlash() {
        if (!isFlashOn) {
            // Get the Camera.Parameters object and set the flash mode to "torch"
            Parameters params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;
            button.setText("Turn off");
        }
    }
    
    private void turnOffFlash() {
        if (isFlashOn) {
            // Get the Camera.Parameters object and set the flash mode to "off"
            Parameters params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;
            button.setText("Turn on");
        }
    }
}
