package com.example.code0829;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionPoint;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceContour;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetector;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceDetectorOptions;
import com.google.firebase.ml.vision.face.FirebaseVisionFaceLandmark;

import java.util.List;

public class FaceActivity extends Activity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_face);
        mContext = this;

        final RelativeLayout RelativeLayout_main  = findViewById(R.id.RelativeLayout_main);

        FirebaseVisionFaceDetectorOptions options =
                new FirebaseVisionFaceDetectorOptions.Builder()
                        .setPerformanceMode(FirebaseVisionFaceDetectorOptions.ACCURATE)
                        .setLandmarkMode(FirebaseVisionFaceDetectorOptions.ALL_LANDMARKS)
                        .setClassificationMode(FirebaseVisionFaceDetectorOptions.ALL_CLASSIFICATIONS)
                        .build();

        final Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.shuhua);

        final FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionFaceDetector detector = FirebaseVision.getInstance()
                .getVisionFaceDetector(options);

        Task<List<FirebaseVisionFace>> result =
                detector.detectInImage(image)
                        .addOnSuccessListener(
                                new OnSuccessListener<List<FirebaseVisionFace>>() {
                                    @Override
                                    public void onSuccess(List<FirebaseVisionFace> faces) {
                                        // Task completed successfully
                                        // ...
                                        Log.d("FACES", faces.toString());

                                        Point p = new Point();
                                        Display display = getWindowManager().getDefaultDisplay();
                                        display.getSize(p);

                                        for (FirebaseVisionFace face : faces) {
                                            // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
                                            // nose available):
                                            FirebaseVisionFaceLandmark leftEye = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_EYE);
                                            float lex = leftEye.getPosition().getX();
                                            float ley = leftEye.getPosition().getY();

                                            FirebaseVisionFaceLandmark rightEye = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_EYE);
                                            float rex = rightEye.getPosition().getX();
                                            float rey = rightEye.getPosition().getY();

                                            FirebaseVisionFaceLandmark leftCheek = face.getLandmark(FirebaseVisionFaceLandmark.LEFT_CHEEK);
                                            float lcx = leftCheek.getPosition().getX();
                                            float lcy = leftCheek.getPosition().getY();

                                            FirebaseVisionFaceLandmark rightCheek = face.getLandmark(FirebaseVisionFaceLandmark.RIGHT_CHEEK);
                                            float rcx = rightCheek.getPosition().getX();
                                            float rcy = rightCheek.getPosition().getY();

                                            ImageView imageLeftBunny = new ImageView(mContext); //이미지 뷰 만들기
                                            imageLeftBunny.setImageResource(R.drawable.bunny_ears_left); //이미지 사진 연결
                                            imageLeftBunny.setX(p.x * lex / bitmap.getWidth() - 220); //위치 표시
                                            imageLeftBunny.setY(p.y * ley / bitmap.getHeight() - 600);
                                            RelativeLayout_main.addView(imageLeftBunny); //이미지 뷰 표시
                                            imageLeftBunny.setLayoutParams(new RelativeLayout.LayoutParams(220, 356)); //이미지 크기

                                            ImageView imageRightBunny = new ImageView(mContext); //이미지 뷰 만들기
                                            imageRightBunny.setImageResource(R.drawable.bunny_ears_right); //이미지 사진 연결
                                            imageRightBunny.setX(p.x * rex / bitmap.getWidth()); //위치 표시
                                            imageRightBunny.setY(p.y * rey / bitmap.getHeight() - 600);
                                            RelativeLayout_main.addView(imageRightBunny); //이미지 뷰 표시
                                            imageRightBunny.setLayoutParams(new RelativeLayout.LayoutParams(237, 356)); //이미지 크기

                                            ImageView imageLeftHeart = new ImageView(mContext);
                                            imageLeftHeart.setImageResource(R.drawable.heart);
                                            imageLeftHeart.setX(p.x * lcx / bitmap.getWidth() - 65);
                                            imageLeftHeart.setY(p.y * lcy / bitmap.getHeight() - 130);
                                            RelativeLayout_main.addView(imageLeftHeart);
                                            imageLeftHeart.setLayoutParams(new RelativeLayout.LayoutParams(130, 97));


                                            ImageView imageRightHeart = new ImageView(mContext);
                                            imageRightHeart.setImageResource(R.drawable.heart);
                                            imageRightHeart.setX(p.x * rcx / bitmap.getWidth() - 65);
                                            imageRightHeart.setY(p.y * rcy / bitmap.getHeight() - 130);
                                            RelativeLayout_main.addView(imageRightHeart);
                                            imageRightHeart.setLayoutParams(new RelativeLayout.LayoutParams(130, 97));


                                        }
                                    }
                                })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Task failed with an exception
                                        // ...
                                    }
                                });
    }
}
