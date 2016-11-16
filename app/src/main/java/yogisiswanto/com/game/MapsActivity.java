package yogisiswanto.com.game;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final int MY_PERMISSIONS_REQUEST = 99;//int bebas, maks 1 byte
    private GoogleMap mMap;
    private Marker mPosSekarang;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;

    //untuk rumah makan
    private GoogleMap wdMap;
    private GoogleMap PadangOmuda;
    private GoogleMap kantinT;
    private GoogleMap ssg;
    private GoogleMap martabak;
    private GoogleMap mKontrakan;

    //contoh
    private GoogleMap rumah;
    private LatLng poisisirumah;

    private LatLng Wadoel;
    private LatLng kantintujuh;
    private LatLng PadangO;
    private LatLng posisiSSG;
    private LatLng posisiMartabak;
    private LatLng Kontrakan;
    private LatLng posSekarang;
    private LatLng gedungIlkom;


    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    private void ambilLokasi() {
       /* mulai Android 6 (API 23), pemberian persmission
        dilakukan secara dinamik (tdk diawal)
        untuk jenis2 persmisson tertentu, termasuk lokasi
        */


        // cek apakah sudah diijinkan oleh user, jika belum tampilkan dialog
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST);
            return;
        }
        //set agar setiap update lokasi maka UI bisa diupdate
        //setiap ada update maka onLocationChanged akan dipanggil
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        //10 detik sekli minta lokasi (10000ms = 10 detik)
        mLocationRequest.setInterval(1000000);
        //tapi tidak boleh cepat dari 5 detik
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();
        createLocationRequest();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //untuk tempat makan
        wdMap = googleMap;
        PadangOmuda = googleMap;
        mKontrakan = googleMap;
        kantinT = googleMap;
        ssg = googleMap;
        martabak = googleMap;

        //contoh
        rumah = googleMap;

        // Add a marker in Sydney and move the camera
        /*LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        //batas lokasi UPI
        //urutan harus kiri bawah, kanan atas kotak
        LatLngBounds UPI = new LatLngBounds(
                new LatLng(-6.863273, 107.587212), new LatLng(-6.858025, 107.597839));


        //marker gedung ilkom
        gedungIlkom = new LatLng(-6.860418, 107.589889);
        mMap.addMarker(new MarkerOptions().position(gedungIlkom).title("Marker di GIK"));


        // Set kamera sesuai batas UPI
        //mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(UPI, 0));

        //contoh
        //marker kontrakan
        Kontrakan = new LatLng(-6.8663673, 107.5918008);
        mKontrakan.addMarker(new MarkerOptions().position(Kontrakan).title("Kontrakan"));

        poisisirumah = new LatLng(-6.9535871, 107.66612);
        rumah.addMarker(new MarkerOptions().position(poisisirumah).title("Rumah"));

        //marker lokasi tempat makan
        //marker warung jadoel
        Wadoel = new LatLng(-6.8649716, 107.5936292);
        wdMap.addMarker(new MarkerOptions().position(Wadoel).title("Warung Jadoel Cafe"));

        //marker Padang Omuda
        PadangO = new LatLng(-6.8658617, 107.5916296);
        PadangOmuda.addMarker(new MarkerOptions().position(PadangO).title("Rumah Makan Padang Omuda"));

        //marker kantin 77
        kantintujuh = new LatLng(-6.863624, 107.589367);
        kantinT.addMarker(new MarkerOptions().position(kantintujuh).title("Kantin 77"));

        //marker SSG
        posisiSSG = new LatLng(-6.8637613, 107.5898821);
        ssg.addMarker(new MarkerOptions().position(posisiSSG).title("SSGC"));

        //marker martabak
        posisiMartabak = new LatLng(-6.864408, 107.5921445);
        martabak.addMarker(new MarkerOptions().position(posisiMartabak).title("Martabak lezat Group Bandung"));


        // Set kamera sesuai batas UPI
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12); // offset dari edges

        //posisi sekarang
        posSekarang = new LatLng(-6.8663673, 107.5918008);

        mPosSekarang = mMap.addMarker(new MarkerOptions().position(posSekarang).title("PosSekarang").flat(true));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        //set kamera sesuai batas di Ilkom
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posSekarang, 17));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        ambilLokasi();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //muncul dialog & user memberikan reson (allow/deny), method ini akan dipanggil
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ambilLokasi();
            } else {
                //permssion tidak diberikan, tampilkan pesan
                AlertDialog ad = new AlertDialog.Builder(this).create();
                ad.setMessage("Tidak mendapat ijin, tidak dapat mengambil lokasi");
                ad.show();
            }
            return;
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop(){
        mGoogleApiClient.disconnect();
        super.onStop();
    }



    @Override
    public void onLocationChanged(Location location) {
        /*AlertDialog ad = new AlertDialog.Builder(this).create();
        ad.setMessage("update lokasi");
        ad.show();*/
        // Get instance of Vibrator from current Context

        //Mengerakan Kamera sesuai lokasi sekarang
        mPosSekarang.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()), 17));

        //untuk menyamakan lokasi
        /*if(Kontrakan.equals(new LatLng(location.getLatitude(),location.getLongitude()))){
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setMessage("Anda Berada disini");
            ad.show();
        }
        else if(Wadoel.equals(new LatLng(location.getLatitude(),location.getLongitude()))){
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setMessage("Anda Berada di Waroeng Jadul");
            ad.show();
        }
        else if(PadangO.equals(new LatLng(location.getLatitude(),location.getLongitude()))){
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setMessage("Anda Berada di Rumah Makan Padang Omuda");
            ad.show();
        }*/

        //untuk notifikasi getar
        double toKontrakan = distFrom((float)location.getLatitude(),(float)location.getLongitude(),getLat(Kontrakan),getLong(Kontrakan));
        double toPadangO = distFrom((float)location.getLatitude(),(float)location.getLongitude(),getLat(PadangO),getLong(PadangO));
        double toKantin = distFrom((float)location.getLatitude(),(float)location.getLongitude(),getLat(kantintujuh),getLong(kantintujuh));
        double toSSG = distFrom((float)location.getLatitude(),(float)location.getLongitude(),getLat(posisiSSG),getLong(posisiSSG));
        double toMartabak = distFrom((float)location.getLatitude(),(float)location.getLongitude(),getLat(posisiMartabak),getLong(posisiMartabak));
        double toWadoel = distFrom((float)location.getLatitude(),(float)location.getLongitude(),getLat(Wadoel),getLong(Wadoel));



        //contoh
        double toGIK = distFrom((float)location.getLatitude(),(float)location.getLongitude(),getLat(gedungIlkom),getLong(gedungIlkom));
        double toRumah = distFrom((float)location.getLatitude(),(float)location.getLongitude(),getLat(poisisirumah),getLong(poisisirumah));

        System.out.println("Jarak : " + toKontrakan);
        if(toKontrakan >= 0 && toKontrakan <=12){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            v.vibrate(400);

            System.out.println("Jarak : " + toKontrakan);
            mPosSekarang.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setMessage("Anda dekat lokasi kontrakan");
            ad.show();
        }

        if(toPadangO >= 0 && toPadangO <=12){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            v.vibrate(400);
            System.out.println("Jarak : " + toPadangO);
            mPosSekarang.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setMessage("Anda dekat lokasi Rumah Makan Padang Omuda");
            ad.show();
        }

        if(toKantin >= 0 && toKantin <=12){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            v.vibrate(400);
            System.out.println("Jarak : " + toKantin);
            mPosSekarang.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setMessage("Anda dekat lokasi Kantin 77");
            ad.show();
        }

        if(toSSG >= 0 && toSSG <=12){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            v.vibrate(400);
            System.out.println("Jarak : " + toSSG);
            mPosSekarang.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setMessage("Anda dekat lokasi SSGC");
            ad.show();
        }

        if(toMartabak >= 0 && toMartabak <=12){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            v.vibrate(400);
            System.out.println("Jarak : " + toMartabak);
            mPosSekarang.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setMessage("Anda dekat lokasi Martabak");
            ad.show();
        }

        if(toWadoel >= 0 && toWadoel <=12){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            v.vibrate(400);
            System.out.println("Jarak : " + toWadoel);
            mPosSekarang.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setMessage("Anda dekat lokasi Warung Jadoel");
            ad.show();
        }

        //contoh
        if(toGIK >= 0 && toGIK <=12){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            v.vibrate(400);
            System.out.println("Jarak : " + toGIK);
            mPosSekarang.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setMessage("Anda dekat lokasi GIK");
            ad.show();
        }

        if(toRumah >= 0 && toRumah <=12){
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 400 milliseconds
            v.vibrate(400);
            System.out.println("Jarak : " + toRumah);
            mPosSekarang.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setMessage("Anda dekat lokasi");
            ad.show();
        }
    }

    //fungsi untuk menghitung jarak
    public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    public float getLat(LatLng latln) {
        String s = latln.toString();
        String[] latLng = s.substring(10, s.length() - 1).split(",");
        String sLat = latLng[0];

        return Float.parseFloat(sLat);
    }

    public float getLong(LatLng latln) {
        String s = latln.toString();
        String[] latLng = s.substring(10, s.length() - 1).split(",");
        String sLong = latLng[1];

        return Float.parseFloat(sLong);
    }

}