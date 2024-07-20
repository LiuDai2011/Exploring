package Exploring.world.meta.info;

import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.world.modules.BlockModule;
import org.jetbrains.annotations.NotNull;

public class InfoStreamModule extends BlockModule {
    public final InfoStream stream = new InfoStream();

    public InfoStreamModule() {
    }

    public InfoStreamModule(float cap) {
        stream.cap = cap;
    }

    @Override
    public void write(Writes write) {
        write.f(stream.cap);
        write.i(stream.information.size);
        for (var e : stream.information) {
            write.b(e.type.id);
            write.f(e.amount);
        }
    }

    @Override
    public void read(Reads read) {
        stream.cap = read.f();
        int size = read.i();
        for (int i = 0; i < size; i++) {
            stream.information.add(new Info(InfoType.get(read.b()), read.f()));
        }
    }

    public void add(@NotNull Info info) {
        stream.add(info);
    }

    public void add(@NotNull InfoType type, float amount) {
        stream.add(type, amount);
    }

    @Override
    public String toString() {
        return "InfoStreamModule{" +
                "stream=" + stream +
                '}';
    }

    public enum InfoType {
        error(-1),
        normal(0),
        abyss(66),
        Void(Integer.MIN_VALUE);

        public static final InfoType[] all = values();

        public final int id;

        InfoType(int id) {
            this.id = id;
        }

        static InfoType get(int id) {
            for (var e : all) {
                if (e.id == id)
                    return e;
            }
            return error;
        }
    }

//    public record Info(InfoType type, float amount) {}

    public static class InfoStream {
        public final Seq<Info> information = new Seq<>();
        public float cap = 0f;

        public InfoStream() {
        }

        public InfoStream(float cap) {
            this.cap = cap;
        }

        public void add(InfoType type, float amount) {
            amount = Math.min(amount, cap - amount());
            boolean has = false;
            for (int i = 0; i < information.size; i++) {
                if (information.get(i).type() == type) {
                    has = true;
                    information.set(i, new Info(type, information.get(i).amount() + amount));
                }
            }
            if (!has) {
                information.add(new Info(type, amount));
            }
        }

        public void add(@NotNull Info info) {
            add(info.type(), info.amount());
        }

        public float amount() {
            float r = 0f;
            for (Info info : information) r += info.amount();
            return r;
        }

        public float get(InfoType type) {
            for (Info info : information) {
                if (info.type == type) return info.amount;
            }
            return 0f;
        }

        @Override
        public String toString() {
            return "InfoStream{" +
                    "information=" + info() +
                    ", cap=" + cap +
                    '}';
        }

        private String info() {
            StringBuilder builder = new StringBuilder("[");
            for (Info info : information) builder.append(info.type()).append("|").append(info.amount()).append(", ");
            builder.delete(builder.length() - 2, builder.length());
            return builder.append("]").toString();
        }
    }

    public static class Info {
        final InfoType type;
        final float amount;

        public Info(InfoType type, float amount) {
            this.type = type;
            this.amount = amount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Info info = (Info) o;

            if (Float.compare(info.amount, amount) != 0) return false;
            return type == info.type;
        }

        @Override
        public int hashCode() {
            int result = type.hashCode();
            result = 31 * result + (amount != +0.0f ? Float.floatToIntBits(amount) : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Info{" +
                    "type=" + type +
                    ", amount=" + amount +
                    '}';
        }

        public InfoType type() {
            return type;
        }

        public float amount() {
            return amount;
        }
    }
}
