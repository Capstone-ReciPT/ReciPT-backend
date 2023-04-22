package samdasu.recipt.service.strategy;

public class ContextV1 {
    private ClickHeart clickHeart;

    public ContextV1(ClickHeart clickHeart) {
        this.clickHeart = clickHeart;
    }

    public void execute() {
        clickHeart.call();
        
    }
}
