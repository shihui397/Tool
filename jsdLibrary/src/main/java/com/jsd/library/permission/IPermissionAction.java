package com.jsd.library.permission;

import android.support.annotation.NonNull;

import java.util.Set;

/**
 * Created by tangshuai on 16/6/20.
 */
public interface IPermissionAction {

    IPermissionAction addPermission(@NonNull String permission);

    IPermissionAction addPermission(@NonNull String permission, boolean isMust);

    Set<String> getRequestPermission();

    Set<String> getMustPermission();

    int getActionId();

    void action(IDone done);

    void done(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults);

    interface IDone {

        void done();

        void unPermissions(String[] permissions);
    }
}
