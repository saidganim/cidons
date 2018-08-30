import javafx.util.Pair;

import java.io.Serializable;

public class _Message implements Serializable{
    public enum MessageType { tokenf2s, tokens2f, visited};
    public int waveNumber = 0;
    public MessageType messageType;
    public NodeInstance sender;
    public int id;
    private String _type(){
        switch(messageType) {
            case tokenf2s:
                return "TOKEN_F2S:";
            case tokens2f:
                return "TOKEN_S2F:";
            default:
                return "VISITED:";
        }
    }
    public String toString(){
        return  _type() + " FROM " + id;
    }
}
