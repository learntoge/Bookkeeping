package cn.shuzilearn.bookkeeping.pojo;

public class yiyan {
    private int id;
    private String hitokoto;
    private String from;
    private String fromWho;

    public yiyan(int id, String hitokoto, String from, String fromWho) {
        this.id = id;
        this.hitokoto = hitokoto;
        this.from = from;
        this.fromWho = fromWho;
    }

    public yiyan() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHitokoto() {
        return hitokoto;
    }

    public void setHitokoto(String hitokoto) {
        this.hitokoto = hitokoto;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getFromWho() {
        return fromWho;
    }

    public void setFromWho(String fromWho) {
        this.fromWho = fromWho;
    }

    @Override
    public String toString() {
        return "yiyan{" +
                "id=" + id +
                ", hitokoto='" + hitokoto + '\'' +
                ", from='" + from + '\'' +
                ", fromWho='" + fromWho + '\'' +
                '}';
    }
}
