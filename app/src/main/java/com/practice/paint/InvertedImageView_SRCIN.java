package com.practice.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author barry
 * @version V1.0
 * @time 2018-6-20
 */
public class InvertedImageView_SRCIN extends View {
    private Paint mBitPaint;
    private Bitmap BmpDST, BmpSRC, BmpRevert;

    public InvertedImageView_SRCIN(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBitPaint = new Paint();
        Bitmap BmpDST = BitmapFactory.decodeResource(getResources(), R.drawable.invert_shade, null);
        Bitmap BmpSRC = BitmapFactory.decodeResource(context.getResources(), R.drawable.xyjy6, null);

        Matrix matrix = new Matrix();
        matrix.setScale(1F, -1F);
        int width = BmpSRC.getWidth();
        int height = BmpSRC.getHeight();
        // 生成倒影图
        BmpRevert = Bitmap.createBitmap(BmpSRC, 0, 0, width, height, matrix, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //先画出小狗图片
        canvas.drawBitmap(BmpSRC, 0, 0, mBitPaint);

        //再画出倒影
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        canvas.translate(0, BmpSRC.getHeight());

        canvas.drawBitmap(BmpDST, 0, 0, mBitPaint);
        mBitPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(BmpRevert, 0, 0, mBitPaint);

        mBitPaint.setXfermode(null);

        canvas.restoreToCount(layerId);
    }
}
