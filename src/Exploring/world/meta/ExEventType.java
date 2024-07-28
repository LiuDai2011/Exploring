package Exploring.world.meta;

import mindustry.ctype.UnlockableContent;

public class ExEventType {
    private ExEventType() {}

    public static class ContentInfoInitEvent {
        public final UnlockableContent content;

        public ContentInfoInitEvent(UnlockableContent content) {
            this.content = content;
        }
    }
}
