package computomovil.romarin.searchbtdevices;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

public class BToothConnectThread {

    private final BluetoothSocket socket;
    private final BluetoothDevice deviceSrv;
    private BluetoothAdapter mBluetoothAdapter;

    private InputStream in;
    private OutputStream out;
    private Activity act;

    private static final UUID MY_UUID = UUID.fromString("04c6093b-0000-1000-8000-00805f9b34fb");

    public BToothConnectThread(Activity act, BluetoothDevice server) {
        BluetoothSocket tmp = null;
        deviceSrv = server;
        this.act = act;
        try {
            tmp = deviceSrv.createRfcommSocketToServiceRecord(MY_UUID);

        } catch (IOException exp) {

        }
        socket = tmp;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void connectDevice() {
        mBluetoothAdapter.cancelDiscovery();
        try {
            socket.connect();
            out = socket.getOutputStream();
            in = socket.getInputStream();
        } catch (IOException connectException) {
            try {
                socket.close();
            } catch (IOException exp) {
            }
            return;
        }

    }

    public void pgDown() {
        try {
            out.write(1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void pgUp() {
        byte image[] = new byte[200];
        try {
            out.write(2);
//			in.read(image);
//			int l = image.length;
//			int a1 = in.available();
//			in.read(new byte[150]);
//			int a2 = in.available();
//			Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
//			ImageView imageView = (ImageView)act.findViewById(R.id.imageView1);
//			imageView.setImageBitmap(bmp);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
