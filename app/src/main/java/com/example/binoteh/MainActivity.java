package com.example.binoteh;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        //map
        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mapView.getController().setCenter(new GeoPoint(55.664187, 37.598672));
        mapView.getController().setZoom(18);

        //1
        GeoPoint markerTwoPosition = new GeoPoint(55.664997, 37.598672);
        Marker markerTwo = new Marker(mapView);
        markerTwo.setPosition(markerTwoPosition);

        Bitmap iconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_mylocation_55dp);
        Drawable drawable = new BitmapDrawable(getResources(), iconBitmap);
        markerTwo.setIcon(drawable);
        mapView.getOverlays().add(markerTwo);

        //2
        GeoPoint markerPosition = new GeoPoint(55.664187, 37.598672);
        Marker marker = new Marker(mapView);
        marker.setPosition(markerPosition);

        Bitmap markerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_tracker_75dp);
        Bitmap overlayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.user_img);

        int desiredWidth = (int) (markerBitmap.getWidth() * 0.7);
        int desiredHeight = (int) (markerBitmap.getHeight() * 0.7);

        Bitmap scaledOverlayBitmap = Bitmap.createScaledBitmap(
                overlayBitmap,
                desiredWidth,
                desiredHeight,
                true);

        Bitmap combinedBitmap = Bitmap.createBitmap(
                markerBitmap.getWidth(),
                markerBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(combinedBitmap);

        Drawable markerDrawable = new BitmapDrawable(getResources(), markerBitmap);
        markerDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        markerDrawable.draw(canvas);

        int x = (markerBitmap.getWidth() - scaledOverlayBitmap.getWidth()) / 2;
        int y = (markerBitmap.getHeight() - scaledOverlayBitmap.getHeight()) / 3;
        canvas.drawBitmap(scaledOverlayBitmap, x, y, null);
        marker.setIcon(new BitmapDrawable(getResources(), combinedBitmap));
        mapView.getOverlays().add(marker);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}