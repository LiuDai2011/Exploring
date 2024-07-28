Events.on(EventType.ClientLoadEvent, e => {
    try {
        let toText = key => Core.bundle.format(key);

        Core.settings.defaults("ex-orui-stylesbasecolor", "8cb4c3");
        Core.settings.defaults("ex-orui-accentcolor", "ceeaf4");

        if (Vars.ui.settings != null) {
            let tip = extend(BaseDialog, "@ex-tip", {});
            tip.cont.add(toText("ex-auto-exit"));
            tip.buttons.button(toText("ex-ok"), () => {
                tip.hide();
                Core.app.exit();
            }).center().size(150, 50);
            Vars.ui.settings.addCategory(toText("ex-orui-set-name"), table => {
                table.pref(extend(SettingsMenuDialog.SettingsTable.Setting, "ex-orui-stylesbasecolor", {
                    add(st) {
                        let button = st.button(toText("ex-orui-color"), () => Vars.ui.picker.show(
                            Color.valueOf(Core.settings.getString("ex-orui-stylesbasecolor", "000000")),
                            color => {
                                Core.settings.put("ex-orui-stylesbasecolor", color.toString());
                                tip.show();
                            }
                        )).get();

                        button.left();
                        st.row();
                        st.add(this.title);
                        this.addDesc(st.add(button).left().padTop(3).get());
                        st.row();
                        // let prefTable = st.table().left().padTop(3).get();
                        // prefTable.label(() => this.title);
                        // prefTable.add(button);
                        // this.addDesc(prefTable);
                        // st.row();
                    }
                }));
            });
            Log.info("exploring-orui setting loaded.");
        }
    } catch (e) {
        Log.err(e);
    }
})