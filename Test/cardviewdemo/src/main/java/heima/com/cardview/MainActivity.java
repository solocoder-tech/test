package heima.com.cardview;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {
    /**
     *
     * @param savedInstanceState
     * CardView的使用
     * 1.导入支持包
     * 2.在布局中CardView包裹要显示的内容
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
