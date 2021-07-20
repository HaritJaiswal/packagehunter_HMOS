package github.nisrulz.projectpackagehunter.slice;

import github.nisrulz.projectpackagehunter.ResourceTable;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.AbilitySliceAnimator;
import ohos.aafwk.content.Intent;

public class AboutAbilitySlice extends AbilitySlice {
    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_about);

    }

    @Override
    protected void onBackPressed() {
        super.onBackPressed();
        getAbility().setAbilitySliceAnimator(new AbilitySliceAnimator().setDuration(10));
    }
}
