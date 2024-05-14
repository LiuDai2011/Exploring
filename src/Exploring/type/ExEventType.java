package Exploring.type;

import Exploring.content.ResearchTree.ResearchNode;

public class ExEventType {
    public static class ExUnlockEvent {
        public final ResearchNode node;

        public ExUnlockEvent(ResearchNode node) {
            this.node = node;
        }
    }

    public static class ExResearchEvent {
        public final ResearchNode node;

        public ExResearchEvent(ResearchNode node) {
            this.node = node;
        }
    }
}
