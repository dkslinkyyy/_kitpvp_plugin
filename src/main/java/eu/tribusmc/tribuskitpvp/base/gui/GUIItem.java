package eu.tribusmc.tribuskitpvp.base.gui;

import com.avaje.ebean.validation.NotNull;
import com.cryptomorin.xseries.XMaterial;

public class GUIItem {


    private final String internalName;
    private final XMaterial material;
    private IAction action;


    public GUIItem(String paramInternalName, XMaterial paramMaterial) {
        this.internalName = paramInternalName;
        this.material = paramMaterial;
    }


    @NotNull
    public GUIItem setAction(IAction action) {
        this.action = action;
        return this;
    }







}
