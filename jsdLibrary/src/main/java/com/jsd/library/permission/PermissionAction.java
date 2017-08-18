package com.jsd.library.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by tangshuai on 16/6/20.
 */
public class PermissionAction implements IPermissionAction {

    private Set<String> permissions = new HashSet<>();

    private Set<String> mustPermissions = new HashSet<>();

    Activity context;
    private Fragment fragment;
    private IDone iDone;
    private int actionId = (int) (Math.random() * 1000);


    PermissionAction(@NonNull Activity context) {
        this.context = context;
    }

    PermissionAction(@NonNull Fragment fragment) {
        this.fragment = fragment;
    }


    public PermissionAction addPermission(@NonNull String permission) {
        addPermission(permission, true);
        return this;
    }

    public PermissionAction addPermission(@NonNull String permission, boolean isMust) {
        if (ActivityCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(permission);
            if (isMust) {
                mustPermissions.add(permission);
            }
        }
        return this;
    }

    private Context getContext() {
        if (context != null) {
            return context;
        } else {
            return fragment.getActivity();
        }
    }

    @Override
    public Set<String> getRequestPermission() {
        return permissions;
    }

    @Override
    public Set<String> getMustPermission() {
        return mustPermissions;
    }

    @Override
    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public final void action(IDone done) {
        this.iDone = done;
        if (permissions.size() == 0) {
            done.done();
        } else {
            if (fragment != null) {
                fragment.requestPermissions(getRequestPermission().toArray(new String[permissions.size()]), getActionId());
            } else {
                ActivityCompat.requestPermissions(context, getRequestPermission().toArray(new String[permissions.size()]), getActionId());
            }
        }
    }

    private void done() {
        if (iDone != null) {
            iDone.done();
        }
    }

    private void unPermissions(String[] permissions) {
        if (iDone != null) {
            iDone.unPermissions(mustPermissions.toArray(new String[mustPermissions.size()]));
        }
    }

    @Override
    public void done(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        getRequestPermission().clear();
        Set<String> mustPermissions = getMustPermission();
        for (int i = 0; i < permissions.length; i++) {
            String permission = permissions[i];
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                mustPermissions.remove(permission);
            }
        }
        if (mustPermissions.size() == 0) {
            done();
        } else {
            unPermissions(mustPermissions.toArray(new String[mustPermissions.size()]));
        }
    }

}
