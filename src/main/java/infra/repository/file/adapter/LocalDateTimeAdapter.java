package infra.repository.file.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * TypeAdapter customizado para serializar e desserializar LocalDateTime com Gson.
 * Resolve problemas de compatibilidade entre LocalDateTime e JSON.
 */
public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    
    /**
     * Serializa um LocalDateTime para JSON.
     * 
     * @param out JsonWriter para escrever o valor
     * @param value LocalDateTime a ser serializado
     * @throws IOException se houver erro na escrita
     */
    @Override
    public void write(JsonWriter out, LocalDateTime value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.toString());
        }
    }
    
    /**
     * Desserializa um LocalDateTime do JSON.
     * 
     * @param in JsonReader para ler o valor
     * @return LocalDateTime desserializado
     * @throws IOException se houver erro na leitura
     */
    @Override
    public LocalDateTime read(JsonReader in) throws IOException {
        if (in.peek() == com.google.gson.stream.JsonToken.NULL) {
            in.nextNull();
            return null;
        } else {
            return LocalDateTime.parse(in.nextString());
        }
    }
} 