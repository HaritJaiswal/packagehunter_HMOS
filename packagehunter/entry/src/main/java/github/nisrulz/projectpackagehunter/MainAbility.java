package github.nisrulz.projectpackagehunter;

import github.nisrulz.projectpackagehunter.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.bundle.IBundleManager;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

public class MainAbility extends Ability {
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "Harit");
    private  static final String PERMISSION_GET_BUNDLE_INFO ="ohos.permission.GET_BUNDLE_INFO";
    private  static final String PERMISSION_GET_BUNDLE_INFO_PRIVILEGED ="ohos.permission.GET_BUNDLE_INFO_PRIVILEGED";
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());
        if (verifySelfPermission(PERMISSION_GET_BUNDLE_INFO) != IBundleManager.PERMISSION_GRANTED) {
            // The application has not been granted the permission.
            if (canRequestPermission(PERMISSION_GET_BUNDLE_INFO)) {
                HiLog.debug(LABEL,"permissionInfo can be requested");
                // Check whether permission authorization can be implemented via a dialog box (at initial request or when the user has not chosen the option of "don't ask again" after rejecting a previous request).
                requestPermissionsFromUser(
                        new String[] {PERMISSION_GET_BUNDLE_INFO} , 1);
            } else {
                HiLog.debug(LABEL,"permissionInfo cannot be requested");
                // Display the reason why the application requests the permission and prompt the user to grant the permission.
            }
        }
        else{
            HiLog.debug(LABEL,"permissionInfo granted??");
        }


        if (verifySelfPermission(PERMISSION_GET_BUNDLE_INFO_PRIVILEGED) != IBundleManager.PERMISSION_GRANTED) {
            // The application has not been granted the permission.
            if (canRequestPermission(PERMISSION_GET_BUNDLE_INFO_PRIVILEGED)) {
                HiLog.debug(LABEL,"permissionInfoPrivileged can be requested");
                // Check whether permission authorization can be implemented via a dialog box (at initial request or when the user has not chosen the option of "don't ask again" after rejecting a previous request).
                requestPermissionsFromUser(
                        new String[] {PERMISSION_GET_BUNDLE_INFO_PRIVILEGED} , 2);
            } else {
                HiLog.debug(LABEL,"permissionInfoPrivileged cannot be requested");
                // Display the reason why the application requests the permission and prompt the user to grant the permission.
            }
        }
        else{
            HiLog.debug(LABEL,"permissionInfoPrivileged granted??");
        }

    }

    @Override
    public void onRequestPermissionsFromUserResult (int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode==1) {
            if(grantResults.length > 0 && grantResults[0]==IBundleManager.PERMISSION_GRANTED) {
                // The permission is granted.
                //Note: During permission check, an interface may be considered to have no required permissions due to time difference. Therefore, it is necessary to capture and process the exception thrown by such an interface.
                HiLog.debug(LABEL,"permission is granted");
            } else {
                // The permission request is rejected.
            }
            return;
        }
    }
}
