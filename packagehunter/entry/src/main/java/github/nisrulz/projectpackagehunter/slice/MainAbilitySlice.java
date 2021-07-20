package github.nisrulz.projectpackagehunter.slice;

import github.nisrulz.packagehunterlib.PackageHunter;
import github.nisrulz.packagehunterlib.PkgInfo;
import github.nisrulz.projectpackagehunter.ListItemProvider;
import github.nisrulz.projectpackagehunter.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.ListContainer;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;
import ohos.rpc.RemoteException;

import java.util.ArrayList;
import java.util.List;

public class MainAbilitySlice extends AbilitySlice {
    private static final HiLogLabel LABEL = new HiLogLabel(HiLog.LOG_APP, 0x00201, "MAINABILITYSLICE");
    List<PkgInfo> pkgInfoArrayList;
    PackageHunter pkgHunter;
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);

        pkgInfoArrayList = new ArrayList<>();
        pkgHunter = new PackageHunter(getContext());
        try {
            pkgInfoArrayList = pkgHunter.getInstalledPackages();
            HiLog.debug(LABEL,"got the list");
            System.out.println("got the list");
        } catch (Exception e) {
            System.out.println(e.toString());
            HiLog.debug(LABEL,e.toString());
        }

        ListContainer listContainer = (ListContainer) findComponentById(ResourceTable.Id_list_container);
        List<Integer> list = getData();
        ListItemProvider listItemProvider = new ListItemProvider(list, this, pkgInfoArrayList);
        listContainer.setItemProvider(listItemProvider);

        listContainer.setItemClickedListener((listContainer1, component, i, l) -> clickTestItem(i));


    }

    private List<Integer> getData() {
        List<Integer> list = new ArrayList<>();
        for (int idx = 0; idx < pkgInfoArrayList.size(); idx++) {
            list.add(idx);
        }
        return list;
    }

    private void clickTestItem(int i) {
    }

    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
