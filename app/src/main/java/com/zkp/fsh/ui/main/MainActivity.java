package com.zkp.fsh.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.coder.zzq.smartshow.toast.SmartToast;
import com.coorchice.library.SuperTextView;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zkp.fsh.R;
import com.zkp.fsh.RuntimeRationale;
import com.zkp.fsh.ui.edit.java.SketcherPictureJavaActivity;
import com.zkp.fsh.ui.edit.jni.SketcherPictureJNIActivity;

import java.io.File;
import java.util.List;

/**
 * ***********************************************
 * **                  _oo0oo_                  **
 * **                 o8888888o                 **
 * **                 88" . "88                 **
 * **                 (| -_- |)                 **
 * **                 0\  =  /0                 **
 * **               ___/'---'\___               **
 * **            .' \\\|     |// '.             **
 * **           / \\\|||  :  |||// \\           **
 * **          / _ ||||| -:- |||||- \\          **
 * **          | |  \\\\  -  /// |   |          **
 * **          | \_|  ''\---/''  |_/ |          **
 * **          \  .-\__  '-'  __/-.  /          **
 * **        ___'. .'  /--.--\  '. .'___        **
 * **     ."" '<  '.___\_<|>_/___.' >'  "".     **
 * **    | | : '-  \'.;'\ _ /';.'/ - ' : | |    **
 * **    \  \ '_.   \_ __\ /__ _/   .-' /  /    **
 * **====='-.____'.___ \_____/___.-'____.-'=====**
 * **                  '=---='                  **
 * ***********************************************
 * **              佛祖保佑  镇类之宝              **
 * ***********************************************
 *
 * @author zkp
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_SETTING = 0x0001;
    private static final int JNI_CODE = 0x0002;
    private static final int JAVA_CODE = 0x0003;

    private SuperTextView mStvImageSketcherJNI, mStvImageSketcherJava;
    public static File file;
    private List<LocalMedia> selectList;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission(
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.CAMERA);

        file = new File(Environment.getExternalStorageDirectory() + File.separator + "浮生绘", "");

        mStvImageSketcherJNI = findViewById(R.id.stvImageSketcherJNI);
        mStvImageSketcherJava = findViewById(R.id.stvImageSketcherJava);

        mStvImageSketcherJNI.setOnClickListener(this);
        mStvImageSketcherJava.setOnClickListener(this);
    }

    private void requestPermission(String... permissions) {
        AndPermission.with(this)
                .runtime()
                .permission(permissions)
                .rationale(new RuntimeRationale())
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(@NonNull List<String> permissions) {
                        SmartToast.success("获取权限失败");
                        if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, permissions)) {
                            showSettingDialog(MainActivity.this, permissions);
                        }
                    }
                })
                .start();
    }

    public void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed,
                TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context).setCancelable(false)
                .setTitle("权限申请")
                .setMessage(message)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    private void setPermission() {
        AndPermission.with(this).runtime().setting().start(REQUEST_CODE_SETTING);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.stvImageSketcherJNI:
                //选择图片显示到ImageView中
                selectPicture(JNI_CODE);
                break;
            case R.id.stvImageSketcherJava:
                selectPicture(JAVA_CODE);
                break;
            default:
                break;
        }
    }

    /**
     * 选择图片显示到ImageView中
     */
    private void selectPicture(int code) {

        if (!file.exists()) {
            //创建文件夹
            file.mkdirs();
        }

        Logger.d(file.getAbsolutePath());

        PictureSelector.create(MainActivity.this)
                .openGallery(PictureMimeType.ofImage())
                .theme(R.style.picture_default_style)
                .maxSelectNum(1)
                .minSelectNum(1)
                .imageSpanCount(4)
                .previewImage(true)
                .isCamera(false)
                .imageFormat(PictureMimeType.PNG)
                .isZoomAnim(true)
                .sizeMultiplier(0.5f)
                .setOutputCameraPath(file.getAbsolutePath())
                .enableCrop(false)
                .compress(true)
                .glideOverride(160, 160)
                .hideBottomControls(false)
                .isGif(false)
                .compressSavePath(file.getAbsolutePath())
                .openClickSound(false)
                .previewEggs(true)
                .cropCompressQuality(90)
                .minimumCompressSize(100)
                .synOrAsy(true)
                .forResult(code);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case JNI_CODE:
                    selectList = PictureSelector.obtainMultipleResult(data);
                    Logger.d(selectList.get(0).getCompressPath());

                    intent = new Intent(MainActivity.this, SketcherPictureJNIActivity.class);
                    intent.putExtra("imgPath", selectList.get(0).getCompressPath());
                    startActivity(intent);
                    break;
                case JAVA_CODE:
                    selectList = PictureSelector.obtainMultipleResult(data);
                    Logger.d(selectList.get(0).getCompressPath());

                    intent = new Intent(MainActivity.this, SketcherPictureJavaActivity.class);
                    intent.putExtra("imgPath", selectList.get(0).getCompressPath());
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}
