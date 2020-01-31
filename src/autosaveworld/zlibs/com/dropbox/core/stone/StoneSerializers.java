package autosaveworld.zlibs.com.dropbox.core.stone;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import autosaveworld.zlibs.com.fasterxml.jackson.core.JsonGenerationException;
import autosaveworld.zlibs.com.fasterxml.jackson.core.JsonGenerator;
import autosaveworld.zlibs.com.fasterxml.jackson.core.JsonParseException;
import autosaveworld.zlibs.com.fasterxml.jackson.core.JsonParser;
import autosaveworld.zlibs.com.fasterxml.jackson.core.JsonToken;

public final class StoneSerializers {

    public static StoneSerializer<Long> uInt64() {
        return LongSerializer.INSTANCE;
    }

    public static StoneSerializer<Long> int64() {
        return LongSerializer.INSTANCE;
    }

    public static StoneSerializer<Long> uInt32() {
        return LongSerializer.INSTANCE;
    }

    public static StoneSerializer<Integer> int32() {
        return IntSerializer.INSTANCE;
    }

    public static StoneSerializer<Double> float64() {
        return DoubleSerializer.INSTANCE;
    }

    public static StoneSerializer<Float> float32() {
        return FloatSerializer.INSTANCE;
    }

    public static StoneSerializer<Boolean> boolean_() {
        return BooleanSerializer.INSTANCE;
    }

    public static StoneSerializer<byte []> binary() {
        return ByteArraySerializer.INSTANCE;
    }

    public static StoneSerializer<String> string() {
        return StringSerializer.INSTANCE;
    }

    public static StoneSerializer<Date> timestamp() {
        return DateSerializer.INSTANCE;
    }

    public static StoneSerializer<Void> void_() {
        return VoidSerializer.INSTANCE;
    }

    public static <T> StoneSerializer<T> nullable(StoneSerializer<T> underlying) {
        return new NullableSerializer<T>(underlying);
    }

    public static <T> StoneSerializer<List<T>> list(StoneSerializer<T> underlying) {
        return new ListSerializer<T>(underlying);
    }


    private static final class LongSerializer extends StoneSerializer<Long> {
        public static final LongSerializer INSTANCE = new LongSerializer();

        @Override
        public void serialize(Long value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeNumber(value);
        }

        @Override
        public Long deserialize(JsonParser p) throws IOException, JsonParseException {
            Long value = p.getLongValue();
            p.nextToken();
            return value;
        }
    }

    private static final class IntSerializer extends StoneSerializer<Integer> {
        public static final IntSerializer INSTANCE = new IntSerializer();

        @Override
        public void serialize(Integer value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeNumber(value);
        }

        @Override
        public Integer deserialize(JsonParser p) throws IOException, JsonParseException {
            Integer value = p.getIntValue();
            p.nextToken();
            return value;
        }
    }

    private static final class DoubleSerializer extends StoneSerializer<Double> {
        public static final DoubleSerializer INSTANCE = new DoubleSerializer();

        @Override
        public void serialize(Double value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeNumber(value);
        }

        @Override
        public Double deserialize(JsonParser p) throws IOException, JsonParseException {
            Double value = p.getDoubleValue();
            p.nextToken();
            return value;
        }
    }

    private static final class FloatSerializer extends StoneSerializer<Float> {
        public static final FloatSerializer INSTANCE = new FloatSerializer();

        @Override
        public void serialize(Float value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeNumber(value);
        }

        @Override
        public Float deserialize(JsonParser p) throws IOException, JsonParseException {
            Float value = p.getFloatValue();
            p.nextToken();
            return value;
        }
    }

    private static final class BooleanSerializer extends StoneSerializer<Boolean> {
        public static final BooleanSerializer INSTANCE = new BooleanSerializer();

        @Override
        public void serialize(Boolean value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeBoolean(value);
        }

        @Override
        public Boolean deserialize(JsonParser p) throws IOException, JsonParseException {
            Boolean value = p.getBooleanValue();
            p.nextToken();
            return value;
        }
    }

    private static final class ByteArraySerializer extends StoneSerializer<byte []> {
        public static final ByteArraySerializer INSTANCE = new ByteArraySerializer();

        @Override
        public void serialize(byte [] value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeBinary(value);
        }

        @Override
        public byte [] deserialize(JsonParser p) throws IOException, JsonParseException {
            byte [] value = p.getBinaryValue();
            p.nextToken();
            return value;
        }
    }

    private static final class StringSerializer extends StoneSerializer<String> {
        public static final StringSerializer INSTANCE = new StringSerializer();

        @Override
        public void serialize(String value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeString(value);
        }

        @Override
        public String deserialize(JsonParser p) throws IOException, JsonParseException {
            String value = getStringValue(p);
            p.nextToken();
            return value;
        }
    }

    private static final class DateSerializer extends StoneSerializer<Date> {
        public static final DateSerializer INSTANCE = new DateSerializer();

        @Override
        public void serialize(Date value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeString(Util.formatTimestamp(value));
        }

        @Override
        public Date deserialize(JsonParser p) throws IOException, JsonParseException {
            String text = getStringValue(p);
            p.nextToken();
            try {
                return Util.parseTimestamp(text);
            } catch (ParseException ex) {
                throw new JsonParseException(p, "Malformed timestamp: '" + text + "'", ex);
            }
        }
    }

    private static final class VoidSerializer extends StoneSerializer<Void> {
        public static final VoidSerializer INSTANCE = new VoidSerializer();

        @Override
        public void serialize(Void value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeNull();
        }

        @Override
        public Void deserialize(JsonParser p) throws IOException, JsonParseException {
            skipValue(p);
            return null;
        }
    }

    private static final class NullableSerializer<T> extends StoneSerializer<T> {
        private final StoneSerializer<T> underlying;

        public NullableSerializer(StoneSerializer<T> underlying) {
            this.underlying = underlying;
        }

        @Override
        public void serialize(T value, JsonGenerator g) throws IOException, JsonGenerationException {
            if (value == null) {
                g.writeNull();
            } else {
                underlying.serialize(value, g);
            }
        }

        @Override
        public T deserialize(JsonParser p) throws IOException, JsonParseException {
            if (p.getCurrentToken() == JsonToken.VALUE_NULL) {
                p.nextToken();
                return null;
            } else {
                return underlying.deserialize(p);
            }
        }
    }

    private static final class ListSerializer<T> extends StoneSerializer<List<T>> {
        private final StoneSerializer<T> underlying;

        public ListSerializer(StoneSerializer<T> underlying) {
            this.underlying = underlying;
        }

        @Override
        public void serialize(List<T> value, JsonGenerator g) throws IOException, JsonGenerationException {
            g.writeStartArray(value.size());
            for (T elem : value) {
                underlying.serialize(elem, g);
            }
            g.writeEndArray();
        }

        @Override
        public List<T> deserialize(JsonParser p) throws IOException, JsonParseException {
            expectStartArray(p);
            List<T> list = new ArrayList<T>();
            while (p.getCurrentToken() != JsonToken.END_ARRAY) {
                T elem = underlying.deserialize(p);
                list.add(elem);
            }
            expectEndArray(p);
            return list;
        }
    }
}
