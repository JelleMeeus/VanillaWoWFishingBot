package model;

/**
 * Created by jelle on 23-6-2017.
 */
public  class Lure {
    private String name;
    private Long duration;
    public Lure(String name, Long duration)
    {
        this.name = name;
        this.duration = duration;
    }
    public String getName() { return name;  }
    public Long getDuration() {
        return duration;
    }
    @Override
    public String toString()
    {
        return String.format("[%s] %smin",name,duration.toString());
    }
}

