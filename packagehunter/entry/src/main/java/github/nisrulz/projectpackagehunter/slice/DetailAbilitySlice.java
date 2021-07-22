package github.nisrulz.projectpackagehunter.slice;

import github.nisrulz.packagehunterlib.PackageHunter;
import github.nisrulz.projectpackagehunter.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.AbilitySliceAnimator;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Text;
import ohos.agp.transition.Transition;
import ohos.agp.transition.TransitionAlpha;
import ohos.media.image.PixelMap;

import java.util.ArrayList;
import java.util.Locale;

public class DetailAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_detail);

        String packageName = intent.getStringParam("data");
        PackageHunter packageHunter = new PackageHunter(this);

        String version = packageHunter.getVersionForPkg(packageName);
        String versionCode = packageHunter.getVersionCodeForPkg(packageName);
        String appName = packageHunter.getAppNameForPkg(packageName);
        long firstInstallTime = packageHunter.getFirstInstallTimeForPkg(packageName);
        long lastUpdateTime = packageHunter.getLastUpdatedTimeForPkg(packageName);
        PixelMap icon = packageHunter.getIconForPkg(packageName,getAbility().getAbilityName()); // 2nd argument here?

        String[] permissions = packageHunter.getPermissionForPkg(packageName);
        String[] activities = packageHunter.getActivitiesForPkg(packageName);
//        String[] services = packageHunter.getServicesForPkg(packageName);
//        String[] providers = packageHunter.getProvidersForPkg(packageName);
//        String[] receivers = packageHunter.getReceiverForPkg(packageName);

//        Text txt_version = (Text) findViewById(R.id.txtvw_vname);
//        Text txt_versioncode = (Text) findViewById(R.id.txtvw_vc);
//        Text txt_pkg = (Text) findViewById(R.id.txtvw_pkgname);
//        Text img_icon = (Text) findViewById(R.id.imgvw_icn);
//
//        Text txt_firsttime = (Text) findViewById(R.id.txtvw_firsttime);
//        Text txt_lastupdated = (Text) findViewById(R.id.txtvw_lastupdated);
//
//        img_icon.setImageDrawable(icon);
//        txt_version.setText("Version : " + version);
//        txt_versioncode.setText("Version Code : " + versionCode);
//        txt_pkg.setText(packageName);
//        txt_firsttime.setText("First Install Time : " + getFormattedUpTime(firstInstallTime));
//        txt_lastupdated.setText("Last Update Time : " + getFormattedUpTime(lastUpdateTime));
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle(appName);
//        }
//
//        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_detaillist);
        ArrayList<ElementInfo> elementInfoArrayList = new ArrayList<>();
        elementInfoArrayList.add(new ElementInfo("Permissions", permissions));
//        elementInfoArrayList.add(new ElementInfo("Services", services));
        elementInfoArrayList.add(new ElementInfo("Activities", activities));
//        elementInfoArrayList.add(new ElementInfo("Providers", providers));
//        elementInfoArrayList.add(new ElementInfo("Receivers", receivers));

//        RVDetailsAdapter adapter = new RVDetailsAdapter(elementInfoArrayList);
//        rv.setHasFixedSize(true);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//        rv.setAdapter(adapter);
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        getAbility().setAbilitySliceAnimator(new AbilitySliceAnimator().setDuration(10));
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//        finish();
    }

    private String getFormattedUpTime(long millis) {
        int sec = (int) (millis / 1000) % 60;
        int min = (int) ((millis / (1000 * 60)) % 60);
        int hr = (int) ((millis / (1000 * 60 * 60)) % 24);

        return String.format(Locale.US, "%02d:%02d:%02d", hr, min, sec);
    }
}
