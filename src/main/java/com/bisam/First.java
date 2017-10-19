package com.bisam;

public class First {

    private final Long id;

    public First(Long id) {
        this.id = id;
    }

    public String maMethode() throws Exception {
        if (id==2){
            return "tata";
        }
        return "toto";
    }
}
