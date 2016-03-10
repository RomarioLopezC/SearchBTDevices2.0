package computomovil.romarin.searchbtdevices;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class RemoteBluetooth extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private BToothConnectThread btConnectThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_bluetooth);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void viewList(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String address = data.getStringExtra("btDeviceSelectedAddress");
                // Get the BLuetoothDevice object

                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                btConnectThread = new BToothConnectThread(this, device);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//onActivityResult

    public void pageUp(View v) {
        btConnectThread.pgUp();
    }

    public void pageDown(View v) {
        btConnectThread.pgDown();
    }
}
