package com.junseo.daepyeong.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.junseo.daepyeong.R;
import com.junseo.daepyeong.store.BarcodePref;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by junse on 2017-09-03.
 */

public class BarcodeActivity extends AppCompatActivity {

    Toolbar toolbar;

    private WindowManager.LayoutParams params;
    private float brightness; // 밝기값은 float형으로 저장되어 있습니다.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("학생증 바코드 보기");
        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);//액션바와 같게 만들어줌
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_24dp));
        toolbar.setNavigationOnClickListener(v -> finish());

        View barcodeView = findViewById(R.id.barcodeResult);

        ImageView barcodeimageView = findViewById(R.id.barcodeImage);
        TextView barcodeTextView = findViewById(R.id.barcodeTextView);

        final BarcodePref barcodePref = new BarcodePref(this);

        if (Objects.equals(barcodePref.getBarcodeData(), "")) {
            barcodeView.setVisibility(View.GONE);
        } else {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                params = getWindow().getAttributes();

                brightness = params.screenBrightness;
                // 최대 밝기로 설정
                params.screenBrightness = 1f;
                // 밝기 설정 적용
                getWindow().setAttributes(params);

                BitMatrix bitMatrix = multiFormatWriter.encode(barcodePref.getBarcodeData(), BarcodeFormat.CODE_39, 4500, 1000);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                barcodeimageView.setImageBitmap(bitmap);
                barcodeTextView.setText(barcodePref.getBarcodeData());
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }

        RelativeLayout addBarcode = findViewById(R.id.addBarcode);
        addBarcode.setOnClickListener(v -> {
            requestAddBarcode();
        });
    }

    private void requestAddBarcode() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(BarcodeActivity.this, ScanBarcode.class);
                startActivity(intent);
                finish();
            } else {
                permissionCheck();
            }
        } else {
            Intent intent = new Intent(BarcodeActivity.this, ScanBarcode.class);
            startActivity(intent);
            finish();
        }
    }

    private void permissionCheck() {
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Intent intent = new Intent(BarcodeActivity.this, ScanBarcode.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(BarcodeActivity.this, "권한이 거부되었습니다.\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("바코드 등록을 위해서는 카메라 접근 권한이 필요해요")
                .setDeniedMessage("왜 거부하셨어요...\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.CAMERA)
                .check();
    }

    @Override protected void onPause() {
        super.onPause();
        // 기존 밝기로 변경
        if (brightness == 1f) {
            params.screenBrightness = brightness;
            getWindow().setAttributes(params);
        }
    }
}
