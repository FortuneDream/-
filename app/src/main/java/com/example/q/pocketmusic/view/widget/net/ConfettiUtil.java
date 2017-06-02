package com.example.q.pocketmusic.view.widget.net;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.ViewGroup;

import com.example.q.pocketmusic.R;
import com.github.jinatonic.confetti.CommonConfetti;
import com.github.jinatonic.confetti.ConfettiManager;
import com.github.jinatonic.confetti.ConfettiSource;
import com.github.jinatonic.confetti.ConfettoGenerator;
import com.github.jinatonic.confetti.confetto.BitmapConfetto;
import com.github.jinatonic.confetti.confetto.Confetto;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 鹏君 on 2016/9/22.
 */

public class ConfettiUtil {
    //五彩
    public static void getCommonConfetti(ViewGroup container) {
        Resources res = container.getContext().getResources();
        int red = res.getColor(R.color.confetti_red);
        int orange = res.getColor(R.color.confetti_orange);
        int yellow = res.getColor(R.color.confetti_yellow);
        int green = res.getColor(R.color.confetti_green);
        int qing = res.getColor(R.color.confetti_qing);
        int blue = res.getColor(R.color.confetti_blue);
        int zi = res.getColor(R.color.confetti_zi);

        int[] colors = new int[]{red, orange, yellow, green, qing, blue, zi};
        final int size = res.getDimensionPixelSize(R.dimen.default_confetti_size);
        final ConfettiSource confettiSource = new ConfettiSource(size, size);
        final CommonConfetti commonConfetti =
                CommonConfetti.rainingConfetti(container, confettiSource, colors);

        final int velocitySlow = res.getDimensionPixelOffset(R.dimen.default_velocity_slow);
        final int velocityNormal = res.getDimensionPixelOffset(R.dimen.default_velocity_normal);
        final int velocityFast = res.getDimensionPixelOffset(R.dimen.default_velocity_fast);

        // Further configure it
        commonConfetti.getConfettiManager()
                .setVelocityX(velocityFast, velocityNormal)
                .setAccelerationX(-velocityNormal, velocitySlow)
                .setTargetVelocityX(0, velocitySlow / 2)
                .setVelocityY(velocityNormal, velocitySlow);
        commonConfetti.oneShot();
    }

    //自定义
    public static void getCustomConfetti(ViewGroup container) {

        //在这里自定义图片
        final List<Bitmap> allPossibleConfetti = new ArrayList<>();
        Bitmap aixin = BitmapFactory.decodeResource(container.getResources(), R.drawable.confetti_aixin);
        allPossibleConfetti.add(aixin);

        final Resources res = container.getContext().getResources();
        int size = res.getDimensionPixelSize(R.dimen.big_confetti_size);
        int velocitySlow = res.getDimensionPixelOffset(R.dimen.default_velocity_slow);
        int velocityNormal = res.getDimensionPixelOffset(R.dimen.default_velocity_normal);

        final int numConfetti = allPossibleConfetti.size();
        ConfettoGenerator confettoGenerator = new ConfettoGenerator() {
            @Override
            public Confetto generateConfetto(Random random) {
                final Bitmap bitmap = allPossibleConfetti.get(random.nextInt(numConfetti));
                return new BitmapConfetto(bitmap);
            }
        };

        final ConfettiSource source = new ConfettiSource(0, -size, container.getWidth(), -size);
        ConfettiManager cm = new ConfettiManager(container.getContext(), confettoGenerator, source, container)
                .setVelocityX(0, velocitySlow)
                .setVelocityY(velocityNormal, velocitySlow)
                .setRotationalVelocity(180, 90)
                .setTouchEnabled(true);

        cm.setNumInitialCount(20)
                .setEmissionDuration(0)
                .animate();
    }

}
