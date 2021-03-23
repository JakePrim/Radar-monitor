package com.prim.base_lib.log.printer;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.prim.base_lib.R;
import com.prim.base_lib.log.LogConfig;
import com.prim.base_lib.log.LogMo;
import com.prim.base_lib.log.LogType;
import com.prim.base_lib.log.ViewPrinterProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prim
 * @version 1.0.0
 * @desc 日志可视化打印器
 * @time 3/22/21 - 10:51 AM
 * @contact https://jakeprim.cn
 * @name HonorKings
 */
public class ViewPrinter implements LogPrinter {

    private RecyclerView recyclerView;
    private LogAdapter adapter;

    private ViewPrinterProvider printerProvider;

    public ViewPrinter(Activity activity) {
        FrameLayout rootView = activity.findViewById(android.R.id.content);
        recyclerView = new RecyclerView(activity);
        adapter = new LogAdapter(LayoutInflater.from(recyclerView.getContext()));
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
        printerProvider = new ViewPrinterProvider(rootView, recyclerView);
    }

    /**
     * 获取viewProvider 外部可以控制log视图的显示和隐藏
     */
    public ViewPrinterProvider getPrinterProvider() {
        return printerProvider;
    }

    @Override
    public void print(@NonNull LogConfig config, int level, String tag, @NonNull String printString) {
        //将log展示到recyclerview
        adapter.addItem(new LogMo(System.currentTimeMillis(), level, tag, printString));
        //滚动到指定的位置
        recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    private static class LogAdapter extends RecyclerView.Adapter<LogViewHolder> {

        private LayoutInflater inflater;
        private List<LogMo> logMos = new ArrayList<>();

        public LogAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        public void addItem(LogMo logMo) {
            logMos.add(logMo);
            notifyItemInserted(logMos.size() - 1);
        }

        @NonNull
        @Override
        public LogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            //加载布局
            View itemView = inflater.inflate(R.layout.log_item, viewGroup, false);
            return new LogViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull LogViewHolder logViewHolder, int i) {
            LogMo logMo = logMos.get(i);
            Log.e("TAG", "onBindViewHolder: " + logMo.tag);
            int typeColor = getTypeColor(logMo.level);
            logViewHolder.tagView.setTextColor(typeColor);
            logViewHolder.messageView.setTextColor(typeColor);

            logViewHolder.tagView.setText(logMo.tag);
            logViewHolder.messageView.setText(logMo.flattenedLog());
        }

        /**
         * 根据log级别获取不同的颜色
         *
         * @return
         */
        private int getTypeColor(int logLevel) {
            int color;
            switch (logLevel) {
                case LogType.V:
                    color = 0xffbbbbbb;
                    break;
                case LogType.D:
                    color = 0xffffffff;
                    break;
                case LogType.I:
                    color = 0xff6a8759;
                    break;
                case LogType.W:
                    color = 0xffbbb529;
                    break;
                case LogType.E:
                    color = 0xffff6b68;
                    break;
                default:
                    color = 0xffffff00;
                    break;
            }
            return color;
        }

        @Override
        public int getItemCount() {
            return logMos.size();
        }
    }

    private static class LogViewHolder extends RecyclerView.ViewHolder {

        TextView tagView;
        TextView messageView;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tagView = itemView.findViewById(R.id.tag);
            messageView = itemView.findViewById(R.id.message);
        }
    }
}
