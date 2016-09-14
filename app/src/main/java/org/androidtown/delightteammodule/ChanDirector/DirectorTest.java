package org.androidtown.delightteammodule.ChanDirector;

import android.content.Intent;

/**
 * Created by Chan on 2016-03-22.
 */
public class DirectorTest {

    Intent intent;
    static DirectorTest inst;

    private DirectorTest()
    {

    }

    public DirectorTest getInstance()
    {
        if(inst==null)
        {
            inst=new DirectorTest();
        }
        return inst;
    }



}
