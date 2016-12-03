package search.engine.cse343;

public class Result {

    private String url;
    private String title;
    private String content;

    public Result(String title, String url){

        this(title, url, "");
    }

    public Result(String title, String url, String content){

        this.url = url;
        this.title = title;
        this.content = content;
    }

    public String getUrl(){

        return url;
    }

    public String getTitle(){

        return title;
    }

    public String getContent(){

        return content;
    }

    @Override
    public String toString(){

        return url;
    }

    @Override
    public boolean equals(Object o){

        if(o instanceof Result == false)

            return false;

        return getUrl().equals(((Result)o).getUrl());
    }
}
