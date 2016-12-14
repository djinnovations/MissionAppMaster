package dj.appmastery.main.activities;

import android.app.Application;

/**
 * Created by DJphy on 28-09-2016.
 */
public class MyApplication extends Application {

    private static MyApplication ourInstance;
    public static MyApplication getInstance(){
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
    }


    public final String LICENSE_KEY_BILLING = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAptUZIP90EK2RQP+Zx/PHTq5RytlrLgqzQkzj+5kW5jz8rmY3IGRRY2T0ZPrxVsibJ5CZk3jqtyYZDAg9AFFKgcwpuCXZ/Up7hmPxSIc+TE1d2g5sOUS0iqkzxDR+uLMsUH+sm7zYlTWaahgV0gDZJaM5Zx++nUw0YcWJO14lUduVdzMP8pKcZPzaUjl8dC7mk1g2Y+tfkrall10Dn4guuRRG40ZAHRbm1iKJElTPZMdM/9YYksOcCYpVpYLSdYnUO45kXMoc7eyZIVUPYSXqkpNGNTydA6b3PEVDGJVi3xFagf5OyPJMsBK2HLYkLbuHpRNT5kXV/Sdo6Eqgou0IjwIDAQAB";
}
