package com.example.doanappmain;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doanappmain.dao.ThuThuDAO;
import com.example.doanappmain.modol.ThuThu;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doanappmain.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    TextView tvUser;
    View mHeaderView;
    ThuThuDAO thuThuDAO;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // enabling action bar app icon and behaving it as toggle button

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navPhieuMuon,R.id.nav_LoaiSach,R.id.nav_Sach,R.id.nav_ThanhVien,R.id.sub_Top,R.id.sub_DoanhThu,R.id.sub_AddUser,R.id.sub_Pass)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {
                if (destination.getId()==R.id.sub_Login){
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                    Toast.makeText(getApplicationContext(),"Đăng Xuất Thành Công",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        NavigationView nv = findViewById(R.id.nav_view);
        //show use in header
        mHeaderView = nv.getHeaderView(0);
        tvUser = mHeaderView.findViewById(R.id.tvUser);
        Intent i = getIntent();
        String user = i.getStringExtra("user");
        thuThuDAO = new ThuThuDAO(this);
        ThuThu thuThu = thuThuDAO.getID(user);
        String username = thuThu.hoTen;
        tvUser.setText(username+"");

        //admin co quyen  add user

        if (user.equalsIgnoreCase("admin")){
            nv.getMenu().findItem(R.id.sub_AddUser).setVisible(true);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}