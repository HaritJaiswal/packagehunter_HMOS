package github.nisrulz.projectpackagehunter;

import github.nisrulz.projectpackagehunter.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.bundle.IBundleManager;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbility extends Ability {
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "MainAbility");
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
        if(verifySelfPermission("ohos.permission.GET_BUNDLE_INFO")!= IBundleManager.PERMISSION_GRANTED){
            HiLog.debug(LABEL,"not granted");
            if(canRequestPermission("ohos.permission.GET_BUNDLE_INFO")){
                requestPermissionsFromUser(new String[]{"ohos.permission.GET_BUNDLE_INFO"},1);
                HiLog.debug(LABEL,"2nd if loop");
            }
        }

    }
}
