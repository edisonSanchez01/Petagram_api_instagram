package com.edisondeveloper.petagram.Presentador.MainActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;

import androidx.fragment.app.Fragment;

import com.edisondeveloper.petagram.Modelo.ContratoTopFive;
import com.edisondeveloper.petagram.Modelo.Mascota;
import com.edisondeveloper.petagram.Vista.Fragments.MainFragment;
import com.edisondeveloper.petagram.Vista.Fragments.PerfilFragment;
import com.edisondeveloper.petagram.Vista.MainActivity.IMainActivityView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivityPresenter implements IMainActivityPresenter {

    private IMainActivityView iMainActivityView;

    public MainActivityPresenter(IMainActivityView iMainActivityView){
        this.iMainActivityView = iMainActivityView;
    }

    @Override
    public void generateTopFive(ArrayList<Mascota> listPets, ContentResolver resolver) {
        Collections.sort(listPets, new Comparator<Mascota>() {
            @Override
            public int compare(Mascota mascota, Mascota t1) {
                return Integer.valueOf(t1.getPuntuacion()).compareTo(mascota.getPuntuacion());
            }
        });

        Cursor cursor = resolver.query(ContratoTopFive.TablaTopFive.URI_TABLA_TOP_FIVE, new String[]{ContratoTopFive.TablaTopFive.COLUMN_ID, ContratoTopFive.TablaTopFive.COLUMN_NAME,
                ContratoTopFive.TablaTopFive.COLUMN_IMAGE, ContratoTopFive.TablaTopFive.COLUMN_RATING}, null, null, null);

        if(cursor.moveToNext()){
            resolver.delete(ContratoTopFive.TablaTopFive.URI_TABLA_TOP_FIVE, null, null);
        }
        cursor.close();
        for(int i=0; i<5; i++){
            Mascota mascotaTop = listPets.get(i);
            ContentValues values = new ContentValues();
            values.put(ContratoTopFive.TablaTopFive.COLUMN_NAME, mascotaTop.getNombre());
            values.put(ContratoTopFive.TablaTopFive.COLUMN_IMAGE, mascotaTop.getImage());
            values.put(ContratoTopFive.TablaTopFive.COLUMN_RATING, mascotaTop.getPuntuacion());
            resolver.insert(ContratoTopFive.TablaTopFive.URI_TABLA_TOP_FIVE, values);
        }
    }

    @Override
    public void generateListFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new MainFragment());
        fragments.add(new PerfilFragment());
        iMainActivityView.configViewPagerAdapter(fragments);
    }
}
