package general.me.edu.dgtmovil.dgtmovil;

import android.util.Log;
import android.widget.TextView;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by ADMIN on 20/06/2016.
 */

public class Encriptar {

    static final String TAG = "SymmetricAlgorithmAES";

    String theTestText = "This is just a simple test";
    String password= "Contrasenia";
    String passwordVerifica = "";
    SecretKeySpec sks = null;
    byte[] encodedBytes = null;


    public void generarLlave(String clave){
        // Set up secret key spec for 128-bit AES encryption and decryption

        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(clave.getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128, sr);
            sks = new SecretKeySpec((kg.generateKey()).getEncoded(), "AES");


        } catch (Exception e) {
            Log.e(TAG, "AES secret key spec error");
        }
    }

    public byte[] encriptar(String mensaje){
        // Encode the original data with AES

        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.ENCRYPT_MODE, sks);
            encodedBytes = c.doFinal(mensaje.getBytes());
        } catch (Exception e) {
            Log.e(TAG, "AES encryption error");
        }

    return encodedBytes;
    }

    public byte[] desencriptar(byte[] encode, String clave){
        // Decode the encoded data with AES
        // generarLlave(clave);
        byte[] decodedBytes = null;
        try {
            Cipher c = Cipher.getInstance("AES");
            c.init(Cipher.DECRYPT_MODE, sks);
            decodedBytes = c.doFinal(encode);
        } catch (Exception e) {
            Log.e(TAG, "AES decryption error" + e);
        }
   //     TextView tvdecoded = (TextView)findViewById(R.id.tvdecoded);
    //    tvdecoded.setText("[DECODED]:\n" + new String(decodedBytes) + "\n");
        return  decodedBytes;
    }


}
