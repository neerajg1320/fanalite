package stream.flinkHelpers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.flink.annotation.PublicEvolving;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.util.Preconditions;

@PublicEvolving
public class CustomStringSchema implements DeserializationSchema<String>, SerializationSchema<String> {
    private static final long serialVersionUID = 1L;
    private transient Charset charset;

    public CustomStringSchema() {
        this(StandardCharsets.UTF_8);
    }

    public CustomStringSchema(Charset charset) {
        this.charset = (Charset)Preconditions.checkNotNull(charset);
    }

    public Charset getCharset() {
        return this.charset;
    }

    public String deserialize(byte[] message) {
        return new String(message, this.charset);
    }

    public boolean isEndOfStream(String nextElement) {
        return false;
    }

    public byte[] serialize(String element) {
        return element.getBytes(this.charset);
    }

    public TypeInformation<String> getProducedType() {
        return BasicTypeInfo.STRING_TYPE_INFO;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(this.charset.name());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String charsetName = in.readUTF();
        this.charset = Charset.forName(charsetName);
    }
}
