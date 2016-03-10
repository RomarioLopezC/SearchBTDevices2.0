package computomovil.romarin.searchbtdevices;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    BluetoothAdapter mBluetoothAdapter;
    ArrayList mArrayAdapter;
    ArrayAdapter adapter;
    BToothConnectThread btThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mArrayAdapter = new ArrayList();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mArrayAdapter);
        setListAdapter(adapter);

        checkAndTurnOnBT();
    }

    // Create a BroadcastReceiver for ACTION_FOUND
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                addBTDevice(device.getName() + "::" + device.getAddress());
                System.out.println("Dispositivo encontrado");
                btThread = new BToothConnectThread(this.getA, device);
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                if (mArrayAdapter.size() == 0) {
                    addBTDevice("Ningún dispositivo encontrado");
                }
                unregisterReceiver(mReceiver); // Don't forget to unregister during onDestroy
                mBluetoothAdapter.cancelDiscovery();
            }
        }
    };

    public void searchBTDevices(View v) {
        mArrayAdapter.clear();
        adapter.notifyDataSetChanged();

        // Register the BroadcastReceiver
        IntentFilter filterActionFound = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        // Register the BroadcastReceiver
        IntentFilter filterDiscoveryFinished = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filterActionFound); // Don't forget to unregister during onDestroy
        registerReceiver(mReceiver, filterDiscoveryFinished); // Don't forget to unregister during onDestroy
        mBluetoothAdapter.startDiscovery();
    }


    private void checkAndTurnOnBT() {
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            return;
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    private void addBTDevice(String device) {
        mArrayAdapter.add(device);
        adapter.notifyDataSetChanged();
    }
}
