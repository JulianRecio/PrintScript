package staticCodeAnalyser;

public class Config {
    private String namingConvention;
    private boolean printlnCondition;

    public void setNamingConvention(String namingConvention) {
        this.namingConvention = namingConvention;
    }

    public void setPrintlnCondition(Boolean printlnCondition) {
        this.printlnCondition = printlnCondition;
    }

    public String getNamingConvention() {
        return namingConvention;
    }

    public boolean getPrintlnCondition() {
        return printlnCondition;
    }
}
