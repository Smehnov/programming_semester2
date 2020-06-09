package special;

public class Word {
    private String ru;
    private String ua;
    private String sl;
    private String es;

    public Word(String ru, String ua, String sl, String es) {
        this.ru = ru;
        this.ua = ua;
        this.sl = sl;
        this.es = es;
    }

    public String getRu() {
        return ru;
    }

    public String getUa() {
        return ua;
    }

    public String getSl() {
        return sl;
    }

    public String getEs() {
        return es;
    }
}
