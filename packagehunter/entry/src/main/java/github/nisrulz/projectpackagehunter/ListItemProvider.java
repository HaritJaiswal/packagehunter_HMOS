package github.nisrulz.projectpackagehunter;

import github.nisrulz.packagehunterlib.PkgInfo;
import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.*;

import java.util.List;

public class ListItemProvider  extends BaseItemProvider {

    private static final int ITEM_COUNT = 4;
    private final List<PkgInfo> pkgList;


    private final List<Integer> list;
    private final AbilitySlice slice;

    public ListItemProvider(List<Integer> list, AbilitySlice slice, List<PkgInfo> pkgInfos) {
        this.list = list;
        this.slice = slice;
        this.pkgList = pkgInfos;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (position < ITEM_COUNT) {
            return list.get(position);
        } else {
            return list.get(list.size() - 1);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Component getComponent(int position, Component convertComponent, ComponentContainer componentContainer) {
        final Component cpt;
        if (convertComponent == null) {
            cpt = LayoutScatter.getInstance(slice).parse(ResourceTable.Layout_list_item, null, false);
        } else {
            cpt = convertComponent;
        }
        int item = list.get(position);
        Text text = (Text) cpt.findComponentById(ResourceTable.Id_listItemText);
        text.setText(pkgList.get(item).toString());
        return cpt;
    }
}
