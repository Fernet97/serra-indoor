package com.example.fernet.indoor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class CustomPreview extends SurfaceView implements SurfaceHolder.Callback{

    public static Bitmap mBitmap;
    SurfaceHolder holder;
    static Camera mCamera;

    public CustomPreview(Context context, AttributeSet attrs) {
        super(context, attrs);

        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {

        Camera.Parameters parameters = mCamera.getParameters();
        parameters.getSupportedPreviewSizes();
        mCamera.setParameters(parameters);
        mCamera.startPreview();
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera = Camera.open();
            mCamera.setPreviewDisplay(holder);

        } catch (Exception e) {
            e.printStackTrace();
            Toast t = Toast.makeText(getContext(), "Non riesco a connettermi alla telecamera :(",Toast.LENGTH_LONG );
            t.show();
        }
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
    }
    /***
     *
     *  Take a picture and and convert it from bytes[] to Bitmap.
     *
     */
    public static void takeAPicture(){

        Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {

                BitmapFactory.Options options = new BitmapFactory.Options();
                mBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
            }
        };
        mCamera.takePicture(null, null, mPictureCallback);
    }
}