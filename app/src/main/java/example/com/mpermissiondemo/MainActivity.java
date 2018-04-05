package example.com.mpermissiondemo;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static android.Manifest.permission.CAMERA;


/**
 * Simple Activity Demonstrating the Android M runtime permission
 * Also handles the multiple permissions at the same time
 *
 * @author Swanand Vaidya
 * @version 0.1
 * @since 3 April 2018
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQ_CAMERA_PER_CODE = 10;
    private Button btnMulPermission;
    private Button btnSinglePermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // initialize the elements with their ids
        initElementsWithIds();

        // handle the onCLick events
        btnSinglePermission.setOnClickListener(this);
        btnMulPermission.setOnClickListener(this);
    }

    private void initElementsWithIds() {
        btnSinglePermission = findViewById(R.id.btn_single_permissions);
        btnMulPermission = findViewById(R.id.btn_mul_permissions);
    }

    // handle onClick events in this method
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_single_permissions:
                // first we will check the Android version
                // if Android version is greater than L then we will check the permission
                // otherwise no need to check
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // check the permission is granted or not
                    // if not request it again
                    if (checkSinglePermission()) {
                        Toast.makeText(MainActivity.this, getString(R.string.text_permission_granted), Toast.LENGTH_LONG).show();
                    } else {
                        // permission has declined previously
                        // so show again
                        requestPermissionAgain();
                    }
                } else {
                    Toast.makeText(this, getString(R.string.text_permission_granted), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btn_mul_permissions:
                // TODO
                break;
        }
    }

    private void requestPermissionAgain() {
        // requesting the permission with never show again check box
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        CAMERA,
                }, REQ_CAMERA_PER_CODE);
    }

    // check the permission
    private boolean checkSinglePermission() {
        int cameraPermission = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        return cameraPermission == PackageManager.PERMISSION_GRANTED;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQ_CAMERA_PER_CODE:
                if(grantResults.length > 0){
                    // check if granted or not
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,getString(R.string.text_permission_granted),Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(this,getString(R.string.text_permission_denied),Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}
