
package com.example.catchthekenny;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;


public class MainActivity extends AppCompatActivity {
    private TextView scoreText, timerText;
    private ImageView target;
    private int score = 0;
    private int timeLeft = 30; // 30 saniyelik süre
    private Handler handler = new Handler();
    private Runnable timerRunnable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scoreText = findViewById(R.id.scoreText);
        timerText = findViewById(R.id.timerText);
        target = findViewById(R.id.target);

        // Timer başlat
        startTimer();

        // Hedefin tıklanabilir olmasını sağla
        target.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Hedef yakalandı, skoru arttır
                    score++;
                    updateScore();

                    // Hedefi yeni bir konuma yerleştir
                    moveTarget();

                    return true;
                }
                return false;
            }
        });
    }

    // Skoru güncelle
    private void updateScore() {
        scoreText.setText("Skor: " + score);
    }

    // Hedefin konumunu rastgele ayarla
    private void moveTarget() {
        Random random = new Random();
        int x = random.nextInt(getWindow().getDecorView().getWidth() - target.getWidth());
        int y = random.nextInt(getWindow().getDecorView().getHeight() - target.getHeight());

        target.setX(x);
        target.setY(y);
    }

    // Kronometreyi başlat
    private void startTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                // Zamanı bir saniye azalt
                if (timeLeft > 0) {
                    timeLeft--;
                    timerText.setText("Zaman: " + timeLeft);
                    handler.postDelayed(this, 1000); // 1 saniye sonra tekrar çalıştır
                } else {
                    timerText.setText("Zaman Bitti!");
                    target.setEnabled(false); // Zaman bittiğinde hedefi tıklanamaz yap
                }
            }
        };
        handler.postDelayed(timerRunnable, 1000);
    }
}


