package com.example.junkdealer;

public class Waste {
    public int newspaper,paper,tins,plastic,cans;
    public String userId,address,mobile,key;
    public boolean picked,recycled;
    Waste(){}

    Waste(int n, int p, int t, int pl, int c, String u,String a,String m,String k){
        newspaper=n;
        paper=p;
        tins=t;
        plastic=pl;
        cans=c;
        userId=u;
        picked=false;
        address = a;
        mobile = m;
        recycled=false;
        key=k;
    }
}
