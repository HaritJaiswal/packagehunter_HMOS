/*
 * Copyright (C) 2016 Nishant Srivastava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package github.nisrulz.packagehunterlib;

import ohos.agp.components.element.Element;
import ohos.app.Context;
import ohos.bundle.BundleInfo;
import ohos.bundle.IBundleManager;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.media.image.PixelMap;
import ohos.rpc.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class PackageHunter {
    private static final String TAG = "PackageHunter";
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, TAG);

    // Flags
    public static final int APPLICATIONS = 0;

    public static final int PACKAGES = 1;

    public static final int PERMISSIONS = 2;

    public static final int SERVICES = 3;

    public static final int RECEIVERS = 4;

    public static final int ACTIVITIES = 5;

    public static final int PROVIDERS = 6;

    private final Context context;

    private final IBundleManager bundleManager;

    public PackageHunter(Context context) {
        bundleManager = context.getBundleManager();
        this.context = context;
    }

    public String[] getActivitiesForPkg(String packageName){
        BundleInfo bundleInfo = getPkgInfo(packageName, IBundleManager.GET_BUNDLE_WITH_ABILITIES); // doubt here "GET_BUNDLE_WITH_ABILITIES"
        if (bundleInfo.abilityInfos != null) {
            ArrayList<String> data = new ArrayList<>(bundleInfo.abilityInfos.size());
            for (int i = 0; i < bundleInfo.	abilityInfos.size(); i++) {
                data.add(bundleInfo.abilityInfos.get(i).bundleName);
            }
            return data.toArray(new String[data.size()]);
        } else {
            return new String[0];
        }
    }

    public String getAppNameForPkg(String packageName) {
        BundleInfo bundleInfo = getPkgInfo(packageName, 0);
        return bundleInfo != null ? bundleInfo.appInfo.getName() : null;
    }

    public long getFirstInstallTimeForPkg(String packageName) {
        BundleInfo bundleInfo = getPkgInfo(packageName, 0);
        return bundleInfo != null ? bundleInfo.getInstallTime() : 0;
    }

    public PixelMap getIconForPkg(String packageName, String abilityname) {
        PixelMap icon = null;
        try {
            icon = bundleManager.getAbilityIcon(packageName,abilityname);
        } catch (RemoteException ex) {
            HiLog.error(LABEL, "Error",ex);
//            icon = context.getResourceManager().
//        (android.R.drawable.ic_menu_help, context.getTheme()); // don't know how to get icon.png here
        }
        return icon;
    }

    public ArrayList<PkgInfo> getInstalledPackages() throws RemoteException {
        HiLog.debug(LABEL,"Inside getInstalledPackages");
        System.out.println("Inside getInstalledPackages");
        return getAllPackagesInfo(PACKAGES);
    }

    public long getLastUpdatedTimeForPkg(String packageName) {
        BundleInfo bundleInfo = getPkgInfo(packageName, 0);
        return bundleInfo != null ? bundleInfo.getUpdateTime() : 0;
    }

    public String[] getPermissionForPkg(String packageName) {
        BundleInfo bundleInfo = getPkgInfo(packageName, IBundleManager.	GET_BUNDLE_WITH_REQUESTED_PERMISSION);
        if (bundleInfo.reqPermissions != null) {
            return bundleInfo.	getReqPermissions().toArray(new String[0]);
        } else {
            return new String[0];
        }
    }

//    public String[] getProvidersForPkg(String packageName) {
//        BundleInfo bundleInfo = getPkgInfo(packageName, IBundleManager.GET_PROVIDERS);
//        if (bundleInfo.providers != null) {
//            ArrayList<String> data = new ArrayList<>(bundleInfo.providers.length);
//            for (int i = 0; i < bundleInfo.providers.length; i++) {
//                data.add(bundleInfo.providers[i].name);
//            }
//            return data.toArray(new String[data.size()]);
//        } else {
//            return null;
//        }
//    }
//
//    public String[] getReceiverForPkg(String packageName) {
//        BundleInfo bundleInfo = getPkgInfo(packageName, IBundleManager.GET_RECEIVERS);
//        if (bundleInfo.receivers != null) {
//            ArrayList<String> data = new ArrayList<>(bundleInfo.receivers.length);
//            for (int i = 0; i < bundleInfo.receivers.length; i++) {
//                data.add(bundleInfo.receivers[i].name);
//            }
//            return data.toArray(new String[data.size()]);
//        } else {
//            return null;
//        }
//    }
//
//    public String[] getServicesForPkg(String packageName) {
//        BundleInfo bundleInfo = getPkgInfo(packageName, IBundleManager.GET_SERVICES);
//        if (bundleInfo.services != null) {
//            ArrayList<String> data = new ArrayList<>(bundleInfo.services.length);
//            for (int i = 0; i < bundleInfo.services.length; i++) {
//                data.add(bundleInfo.services[i].name);
//            }
//            return data.toArray(new String[data.size()]);
//        } else {
//            return null;
//        }
//    }

    public String getVersionCodeForPkg(String packageName) {
        BundleInfo bundleInfo = getPkgInfo(packageName, 0);
        return String.valueOf(bundleInfo.getVersionCode());
    }

    public String getVersionForPkg(String packageName) {
        BundleInfo bundleInfo = getPkgInfo(packageName, 0);
        return bundleInfo != null ? bundleInfo.getVersionName() : null;
    }

    public ArrayList<PkgInfo> searchInList(String query, int flag) throws RemoteException {
        String query_lowercase = query.toLowerCase();
        ArrayList<PkgInfo> pkgInfoArrayList = new ArrayList<>();
        ArrayList<PkgInfo> installed_packages_list = getAllPackagesInfo(flag);

        for (int i = 0; i < installed_packages_list.size(); i++) {
            PkgInfo pkgInfo = installed_packages_list.get(i);

            switch (flag) {
                case APPLICATIONS:
                    String appname = pkgInfo.getAppName();
                    if (appname != null && appname.toLowerCase().contains(query_lowercase)) {
                        pkgInfoArrayList.add(pkgInfo);
                    }
                    break;
                case PACKAGES:
                    String packagename = pkgInfo.getPackageName();
                    if (packagename != null && packagename.toLowerCase().contains(query_lowercase)) {
                        pkgInfoArrayList.add(pkgInfo);
                    }
                    break;
                case PERMISSIONS: {
                    String[] permissions = getPermissionForPkg(pkgInfo.getPackageName());
                    if (permissions != null) {
                        for (int j = 0; j < permissions.length; j++) {
                            if (permissions[j].toLowerCase().contains(query_lowercase)) {
                                pkgInfoArrayList.add(pkgInfo);
                                break;
                            }
                        }
                    }
                    break;
                }
//                case SERVICES: {
//                    String[] services = getServicesForPkg(pkgInfo.getPackageName());
//                    if (services != null) {
//                        for (int j = 0; j < services.length; j++) {
//                            if (services[j].toLowerCase().contains(query_lowercase)) {
//                                pkgInfoArrayList.add(pkgInfo);
//                                break;
//                            }
//                        }
//                    }
//                    break;
//                }
//                case RECEIVERS: {
//                    String[] recievers = getReceiverForPkg(pkgInfo.getPackageName());
//                    if (recievers != null) {
//                        for (int j = 0; j < recievers.length; j++) {
//                            if (recievers[j].toLowerCase().contains(query_lowercase)) {
//                                pkgInfoArrayList.add(pkgInfo);
//                                break;
//                            }
//                        }
//                    }
//                    break;
//                }
                case ACTIVITIES: {
                    String[] activities = getActivitiesForPkg(pkgInfo.getPackageName());
                    if (activities != null) {
                        for (int j = 0; j < activities.length; j++) {
                            if (activities[j].toLowerCase().contains(query_lowercase)) {
                                pkgInfoArrayList.add(pkgInfo);
                                break;
                            }
                        }
                    }
                    break;
                }
//                case PROVIDERS: {
//                    String[] providers = getProvidersForPkg(pkgInfo.getPackageName());
//                    if (providers != null) {
//                        for (int j = 0; j < providers.length; j++) {
//                            if (providers[j].toLowerCase().contains(query_lowercase)) {
//                                pkgInfoArrayList.add(pkgInfo);
//                                break;
//                            }
//                        }
//                    }
//                    break;
//                }
                default: {
                    String packagename1 = pkgInfo.getPackageName();
                    if (packagename1 != null && packagename1.toLowerCase().contains(query_lowercase)) {
                        pkgInfoArrayList.add(pkgInfo);
                    }
                    break;
                }
            }
        }

        return pkgInfoArrayList;
    }

    private ArrayList<PkgInfo> getAllPackagesInfo(int flag) throws RemoteException {
        ArrayList<PkgInfo> pkgInfoArrayList = new ArrayList<>();

        List<BundleInfo> installed_bundles_list;
        switch (flag) {
            case PACKAGES:
                HiLog.debug(LABEL,"Inside PACKAGES case, getAllPackagesInfo");
                System.out.println("Inside PACKAGES case, getAllPackagesInfo");
                installed_bundles_list = bundleManager.getBundleInfos(IBundleManager.GET_BUNDLE_WITH_ABILITIES);
                break;
            case PERMISSIONS:
                installed_bundles_list =
                        bundleManager.getBundleInfos(IBundleManager.GET_BUNDLE_WITH_REQUESTED_PERMISSION);
                break;
//            case SERVICES:
//                installed_bundles_list = bundleManager.getBundleInfos(IBundleManager.GET_SERVICES);
//                break;
//            case RECEIVERS:
//                installed_bundles_list = bundleManager.getBundleInfos(IBundleManager.GET_RECEIVERS);
//                break;
            case ACTIVITIES:
                installed_bundles_list =
                        bundleManager.getBundleInfos(IBundleManager.GET_BUNDLE_WITH_ABILITIES);
                break;
//            case PROVIDERS:
//                installed_bundles_list = bundleManager.getBundleInfos(IBundleManager.GET_PROVIDERS);
//                break;
            default:
                installed_bundles_list = bundleManager.getBundleInfos(IBundleManager.GET_BUNDLE_DEFAULT);
                break;
        }

        //get a list of installed packages.
        for (int i = 0; i < installed_bundles_list.size(); i++) {
            BundleInfo bundleInfo = installed_bundles_list.get(i);
//            if (!bundleInfo.name.contains("com.android.")) { // doubt name or packagename + "com.android."?
                pkgInfoArrayList.add(getPkgInfoModel(bundleInfo, flag));
//            }
        }
        return pkgInfoArrayList;
    }

    private BundleInfo getPkgInfo(String packageName, int flag) {
        try {
            return bundleManager.getBundleInfo(packageName, flag);
        }  catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    private PkgInfo getPkgInfoModel(BundleInfo bundleInfo, int flag) {
        // Always available
        PkgInfo newInfo = new PkgInfo();
        if (bundleInfo != null) {
            newInfo.setAppName(bundleInfo.appInfo.getName());
            newInfo.setPackageName(bundleInfo.name);
            newInfo.setVersionCode(bundleInfo.getVersionCode());
            newInfo.setVersionName(bundleInfo.getVersionName());
            newInfo.setLastUpdateTime(bundleInfo.getUpdateTime());
            newInfo.setFirstInstallTime(bundleInfo.getInstallTime());
        }

        return newInfo;
    }
}