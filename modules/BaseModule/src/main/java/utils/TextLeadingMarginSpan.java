package utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

/**
 * Created by summer on 2016/12/13.
 * 图文混排MarginSapn，确定距离图片的间距和文本多少行后折行
 */

public class TextLeadingMarginSpan implements LeadingMarginSpan.LeadingMarginSpan2  {

    private int mLines, mMargin;

    public TextLeadingMarginSpan(int lines, int margin){
        this.mLines = lines;
        this.mMargin = margin;
    }

    @Override
    public int getLeadingMarginLineCount() {
        return mLines;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        if (first){
            return mMargin;
        }
        return 0;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {

    }
}
