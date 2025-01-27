package ramirez.inma.apppokemon.settingTab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Locale;
import java.util.Objects;

import ramirez.inma.apppokemon.LoginActivity;
import ramirez.inma.apppokemon.MainActivity;
import ramirez.inma.apppokemon.R;
import ramirez.inma.apppokemon.databinding.FragmentPokedexBinding;
import ramirez.inma.apppokemon.databinding.FragmentSettingsBinding;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.setting, rootKey);

        // Configurar cambio de idioma
        Preference languagePreference = findPreference("language_preference");
        if (languagePreference != null) {
            languagePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                // Actualiza el idioma
                setLocale(newValue.toString());
                return true;
            });
        }

        // Configurar switch eliminar o no pokemons
        Preference deletePreference = findPreference("delete_pokemon_preference");
        if (deletePreference != null) {
            // Escucha los cambios en el SwitchPreference
            deletePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean isEnabled = (boolean) newValue;

                // Guarda el estado en SharedPreferences
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
                sharedPreferences.edit().putBoolean("delete_pokemon_enabled", isEnabled).apply();

                return true; // Devuelve true para guardar el cambio en las preferencias

            });
        }

        //Configurar "Acerca de"
        Preference aboutPreference = findPreference("about_preference");
        if(aboutPreference != null){
            aboutPreference.setOnPreferenceClickListener(preference -> {
                showAboutDialog();
                return true;
            });
        }

        //Configurar "Cerrar Sesión"
        Preference logoutPreference = findPreference("logout_preference");
        if (logoutPreference != null) {
            logoutPreference.setOnPreferenceClickListener(preference -> {
                // Crear el AlertDialog de confirmación
                new AlertDialog.Builder(getContext())
                        .setMessage(getString(R.string.comprobar_cierre_sesion))
                        .setCancelable(false) // Para que no pueda cerrarse tocando fuera del diálogo
                        .setPositiveButton(getString(R.string.si), (dialog, id) -> {
                            // Si el usuario elige "Sí", cierra la sesión
                            logoutSession();
                        })
                        .setNegativeButton("No", null) // Si elige "No", simplemente cierra el diálogo
                        .show();
                return true;
            });
        }
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = requireContext().getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Reinicia la actividad para reflejar el cambio
        requireActivity().recreate();
    }

    private void showAboutDialog() {
        // Crear un AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.acerca_de)
                .setMessage(R.string.aplicaci_n_desarrollada_por_inma_versi_n_1_0_0)
                .setPositiveButton("OK", null) // Botón OK para cerrar el dialog
                .show();
    }

    private void logoutSession() {
        AuthUI.getInstance()
                .signOut(this.requireContext())
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(getContext(), getString(R.string.sesi_n_cerrada_correctamente), Toast.LENGTH_SHORT).show();
                        goToLogin();
                    }else{
                        Toast.makeText(getContext(), getString(R.string.error_al_cerrar_sesi_n), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void goToLogin() {
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        requireActivity().finish();
    }


}