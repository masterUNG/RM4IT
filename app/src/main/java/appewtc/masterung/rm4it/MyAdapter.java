package appewtc.masterung.rm4it;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by masterUNG on 3/25/16 AD.
 */
public class MyAdapter extends BaseAdapter{

    //Explicit
    private Context context;
    private String[] titleStrings;

    public MyAdapter(Context context, String[] titleStrings) {
        this.context = context;
        this.titleStrings = titleStrings;
    }   // Constructor

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}   // Main Class
