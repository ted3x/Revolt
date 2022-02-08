package ge.ted3x.revolt

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.hcaptcha.sdk.HCaptcha

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.captcha).setOnClickListener { captcha()}
    }

    private fun captcha() {
        HCaptcha.getClient(this).verifyWithHCaptcha("3daae85e-09ab-4ff6-9f24-e8f4f335e433")
            .addOnSuccessListener {
                print(it.tokenResult)
            }
            .addOnFailureListener {
                print(it)
            }
    }
}