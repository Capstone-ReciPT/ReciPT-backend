package GoogleCloud;

public class MergeSTTS {
    public static void main(String[] args) throws Exception {
        STT stt = new STT();
        String text = stt.STT();

        TTS tts = new TTS();
        tts.TTS(text);
    }
}
